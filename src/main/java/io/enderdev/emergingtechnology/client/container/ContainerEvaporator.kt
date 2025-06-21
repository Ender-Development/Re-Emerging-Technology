package io.enderdev.emergingtechnology.client.container

import io.enderdev.emergingtechnology.tiles.TileEvaporator
import io.enderdev.catalyx.client.container.BaseContainer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraftforge.items.SlotItemHandler

class ContainerEvaporator(playerInv: InventoryPlayer, tile: TileEvaporator) :
	BaseContainer<TileEvaporator>(playerInv, tile) {

	override fun addOwnSlots() {
		this.addSlotToContainer(SlotItemHandler(tile.output, 0, 116, 75))
	}
}
