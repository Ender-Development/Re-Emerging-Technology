package io.enderdev.emergingtechnology.client.gui

import io.enderdev.catalyx.client.gui.wrappers.CapabilityEnergyDisplayWrapper
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerTissueScaffolder
import io.enderdev.emergingtechnology.tiles.TileTissueScaffolder
import net.minecraft.inventory.IInventory
import net.minecraft.util.ResourceLocation

class GuiTissueScaffolder(playerInv: IInventory, tile: TileTissueScaffolder) : BaseETGui(ContainerTissueScaffolder(playerInv, tile), tile) {
	override val textureLocation = ResourceLocation(Tags.MODID, "textures/gui/container/tissue_scaffolder_gui.png")

	// TODO the title clips into the power bar

	init {
		displayData.add(CapabilityEnergyDisplayWrapper(129, 7, 39, 9, tile::energyStorage))
	}

	override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY)
		drawProgressBar(35, 38, 175, 0, 40, 10)
	}
}
