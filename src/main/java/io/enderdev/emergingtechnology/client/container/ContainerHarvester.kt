package io.enderdev.emergingtechnology.client.container

import io.enderdev.emergingtechnology.tiles.TileHarvester
import net.minecraft.inventory.IInventory
import net.minecraftforge.items.SlotItemHandler
import org.ender_development.catalyx.client.container.BaseContainer

class ContainerHarvester(playerInv: IInventory, tile: TileHarvester) : BaseContainer(playerInv, tile) {
	init {
		addSlotToContainer(SlotItemHandler(tile.input, 0, 16, 35))
		addSlotArray(61, 35, 1, 3, tile.output)
	}
}
