package io.enderdev.emergingtechnology.recipes.register

import io.enderdev.catalyx.utils.extensions.toIngredient
import io.enderdev.catalyx.utils.extensions.toOre
import io.enderdev.catalyx.utils.extensions.toStack
import io.enderdev.emergingtechnology.items.ModItems
import io.enderdev.emergingtechnology.recipes.NutrientInjectorRecipe
import net.minecraft.init.Blocks
import net.minecraft.item.Item

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
