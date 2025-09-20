package io.enderdev.emergingtechnology.recipes.register

import io.enderdev.emergingtechnology.items.ModItems
import io.enderdev.emergingtechnology.recipes.BiomassGeneratorRecipe
import org.ender_development.catalyx.utils.extensions.toOre
import org.ender_development.catalyx.utils.extensions.toStack

class BiomassGeneratorRegister : AbstractRecipeRegister<BiomassGeneratorRecipe>() {
	companion object {
		val INSTANCE = BiomassGeneratorRegister()
	}

	override fun registerRecipes() {
		recipes.add(BiomassGeneratorRecipe("biomass".toOre(), ModItems.biochar.toStack()))
	}
}
