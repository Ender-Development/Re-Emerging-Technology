package io.enderdev.emergingtechnology.client.gui

import io.enderdev.catalyx.client.gui.wrappers.CapabilityEnergyDisplayWrapper
import io.enderdev.catalyx.client.gui.wrappers.CapabilityFluidDisplayWrapper
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerCo2Scrubber
import io.enderdev.emergingtechnology.tiles.TileCo2Scrubber
import net.minecraft.inventory.IInventory
import net.minecraft.util.ResourceLocation

class GuiCo2Scrubber(playerInv: IInventory, tile: TileCo2Scrubber) : BaseETGui(ContainerCo2Scrubber(playerInv, tile), tile) {
	override val textureLocation = ResourceLocation(Tags.MODID, "textures/gui/container/co2_scrubber_gui.png")

	init {
		displayData.add(CapabilityEnergyDisplayWrapper(129, 22, 39, 9, tile::energyStorage))
		displayData.add(CapabilityFluidDisplayWrapper(129, 7, 39, 9, tile::waterTank))
		displayData.add(CapabilityFluidDisplayWrapper(68, 63, 39, 9, tile::co2Tank))
	}

	override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY)
		drawProgressBar(35, 38, 175, 0, 41, 10)
	}
}
