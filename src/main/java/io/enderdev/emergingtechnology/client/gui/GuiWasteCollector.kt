package io.enderdev.emergingtechnology.client.gui

import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerWasteCollector
import io.enderdev.emergingtechnology.tiles.TileWasteCollector
import net.minecraft.inventory.IInventory
import net.minecraft.util.ResourceLocation

class GuiWasteCollector(playerInv: IInventory, tile: TileWasteCollector) : BaseETGui(ContainerWasteCollector(playerInv, tile), tile) {
	override val textureLocation = ResourceLocation(Tags.MODID, "textures/gui/container/waste_collector_gui.png")
}
