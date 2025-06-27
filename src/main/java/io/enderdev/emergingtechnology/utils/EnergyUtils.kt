package io.enderdev.emergingtechnology.utils

import net.minecraftforge.energy.IEnergyStorage

object EnergyUtils {

	open class ExtractOnlyEnergyStorage(private val energyStorage: IEnergyStorage) : IEnergyStorage {
		override fun receiveEnergy(maxReceive: Int, simulate: Boolean) = 0
		override fun extractEnergy(maxExtract: Int, simulate: Boolean) = energyStorage.extractEnergy(maxExtract, simulate)
		override fun getEnergyStored() = energyStorage.energyStored
		override fun getMaxEnergyStored() = energyStorage.maxEnergyStored
		override fun canExtract() = true
		override fun canReceive() = false
	}
}
