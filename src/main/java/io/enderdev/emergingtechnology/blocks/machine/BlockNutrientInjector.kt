package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerNutrientInjector
import io.enderdev.emergingtechnology.client.gui.GuiNutrientInjector
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.tiles.TileNutrientInjector
import io.enderdev.emergingtechnology.utils.ItemUtils
import org.ender_development.catalyx.items.TooltipItemBlock
import org.ender_development.catalyx.utils.extensions.translate

class BlockNutrientInjector() : RotatableMachineBlock("nutrient_injector", EmergingTechnology.guiHandler.registerId(TileNutrientInjector::class.java, ContainerNutrientInjector::class.java) { GuiNutrientInjector::class.java }) {
	init {
		blockHardness = 1f
	}

	override val item =
		TooltipItemBlock(this) { stack, world, flag ->
			ItemUtils.extendedTooltip(
				"tile.${Tags.MODID}:nutrient_injector.desc".translate(),
				"info.${Tags.MODID}:energy.required".translate(EmergingTechnologyConfig.HYDROPONICS_MODULE.INJECTOR.injectorEnergyBaseUsage),
				"info.${Tags.MODID}:water.required".translate(EmergingTechnologyConfig.HYDROPONICS_MODULE.INJECTOR.injectorWaterBaseUsage)
			)
		}
}
