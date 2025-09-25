package io.enderdev.emergingtechnology.tiles

import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.utils.CapabilityUtils
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate
import org.ender_development.catalyx.tiles.BaseTile.Companion.FLUID_CAP
import org.ender_development.catalyx.tiles.helper.IFluidTile
import org.ender_development.catalyx.utils.FluidTankUtils

class TileWaterFiller : TileEntity(), ITickable, IFluidTile {
	val fluidTank = FluidTankUtils.create(this, EmergingTechnologyConfig.HYDROPONICS_MODULE.FILLER.fillerFluidTransferRate, true, false, FluidRegistry.WATER) {}

	override val fluidTanks = FluidHandlerConcatenate(fluidTank)

	override fun update() {
		fluidTank.fillInternal(FluidStack(FluidRegistry.WATER, fluidTank.capacity), true)
		CapabilityUtils.spreadLiquid(world, pos, fluidTank, *EnumFacing.entries.toTypedArray())
	}

	override fun hasCapability(capability: Capability<*>, facing: EnumFacing?) =
		capability == FLUID_CAP

	override fun <T : Any?> getCapability(capability: Capability<T?>, facing: EnumFacing?): T? {
		if(capability != FLUID_CAP)
			return null

		return FLUID_CAP.cast(fluidTank)
	}
}
