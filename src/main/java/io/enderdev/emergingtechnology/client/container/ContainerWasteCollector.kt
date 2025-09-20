package io.enderdev.emergingtechnology.client.container

import io.enderdev.emergingtechnology.tiles.TileWasteCollector
import net.minecraft.inventory.IInventory
import org.ender_development.catalyx.client.container.BaseContainer

class ContainerWasteCollector(playerInv: IInventory, tile: TileWasteCollector) : BaseContainer(playerInv, tile) {
	init {
		addSlotArray(44, 42, 1, 5, tile.output)
	}
}
