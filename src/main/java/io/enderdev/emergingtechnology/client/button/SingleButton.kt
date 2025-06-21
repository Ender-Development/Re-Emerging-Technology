package io.enderdev.emergingtechnology.client.button

import io.enderdev.emergingtechnology.Tags
import io.enderdev.catalyx.client.button.AbstractButton
import io.enderdev.catalyx.client.button.CatalyxButtons
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.util.ResourceLocation

class SingleButton(x: Int, y: Int) : AbstractButton(buttonId, x, y) {
	companion object {
		val buttonId = CatalyxButtons.nextId()
	}
	override val textureLocation = ResourceLocation(Tags.MODID, "textures/gui/container/template_redox.png")

	enum class State {
		SINGLE, REGULAR
	}

	var isSingle = State.REGULAR

	override fun drawButton(mc: Minecraft, mouseX: Int, mouseY: Int, partialTicks: Float) {
		if(visible) {
			mc.textureManager.bindTexture(textureLocation)
			GlStateManager.color(1F, 1F, 1F)
			val i = if(isSingle == State.SINGLE) 16 else 0
			drawTexturedModalRect(x, y, 80, i, 16, 16)
		}
		super.drawButton(mc, mouseX, mouseY, partialTicks)
	}
}
