package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerAlgaeBioreactor
import io.enderdev.emergingtechnology.client.gui.GuiAlgaeBioreactor
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.items.TooltipItemBlock
import io.enderdev.emergingtechnology.tiles.TileAlgaeBioreactor
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent
import org.ender_development.catalyx.utils.extensions.translate

class BlockAlgaeBioreactor() : RotatableMachineBlock("algae_bioreactor", TileAlgaeBioreactor::class.java,
	EmergingTechnology.guiHandler.registerId(TileAlgaeBioreactor::class.java, ContainerAlgaeBioreactor::class.java) { GuiAlgaeBioreactor::class.java }) {
	init {
		blockHardness = 1f
	}

	override fun registerItem(event: RegistryEvent.Register<Item>) {
		event.registry.register(TooltipItemBlock(this) {
			ItemUtils.extendedTooltip(
				"tile.${Tags.MODID}:algae_bioreactor.desc".translate(),
				"info.${Tags.MODID}:energy.required".translate(EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.bioreactorEnergyUsage),
				"info.${Tags.MODID}:water.required".translate(EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.bioreactorWaterUsage),
				"info.${Tags.MODID}:co2.required".translate(EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.bioreactorGasUsage)
			)
		})
	}
}
