package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerProcessor
import io.enderdev.emergingtechnology.client.gui.GuiProcessor
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.tiles.TileProcessor
import io.enderdev.emergingtechnology.utils.ItemUtils
import org.ender_development.catalyx.items.TooltipItemBlock
import org.ender_development.catalyx.utils.extensions.translate

class BlockProcessor() : RotatableMachineBlock("processor", TileProcessor::class.java,
	EmergingTechnology.guiHandler.registerId(TileProcessor::class.java, ContainerProcessor::class.java) { GuiProcessor::class.java }) {
	init {
		blockHardness = 1f
	}

	override val item =
		TooltipItemBlock(this) { stack, world, flag ->
			ItemUtils.extendedTooltip(
				"tile.${Tags.MODID}:processor.desc".translate(),
				"info.${Tags.MODID}:energy.required".translate(EmergingTechnologyConfig.POLYMERS_MODULE.PROCESSOR.processorEnergyBaseUsage),
				"info.${Tags.MODID}:water.required".translate(EmergingTechnologyConfig.POLYMERS_MODULE.PROCESSOR.processorWaterBaseUsage)
			)
		}
}
