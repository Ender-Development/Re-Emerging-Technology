package io.enderdev.emergingtechnology.tiles

import io.enderdev.catalyx.tiles.BaseMachineTile
import io.enderdev.catalyx.tiles.helper.EnergyTileImpl
import io.enderdev.catalyx.tiles.helper.IEnergyTile
import io.enderdev.catalyx.tiles.helper.TileStackHandler
import io.enderdev.catalyx.utils.extensions.canMergeWith
import io.enderdev.catalyx.utils.extensions.get
import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.recipes.ModRecipes
import io.enderdev.emergingtechnology.recipes.ShredderRecipe
import net.minecraft.item.ItemStack

class TileShredder : BaseMachineTile<ShredderRecipe>(EmergingTechnology.catalyxSettings), IEnergyTile by EnergyTileImpl(10000) {
	init {
		initInventoryCapability(1, 1)
	}

	override fun initInventoryInputCapability() {
		input = object : TileStackHandler(inputSlots, this) {
			override fun isItemValid(slot: Int, stack: ItemStack) =
				ModRecipes.shredderRecipes.recipes.any { it.input.test(stack) }
		}
	}

	override val recipeTime = EmergingTechnologyConfig.POLYMERS_MODULE.SHREDDER.shredderBaseTimeTaken
	override val energyPerTick = EmergingTechnologyConfig.POLYMERS_MODULE.SHREDDER.shredderEnergyBaseUsage

	override fun updateRecipe() {
		currentRecipe = if(input[0].isEmpty) null else ModRecipes.shredderRecipes.recipes.firstOrNull { it.input.test(input[0]) }
	}

	override fun onProcessComplete() {
		input.decrementSlot(0, 1) // Ingredients don't have any amount
		output.setOrIncrement(0, currentRecipe!!.output.copy())
	}

	override fun onWorkTick() {
		energyStorage.extractEnergy(energyPerTick, false)
		markDirtyGUI() // looks cool
	}

	override fun shouldTick() = true

	override fun shouldProcess() = currentRecipe!!.output.canMergeWith(output[0], true) && energyStorage.energyStored >= energyPerTick
}
