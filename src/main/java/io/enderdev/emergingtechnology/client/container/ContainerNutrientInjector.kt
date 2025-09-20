package io.enderdev.emergingtechnology.client.container

import io.enderdev.emergingtechnology.tiles.TileNutrientInjector
import net.minecraft.inventory.IInventory
import net.minecraftforge.items.SlotItemHandler
import org.ender_development.catalyx.client.container.BaseContainer

class ContainerNutrientInjector(playerInv: IInventory, tile: TileNutrientInjector) : BaseContainer(playerInv, tile) {
	init {
		addSlotToContainer(SlotItemHandler(tile.input, 0, 16, 35))
		addSlotToContainer(SlotItemHandler(tile.output, 0, 79, 35))
	}
}
