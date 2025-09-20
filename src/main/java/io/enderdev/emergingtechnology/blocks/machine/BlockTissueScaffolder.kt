package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerTissueScaffolder
import io.enderdev.emergingtechnology.client.gui.GuiTissueScaffolder
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.items.TooltipItemBlock
import io.enderdev.emergingtechnology.tiles.TileTissueScaffolder
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent
import org.ender_development.catalyx.utils.extensions.translate

class BlockTissueScaffolder() : RotatableMachineBlock("tissue_scaffolder", TileTissueScaffolder::class.java,
	EmergingTechnology.guiHandler.registerId(TileTissueScaffolder::class.java, ContainerTissueScaffolder::class.java) { GuiTissueScaffolder::class.java }) {
	init {
		blockHardness = 1f
	}

	override fun registerItem(event: RegistryEvent.Register<Item>) {
		event.registry.register(TooltipItemBlock(this) {
			ItemUtils.extendedTooltip(
				"tile.${Tags.MODID}:tissue_scaffolder.desc".translate(),
				"info.${Tags.MODID}:energy.required".translate(EmergingTechnologyConfig.SYNTHETICS_MODULE.SCAFFOLDER.scaffolderEnergyUsage)
			)
		})
	}
}
