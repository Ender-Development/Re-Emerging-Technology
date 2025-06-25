package io.enderdev.emergingtechnology.recipes.register

import io.enderdev.catalyx.utils.extensions.toIngredient
import io.enderdev.catalyx.utils.extensions.toOre
import io.enderdev.catalyx.utils.extensions.toStack
import io.enderdev.emergingtechnology.blocks.ModBlocks
import io.enderdev.emergingtechnology.items.ModItems
import io.enderdev.emergingtechnology.recipes.ShredderRecipe
import net.minecraft.block.Block
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.crafting.Ingredient

class ShredderRegister : AbstractRecipeRegister<ShredderRecipe>() {
	companion object {
		val INSTANCE = ShredderRegister()
	}

	override fun registerRecipes() {
		// Plastic
		// TODO - ModBlocks.frame, plasticBlock, clearPlasticBlock, light, hydroponic, machineCase, processor
		arrayOf<Any?>(ModItems.plasticSheet, ModItems.plasticRod, ModItems.plasticWaste, ModItems.bulbRed, ModItems.bulbGreen, ModItems.bulbBlue, ModItems.bulbPurple, ModBlocks.shredder, Item.getByNameOrId("rats:plastic_waste")).forEach {
			recipes.add(ShredderRecipe(toIngredient(it) ?: return@forEach, ModItems.shreddedPlastic.toStack()))
		}

		arrayOf("sheetPlastic", "rodPlastic", "stickPlastic", "platePlastic", "blockPlastic", "itemPlastic", "bioplastic").forEach {
			if(oreNotEmpty(it))
				recipes.add(ShredderRecipe(it.toOre(), ModItems.shreddedPlastic.toStack()))
		}

		arrayOf("dumpsterdiving:scrap_plastic", "dumpsterdiving:scrap_rubber").forEach {
			Item.getByNameOrId(it)?.let { item ->
				recipes.add(ShredderRecipe(item.toIngredient(), ModItems.shreddedPlastic.toStack(4)))
			}
		}

		// Plant
		arrayOf(Items.REEDS, ModItems.algae).forEach {
			recipes.add(ShredderRecipe(it.toIngredient(), ModItems.shreddedPlant.toStack()))
		}

		// Starch
		arrayOf(Items.BEETROOT, Items.POTATO, Items.POISONOUS_POTATO).forEach {
			recipes.add(ShredderRecipe(it.toIngredient(), ModItems.shreddedStarch.toStack()))
		}

		arrayOf("cropCorn", "corn").forEach {
			if(oreNotEmpty(it))
				recipes.add(ShredderRecipe(it.toIngredient(), ModItems.shreddedStarch.toStack()))
		}

		// Paper
		arrayOf(Items.PAPER, ModItems.paperWaste).forEach {
			recipes.add(ShredderRecipe(it.toIngredient(), ModItems.shreddedPaper.toStack()))
		}

		recipes.add(ShredderRecipe(Items.BOOK.toIngredient(), ModItems.shreddedPaper.toStack(4)))

		Item.getByNameOrId("dumpsterdiving:scrap_paper")?.let {
			recipes.add(ShredderRecipe(it.toIngredient(), ModItems.shreddedPaper.toStack(4)))
		}

		if(oreNotEmpty("paper"))
			recipes.add(ShredderRecipe("paper".toOre(), ModItems.shreddedPaper.toStack()))

		if(oreNotEmpty("book"))
			recipes.add(ShredderRecipe("book".toOre(), ModItems.shreddedPaper.toStack(4)))
	}

	fun toIngredient(thing: Any?): Ingredient? {
		if(thing == null)
			return null

		return when(thing) {
			is Item -> thing.toIngredient()
			is Block -> thing.toIngredient()
			else -> throw RuntimeException("wtf")
		}
	}
}
