package io.enderdev.emergingtechnology.client.container

import io.enderdev.emergingtechnology.tiles.TileLiquifier
import io.enderdev.catalyx.client.container.BaseContainer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraftforge.items.SlotItemHandler

class ContainerLiquifier(playerInv: InventoryPlayer, tile: TileLiquifier) :
	BaseContainer<TileLiquifier>(playerInv, tile) {

	override fun addOwnSlots() {
		this.addSlotToContainer(SlotItemHandler(tile.input, 0, 44, 75))
	}
}
