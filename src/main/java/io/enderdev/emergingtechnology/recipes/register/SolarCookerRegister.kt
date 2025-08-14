package io.enderdev.emergingtechnology.recipes.register

import io.enderdev.catalyx.utils.extensions.toIngredient
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.recipes.SolarCookerRecipe
import net.minecraft.item.EnumAction
import net.minecraft.item.ItemFood
import net.minecraft.item.crafting.FurnaceRecipes

class SolarCookerRegister : AbstractRecipeRegister<SolarCookerRecipe>() {
	companion object {
		val INSTANCE = SolarCookerRegister()
	}

	override fun registerRecipes() {
		FurnaceRecipes.instance().smeltingList.forEach { (inp, out) ->
			if(inp.itemUseAction == EnumAction.EAT || (out.itemUseAction == EnumAction.EAT && out.item.registryName?.namespace == Tags.MODID))
				recipes.add(SolarCookerRecipe(inp.toIngredient(), out))
		}
	}
}
