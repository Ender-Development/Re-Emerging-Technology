package io.enderdev.emergingtechnology.client.container

import io.enderdev.emergingtechnology.tiles.TileFissionController
import io.enderdev.catalyx.client.container.BaseContainer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraftforge.items.SlotItemHandler

class ContainerFissionController(playerInv: InventoryPlayer, tile: TileFissionController) :
	BaseContainer<TileFissionController>(playerInv, tile) {

	override fun addOwnSlots() {
		this.addSlotToContainer(SlotItemHandler(tile.input, 0, 44, 75))
		addSlotArray(116, 75, 1, 2, tile.output)
	}
}
