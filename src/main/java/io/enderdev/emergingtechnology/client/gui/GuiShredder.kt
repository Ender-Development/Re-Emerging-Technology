package io.enderdev.emergingtechnology.client.gui

import io.enderdev.catalyx.client.gui.wrappers.CapabilityEnergyDisplayWrapper
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerShredder
import io.enderdev.emergingtechnology.client.container.ContainerWasteCollector
import io.enderdev.emergingtechnology.tiles.TileShredder
import io.enderdev.emergingtechnology.tiles.TileWasteCollector
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.util.ResourceLocation

class GuiShredder(playerInv: IInventory, tile: TileShredder) : BaseGui(ContainerShredder(playerInv, tile), tile) {
	override val textureLocation = ResourceLocation(Tags.MODID, "textures/gui/container/shredder_gui.png")

	init {
		displayData.add(CapabilityEnergyDisplayWrapper(129, 23, 39, 9, tile::energyStorage))
	}

	override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY)
		drawProgressBar(36, 54, 175, 0, 37, 10)
	}
}
