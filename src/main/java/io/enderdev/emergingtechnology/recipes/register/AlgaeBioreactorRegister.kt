package io.enderdev.emergingtechnology.recipes.register

import io.enderdev.catalyx.utils.extensions.toIngredient
import io.enderdev.catalyx.utils.extensions.toOre
import io.enderdev.catalyx.utils.extensions.toStack
import io.enderdev.emergingtechnology.items.ModItems
import io.enderdev.emergingtechnology.recipes.AlgaeBioreactorRecipe

class AlgaeBioreactorRegister : AbstractRecipeRegister<AlgaeBioreactorRecipe>() {
	companion object {
		val INSTANCE = AlgaeBioreactorRegister()
	}

	override fun registerRecipes() {
		recipes.add(AlgaeBioreactorRecipe(ModItems.algae.toIngredient(), ModItems.algae.toStack(2)))
		recipes.add(AlgaeBioreactorRecipe("slimeball".toOre(), ModItems.algae.toStack(2)))
	}
}
