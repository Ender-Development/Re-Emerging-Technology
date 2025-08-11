package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.catalyx.utils.extensions.translate
import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerNutrientInjector
import io.enderdev.emergingtechnology.client.gui.GuiNutrientInjector
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.items.TooltipItemBlock
import io.enderdev.emergingtechnology.tiles.TileNutrientInjector
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent

class BlockNutrientInjector() : RotatableMachineBlock("nutrient_injector", TileNutrientInjector::class.java,
	EmergingTechnology.guiHandler.registerId(TileNutrientInjector::class.java, ContainerNutrientInjector::class.java) { GuiNutrientInjector::class.java }) {
	init {
		blockHardness = 1f
	}

	override fun registerItem(event: RegistryEvent.Register<Item>) {
		event.registry.register(TooltipItemBlock(this) {
			ItemUtils.extendedTooltip(
				"tile.${Tags.MODID}:nutrient_injector.desc".translate(),
				"info.${Tags.MODID}.energy.required".translate(EmergingTechnologyConfig.HYDROPONICS_MODULE.INJECTOR.injectorEnergyBaseUsage),
				"info.${Tags.MODID}.water.required".translate(EmergingTechnologyConfig.HYDROPONICS_MODULE.INJECTOR.injectorWaterBaseUsage)
			)
		})
	}
}
