package io.enderdev.emergingtechnology.blocks

import io.enderdev.catalyx.blocks.BaseBlock
import io.enderdev.catalyx.blocks.IBlockProvider
import io.enderdev.emergingtechnology.blocks.machine.BlockShredder
import io.enderdev.emergingtechnology.blocks.machine.BlockWasteCollector
import io.enderdev.emergingtechnology.blocks.machine.IHasModel
import io.enderdev.emergingtechnology.items.GlassBlock
import net.minecraft.block.Block
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

object ModBlocks {
	val blocks = mutableListOf<BaseBlock>()

	// TODO - CTM support for certain blocks (aquaponic_base, aquaponic_frame, aquaponic_glass)
	// TODO - run optipng once finished :3

	// Hydroponics
	// hydroponic_grow_bed
	// hydroponic_grow_light
	// harvester
	// water_filler
	// co2_scrubber
	// co2_diffuser
	// nutrient_injector

	val aquaponicBase = ModelBlock("aquaponic_base", hardness = 2f)
	val aquaponicFrame = ModelBlock("aquaponic_frame", hardness = 2f)
	val aquaponicGlass = GlassBlock("aquaponic_glass", hardness = 1f)

	// Polymers
	// processor
	val shredder = BlockShredder()
	// plastic_block
	// clear_plastic_block
	// machine_case
	// fabricator
	val wasteCollector = BlockWasteCollector()
	// bioplastic_ladder

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
	// biomass_generator
	// solar_panel
	// solar_glass
	// battery
	// biochar_block
	// algorithmic_optimiser
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
