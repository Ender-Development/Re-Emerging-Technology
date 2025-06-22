package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.client.container.ContainerWasteCollector
import io.enderdev.emergingtechnology.client.gui.GuiWasteCollector
import io.enderdev.emergingtechnology.items.TooltipItemBlock
import io.enderdev.emergingtechnology.tiles.TileWasteCollector
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent

class BlockWasteCollector() : RotatableMachineBlock("waste_collector", TileWasteCollector::class.java,
	EmergingTechnology.guiHandler.registerId(ContainerWasteCollector::class.java, GuiWasteCollector::class.java, TileWasteCollector::class.java)) {
	init {
		blockHardness = 1f
	}

	override fun registerItem(event: RegistryEvent.Register<Item>) {
		event.registry.register(TooltipItemBlock(this) {
			ItemUtils.extendedTooltip("TODO")
		})
	}
}
