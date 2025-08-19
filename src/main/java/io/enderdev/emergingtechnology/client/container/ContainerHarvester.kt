package io.enderdev.emergingtechnology.client.container

import io.enderdev.catalyx.client.container.BaseContainer
import io.enderdev.emergingtechnology.tiles.TileHarvester
import net.minecraft.inventory.IInventory
import net.minecraftforge.items.SlotItemHandler

class ContainerHarvester(playerInv: IInventory, tile: TileHarvester) : BaseContainer(playerInv, tile) {
	init {
		addSlotToContainer(SlotItemHandler(tile.input, 0, 16, 35))
		addSlotToContainer(SlotItemHandler(tile.output, 0, 54, 35))
		addSlotToContainer(SlotItemHandler(tile.output, 1, 79, 35))
		addSlotToContainer(SlotItemHandler(tile.output, 2, 104, 35))
	}
}
