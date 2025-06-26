package io.enderdev.emergingtechnology.tiles

import io.enderdev.catalyx.client.button.AbstractButton
import io.enderdev.catalyx.client.button.PauseButton
import io.enderdev.catalyx.client.button.RedstoneButton
import io.enderdev.catalyx.client.gui.BaseGuiTyped
import io.enderdev.catalyx.tiles.BaseTile
import io.enderdev.catalyx.tiles.helper.*
import io.enderdev.catalyx.utils.extensions.get
import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.items.ItemCircuit
import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fluids.Fluid
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.FluidTank
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate

class TileAlgorithmicOptimiser : BaseTile(EmergingTechnology.catalyxSettings), ITickable, IEnergyTile by EnergyTileImpl(5000), IItemTile, IFluidTile, IGuiTile, IButtonTile, BaseGuiTyped.IDefaultButtonVariables {
	override var isPaused = false
	override var needsRedstonePower = false

	init {
		initInventoryCapability(1, 0)
	}

	val inputTank = object : FluidTank(Fluid.BUCKET_VOLUME * 10) {
		override fun canFillFluidType(fluid: FluidStack?) = fluid?.fluid == FluidRegistry.WATER
	}.apply {
		setTileEntity(this@TileAlgorithmicOptimiser)
		setCanFill(true)
		setCanDrain(false)
	}

	override val fluidTanks: FluidHandlerConcatenate
		get() = FluidHandlerConcatenate(inputTank)

	override fun initInventoryInputCapability() {
		input = object : TileStackHandler(inputSlots, this) {
			override fun isItemValid(slot: Int, stack: ItemStack) = stack.item is ItemCircuit

			override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean): ItemStack {
				val result = super.insertItem(slot, stack, simulate)
				if(!simulate)
					validateAssignments()
				return result
			}

			override fun extractItem(slot: Int, amount: Int, simulate: Boolean): ItemStack {
				val result = super.extractItem(slot, amount, simulate)
				if(!simulate)
					validateAssignments()
				return result
			}
		}
	}

	val maxOptimiseDelay = 50
	var optimiseDelay = 1 // on world load, if conditions allow, (almost) instantly apply optimisations
	val energyPerTick = EmergingTechnologyConfig.ELECTRICS_MODULE.OPTIMISER.energyUsage
	val waterPerTick = EmergingTechnologyConfig.ELECTRICS_MODULE.OPTIMISER.waterUsage

	/*
	every couple ticks (probably 50), optimiser checks for optimisable tiles within its range, if it finds them, it calls a function that caches that data within the TE itself
	TEs then use that data, it expires after a bit more than 50 ticks though (52? to account for update order and stuff), unless it gets another packet

	packet has data:
	- energy, water, gas, speed allocation
	- blockPos of the optimiser itself
	- expiry
	 */

	val assignments = MutableOptimiserData(0, 0, 0, 0, pos, maxOptimiseDelay + 2)

	override fun update() {
		markDirtyGUIEvery(5)
		if(isPaused || assignments.sum() == 0 || needsRedstonePower != world.isBlockPowered(pos) || energyStorage.energyStored < energyPerTick || inputTank.fluidAmount < waterPerTick) {
			optimiseDelay = maxOptimiseDelay
			return
		}

		energyStorage.extractEnergy(energyPerTick, false)
		inputTank.drainInternal(waterPerTick, true)
		markDirtyGUI()

		if(--optimiseDelay != -1)
			return

		optimiseDelay = maxOptimiseDelay

		// TODO: temp impl, ignoring the configurable range - either implement the stupid impl from EMT or write something better
		val data = assignments.convert()
		for(side in EnumFacing.HORIZONTALS) {
			val te = world.getTileEntity(pos.offset(side))
			if(te is IOptimisableTile)
				te.optimise(data)
		}
	}

	override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
		super.writeToNBT(compound)
		compound.setBoolean("IsPaused", isPaused)
		compound.setBoolean("NeedsPower", needsRedstonePower)
		compound.setTag("InputTankNBT", inputTank.writeToNBT(NBTTagCompound()))
		compound.setInteger("EnergyAssignment", assignments.energy)
		compound.setInteger("WaterAssignment", assignments.water)
		compound.setInteger("GasAssignment", assignments.gas)
		compound.setInteger("RecipeTimeAssignment", assignments.recipeTime)
		return compound
	}

	override fun readFromNBT(compound: NBTTagCompound) {
		super.readFromNBT(compound)
		isPaused = compound.getBoolean("IsPaused")
		needsRedstonePower = compound.getBoolean("NeedsPower")
		inputTank.readFromNBT(compound.getCompoundTag("InputTankNBT"))
		assignments.energy = compound.getInteger("EnergyAssignment")
		assignments.water = compound.getInteger("WaterAssignment")
		assignments.gas = compound.getInteger("GasAssignment")
		assignments.recipeTime = compound.getInteger("RecipeTimeAssignment")
	}

	override fun handleButtonPress(button: AbstractButton) {
		when(button) {
			is PauseButton -> isPaused = !isPaused
			is RedstoneButton -> needsRedstonePower = !needsRedstonePower
			is AssignButton -> {
				assignments.add(button.resource, button.count)
				validateAssignments()
			}
		}
		markDirtyGUI()
	}

	fun validateAssignments() {
		if(assignments.sum() > getCores()) {
			assignments.energy = 0
			assignments.water = 0
			assignments.gas = 0
			assignments.recipeTime = 0
		}

		assignments.energy = assignments.energy.coerceIn(0, 5)
		assignments.water = assignments.water.coerceIn(0, 5)
		assignments.gas = assignments.gas.coerceIn(0, 5)
		assignments.recipeTime = assignments.recipeTime.coerceIn(0, 5)
	}

	fun getCores() = input[0].item.let {
		if(it is ItemCircuit) // basically check if empty and check if someone purposefully tried to put an invalid item into there via nbt
			it.cores
		else
			0
	}

	class AssignButton(x: Int, y: Int) : AbstractButton(x, y) {
		var count = 0
		lateinit var resource: OptimiserResource

		override val textureLocation = ResourceLocation(Tags.MODID, "textures/gui/container/algorithmic_optimiser_gui.png")

		override fun drawButton(mc: Minecraft, mouseX: Int, mouseY: Int, partialTicks: Float) {
			if(visible) {
				mc.textureManager.bindTexture(textureLocation)
				GlStateManager.color(1F, 1F, 1F)
				val v = if(count > 0) 16 else 0
				drawTexturedModalRect(x, y, 175, v, 16, 16)
			}
		}

		override fun readExtraData(buf: ByteBuf) {
			count = buf.readInt()
			resource = OptimiserResource.entries.toTypedArray()[buf.readInt()]
		}

		override fun writeExtraData(buf: ByteBuf) {
			buf.writeInt(count)
			buf.writeInt(resource.ordinal)
		}

		constructor(x: Int, y: Int, count: Int, resource: OptimiserResource) : this(x, y) {
			this.count = count
			this.resource = resource
		}
	}
}

