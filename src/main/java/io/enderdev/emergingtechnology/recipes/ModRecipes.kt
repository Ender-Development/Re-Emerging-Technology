package io.enderdev.emergingtechnology.recipes

import io.enderdev.emergingtechnology.blocks.ModBlocks
import io.enderdev.emergingtechnology.items.ModItems
import io.enderdev.emergingtechnology.recipes.register.BiomassGeneratorRegister
import io.enderdev.emergingtechnology.recipes.register.ProcessorRegister
import io.enderdev.emergingtechnology.recipes.register.ShredderRegister
import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraft.item.Item
import net.minecraftforge.oredict.OreDictionary.registerOre

object ModRecipes {
	val shredderRecipes = ShredderRegister.INSTANCE
	val processorRecipes = ProcessorRegister.INSTANCE
	val biomassGeneratorRecipes = BiomassGeneratorRegister.INSTANCE

	fun init() {
		shredderRecipes.registerRecipes()
		processorRecipes.registerRecipes()
		biomassGeneratorRecipes.registerRecipes()
	}

	// copy of the OreRegistrationHandler in original EMT
	fun initOredict() {
		registerOres("machinePlastic",
			//ModBlocks.hydroponic,
			//ModBlocks.light,
			ModBlocks.shredder,
			ModBlocks.processor,
			//ModBlocks.machinecase,
			//ModBlocks.cooker,
			//ModBlocks.bioreactor,
			//ModBlocks.scaffolder,
			//ModBlocks.fabricator
		)

		registerOre("blockPlastic", ModBlocks.plasticBlock)
		registerOre("blockGlass", ModBlocks.clearPlasticBlock)
		registerOre("glass", ModBlocks.clearPlasticBlock)
		registerOre("sheetPlastic", ModItems.plasticSheet)
		registerOre("platePlastic", ModItems.plasticSheet)
		registerOre("stickPlastic", ModItems.plasticRod)
		registerOre("rodPlastic", ModItems.plasticRod)
		registerOre("filamentPlastic", ModItems.filament)
		registerOre("bioplastic", ModItems.plasticSheet)
		registerOre("starch", ModItems.shreddedStarch)
		registerOre("biomass", ModItems.biomass)
		registerOre("dustBiomass", ModItems.biomass)
		registerOre("pulpWood", ModItems.paperPulp)
		registerOre("coal", ModItems.biochar)
		registerOre("blockCoal", ModBlocks.biocharBlock)
		registerOre("leather", ModItems.syntheticLeather)
		registerOre("string", ModItems.syntheticSilk)
		registerOre("slimeball", ModItems.syntheticSlime)
		registerOre("fertilizer", ModItems.fertilizer)
		registerOre("blockWool", Blocks.WOOL)

		arrayOf("listAllmeat", "cookedMeat", "listAllmeatcooked").forEach {
			registerOres(it, ModItems.syntheticChickenCooked, ModItems.syntheticSteakCooked, ModItems.syntheticPorkchopCooked)
		}

		arrayOf("rawMeat", "listAllmeatraw").forEach {
			registerOres(it, ModItems.syntheticChicken, ModItems.syntheticSteak, ModItems.syntheticPorkchop)
		}

		registerOre("listAllchickenraw", ModItems.syntheticChicken)
		registerOre("listAllporkraw", ModItems.syntheticPorkchop)
		registerOre("listAllbeefraw", ModItems.syntheticSteak)

		registerOre("listAllchickencooked", ModItems.syntheticChickenCooked)
		registerOre("listAllporkcooked", ModItems.syntheticPorkchopCooked)
		registerOre("listAllbeefcooked", ModItems.syntheticSteakCooked)
	}

	private fun registerOres(name: String, vararg items: Item) {
		items.forEach { registerOre(name, it) }
	}

	private fun registerOres(name: String, vararg blocks: Block) {
		blocks.forEach { registerOre(name, it) }
	}
}
