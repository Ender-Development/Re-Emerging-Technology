package io.enderdev.emergingtechnology.client.gui

import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerCreativeFiller
import io.enderdev.emergingtechnology.tiles.TileCreativeFiller
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.inventory.IInventory
import net.minecraft.util.ResourceLocation
import org.ender_development.catalyx.client.button.AbstractButtonWrapper
import org.ender_development.catalyx.network.ButtonPacket
import org.ender_development.catalyx.network.PacketHandler
import org.lwjgl.input.Mouse
import java.awt.Color
import java.text.DecimalFormat

class GuiCreativeFiller(playerInv: IInventory, val tile: TileCreativeFiller) : GuiContainer(ContainerCreativeFiller(playerInv, tile)) {

	var changeBy = 100

	init {
		xSize = 175
		ySize = 179
	}

	override fun initGui() {
		val halfX = ((width - xSize) shr 1)
		val halfY = ((height - ySize) shr 1)
		repeat(4) { idx ->
			buttonList.add(TileCreativeFiller.UpdateButtonWrapper(halfX + 132, halfY + 15 + idx * 16, idx, 0).button)
		}
		repeat(4) { idx ->
			buttonList.add(TileCreativeFiller.UpdateButtonWrapper(halfX + 71, halfY + 15 + idx * 16, idx + 4, 0).button)
		}
		super.initGui()
	}

	override fun actionPerformed(button: GuiButton) { // fallback
		val wrapper = AbstractButtonWrapper.getWrapper<TileCreativeFiller.UpdateButtonWrapper>(button)
		if(wrapper == null)
			super.actionPerformed(button)
		else
			buttonClick(wrapper, 0)
	}

	fun buttonClick(wrapper: TileCreativeFiller.UpdateButtonWrapper, mouseButton: Int) {
		wrapper.value = when {
			!isShiftKeyDown() && mouseButton == 0 -> changeBy
			!isShiftKeyDown() && mouseButton == 1 -> -changeBy
			isShiftKeyDown() && mouseButton == 0 -> Int.MAX_VALUE - tile.getField(wrapper.field)
			isShiftKeyDown() && mouseButton == 1 -> -tile.getField(wrapper.field)
			else -> 0
		}

		PacketHandler.channel.sendToServer(ButtonPacket(tile.pos, wrapper))
	}

	override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
		buttonList.forEach {
			val wrapper = AbstractButtonWrapper.getWrapper<TileCreativeFiller.UpdateButtonWrapper>(it) ?: return@forEach

			if(it.mousePressed(mc, mouseX, mouseY)) {
				selectedButton = it
				it.playPressSound(mc.soundHandler)
				buttonClick(wrapper, mouseButton)
			}
		}
	}

	override fun handleMouseInput() {
		super.handleMouseInput()
		val scroll = Mouse.getEventDWheel()
		if(scroll != 0) {
			if(scroll > 0)
				changeBy *= 10
			else
				changeBy /= 10

			if(changeBy <= 0)
				changeBy = 1
		}
	}

	override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY)

		val halfX = (width - xSize) shr 1
		val halfY = (height - ySize) shr 1
		buttonList.forEach {
			val wrapper = AbstractButtonWrapper.getWrapper<TileCreativeFiller.UpdateButtonWrapper>(it) ?: return@forEach
			fontRenderer.drawString(tile.getField(wrapper.field).stringify(), it.x - halfX - 32, it.y - halfY + 4, Color.gray.rgb)
		}
		fontRenderer.drawString(changeBy.toString(), 16, 84, Color.gray.rgb)
	}

	val decimalFormat = DecimalFormat()

	fun Int.stringify(): String = if(this == Int.MAX_VALUE) "inf" else decimalFormat.format(this)

	override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
		GlStateManager.color(1f, 1f, 1f, 1f)
		mc.textureManager.bindTexture(ResourceLocation(Tags.MODID, "textures/gui/container/creative_filler_gui.png"))

		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize)
	}
}
