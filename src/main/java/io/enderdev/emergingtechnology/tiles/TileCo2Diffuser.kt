package io.enderdev.emergingtechnology.tiles

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.fluids.ModFluids
import io.enderdev.emergingtechnology.items.ItemNozzle
import io.enderdev.emergingtechnology.utils.PlantUtils
import net.minecraft.block.IGrowable
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.fluids.Fluid
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate
import org.ender_development.catalyx.client.button.AbstractButtonWrapper
import org.ender_development.catalyx.client.button.PauseButtonWrapper
import org.ender_development.catalyx.client.button.RedstoneButtonWrapper
import org.ender_development.catalyx.client.gui.BaseGuiTyped
import org.ender_development.catalyx.tiles.BaseTile
import org.ender_development.catalyx.tiles.helper.*
import org.ender_development.catalyx.utils.FluidTankUtils
import org.ender_development.catalyx.utils.extensions.get

class TileCo2Diffuser : BaseTile(EmergingTechnology.catalyxSettings), ITickable, IGuiTile, IItemTile, IButtonTile, BaseGuiTyped.IDefaultButtonVariables, IEnergyTile by EnergyTileImpl(5000), IFluidTile {
	init {
		initInventoryCapability(1, 0)
	}

	override fun initInventoryInputCapability() {
		input = object : TileStackHandler(inputSlots, this) {
			override fun isItemValid(slot: Int, stack: ItemStack) = stack.item is ItemNozzle
			override fun getStackLimit(slot: Int, stack: ItemStack) = 1
			override fun getSlotLimit(slot: Int) = 1
		}
	}

	override var isPaused = false
	override var needsRedstonePower = false

	val gasTank = FluidTankUtils.create(this, Fluid.BUCKET_VOLUME * 10, true, false, ModFluids.co2, onContentsChangedCallback = this::markDirtyGUI)

	override val fluidTanks = FluidHandlerConcatenate(gasTank)

	val energyPerTick = EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.diffuserEnergyBaseUsage
	val gasPerPlant = EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.diffuserGasBaseUsage
	
	override fun update() {
		if(world.isRemote)
			return

		markDirtyGUIEvery(5)
		
		if(input[0].isEmpty || energyStorage.energyStored < energyPerTick || gasTank.fluidAmount < gasPerPlant)
			return
		
		val nozzle = input[0].item
		if(nozzle !is ItemNozzle) // should be guaranteed but sanity check
			return
		
		val range = EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.diffuserBaseRange * nozzle.range
		val probability = EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.diffuserBaseBoostProbability * nozzle.boost
		var boostedAnything = false

		EnumFacing.HORIZONTALS.forEach { direction ->
			for(it in 1..range) {
				val pos = pos.offset(direction, it)
				val state = world.getBlockState(pos)
				if(state.block !is IGrowable)
					if(state.block.isAir(state, world, pos))
						continue
					else
						break

				if(PlantUtils.isPlantGrown(state, world, pos))
					continue

				if(world.rand.nextInt(101) < probability) {
					state.block.randomTick(world, pos, state, world.rand)
					boostedAnything = true
					gasTank.drainInternal(gasPerPlant, true)
					if(gasTank.fluidAmount < gasPerPlant)
						return@forEach
				}
			}
		}

		if(boostedAnything) {
			energyStorage.extractEnergy(energyPerTick, false)
			markDirtyGUI()
		}
	}

	override fun hasCapability(capability: Capability<*>, facing: EnumFacing?) =
		(facing == null || facing == EnumFacing.UP || facing == EnumFacing.DOWN) && super.hasCapability(capability, facing)

	override fun <T : Any> getCapability(capability: Capability<T>, facing: EnumFacing?) =
		if(facing == null || facing == EnumFacing.UP || facing == EnumFacing.DOWN)
			super.getCapability(capability, facing)
		else
			null

	override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
		super.writeToNBT(compound)
		compound.setTag("InputTankNBT", gasTank.writeToNBT(NBTTagCompound()))
		return compound
	}

	override fun readFromNBT(compound: NBTTagCompound) {
		super.readFromNBT(compound)
		gasTank.readFromNBT(compound.getCompoundTag("InputTankNBT"))
	}

	override fun handleButtonPress(button: AbstractButtonWrapper) {
		when(button) {
			is RedstoneButtonWrapper -> needsRedstonePower = !needsRedstonePower
			is PauseButtonWrapper -> isPaused = !isPaused
		}
	}
}
