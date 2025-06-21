package io.enderdev.emergingtechnology.recipes.register

import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.oredict.OreDictionary

abstract class AbstractRecipeRegister<T> {
	val recipes: MutableList<T> = mutableListOf()

	abstract fun registerRecipes()

	fun fluidExists(name: String): Boolean = FluidRegistry.isFluidRegistered(name)

	fun oreNotEmpty(ore: String) = OreDictionary.doesOreNameExist(ore) && OreDictionary.getOres(ore).isNotEmpty()
}
