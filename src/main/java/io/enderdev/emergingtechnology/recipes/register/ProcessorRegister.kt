package io.enderdev.emergingtechnology.recipes.register

import io.enderdev.catalyx.utils.extensions.toIngredient
import io.enderdev.catalyx.utils.extensions.toOre
import io.enderdev.catalyx.utils.extensions.toStack
import io.enderdev.emergingtechnology.blocks.ModBlocks
import io.enderdev.emergingtechnology.items.ModItems
import io.enderdev.emergingtechnology.recipes.ProcessorRecipe
import net.minecraft.init.Items

class ProcessorRegister : AbstractRecipeRegister<ProcessorRecipe>() {
	companion object {
		val INSTANCE = ProcessorRegister()
	}

	override fun registerRecipes() {
		// plasticBlock
		arrayOf(ModItems.shreddedPlastic, ModItems.shreddedStarch).forEach {
			recipes.add(ProcessorRecipe(it.toIngredient(), 4, ModBlocks.plasticBlock.toStack()))
		}

		arrayOf(ModBlocks.shreddedPlasticBlock, ModBlocks.shreddedStarchBlock).forEach {
			recipes.add(ProcessorRecipe(it.toIngredient(), 1, ModBlocks.plasticBlock.toStack()))
		}

		// clearPlasticBlock
		recipes.add(ProcessorRecipe(ModItems.shreddedPlant.toIngredient(), 4, ModBlocks.clearPlasticBlock.toStack()))
		recipes.add(ProcessorRecipe(ModBlocks.shreddedPlantBlock.toIngredient(), 1, ModBlocks.clearPlasticBlock.toStack()))

		// paperPulp
		recipes.add(ProcessorRecipe(ModItems.paperWaste.toIngredient(), 1, ModItems.paperPulp.toStack()))

		// fertilizer
		arrayOf(ModItems.algae, Items.EGG, Items.BONE, Items.LEAD, Items.FISH).forEach {
			recipes.add(ProcessorRecipe(it.toIngredient(), 1, ModItems.fertilizer.toStack()))
		}

		recipes.add(ProcessorRecipe(Items.DYE.toIngredient(15), 1, ModItems.fertilizer.toStack()))
		recipes.add(ProcessorRecipe("blockWool".toOre(), 1, ModItems.fertilizer.toStack()))
	}
}
