package io.enderdev.emergingtechnology.recipes.register

import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.items.ModItems
import io.enderdev.emergingtechnology.recipes.Co2ScrubberRecipe
import net.minecraft.item.ItemStack
import org.ender_development.catalyx.utils.extensions.toIngredient

class Co2ScrubberRegister : AbstractRecipeRegister<Co2ScrubberRecipe>() {
	companion object {
		val INSTANCE = Co2ScrubberRegister()
	}

	lateinit var emptyRecipe: Co2ScrubberRecipe

	override fun registerRecipes() {
		recipes.add(Co2ScrubberRecipe(ItemStack.EMPTY.toIngredient(), ItemStack.EMPTY, 0).also {
			emptyRecipe = it
		})
		recipes.add(Co2ScrubberRecipe(ModItems.biochar.toIngredient(), ItemStack.EMPTY, EmergingTechnologyConfig.HYDROPONICS_MODULE.SCRUBBER.biocharBoostAmount))
	}
}
