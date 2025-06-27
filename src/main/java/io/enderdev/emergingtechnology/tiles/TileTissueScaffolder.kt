package io.enderdev.emergingtechnology.tiles

import io.enderdev.catalyx.tiles.BaseMachineTile
import io.enderdev.catalyx.tiles.helper.EnergyTileImpl
import io.enderdev.catalyx.tiles.helper.IEnergyTile
import io.enderdev.catalyx.tiles.helper.TileStackHandler
import io.enderdev.catalyx.utils.extensions.canMergeWith
import io.enderdev.catalyx.utils.extensions.get
import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.items.ItemEntityThing
import io.enderdev.emergingtechnology.items.ModItems
import io.enderdev.emergingtechnology.recipes.ModRecipes
import io.enderdev.emergingtechnology.recipes.TissueScaffolderRecipe
import net.minecraft.item.ItemStack

class TileTissueScaffolder : BaseMachineTile<TissueScaffolderRecipe>(EmergingTechnology.catalyxSettings), IEnergyTile by EnergyTileImpl(10000), IOptimisableTile by OptimisableTileImpl() {
	init {
		initInventoryCapability(2, 1)
	}

	override fun initInventoryInputCapability() {
		input = object : TileStackHandler(inputSlots, this) {
			override fun isItemValid(slot: Int, stack: ItemStack) = stack.item === if(slot == 0) ModItems.plasticTissueScaffold else ModItems.sample
		}
	}

	override val recipeTime: Int
		get() = getEffectiveRecipeTime(EmergingTechnologyConfig.SYNTHETICS_MODULE.SCAFFOLDER.scaffolderBaseTimeTaken)
	override val energyPerTick: Int
		get() = getEffectiveEnergyUsage(EmergingTechnologyConfig.SYNTHETICS_MODULE.SCAFFOLDER.scaffolderEnergyUsage)

	override fun onIdleTick() {
		updateRecipe()
		optimisationTick()
	}

	override fun updateRecipe() {
		currentRecipe = ModRecipes.tissueScaffolderRecipes.recipes.firstOrNull { it.entityId == ItemEntityThing.getEntityId(input[1]) }
	}

	override fun onProcessComplete() {
		input.decrementSlot(0, 1)
		input.decrementSlot(1, 1)
		output.setOrIncrement(0, currentRecipe!!.output.copy())
	}

	override fun onWorkTick() {
		energyStorage.extractEnergy(energyPerTick, false)
		markDirtyGUI() // looks cool
	}

	override fun shouldTick() = true

	override fun shouldProcess() = !input[0].isEmpty && currentRecipe!!.output.canMergeWith(output[0], true) && energyStorage.energyStored >= energyPerTick

	override fun shouldResetProgress() = false
}
