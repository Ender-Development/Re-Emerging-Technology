package io.enderdev.emergingtechnology.client.container

import io.enderdev.catalyx.client.container.BaseContainer
import io.enderdev.emergingtechnology.tiles.TileCo2Diffuser
import net.minecraft.inventory.IInventory
import net.minecraftforge.items.SlotItemHandler

class ContainerCo2Diffuser(playerInv: IInventory, tile: TileCo2Diffuser) : BaseContainer(playerInv, tile) {
	init {
		addSlotToContainer(SlotItemHandler(tile.input, 0, 16, 35))
	}
}
