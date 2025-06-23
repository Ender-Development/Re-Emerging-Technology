package io.enderdev.emergingtechnology.client.gui

import io.enderdev.catalyx.client.gui.BaseGui
import io.enderdev.catalyx.client.gui.wrappers.CapabilityEnergyDisplayWrapper
import io.enderdev.catalyx.tiles.BaseMachineTile
import io.enderdev.emergingtechnology.Tags
import net.minecraft.inventory.Container
import net.minecraft.util.ResourceLocation

abstract class BaseGui(container: Container, tile: BaseMachineTile<*>) : BaseGui(container, tile) {
	override val powerBarTexture = ResourceLocation(Tags.MODID, "textures/gui/container/shared.png")

	override fun drawPowerBar(storage: CapabilityEnergyDisplayWrapper, texture: ResourceLocation, textureX: Int, textureY: Int) {
		if(storage.getStored() == 0)
			return

		val x = storage.x + ((width - xSize) shr 1)
		val y = storage.y + ((height - ySize) shr 1)
		val w = getBarScaled(storage.width, storage.getStored(), storage.getCapacity())
		mc.textureManager.bindTexture(texture)
		drawTexturedModalRect(x, y, textureX, textureY, w, storage.height)
		mc.textureManager.bindTexture(textureLocation)
	}
}
