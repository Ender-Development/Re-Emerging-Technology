package io.enderdev.emergingtechnology.client.container

import io.enderdev.catalyx.client.container.BaseContainer
import io.enderdev.emergingtechnology.tiles.TileTissueBioreactor
import net.minecraft.inventory.IInventory
import net.minecraftforge.items.SlotItemHandler

class ContainerTissueBioreactor(playerInv: IInventory, tile: TileTissueBioreactor) : BaseContainer(playerInv, tile) {
	init {
		addSlotToContainer(SlotItemHandler(tile.input, 0, 16, 35))
		addSlotToContainer(SlotItemHandler(tile.output, 0, 79, 35))
	}
}
