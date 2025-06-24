package io.enderdev.emergingtechnology.utils

import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.energy.IEnergyStorage

object EnergyUtils {
	fun spreadEnergy(world: World, pos: BlockPos, energyStorage: IEnergyStorage, vararg directions: EnumFacing) {
		for(direction in directions) {
			val te = world.getTileEntity(pos.offset(direction)) ?: continue
			if(!te.hasCapability(CapabilityEnergy.ENERGY, direction))
				continue
			val cap = te.getCapability(CapabilityEnergy.ENERGY, direction)!!
			energyStorage.extractEnergy(cap.receiveEnergy(energyStorage.energyStored, false), false)
			if(energyStorage.energyStored == 0)
				break
		}
	}

	open class ExtractOnlyEnergyStorage(private val energyStorage: IEnergyStorage) : IEnergyStorage {
		override fun receiveEnergy(maxReceive: Int, simulate: Boolean) = 0
		override fun extractEnergy(maxExtract: Int, simulate: Boolean) = energyStorage.extractEnergy(maxExtract, simulate)
		override fun getEnergyStored() = energyStorage.energyStored
		override fun getMaxEnergyStored() = energyStorage.maxEnergyStored
		override fun canExtract() = true
		override fun canReceive() = false
	}
}
