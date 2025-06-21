package io.enderdev.emergingtechnology.client.gui

import io.enderdev.emergingtechnology.client.container.ContainerFissionController
import io.enderdev.emergingtechnology.tiles.TileFissionController
import net.minecraft.entity.player.InventoryPlayer

class GuiFissionController(playerInv: InventoryPlayer, tile: TileFissionController) :
	GuiReactorController<TileFissionController>(ContainerFissionController(playerInv, tile), tile, "fission_controller") {

	override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY)
		drawProgressBar(70, 75, 175, 0, 36, 16)
	}
}
