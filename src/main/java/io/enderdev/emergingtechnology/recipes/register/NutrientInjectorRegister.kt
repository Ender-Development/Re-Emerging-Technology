package io.enderdev.emergingtechnology.recipes.register

import io.enderdev.emergingtechnology.recipes.NutrientInjectorRecipe
import net.minecraft.init.Blocks
import net.minecraft.item.Item
import org.ender_development.catalyx.utils.extensions.toIngredient
import org.ender_development.catalyx.utils.extensions.toOre
import org.ender_development.catalyx.utils.extensions.toStack

class NutrientInjectorRegister : AbstractRecipeRegister<NutrientInjectorRecipe>() {
	companion object {
		val INSTANCE = NutrientInjectorRegister()
	}

	override fun registerRecipes() {
		recipes.add(NutrientInjectorRecipe("fertilizer".toOre(), Blocks.DIRT.toStack()))
		Item.getByNameOrId("alchemistry:fertilizer")?.let { // :blobcatcozy:
			recipes.add(NutrientInjectorRecipe(it.toIngredient(), Blocks.DIRT.toStack()))
		}
	}
}
