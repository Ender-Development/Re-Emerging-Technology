package io.enderdev.emergingtechnology.client.container

import io.enderdev.catalyx.client.container.BaseContainer
import io.enderdev.emergingtechnology.tiles.TileBiomassGenerator
import net.minecraft.inventory.IInventory
import net.minecraftforge.items.SlotItemHandler

class ContainerBiomassGenerator(playerInv: IInventory, tile: TileBiomassGenerator) : BaseContainer(playerInv, tile) {
	init {
		addSlotToContainer(SlotItemHandler(tile.input, 0, 16, 35))
		addSlotToContainer(SlotItemHandler(tile.output, 0, 79, 35))
	}
}
