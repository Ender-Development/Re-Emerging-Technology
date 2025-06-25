package io.enderdev.emergingtechnology.client.gui

import io.enderdev.catalyx.client.gui.BaseGui
import io.enderdev.catalyx.client.gui.ButtonSide
import io.enderdev.catalyx.client.gui.wrappers.CapabilityDisplayWrapper
import io.enderdev.catalyx.client.gui.wrappers.CapabilityEnergyDisplayWrapper
import io.enderdev.catalyx.client.gui.wrappers.CapabilityFluidDisplayWrapper
import io.enderdev.catalyx.tiles.BaseMachineTile
import io.enderdev.emergingtechnology.Tags
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.inventory.Container
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.client.config.GuiUtils.drawTexturedModalRect
import kotlin.div

abstract class BaseGui(container: Container, tile: BaseMachineTile<*>) : BaseGui(container, tile) {
	override val powerBarTexture = ResourceLocation(Tags.MODID, "textures/gui/container/shared.png")
	override val buttonSide = ButtonSide.LEFT

	fun drawBar(storage: CapabilityDisplayWrapper) {
		if(storage.getStored() < 5)
			return

		val x = storage.x + ((width - xSize) shr 1)
		val y = storage.y + ((height - ySize) shr 1)
		val w = getBarScaled(storage.width, storage.getStored(), storage.getCapacity())
		val v = if(storage is CapabilityFluidDisplayWrapper) 9 else 0
		mc.textureManager.bindTexture(powerBarTexture)
		drawTexturedModalRect(x, y, 0, v, w, storage.height)
		mc.textureManager.bindTexture(textureLocation)
	}

	override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
		GlStateManager.color(1f, 1f, 1f, 1f)
		mc.textureManager.bindTexture(textureLocation)

		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize)
		displayData.forEach(this::drawBar)
	}
}
