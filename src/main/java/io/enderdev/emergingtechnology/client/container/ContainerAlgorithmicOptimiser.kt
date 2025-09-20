package io.enderdev.emergingtechnology.client.container

import io.enderdev.emergingtechnology.tiles.TileAlgorithmicOptimiser
import net.minecraft.inventory.IInventory
import net.minecraftforge.items.SlotItemHandler
import org.ender_development.catalyx.client.container.BaseContainer

class ContainerAlgorithmicOptimiser(playerInv: IInventory, tile: TileAlgorithmicOptimiser) : BaseContainer(playerInv, tile) {
	init {
		addSlotToContainer(SlotItemHandler(tile.input, 0, 16, 35))
	}
}
