package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerFabricator
import io.enderdev.emergingtechnology.client.gui.GuiFabricator
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.tiles.TileFabricator
import io.enderdev.emergingtechnology.utils.ItemUtils
import org.ender_development.catalyx.items.TooltipItemBlock
import org.ender_development.catalyx.utils.extensions.translate

class BlockFabricator : RotatableMachineBlock("fabricator", EmergingTechnology.guiHandler.registerId(TileFabricator::class.java, ContainerFabricator::class.java) { GuiFabricator::class.java }) {
	init {
		blockHardness = 1f
	}

	override val item =
		TooltipItemBlock(this) { stack, world, flag ->
			ItemUtils.extendedTooltip(
				"tile.${Tags.MODID}:fabricator.desc".translate(),
				"info.${Tags.MODID}:energy.required".translate(EmergingTechnologyConfig.POLYMERS_MODULE.FABRICATOR.fabricatorEnergyBaseUsage)
			)
		}
}
