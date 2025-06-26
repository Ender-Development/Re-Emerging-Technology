package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.catalyx.utils.extensions.translate
import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerShredder
import io.enderdev.emergingtechnology.client.gui.GuiShredder
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.items.TooltipItemBlock
import io.enderdev.emergingtechnology.tiles.TileShredder
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent

class BlockShredder() : RotatableMachineBlock("shredder", TileShredder::class.java,
	EmergingTechnology.guiHandler.registerId(ContainerShredder::class.java, GuiShredder::class.java, TileShredder::class.java)) {
	init {
		blockHardness = 1f
	}

	override fun registerItem(event: RegistryEvent.Register<Item>) {
		event.registry.register(TooltipItemBlock(this) {
			ItemUtils.extendedTooltip(
				"tile.${Tags.MODID}:shredder.desc".translate(),
				"info.emergingtechnology.energy.required".translate(EmergingTechnologyConfig.POLYMERS_MODULE.SHREDDER.shredderEnergyBaseUsage)
			)
		})
	}
}
