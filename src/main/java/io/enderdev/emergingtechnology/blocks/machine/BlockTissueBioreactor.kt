package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.catalyx.utils.extensions.translate
import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerTissueBioreactor
import io.enderdev.emergingtechnology.client.gui.GuiTissueBioreactor
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.items.TooltipItemBlock
import io.enderdev.emergingtechnology.tiles.TileTissueBioreactor
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent

class BlockTissueBioreactor() : RotatableMachineBlock("tissue_bioreactor", TileTissueBioreactor::class.java,
	EmergingTechnology.guiHandler.registerId(ContainerTissueBioreactor::class.java, GuiTissueBioreactor::class.java, TileTissueBioreactor::class.java)) {
	init {
		blockHardness = 1f
	}

	override fun registerItem(event: RegistryEvent.Register<Item>) {
		event.registry.register(TooltipItemBlock(this) {
			ItemUtils.extendedTooltip(
				"tile.${Tags.MODID}:tissue_bioreactor.desc".translate(),
				"info.${Tags.MODID}.energy.required".translate(EmergingTechnologyConfig.SYNTHETICS_MODULE.BIOREACTOR.bioreactorEnergyUsage),
				"info.${Tags.MODID}.water.required".translate(EmergingTechnologyConfig.SYNTHETICS_MODULE.BIOREACTOR.bioreactorWaterUsage)
			)
		})
	}
}
