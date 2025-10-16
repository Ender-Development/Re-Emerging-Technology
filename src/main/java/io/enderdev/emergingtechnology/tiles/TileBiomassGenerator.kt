package io.enderdev.emergingtechnology.tiles

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.recipes.BiomassGeneratorRecipe
import io.enderdev.emergingtechnology.recipes.ModRecipes
import io.enderdev.emergingtechnology.utils.CapabilityUtils
import io.enderdev.emergingtechnology.utils.EnergyUtils
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import org.ender_development.catalyx.tiles.BaseMachineTile
import org.ender_development.catalyx.tiles.helper.EnergyTileImpl
import org.ender_development.catalyx.tiles.helper.IEnergyTile
import org.ender_development.catalyx.tiles.helper.TileStackHandler
import org.ender_development.catalyx.utils.extensions.canMergeWith
import org.ender_development.catalyx.utils.extensions.get

class TileBiomassGenerator : BaseMachineTile<BiomassGeneratorRecipe>(EmergingTechnology), IEnergyTile by EnergyTileImpl(10000) {
	init {
		initInventoryCapability(1, 1)
	}

	override fun initInventoryInputCapability() {
		input = object : TileStackHandler(inputSlots, this) {
			override fun isItemValid(slot: Int, stack: ItemStack) =
				ModRecipes.biomassGeneratorRecipes.recipes.any { it.input.test(stack) }
		}
	}

	override val recipeTime = EmergingTechnologyConfig.ELECTRICS_MODULE.BIOMASSGENERATOR.baseTimeTaken
	override val energyPerTick = EmergingTechnologyConfig.ELECTRICS_MODULE.BIOMASSGENERATOR.biomassEnergyGenerated

	override fun updateRecipe() {
		currentRecipe = if(input[0].isEmpty) null else ModRecipes.biomassGeneratorRecipes.recipes.firstOrNull { it.input.test(input[0]) }
	}

	override fun onProcessComplete() {
		input.decrementSlot(0, 1) // Ingredients don't have any amount
		output.setOrIncrement(0, currentRecipe!!.output.copy())
	}

	override fun onWorkTick() {
		energyStorage.receiveEnergy(energyPerTick, false)
		markDirtyGUI() // looks cool
	}

	override fun onIdleTick() {
		updateRecipe()
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

	override fun shouldTick() = true

	override fun shouldProcess() = currentRecipe!!.output.canMergeWith(output[0], true) && energyStorage.maxEnergyStored - energyStorage.energyStored >= energyPerTick

	override fun shouldResetProgress() = false
}
