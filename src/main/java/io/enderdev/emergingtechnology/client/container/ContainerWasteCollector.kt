package io.enderdev.emergingtechnology.client.container

import io.enderdev.catalyx.client.container.BaseContainer
import io.enderdev.emergingtechnology.tiles.TileWasteCollector
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.IInventory
import net.minecraftforge.items.SlotItemHandler

class ContainerWasteCollector(playerInv: IInventory, tile: TileWasteCollector) : BaseContainer<TileWasteCollector>(playerInv, tile) {
	override fun addOwnSlots() {
		addSlotArray(44, 42, 1, 5, tile.output)
	}
}
