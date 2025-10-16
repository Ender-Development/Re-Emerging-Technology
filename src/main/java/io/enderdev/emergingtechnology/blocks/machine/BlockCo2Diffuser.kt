package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerCo2Diffuser
import io.enderdev.emergingtechnology.client.gui.GuiCo2Diffuser
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.tiles.TileCo2Diffuser
import io.enderdev.emergingtechnology.utils.ItemUtils
import org.ender_development.catalyx.items.TooltipItemBlock
import org.ender_development.catalyx.utils.extensions.translate

class BlockCo2Diffuser() : ModelMachineBlock("co2_diffuser", TileCo2Diffuser::class.java,
	EmergingTechnology.guiHandler.registerId(TileCo2Diffuser::class.java, ContainerCo2Diffuser::class.java) { GuiCo2Diffuser::class.java }) {
	init {
		blockHardness = 1f
	}

	override val item =
		TooltipItemBlock(this) { stack, world, flag ->
			ItemUtils.extendedTooltip(
				"tile.${Tags.MODID}:co2_diffuser.desc".translate(),
				"tile.${Tags.MODID}:co2_diffuser.energy".translate(EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.diffuserEnergyBaseUsage),
				"tile.${Tags.MODID}:co2_diffuser.co2".translate(EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.diffuserGasBaseUsage)
			)
		}
}
