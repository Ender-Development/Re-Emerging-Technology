package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerAlgaeBioreactor
import io.enderdev.emergingtechnology.client.gui.GuiAlgaeBioreactor
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.tiles.TileAlgaeBioreactor
import io.enderdev.emergingtechnology.utils.ItemUtils
import org.ender_development.catalyx.items.TooltipItemBlock
import org.ender_development.catalyx.utils.extensions.translate

class BlockAlgaeBioreactor() : RotatableMachineBlock("algae_bioreactor", EmergingTechnology.guiHandler.registerId(TileAlgaeBioreactor::class.java, ContainerAlgaeBioreactor::class.java) { GuiAlgaeBioreactor::class.java }) {
	init {
		blockHardness = 1f
	}

	override val item =
		TooltipItemBlock(this) { stack, world, flag ->
			ItemUtils.extendedTooltip(
				"tile.${Tags.MODID}:algae_bioreactor.desc".translate(),
				"info.${Tags.MODID}:energy.required".translate(EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.bioreactorEnergyUsage),
				"info.${Tags.MODID}:water.required".translate(EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.bioreactorWaterUsage),
				"info.${Tags.MODID}:co2.required".translate(EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.bioreactorGasUsage)
			)
		}
}
