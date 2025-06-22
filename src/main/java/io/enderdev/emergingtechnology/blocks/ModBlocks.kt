package io.enderdev.emergingtechnology.blocks

import io.enderdev.catalyx.blocks.BaseBlock
import io.enderdev.emergingtechnology.blocks.machine.IHasModel
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

object ModBlocks {
	val blocks = mutableListOf<BaseBlock>()

	// Hydroponics
	// hydroponic_grow_bed
	// hydroponic_grow_light
	// harvester
	// water_filler
	// co2_scrubber
	// co2_diffuser
	// nutrient_injector

	// aquaponic_base
	// aquaponic_frame
	// aquaponic_glass

	// Polymers
	// processor
	// shredder
	// plastic_block
	// clear_plastic_block
	// machine_case
	// fabricator
	// waste_collector
	// bioplastic_ladder

	// shredded_plant_block
	// shredded_plastic_block
	// shredded_starch_block

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
