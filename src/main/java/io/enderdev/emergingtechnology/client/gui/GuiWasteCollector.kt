package io.enderdev.emergingtechnology.client.gui

import io.enderdev.catalyx.client.gui.BaseGui
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerWasteCollector
import io.enderdev.emergingtechnology.tiles.TileWasteCollector
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.util.ResourceLocation

class GuiWasteCollector(playerInv: IInventory, tile: TileWasteCollector) : BaseGui<TileWasteCollector>(ContainerWasteCollector(playerInv, tile), tile, "waste_collector") {
	override val textureLocation = ResourceLocation(Tags.MODID, "textures/gui/container/${guiName}_gui.png")
}
