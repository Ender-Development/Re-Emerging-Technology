package io.enderdev.emergingtechnology.client.gui

import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerAtomizer
import io.enderdev.emergingtechnology.tiles.TileAtomizer
import io.enderdev.catalyx.client.gui.BaseGui
import io.enderdev.catalyx.client.gui.wrappers.CapabilityEnergyDisplayWrapper
import io.enderdev.catalyx.client.gui.wrappers.CapabilityFluidDisplayWrapper
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.util.ResourceLocation

class GuiAtomizer(playerInv: InventoryPlayer, tile: TileAtomizer) :
	BaseGui<TileAtomizer>(ContainerAtomizer(playerInv, tile), tile, "atomizer") {

	override val textureLocation = ResourceLocation(Tags.MODID, "textures/gui/container/${guiName}_gui_redox.png")

	init {
		this.displayData.add(CapabilityEnergyDisplayWrapper(8, 21, 16, 70, tile::energyStorage))
		this.displayData.add(CapabilityFluidDisplayWrapper(44, 21, 16, 70, tile::inputTank))
	}

	override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY)
		drawProgressBar(70, 75, 175, 0, 36, 16)
	}
}
