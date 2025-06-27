package io.enderdev.emergingtechnology.recipes.register

import io.enderdev.catalyx.utils.extensions.toStack
import io.enderdev.emergingtechnology.items.ModItems
import io.enderdev.emergingtechnology.recipes.TissueScaffolderRecipe
import net.minecraft.init.Items

class TissueScaffolderRegister  : AbstractRecipeRegister<TissueScaffolderRecipe>() {
	companion object {
		val INSTANCE = TissueScaffolderRegister()
	}

	override fun registerRecipes() {
		recipes.add(TissueScaffolderRecipe("minecraft:cow", ModItems.syntheticSteak.toStack()))
		recipes.add(TissueScaffolderRecipe("minecraft:chicken", ModItems.syntheticChicken.toStack()))
		recipes.add(TissueScaffolderRecipe("minecraft:pig", ModItems.syntheticPorkchop.toStack()))
		recipes.add(TissueScaffolderRecipe("minecraft:horse", ModItems.syntheticLeather.toStack()))
		recipes.add(TissueScaffolderRecipe("minecraft:spider", ModItems.syntheticSilk.toStack()))
		recipes.add(TissueScaffolderRecipe("minecraft:slime", ModItems.syntheticSlime.toStack()))
		recipes.add(TissueScaffolderRecipe("minecraft:zombie", Items.ROTTEN_FLESH.toStack()))
	}
}
