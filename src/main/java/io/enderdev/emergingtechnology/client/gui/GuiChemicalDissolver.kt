package io.enderdev.emergingtechnology.client.gui

import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerChemicalDissolver
import io.enderdev.emergingtechnology.tiles.TileChemicalDissolver
import io.enderdev.catalyx.client.gui.BaseGui
import io.enderdev.catalyx.client.gui.wrappers.CapabilityEnergyDisplayWrapper
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.util.ResourceLocation

class GuiChemicalDissolver(playerInv: InventoryPlayer, tile: TileChemicalDissolver) :
	BaseGui<TileChemicalDissolver>(ContainerChemicalDissolver(playerInv, tile), tile, "chemical_dissolver") {

	override val textureLocation = ResourceLocation(Tags.MODID, "textures/gui/container/${guiName}_gui_redox.png")

	init {
		this.displayData.add(CapabilityEnergyDisplayWrapper(8, 21, 16, 70, tile::energyStorage))
	}

	override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY)
		drawProgressBar(63, 43, 175, 0, 32, 44)
	}
}
