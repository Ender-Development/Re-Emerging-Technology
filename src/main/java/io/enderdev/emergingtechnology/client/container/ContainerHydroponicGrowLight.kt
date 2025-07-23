package io.enderdev.emergingtechnology.client.container

import io.enderdev.catalyx.client.container.BaseContainer
import io.enderdev.emergingtechnology.tiles.TileAlgorithmicOptimiser
import io.enderdev.emergingtechnology.tiles.TileHydroponicGrowLight
import net.minecraft.inventory.IInventory
import net.minecraftforge.items.SlotItemHandler

class ContainerHydroponicGrowLight(playerInv: IInventory, tile: TileHydroponicGrowLight) : BaseContainer(playerInv, tile) {
	init {
		addSlotToContainer(SlotItemHandler(tile.input, 0, 16, 35))
	}
}
