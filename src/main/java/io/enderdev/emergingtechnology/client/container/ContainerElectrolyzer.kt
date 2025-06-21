package io.enderdev.emergingtechnology.client.container

import io.enderdev.emergingtechnology.tiles.TileElectrolyzer
import io.enderdev.catalyx.client.container.BaseContainer
import io.enderdev.catalyx.tiles.helper.TileStackHandler
import net.minecraft.entity.player.InventoryPlayer
import net.minecraftforge.items.SlotItemHandler

class ContainerElectrolyzer(playerInv: InventoryPlayer, tile: TileElectrolyzer) :
	BaseContainer<TileElectrolyzer>(playerInv, tile) {

	override fun addOwnSlots() {
		if(tile.input.slots < 1) {
			tile.input = TileStackHandler(1, tile)
		}
		addSlotToContainer(SlotItemHandler(tile.input, 0, 80, 39))
		addSlotArray(116, 57, 2, 2, tile.output)
	}
}
