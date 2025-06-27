package io.enderdev.emergingtechnology.client.container

import io.enderdev.catalyx.client.container.BaseContainer
import io.enderdev.emergingtechnology.tiles.TileTissueScaffolder
import net.minecraft.inventory.IInventory
import net.minecraftforge.items.SlotItemHandler

class ContainerTissueScaffolder(playerInv: IInventory, tile: TileTissueScaffolder) : BaseContainer(playerInv, tile) {
	init {
		addSlotToContainer(SlotItemHandler(tile.input, 0, 16, 35))
		addSlotToContainer(SlotItemHandler(tile.input, 1, 79, 72))
		addSlotToContainer(SlotItemHandler(tile.output, 0, 79, 35))
	}
}
