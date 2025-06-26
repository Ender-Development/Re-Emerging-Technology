package io.enderdev.emergingtechnology.tiles

import io.enderdev.catalyx.tiles.BaseMachineTile
import io.enderdev.catalyx.tiles.helper.EnergyTileImpl
import io.enderdev.catalyx.tiles.helper.IEnergyTile
import io.enderdev.catalyx.tiles.helper.IFluidTile
import io.enderdev.catalyx.tiles.helper.TileStackHandler
import io.enderdev.catalyx.utils.extensions.canMergeWith
import io.enderdev.catalyx.utils.extensions.get
import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.recipes.ModRecipes
import io.enderdev.emergingtechnology.recipes.ProcessorRecipe
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.fluids.Fluid
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.FluidTank
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate

class TileProcessor : BaseMachineTile<ProcessorRecipe>(EmergingTechnology.catalyxSettings), IFluidTile, IEnergyTile by EnergyTileImpl(10000), IOptimisableTile by OptimisableTileImpl() {
	init {
		initInventoryCapability(1, 1)
	}

	val inputTank = object : FluidTank(Fluid.BUCKET_VOLUME * 10) {
		override fun canFillFluidType(fluid: FluidStack?) = fluid?.fluid == FluidRegistry.WATER
	}.apply {
		setTileEntity(this@TileProcessor)
		setCanFill(true)
		setCanDrain(false)
	}

	override val fluidTanks: FluidHandlerConcatenate
		get() = FluidHandlerConcatenate(inputTank)

	override fun initInventoryInputCapability() {
		input = object : TileStackHandler(inputSlots, this) {
			override fun isItemValid(slot: Int, stack: ItemStack) =
				ModRecipes.processorRecipe.recipes.any { it.input.test(stack) }
		}
	}

	override val recipeTime: Int
		get() = getEffectiveRecipeTime(EmergingTechnologyConfig.POLYMERS_MODULE.PROCESSOR.processorBaseTimeTaken)
	override val energyPerTick: Int
		get() = getEffectiveEnergyUsage(EmergingTechnologyConfig.POLYMERS_MODULE.PROCESSOR.processorEnergyBaseUsage)
	val fluidPerTick: Int
		get() = getEffectiveWaterUsage(EmergingTechnologyConfig.POLYMERS_MODULE.PROCESSOR.processorWaterBaseUsage)

	override fun onIdleTick() {
		updateRecipe()
		optimisationTick()
	}

	override fun updateRecipe() {
		currentRecipe = if(input[0].isEmpty) null else ModRecipes.processorRecipe.recipes.firstOrNull { it.inputCount <= input[0].count && it.input.test(input[0]) }
	}

	override fun onProcessComplete() {
		input.decrementSlot(0, currentRecipe!!.inputCount)
		output.setOrIncrement(0, currentRecipe!!.output.copy())
	}

	override fun onWorkTick() {
		energyStorage.extractEnergy(energyPerTick, false)
		inputTank.drainInternal(fluidPerTick, true)
		markDirtyGUI() // looks cool
	}

	override fun shouldTick() = true

	override fun shouldProcess() =
		currentRecipe!!.output.canMergeWith(output[0], true) && energyStorage.energyStored >= energyPerTick && inputTank.fluidAmount >= fluidPerTick

	override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
		super.writeToNBT(compound)
		compound.setTag("InputTankNBT", inputTank.writeToNBT(NBTTagCompound()))
		return compound
	}

	override fun readFromNBT(compound: NBTTagCompound) {
		super.readFromNBT(compound)
		inputTank.readFromNBT(compound.getCompoundTag("InputTankNBT"))
	}
}
