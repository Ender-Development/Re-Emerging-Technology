package io.enderdev.emergingtechnology.client.gui

import io.enderdev.catalyx.client.gui.wrappers.CapabilityEnergyDisplayWrapper
import io.enderdev.catalyx.utils.extensions.translate
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerBattery
import io.enderdev.emergingtechnology.tiles.TileBattery
import net.minecraft.client.Minecraft
import net.minecraft.inventory.IInventory
import net.minecraft.util.ResourceLocation
import java.awt.Color
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.absoluteValue

class GuiBattery(playerInv: IInventory, val tile: TileBattery) : BaseETGuiTyped<TileBattery>(ContainerBattery(playerInv, tile), tile) {
	override val textureLocation = ResourceLocation(Tags.MODID, "textures/gui/container/battery_gui.png")

	val inputX = (xSize shr 1) - 35
	val outputX = (xSize shr 1) + 15
	val bothY = (ySize shr 2) - 10

	val netX = xSize shr 1
	val netY = bothY + 25

	init {
		displayData.add(CapabilityEnergyDisplayWrapper(129, 7, 39, 9, tile::energyStorage))
	}

	override fun initGui() {
		super.initGui()
		buttonList.clear()
	}

	override fun renderTooltips(mouseX: Int, mouseY: Int) {}

	// cache these to not have to do the darker() math every render frame
	val darkGray = Color.darkGray.rgb
	val green = Color.green.darker().rgb
	val red = Color.red.darker().rgb
	val inputText = "tile.${Tags.MODID}:battery.input".translate()
	val inputTextOffset = Minecraft.getMinecraft().fontRenderer.getStringWidth(inputText) // yes this needs to go through Minecraft.getMinecraft()
	val outputText = "tile.${Tags.MODID}:battery.output".translate()
	val netFlowText = "tile.${Tags.MODID}:battery.net_flow".translate()
	val netFlowTextOffset = Minecraft.getMinecraft().fontRenderer.getStringWidth(netFlowText) shr 1

	val formatter: NumberFormat = NumberFormat.getInstance(Locale.getDefault())

	override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY)
		val netFlow = tile.netInput - tile.netOutput

		drawLines(arrayOf(inputText, "+ ${formatter.format(tile.netInput)} FE"), inputX - inputTextOffset, bothY, arrayOf(darkGray, if(tile.netInput == 0) darkGray else green))
		drawLines(arrayOf(outputText, "- ${formatter.format(tile.netOutput)} FE"), outputX, bothY, arrayOf(darkGray, if(tile.netOutput == 0) darkGray else red))
		drawLines(arrayOf(netFlowText, "${if(netFlow >= 0) "+" else "-"} ${formatter.format(netFlow.absoluteValue)} FE"), netX - netFlowTextOffset, netY, arrayOf(darkGray, if(netFlow == 0) darkGray else if(netFlow > 0) green else red))
	}

	fun drawLines(lines: Array<String>, x: Int, y: Int, colours: Array<Int>) {
		for(line in lines.indices)
			fontRenderer.drawString(lines[line], x, y + fontRenderer.FONT_HEIGHT * line, colours[line])
	}
}
