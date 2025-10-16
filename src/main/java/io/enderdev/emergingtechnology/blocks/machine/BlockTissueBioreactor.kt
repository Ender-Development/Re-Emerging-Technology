package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerTissueBioreactor
import io.enderdev.emergingtechnology.client.gui.GuiTissueBioreactor
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.tiles.TileTissueBioreactor
import io.enderdev.emergingtechnology.utils.ItemUtils
import org.ender_development.catalyx.items.TooltipItemBlock
import org.ender_development.catalyx.utils.extensions.translate

class BlockTissueBioreactor() : RotatableMachineBlock("tissue_bioreactor", TileTissueBioreactor::class.java,
	EmergingTechnology.guiHandler.registerId(TileTissueBioreactor::class.java, ContainerTissueBioreactor::class.java) { GuiTissueBioreactor::class.java }) {
	init {
		blockHardness = 1f
	}

	override val item =
		TooltipItemBlock(this) { stack, world, flag ->
			ItemUtils.extendedTooltip(
				"tile.${Tags.MODID}:tissue_bioreactor.desc".translate(),
				"info.${Tags.MODID}:energy.required".translate(EmergingTechnologyConfig.SYNTHETICS_MODULE.BIOREACTOR.bioreactorEnergyUsage),
				"info.${Tags.MODID}:water.required".translate(EmergingTechnologyConfig.SYNTHETICS_MODULE.BIOREACTOR.bioreactorWaterUsage)
			)
		}
}
