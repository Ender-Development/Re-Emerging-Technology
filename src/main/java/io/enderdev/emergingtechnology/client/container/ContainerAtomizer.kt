package io.enderdev.emergingtechnology.client.container

import io.enderdev.emergingtechnology.tiles.TileAtomizer
import io.enderdev.catalyx.client.container.BaseContainer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraftforge.items.SlotItemHandler

class ContainerAtomizer(playerInv: InventoryPlayer, tile: TileAtomizer) :
	BaseContainer<TileAtomizer>(playerInv, tile) {

	override fun addOwnSlots() {
		this.addSlotToContainer(SlotItemHandler(tile.output, 0, 116, 75))
	}
}
