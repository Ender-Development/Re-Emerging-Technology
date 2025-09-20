package io.enderdev.emergingtechnology.client.container

import io.enderdev.emergingtechnology.tiles.TileHydroponicGrowLight
import net.minecraft.inventory.IInventory
import net.minecraftforge.items.SlotItemHandler
import org.ender_development.catalyx.client.container.BaseContainer

class ContainerHydroponicGrowLight(playerInv: IInventory, tile: TileHydroponicGrowLight) : BaseContainer(playerInv, tile) {
	init {
		addSlotToContainer(SlotItemHandler(tile.input, 0, 16, 35))
	}
}
