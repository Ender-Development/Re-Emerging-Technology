package io.enderdev.emergingtechnology.utils

import net.minecraftforge.energy.IEnergyStorage

object EnergyUtils {
	open class ExtractOnlyEnergyStorage(internal val energyStorage: IEnergyStorage) : IEnergyStorage {
		override fun receiveEnergy(maxReceive: Int, simulate: Boolean) = 0
		override fun extractEnergy(maxExtract: Int, simulate: Boolean) = energyStorage.extractEnergy(maxExtract, simulate)
		override fun getEnergyStored() = energyStorage.energyStored
		override fun getMaxEnergyStored() = energyStorage.maxEnergyStored
		override fun canExtract() = true
		override fun canReceive() = false
	}

	open class ReceiveOnlyEnergyStorage(internal val energyStorage: IEnergyStorage) : IEnergyStorage {
		override fun receiveEnergy(maxReceive: Int, simulate: Boolean) = energyStorage.receiveEnergy(maxReceive, simulate)
		override fun extractEnergy(maxExtract: Int, simulate: Boolean) = 0
		override fun getEnergyStored() = energyStorage.energyStored
		override fun getMaxEnergyStored() = energyStorage.maxEnergyStored
		override fun canExtract() = false
		override fun canReceive() = true
	}
}
