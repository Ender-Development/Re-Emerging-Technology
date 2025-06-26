package io.enderdev.emergingtechnology.recipes.register

import io.enderdev.catalyx.utils.extensions.toOre
import io.enderdev.catalyx.utils.extensions.toStack
import io.enderdev.emergingtechnology.items.ModItems
import io.enderdev.emergingtechnology.recipes.BiomassGeneratorRecipe

class BiomassGeneratorRegister : AbstractRecipeRegister<BiomassGeneratorRecipe>() {
	companion object {
		val INSTANCE = BiomassGeneratorRegister()
	}

	override fun registerRecipes() {
		recipes.add(BiomassGeneratorRecipe("biomass".toOre(), ModItems.biochar.toStack()))
	}
}
