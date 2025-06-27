package io.enderdev.emergingtechnology.blocks

import io.enderdev.catalyx.IBothProvider
import io.enderdev.catalyx.blocks.BaseBlock
import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.blocks.ModBlocks.blocks
import io.enderdev.emergingtechnology.blocks.machine.*
import net.minecraft.block.Block
import net.minecraft.block.BlockLadder
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import scala.reflect.macros.contexts.`Infrastructure$class`.settings

object ModBlocks {
	val blocks = mutableListOf<IBothProvider>()

	// TODO - CTM support for certain blocks (aquaponic_base, aquaponic_frame, aquaponic_glass)
	// TODO - run optipng once finished :3

	// Hydroponics
	// hydroponic_grow_bed
	// hydroponic_grow_light
	// harvester
	val waterFiller = BlockWaterFiller()
	// co2_scrubber
	// co2_diffuser
	// nutrient_injector

	val aquaponicBase = ModelBlock("aquaponic_base", hardness = 2f)
	val aquaponicFrame = ModelBlock("aquaponic_frame", hardness = 2f)
	val aquaponicGlass = GlassBlock("aquaponic_glass", hardness = 1f)

	// Polymers
	val processor = BlockProcessor()
	val shredder = BlockShredder()
	val fabricator = BlockFabricator()
	val wasteCollector = BlockWasteCollector()

	val machineCase = ModelBlock("machine_case", Material.IRON, SoundType.METAL, 1f)
	val bioplasticLadder = LadderBlock("bioplastic_ladder")

	val plasticBlock = ModelBlock("plastic_block", hardness = 2f)
	val clearPlasticBlock = GlassBlock("clear_plastic_block")

	val shreddedPlantBlock = ModelBlock("shredded_plant_block", Material.PLANTS, SoundType.PLANT, 1f)
	val shreddedPlasticBlock = ModelBlock("shredded_plastic_block", hardness = 1f)
	val shreddedStarchBlock = ModelBlock("shredded_starch_block", Material.PLANTS, SoundType.PLANT, 1f)

	// Synthetics
	// solar_cooker
	// tissue_bioreactor
	// tissue_scaffolder
	// algae_bioreactor

	// Electrics
	// piezoelectric_generator
	// tidal_generator
	// wind_generator
	val biomassGenerator = BlockBiomassGenerator()
	val solarPanel = BlockSolarPanel()
	// solar_glass
	// battery
	val biocharBlock = ModelBlock("biochar_block", Material.WOOD, SoundType.WOOD, 1f)
	val algorithmicOptimiser = BlockAlgorithmicOptimiser()
	// guide_light

	// Fluids
	// carbon_dioxide_block
	// nutrient_block

	// Ores
	// polluted_dirt
	// polluted_sand
	// polluted_gravel

	fun registerBlocks(event: RegistryEvent.Register<Block>) = blocks.forEach { it.registerBlock(event) }

	fun registerItems(event: RegistryEvent.Register<Item>) = blocks.forEach { it.registerItem(event) }

	@SideOnly(Side.CLIENT)
	fun registerModels() = blocks.forEach { if(it is IHasModel) it.registerModel() }
}
