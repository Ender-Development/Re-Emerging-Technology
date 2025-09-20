package io.enderdev.emergingtechnology.client.container

import io.enderdev.emergingtechnology.tiles.TileCo2Diffuser
import net.minecraft.inventory.IInventory
import net.minecraftforge.items.SlotItemHandler
import org.ender_development.catalyx.client.container.BaseContainer

class ContainerCo2Diffuser(playerInv: IInventory, tile: TileCo2Diffuser) : BaseContainer(playerInv, tile) {
	init {
		addSlotToContainer(SlotItemHandler(tile.input, 0, 16, 35))
	}
}
