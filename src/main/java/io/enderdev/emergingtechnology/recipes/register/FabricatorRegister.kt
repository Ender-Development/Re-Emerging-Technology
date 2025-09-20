package io.enderdev.emergingtechnology.recipes.register

import io.enderdev.emergingtechnology.blocks.ModBlocks
import io.enderdev.emergingtechnology.items.ModItems
import io.enderdev.emergingtechnology.recipes.FabricatorRecipe
import org.ender_development.catalyx.utils.extensions.toStack

class FabricatorRegister : AbstractRecipeRegister<FabricatorRecipe>() {
	companion object {
		val INSTANCE = FabricatorRegister()
	}

	private var id = 0
	private val nextId: Int
		get() = id++

	override fun registerRecipes() {
		arrayOf(
			ModBlocks.plasticBlock.toStack(),
			ModBlocks.clearPlasticBlock.toStack(),
			ModItems.plasticRod.toStack(4),
			ModItems.plasticSheet.toStack(2),
			ModBlocks.aquaponicFrame.toStack(2),
			ModBlocks.bioplasticLadder.toStack(2),
			ModItems.plasticTissueScaffold.toStack(2),
			ModItems.nozzleComponent.toStack()).forEach {
			recipes.add(FabricatorRecipe(nextId, 1, it))
		}

		arrayOf(
			ModBlocks.machineCase.toStack(),
			ModItems.syringeEmpty.toStack(3),
			ModItems.turbine.toStack(3)
		).forEach {
			recipes.add(FabricatorRecipe(nextId, 2, it))
		}
	}
}

