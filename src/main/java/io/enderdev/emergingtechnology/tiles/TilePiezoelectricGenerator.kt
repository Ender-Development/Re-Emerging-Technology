package io.enderdev.emergingtechnology.tiles

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.utils.CapabilityUtils
import io.enderdev.emergingtechnology.utils.EnergyUtils
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraftforge.common.capabilities.Capability
import org.ender_development.catalyx.tiles.BaseTile
import org.ender_development.catalyx.tiles.helper.EnergyTileImpl
import org.ender_development.catalyx.tiles.helper.IEnergyTile

class TilePiezoelectricGenerator : BaseTile(EmergingTechnology), IEnergyTile by EnergyTileImpl(1000), ITickable {
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
		capability == ENERGY_CAP || super.hasCapability(capability, facing)

	override fun <T : Any> getCapability(capability: Capability<T>, facing: EnumFacing?) =
		if(capability == ENERGY_CAP)
			ENERGY_CAP.cast(energyStorageWrapper)
		else
			super.getCapability(capability, facing)

	fun steppedOn() {
		if(cooldown == 0) {
			cooldown = recipeTime
			energyStorage.receiveEnergy(energyPerTick, false)
		}
	}
}