// per EMT, these all reduce their respective values by 10% per value, this is reflected in getEffectiveX
class OptimiserData(val energy: Int, val water: Int, val gas: Int, val recipeTime: Int, val pos: BlockPos, var expiry: Int) {
	fun clone() = OptimiserData(energy, water, gas, recipeTime, pos, expiry)
}

class MutableOptimiserData(var energy: Int, var water: Int, var gas: Int, var recipeTime: Int, val pos: BlockPos, val expiry: Int) {
	fun convert() = OptimiserData(energy, water, gas, recipeTime, pos, expiry)
	fun add(resource: OptimiserResource, count: Int) {
		when(resource) {
			OptimiserResource.ENERGY -> energy += count
			OptimiserResource.WATER -> water += count
			OptimiserResource.GAS -> gas += count
			OptimiserResource.RECIPE_TIME -> recipeTime += count
		}
	}

	operator fun get(resource: OptimiserResource) =
		when(resource) {
			OptimiserResource.ENERGY -> energy
			OptimiserResource.WATER -> water
			OptimiserResource.GAS -> gas
			OptimiserResource.RECIPE_TIME -> recipeTime
		}

	fun sum() = energy + water + gas + recipeTime
}

enum class OptimiserResource {
	ENERGY, WATER, GAS, RECIPE_TIME;
}

// this is really just me wanting to inject a class into another class via Kotlin's delegation
interface IOptimisableTile {
	fun optimise(data: OptimiserData)
	fun getOptimisation(): OptimiserData?
	fun optimisationTick()

	fun getEffectiveEnergyUsage(base: Int): Int
	fun getEffectiveWaterUsage(base: Int): Int
	fun getEffectiveGasUsage(base: Int): Int
	fun getEffectiveRecipeTime(base: Int): Int
}

class OptimisableTileImpl : IOptimisableTile {
	var optimiserData: OptimiserData? = null

	override fun optimise(data: OptimiserData) {
		optimiserData = data.clone()
	}

	override fun getOptimisation() = optimiserData

	override fun optimisationTick() {
		optimiserData?.apply {
			if(--expiry == -1)
				optimiserData = null
		}
	}

	override fun getEffectiveEnergyUsage(base: Int) = getEffectiveX(base, OptimiserData::energy)
	override fun getEffectiveWaterUsage(base: Int) = getEffectiveX(base, OptimiserData::water)
	override fun getEffectiveGasUsage(base: Int) = getEffectiveX(base, OptimiserData::gas)
	override fun getEffectiveRecipeTime(base: Int) = getEffectiveX(base, OptimiserData::recipeTime)

	private fun getEffectiveX(base: Int, wrapper: (OptimiserData) -> Int) =
		if(optimiserData == null)
			base
		else
			base * (10 - wrapper(optimiserData!!)) / 10
}
