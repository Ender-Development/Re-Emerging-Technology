package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.catalyx.utils.extensions.translate
import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.client.container.ContainerProcessor
import io.enderdev.emergingtechnology.client.gui.GuiProcessor
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.items.TooltipItemBlock
import io.enderdev.emergingtechnology.tiles.TileProcessor
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent

class BlockProcessor() : RotatableMachineBlock("processor", TileProcessor::class.java,
	EmergingTechnology.guiHandler.registerId(ContainerProcessor::class.java, GuiProcessor::class.java, TileProcessor::class.java)) {
	init {
		blockHardness = 1f
	}

	override fun registerItem(event: RegistryEvent.Register<Item>) {
		event.registry.register(TooltipItemBlock(this) {
			ItemUtils.extendedTooltip(
				"tile.emergingtechnology:processor.desc".translate(),
				"info.emergingtechnology.energy.required".translate(EmergingTechnologyConfig.POLYMERS_MODULE.PROCESSOR.processorEnergyBaseUsage),
				"info.emergingtechnology.water.required".translate(EmergingTechnologyConfig.POLYMERS_MODULE.PROCESSOR.processorWaterBaseUsage)
			)
		})
	}
}
