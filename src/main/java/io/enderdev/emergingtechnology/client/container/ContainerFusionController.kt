package io.enderdev.emergingtechnology.client.container

import io.enderdev.emergingtechnology.tiles.FusionSlotHandler
import io.enderdev.emergingtechnology.tiles.TileFusionController
import io.enderdev.catalyx.client.container.BaseContainer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraftforge.items.SlotItemHandler

class ContainerFusionController(playerInv: InventoryPlayer, tile: TileFusionController) :
	BaseContainer<TileFusionController>(playerInv, tile) {

	override fun addOwnSlots() {
		this.addSlotToContainer(FusionSlotHandler(tile, tile.input, 0, 44, 75))
		this.addSlotToContainer(FusionSlotHandler(tile, tile.input, 1, 44 + 18, 75))
		this.addSlotToContainer(SlotItemHandler(tile.output, 0, 134, 75))
	}
}
