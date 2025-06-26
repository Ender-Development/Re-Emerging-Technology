package io.enderdev.emergingtechnology.client.container

import io.enderdev.catalyx.client.container.BaseContainer
import io.enderdev.emergingtechnology.tiles.TileFabricator
import net.minecraft.inventory.IInventory
import net.minecraftforge.items.SlotItemHandler

class ContainerFabricator(playerInv: IInventory, tile: TileFabricator) : BaseContainer(playerInv, tile) {
	init {
		addSlotToContainer(SlotItemHandler(tile.input, 0, 16, 35))
		addSlotToContainer(SlotItemHandler(tile.output, 0, 122, 35))
	}
}
