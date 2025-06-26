package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.catalyx.utils.extensions.translate
import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerAlgorithmicOptimiser
import io.enderdev.emergingtechnology.client.gui.GuiAlgorithmicOptimiser
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.items.TooltipItemBlock
import io.enderdev.emergingtechnology.tiles.TileAlgorithmicOptimiser
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent

class BlockAlgorithmicOptimiser() : RotatableMachineBlock("algorithmic_optimiser", TileAlgorithmicOptimiser::class.java,
	EmergingTechnology.guiHandler.registerId(ContainerAlgorithmicOptimiser::class.java, GuiAlgorithmicOptimiser::class.java, TileAlgorithmicOptimiser::class.java)) {
	init {
		blockHardness = 1f
	}

	override fun registerItem(event: RegistryEvent.Register<Item>) {
		event.registry.register(TooltipItemBlock(this) {
			ItemUtils.extendedTooltip(
				"tile.${Tags.MODID}:algorithmic_optimiser.desc".translate(),
				"info.emergingtechnology.energy.required".translate(EmergingTechnologyConfig.ELECTRICS_MODULE.OPTIMISER.energyUsage),
				"info.emergingtechnology.water.required".translate(EmergingTechnologyConfig.ELECTRICS_MODULE.OPTIMISER.waterUsage)
			)
		})
	}
}
