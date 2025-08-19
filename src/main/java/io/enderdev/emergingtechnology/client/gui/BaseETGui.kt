package io.enderdev.emergingtechnology.client.gui

import io.enderdev.catalyx.client.gui.BaseGuiTyped
import io.enderdev.catalyx.client.gui.wrappers.CapabilityDisplayWrapper
import io.enderdev.catalyx.client.gui.wrappers.CapabilityEnergyDisplayWrapper
import io.enderdev.catalyx.client.gui.wrappers.CapabilityFluidDisplayWrapper
import io.enderdev.catalyx.tiles.BaseMachineTile
import io.enderdev.catalyx.tiles.BaseTile
import io.enderdev.catalyx.tiles.helper.IGuiTile
import io.enderdev.catalyx.utils.RenderAlignment
import io.enderdev.catalyx.utils.extensions.get
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.fluids.ModFluids
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.inventory.Container
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fluids.FluidRegistry

abstract class BaseETGuiTyped<T>(container: Container, tile: T) : BaseGuiTyped<T>(container, tile) where T : IGuiTile, T : BaseTile, T : BaseGuiTyped.IDefaultButtonVariables {
	override val powerBarTexture = ResourceLocation(Tags.MODID, "textures/gui/container/shared.png")
	override val buttonAlignment = RenderAlignment(RenderAlignment.Alignment.MIDDLE_RIGHT)
	override val displayNameAlignment = RenderAlignment(RenderAlignment.Alignment.TOP_LEFT)

	fun drawBar(storage: CapabilityDisplayWrapper) {
		if(storage.getStored() < 5)
			return

		val x = storage.x + ((width - xSize) shr 1)
		val y = storage.y + ((height - ySize) shr 1)
		val w = getBarScaled(storage.width, storage.getStored(), storage.getCapacity())
		val v = when(storage) {
			is CapabilityFluidDisplayWrapper -> when(storage.getFluid()?.fluid) {
				FluidRegistry.WATER -> 9
				ModFluids.co2 -> 18
				ModFluids.nutrient -> 27
				else -> 100
			}
			is CapabilityEnergyDisplayWrapper -> 0
			is GuiSolarCooker.HeatDisplayWrapper -> 36
			else -> 100
		}
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

abstract class BaseETGui(container: Container, tile: BaseMachineTile<*>) : BaseETGuiTyped<BaseMachineTile<*>>(container, tile) {
	fun drawProgressBar(x: Int, y: Int, u: Int, v: Int, w: Int, h: Int) {
		mc.textureManager.bindTexture(textureLocation)
		val i = (width - xSize) shr 1
		val j = (height - ySize) shr 1
		if(tileEntity.recipeTime == 0 && !tileEntity.input[0].isEmpty) {
			drawTexturedModalRect(x + i, y + j, u, v, w, h)
		} else if(tileEntity.progressTicks > 0) {
			val k = getBarScaled(w, tileEntity.progressTicks, tileEntity.recipeTime)
			drawTexturedModalRect(x + i, y + j, u, v, k, h)
		}
	}
}
