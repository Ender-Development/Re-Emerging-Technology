package io.enderdev.emergingtechnology.tiles

import io.enderdev.catalyx.tiles.helper.IFluidTile
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.utils.CapabilityUtils
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.FluidTank
import net.minecraftforge.fluids.capability.CapabilityFluidHandler

class TileWaterFiller : TileEntity(), ITickable, IFluidTile {
	val fluidTank = object : FluidTank(EmergingTechnologyConfig.HYDROPONICS_MODULE.FILLER.fillerFluidTransferRate) {
		override fun canFillFluidType(fluid: FluidStack?) = fluid?.fluid == FluidRegistry.WATER
	}.apply {
		setTileEntity(this@TileWaterFiller)
		setCanFill(false)
		setCanDrain(true)
	}

	override fun update() {
		fluidTank.fillInternal(FluidStack(FluidRegistry.WATER, fluidTank.capacity), true)
		CapabilityUtils.spreadLiquid(world, pos, fluidTank, *EnumFacing.entries.toTypedArray())
	}

	override fun hasCapability(capability: Capability<*>, facing: EnumFacing?) =
		capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY

	override fun <T : Any?> getCapability(capability: Capability<T?>, facing: EnumFacing?): T? {
		if(capability != CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return null

		return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(fluidTank)
	}
}
