package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerAlgorithmicOptimiser
import io.enderdev.emergingtechnology.client.gui.GuiAlgorithmicOptimiser
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.tiles.TileAlgorithmicOptimiser
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.item.Item
import org.ender_development.catalyx.items.TooltipItemBlock
import org.ender_development.catalyx.utils.extensions.translate

class BlockAlgorithmicOptimiser() : RotatableMachineBlock("algorithmic_optimiser", TileAlgorithmicOptimiser::class.java,
	EmergingTechnology.guiHandler.registerId(TileAlgorithmicOptimiser::class.java, ContainerAlgorithmicOptimiser::class.java) { GuiAlgorithmicOptimiser::class.java }) {
	init {
		blockHardness = 1f
	}

	override val item =
		TooltipItemBlock(this) { stack, world, flag ->
			ItemUtils.extendedTooltip(
				"tile.${Tags.MODID}:algorithmic_optimiser.desc".translate(),
				"info.${Tags.MODID}:energy.required".translate(EmergingTechnologyConfig.ELECTRICS_MODULE.OPTIMISER.energyUsage),
				"info.${Tags.MODID}:water.required".translate(EmergingTechnologyConfig.ELECTRICS_MODULE.OPTIMISER.waterUsage)
			)
		}
}
