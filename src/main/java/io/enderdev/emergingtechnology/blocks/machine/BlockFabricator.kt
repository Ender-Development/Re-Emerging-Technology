package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.catalyx.utils.extensions.translate
import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerFabricator
import io.enderdev.emergingtechnology.client.gui.GuiFabricator
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.items.TooltipItemBlock
import io.enderdev.emergingtechnology.tiles.TileFabricator
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent

class BlockFabricator : RotatableMachineBlock("fabricator", TileFabricator::class.java,
	EmergingTechnology.guiHandler.registerId(ContainerFabricator::class.java, GuiFabricator::class.java, TileFabricator::class.java)) {
	init {
		blockHardness = 1f
	}

	override fun registerItem(event: RegistryEvent.Register<Item>) {
		event.registry.register(TooltipItemBlock(this) {
			ItemUtils.extendedTooltip(
				"tile.${Tags.MODID}:fabricator.desc".translate(),
				"info.emergingtechnology.energy.required".translate(EmergingTechnologyConfig.POLYMERS_MODULE.FABRICATOR.fabricatorEnergyBaseUsage)
			)
		})
	}
}
