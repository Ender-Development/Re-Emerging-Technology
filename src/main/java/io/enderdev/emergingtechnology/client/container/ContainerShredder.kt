package io.enderdev.emergingtechnology.client.container

import io.enderdev.catalyx.client.container.BaseContainer
import io.enderdev.emergingtechnology.tiles.TileShredder
import io.enderdev.emergingtechnology.tiles.TileWasteCollector
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.IInventory
import net.minecraftforge.items.SlotItemHandler

class ContainerShredder(playerInv: IInventory, tile: TileShredder) : BaseContainer(playerInv, tile) {
	override fun addOwnSlots() {
		addSlotToContainer(SlotItemHandler(tile.input, 0, 17, 51))
		addSlotToContainer(SlotItemHandler(tile.output, 0, 77, 51))
	}
}
