package io.enderdev.emergingtechnology.client.gui

import io.enderdev.catalyx.client.gui.wrappers.CapabilityEnergyDisplayWrapper
import io.enderdev.catalyx.client.gui.wrappers.CapabilityFluidDisplayWrapper
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerHarvester
import io.enderdev.emergingtechnology.tiles.TileHarvester
import net.minecraft.inventory.IInventory
import net.minecraft.util.ResourceLocation

class GuiHarvester(playerInv: IInventory, tile: TileHarvester) : BaseETGuiTyped<TileHarvester>(ContainerHarvester(playerInv, tile), tile) {
	override val textureLocation = ResourceLocation(Tags.MODID, "textures/gui/container/harvester_gui.png")

	init {
		displayData.add(CapabilityEnergyDisplayWrapper(129, 7, 39, 9, tile::energyStorage))
	}
}
