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
import io.enderdev.emergingtechnology.items.ItemEntityThing
import io.enderdev.emergingtechnology.items.ModItems
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.fluids.Fluid
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.FluidTank
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate

class TileTissueBioreactor : BaseMachineTile<Any>(EmergingTechnology.catalyxSettings), IEnergyTile by EnergyTileImpl(10000), IFluidTile {
	init {
		initInventoryCapability(1, 1)
		currentRecipe = 1
	}

	override fun initInventoryInputCapability() {
		input = object : TileStackHandler(inputSlots, this) {
			override fun isItemValid(slot: Int, stack: ItemStack) = stack.item === ModItems.syringeFull
		}
	}

	val inputTank = object : FluidTank(Fluid.BUCKET_VOLUME * 10) {
		override fun canFillFluidType(fluid: FluidStack?) = fluid?.fluid == FluidRegistry.WATER
	}.apply {
		setTileEntity(this@TileTissueBioreactor)
		setCanFill(true)
		setCanDrain(false)
	}

	override val fluidTanks: FluidHandlerConcatenate
		get() = FluidHandlerConcatenate(inputTank)

	override val recipeTime = EmergingTechnologyConfig.SYNTHETICS_MODULE.BIOREACTOR.bioreactorBaseTimeTaken
	override val energyPerTick = EmergingTechnologyConfig.SYNTHETICS_MODULE.BIOREACTOR.bioreactorEnergyUsage
	val fluidPerTick = EmergingTechnologyConfig.SYNTHETICS_MODULE.BIOREACTOR.bioreactorWaterUsage

	override fun updateRecipe() {}

	override fun onProcessComplete() {
		output.setOrIncrement(0, createOutput())
		input.decrementSlot(0, 1)
	}

	override fun onWorkTick() {
		energyStorage.extractEnergy(energyPerTick, false)
		inputTank.drainInternal(fluidPerTick, true)
		markDirtyGUI() // looks cool
	}

	override fun onIdleTick() {}

	override fun shouldTick() = true

	override fun shouldProcess() =
		!input[0].isEmpty
		&& (output[0].isEmpty || ItemEntityThing.getEntityId(output[0]) == ItemEntityThing.getEntityId(input[0]))
		&& energyStorage.energyStored >= energyPerTick
		&& inputTank.fluidAmount >= fluidPerTick

	override fun shouldResetProgress() = false

	override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
		super.writeToNBT(compound)
		compound.setTag("InputTankNBT", inputTank.writeToNBT(NBTTagCompound()))
		return compound
	}

	override fun readFromNBT(compound: NBTTagCompound) {
		super.readFromNBT(compound)
		inputTank.readFromNBT(compound.getCompoundTag("InputTankNBT"))
	}

	fun createOutput(): ItemStack =
		if(input[0].isEmpty || input[0].item !is ItemEntityThing) // sanity check
			ItemStack.EMPTY
		else
			ModItems.sample.getFor(ItemEntityThing.getEntityId(input[0]))
}
