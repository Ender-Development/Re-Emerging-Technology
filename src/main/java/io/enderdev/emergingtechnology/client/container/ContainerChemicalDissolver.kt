package io.enderdev.emergingtechnology.client.container

import io.enderdev.emergingtechnology.tiles.TileChemicalDissolver
import io.enderdev.catalyx.client.container.BaseContainer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraftforge.items.SlotItemHandler

class ContainerChemicalDissolver(playerInv: InventoryPlayer, tile: TileChemicalDissolver) :
	BaseContainer<TileChemicalDissolver>(playerInv, tile) {

	override fun addOwnSlots() {
		addSlotToContainer(SlotItemHandler(tile.input, 0, 44, 57))
		addSlotArray(98, 39, 3, 4, tile.output)
	}
}
