package io.enderdev.emergingtechnology.recipes.register

import io.enderdev.emergingtechnology.items.ModItems
import io.enderdev.emergingtechnology.recipes.AlgaeBioreactorRecipe
import org.ender_development.catalyx.utils.extensions.toIngredient
import org.ender_development.catalyx.utils.extensions.toOre
import org.ender_development.catalyx.utils.extensions.toStack

class AlgaeBioreactorRegister : AbstractRecipeRegister<AlgaeBioreactorRecipe>() {
	companion object {
		val INSTANCE = AlgaeBioreactorRegister()
	}

	override fun registerRecipes() {
		recipes.add(AlgaeBioreactorRecipe(ModItems.algae.toIngredient(), ModItems.algae.toStack(2)))
		recipes.add(AlgaeBioreactorRecipe("slimeball".toOre(), ModItems.algae.toStack(2)))
	}
}
