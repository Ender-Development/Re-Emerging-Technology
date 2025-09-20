package io.enderdev.emergingtechnology.client.gui

import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerSolarCooker
import io.enderdev.emergingtechnology.tiles.TileSolarCooker
import net.minecraft.inventory.IInventory
import net.minecraft.util.ResourceLocation
import org.ender_development.catalyx.client.gui.wrappers.CapabilityDisplayWrapper

class GuiSolarCooker(playerInv: IInventory, tile: TileSolarCooker) : BaseETGui(ContainerSolarCooker(playerInv, tile), tile) {
	override val textureLocation = ResourceLocation(Tags.MODID, "textures/gui/container/solar_cooker_gui.png")

	init {
		displayData.add(HeatDisplayWrapper(129, 7, 39, 9, tile::heat, TileSolarCooker.MAX_HEAT))
	}

	override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY)
		drawProgressBar(35, 38, 175, 0, 41, 10)
	}

	class HeatDisplayWrapper(x: Int, y: Int, width: Int, height: Int, val heatGetter: () -> Int, val max: Int) : CapabilityDisplayWrapper(x, y, width, height) {
		override fun getStored() = heatGetter()
		override fun getCapacity() = max
		override fun toStringList() = listOf("${heatGetter()}/$max °C") // TODO: does this unit make sense with config values (no, it really doesn't, but it's kinda funny)
	}
}
