package io.enderdev.emergingtechnology.client.gui

import io.enderdev.catalyx.client.gui.wrappers.CapabilityEnergyDisplayWrapper
import io.enderdev.catalyx.utils.extensions.translate
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerBattery
import io.enderdev.emergingtechnology.tiles.TileBattery
import net.minecraft.inventory.IInventory
import net.minecraft.util.ResourceLocation
import java.awt.Color
import java.text.NumberFormat
import java.util.Locale

class GuiBattery(playerInv: IInventory, val tile: TileBattery) : BaseETGuiTyped<TileBattery>(ContainerBattery(playerInv, tile), tile) {
	override val textureLocation = ResourceLocation(Tags.MODID, "textures/gui/container/battery_gui.png")

	val inputX = (xSize shr 1) - 30
	val outputX = (xSize shr 1) + 30
	val bothY = ySize shr 2

	init {
		displayData.add(CapabilityEnergyDisplayWrapper(129, 7, 39, 9, tile::energyStorage))
	}

	override fun initGui() {
		super.initGui()
		buttonList.clear()
	}

	override fun renderTooltips(mouseX: Int, mouseY: Int) {}

	// cache these to not have to do the brighter() math every render frame
	val darkGray = Color.darkGray.rgb
	val green = Color.green.rgb
	val lightGreen = Color.green.brighter().rgb
	val red = Color.red.rgb
	val lightRed = Color.red.brighter().rgb

	val formatter: NumberFormat = NumberFormat.getInstance(Locale.getDefault())

	override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY)
		val netFlow = tile.netInput - tile.netOutput

		val lines = arrayOf("tile.${Tags.MODID}:battery.input".translate(), "+ ${formatter.format(tile.netInput)} FE")
		val widthOffset = fontRenderer.getStringWidth(lines[0])
		drawLines(lines, inputX - widthOffset, bothY, arrayOf(darkGray, if(tile.netInput == 0) darkGray else if(netFlow > 0) lightGreen else green))

		drawLines(arrayOf("tile.${Tags.MODID}:battery.output".translate(), "- ${tile.netOutput} FE"), outputX, bothY, arrayOf(darkGray, if(tile.netOutput == 0) darkGray else if(netFlow < 0) lightRed else red))
	}

	fun drawLines(lines: Array<String>, x: Int, y: Int, colours: Array<Int>) {
		for(line in lines.indices)
			fontRenderer.drawString(lines[line], x, y + fontRenderer.FONT_HEIGHT * line, colours[line])
	}
}
