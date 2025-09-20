package io.enderdev.emergingtechnology.client.gui

import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerWasteCollector
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.tiles.TileWasteCollector
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.inventory.IInventory
import net.minecraft.util.ResourceLocation
import org.ender_development.catalyx.utils.extensions.translate

class GuiWasteCollector(playerInv: IInventory, val tile: TileWasteCollector) : BaseETGui(ContainerWasteCollector(playerInv, tile), tile) {
	override val textureLocation = ResourceLocation(Tags.MODID, "textures/gui/container/waste_collector_gui.png")

	var updateTimer = 0
	var requirementsMet = false

	private companion object {
		const val ICON_X = 137
		const val ICON_Y = 30
		const val ICON_U = 175
		const val ICON_CROSS_V = 0
		const val ICON_CHECK_V = 7
		const val ICON_WIDTH = 8
		const val ICON_HEIGHT = 7
	}

	override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY)

		if(updateTimer-- == 0) {
			updateTimer = 20
			requirementsMet = tile.checkSurroundings()
			if(requirementsMet) {
				updateTimer *= 2
				return
			}
		}

		GlStateManager.color(1f, 1f, 1f)
		mc.textureManager.bindTexture(textureLocation)
		drawTexturedModalRect(ICON_X, ICON_Y, ICON_U, if(requirementsMet) ICON_CHECK_V else ICON_CROSS_V, ICON_WIDTH, ICON_HEIGHT)
	}

	override fun renderTooltips(mouseX: Int, mouseY: Int) {
		super.renderTooltips(mouseX, mouseY)
		if(!requirementsMet && mouseX >= guiLeft + ICON_X && mouseX <= guiLeft + ICON_X + ICON_WIDTH + 1 && mouseY >= guiTop + ICON_Y && mouseY <= guiTop + ICON_Y + ICON_HEIGHT + 1)
			drawHoveringText(tile.failReasons.map {
				"tile.${Tags.MODID}:waste_collector.req.${it.translationKey}".translate(EmergingTechnologyConfig.POLYMERS_MODULE.COLLECTOR.minimumWaterBlocks)
			}, mouseX, mouseY)
	}
}
