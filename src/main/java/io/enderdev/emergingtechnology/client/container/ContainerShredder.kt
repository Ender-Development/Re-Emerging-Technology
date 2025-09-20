package io.enderdev.emergingtechnology.client.container

import io.enderdev.emergingtechnology.tiles.TileShredder
import net.minecraft.inventory.IInventory
import net.minecraftforge.items.SlotItemHandler
import org.ender_development.catalyx.client.container.BaseContainer

class ContainerShredder(playerInv: IInventory, tile: TileShredder) : BaseContainer(playerInv, tile) {
	init {
		addSlotToContainer(SlotItemHandler(tile.input, 0, 17, 35))
		addSlotToContainer(SlotItemHandler(tile.output, 0, 77, 35))
	}
}
