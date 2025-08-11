package io.enderdev.emergingtechnology.client.container

import io.enderdev.catalyx.client.container.BaseContainer
import io.enderdev.emergingtechnology.tiles.TileCo2Scrubber
import net.minecraft.inventory.IInventory
import net.minecraftforge.items.SlotItemHandler

class ContainerCo2Scrubber(playerInv: IInventory, tile: TileCo2Scrubber) : BaseContainer(playerInv, tile) {
	init {
		addSlotToContainer(SlotItemHandler(tile.input, 0, 16, 35))
		addSlotToContainer(SlotItemHandler(tile.output, 0, 79, 35))
	}
}
