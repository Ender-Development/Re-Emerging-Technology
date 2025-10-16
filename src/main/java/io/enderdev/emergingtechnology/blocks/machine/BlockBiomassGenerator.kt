package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerBiomassGenerator
import io.enderdev.emergingtechnology.client.gui.GuiBiomassGenerator
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.tiles.TileBiomassGenerator
import io.enderdev.emergingtechnology.utils.ItemUtils
import org.ender_development.catalyx.items.TooltipItemBlock
import org.ender_development.catalyx.utils.extensions.translate

class BlockBiomassGenerator() : RotatableMachineBlock("biomass_generator", TileBiomassGenerator::class.java,
	EmergingTechnology.guiHandler.registerId(TileBiomassGenerator::class.java, ContainerBiomassGenerator::class.java) { GuiBiomassGenerator::class.java }) {
	init {
		blockHardness = 1f
	}

	override val item =
		TooltipItemBlock(this) { stack, world, flag ->
			ItemUtils.extendedTooltip(
				"tile.${Tags.MODID}:biomass_generator.desc".translate(EmergingTechnologyConfig.ELECTRICS_MODULE.BIOMASSGENERATOR.biomassEnergyGenerated)
			)
		}
}
