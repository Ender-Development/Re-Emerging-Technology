package io.enderdev.emergingtechnology.client.container

import io.enderdev.catalyx.client.container.BaseContainer
import io.enderdev.emergingtechnology.tiles.TileProcessor
import net.minecraft.inventory.IInventory
import net.minecraftforge.items.SlotItemHandler

class ContainerProcessor(playerInv: IInventory, tile: TileProcessor) : BaseContainer(playerInv, tile) {
	override fun addOwnSlots() {
		addSlotToContainer(SlotItemHandler(tile.input, 0, 16, 35))
		addSlotToContainer(SlotItemHandler(tile.output, 0, 79, 35))
	}
}
