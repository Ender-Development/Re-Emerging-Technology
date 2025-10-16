package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerTissueScaffolder
import io.enderdev.emergingtechnology.client.gui.GuiTissueScaffolder
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.tiles.TileTissueScaffolder
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.item.Item
import org.ender_development.catalyx.items.TooltipItemBlock
import org.ender_development.catalyx.utils.extensions.translate

class BlockTissueScaffolder() : RotatableMachineBlock("tissue_scaffolder", TileTissueScaffolder::class.java,
	EmergingTechnology.guiHandler.registerId(TileTissueScaffolder::class.java, ContainerTissueScaffolder::class.java) { GuiTissueScaffolder::class.java }) {
	init {
		blockHardness = 1f
	}

	override val item =
		TooltipItemBlock(this) { stack, world, flag ->
			ItemUtils.extendedTooltip(
				"tile.${Tags.MODID}:tissue_scaffolder.desc".translate(),
				"info.${Tags.MODID}:energy.required".translate(EmergingTechnologyConfig.SYNTHETICS_MODULE.SCAFFOLDER.scaffolderEnergyUsage)
			)
		}
}
