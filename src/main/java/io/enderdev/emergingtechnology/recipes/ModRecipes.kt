package io.enderdev.emergingtechnology.recipes

import io.enderdev.emergingtechnology.recipes.register.ShredderRegister

object ModRecipes {
	val shredderRecipes = ShredderRegister.INSTANCE

	fun init() {
		shredderRecipes.registerRecipes()
	}
}
