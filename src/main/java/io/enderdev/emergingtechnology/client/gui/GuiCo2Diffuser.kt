package io.enderdev.emergingtechnology.client.gui

import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerCo2Diffuser
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.items.ItemNozzle
import io.enderdev.emergingtechnology.tiles.TileCo2Diffuser
import net.minecraft.inventory.IInventory
import net.minecraft.util.ResourceLocation
import org.ender_development.catalyx.client.gui.wrappers.CapabilityEnergyDisplayWrapper
import org.ender_development.catalyx.client.gui.wrappers.CapabilityFluidDisplayWrapper
import org.ender_development.catalyx.utils.extensions.get
import org.ender_development.catalyx.utils.extensions.translate
import java.awt.Color

class GuiCo2Diffuser(playerInv: IInventory, val tile: TileCo2Diffuser) : BaseETGuiTyped<TileCo2Diffuser>(ContainerCo2Diffuser(playerInv, tile), tile) {
	override val textureLocation = ResourceLocation(Tags.MODID, "textures/gui/container/co2_diffuser_gui.png")

	init {
		displayData.add(CapabilityEnergyDisplayWrapper(129, 22, 39, 9, tile::energyStorage))
		displayData.add(CapabilityFluidDisplayWrapper(129, 7, 39, 9, tile::gasTank))
	}

	val baseRange = EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.diffuserBaseRange
	val baseProbability = EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.diffuserBaseBoostProbability
	val darkRed = Color.red.darker().rgb

	private companion object {
		const val textX = 40
		const val textY = 35
	}

	override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY)

		val nozzle = tile.input[0].item
		if(nozzle !is ItemNozzle) {
			fontRenderer.drawString("tile.${Tags.MODID}:co2_diffuser.no_nozzle".translate(), textX, textY, darkRed)
			return
		}

		fontRenderer.drawString("${nozzle.translationKey}.name".translate(), textX, textY, Color.darkGray.rgb)
		val rangeEndX = fontRenderer.drawString("tile.${Tags.MODID}:co2_diffuser.range".translate(baseRange * nozzle.range), textX, textY + fontRenderer.FONT_HEIGHT, Color.darkGray.rgb)
		fontRenderer.drawString("tile.${Tags.MODID}:hydroponic_grow_light.growth".translate(baseProbability * nozzle.boost), textX, textY + (fontRenderer.FONT_HEIGHT shl 1), Color.darkGray.rgb)

		val halfX = (width - xSize) shr 1
		val halfY = (height - ySize) shr 1
		if(mouseX >= halfX + textX && mouseX < halfX + rangeEndX && mouseY > halfY + textY + fontRenderer.FONT_HEIGHT && mouseY <= halfY + textY + (fontRenderer.FONT_HEIGHT shl 1))
			drawHoveringText(listOf("tile.${Tags.MODID}:co2_diffuser.range.explanation.1".translate(baseRange * nozzle.range), "tile.${Tags.MODID}:co2_diffuser.range.explanation.2".translate()), mouseX - halfX, mouseY - halfY)
	}
}
