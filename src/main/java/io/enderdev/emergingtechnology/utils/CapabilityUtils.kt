package io.enderdev.emergingtechnology.utils

import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.energy.IEnergyStorage
import net.minecraftforge.fluids.IFluidTank
import org.ender_development.catalyx.tiles.BaseTile.Companion.ENERGY_CAP
import org.ender_development.catalyx.tiles.BaseTile.Companion.FLUID_CAP

object CapabilityUtils {
	fun <T> getCapabilities(world: World, pos: BlockPos, cap: Capability<T>, vararg directions: EnumFacing) : Iterable<T> {
		val ret = mutableListOf<T>()
		for(direction in directions) {
			val te = world.getTileEntity(pos.offset(direction)) ?: continue
			if(!te.hasCapability(cap, direction.opposite))
				continue
			ret.add(te.getCapability(cap, direction.opposite)!!)
		}
		return ret
	}

	fun spreadEnergy(world: World, pos: BlockPos, energyStorage: IEnergyStorage, vararg directions: EnumFacing) {
		for(cap in getCapabilities(world, pos, ENERGY_CAP, *directions)) {
			energyStorage.extractEnergy(cap.receiveEnergy(energyStorage.energyStored, false), false)
			if(energyStorage.energyStored == 0)
				break
		}
	}

	fun spreadLiquid(world: World, pos: BlockPos, fluidTank: IFluidTank, vararg directions: EnumFacing) {
		for(cap in getCapabilities(world, pos, FLUID_CAP, *directions)) {
			fluidTank.drain(cap.fill(fluidTank.fluid, true), true)
			if(fluidTank.fluidAmount == 0)
				break
		}
	}
}
