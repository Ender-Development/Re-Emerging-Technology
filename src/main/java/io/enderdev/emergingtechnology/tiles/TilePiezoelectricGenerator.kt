package io.enderdev.emergingtechnology.tiles

import io.enderdev.catalyx.tiles.BaseTile
import io.enderdev.catalyx.tiles.helper.EnergyTileImpl
import io.enderdev.catalyx.tiles.helper.IEnergyTile
import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.utils.CapabilityUtils
import io.enderdev.emergingtechnology.utils.EnergyUtils
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.energy.CapabilityEnergy

class TilePiezoelectricGenerator : BaseTile(EmergingTechnology.catalyxSettings), IEnergyTile by EnergyTileImpl(1000), ITickable {
	val recipeTime = EmergingTechnologyConfig.ELECTRICS_MODULE.PIEZOELECTRIC.piezoelectricStepCooldown
	val energyPerTick = EmergingTechnologyConfig.ELECTRICS_MODULE.PIEZOELECTRIC.piezoelectricEnergyGenerated

	var cooldown = recipeTime

	override fun update() {
		if(cooldown > 0)
			--cooldown
		CapabilityUtils.spreadEnergy(world, pos, energyStorage, *EnumFacing.entries.toTypedArray())
	}

	val energyStorageWrapper = EnergyUtils.ExtractOnlyEnergyStorage(energyStorage)

	override fun hasCapability(capability: Capability<*>, facing: EnumFacing?) =
		capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing)

	override fun <T : Any> getCapability(capability: Capability<T>, facing: EnumFacing?) =
		if(capability == CapabilityEnergy.ENERGY)
			CapabilityEnergy.ENERGY.cast(energyStorageWrapper)
		else
			super.getCapability(capability, facing)

	fun steppedOn() {
		if(cooldown == 0) {
			cooldown = recipeTime
			energyStorage.receiveEnergy(energyPerTick, false)
		}
	}
}
