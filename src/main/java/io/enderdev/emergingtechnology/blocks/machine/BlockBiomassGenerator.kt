package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerBiomassGenerator
import io.enderdev.emergingtechnology.client.gui.GuiBiomassGenerator
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.items.TooltipItemBlock
import io.enderdev.emergingtechnology.tiles.TileBiomassGenerator
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent
import org.ender_development.catalyx.utils.extensions.translate

class BlockBiomassGenerator() : RotatableMachineBlock("biomass_generator", TileBiomassGenerator::class.java,
	EmergingTechnology.guiHandler.registerId(TileBiomassGenerator::class.java, ContainerBiomassGenerator::class.java) { GuiBiomassGenerator::class.java }) {
	init {
		blockHardness = 1f
	}

	override fun registerItem(event: RegistryEvent.Register<Item>) {
		event.registry.register(TooltipItemBlock(this) {
			ItemUtils.extendedTooltip(
				"tile.${Tags.MODID}:biomass_generator.desc".translate(EmergingTechnologyConfig.ELECTRICS_MODULE.BIOMASSGENERATOR.biomassEnergyGenerated)
			)
		})
	}
}
