package io.enderdev.emergingtechnology.client.gui

import io.enderdev.catalyx.client.gui.wrappers.CapabilityEnergyDisplayWrapper
import io.enderdev.catalyx.client.gui.wrappers.CapabilityFluidDisplayWrapper
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerTissueBioreactor
import io.enderdev.emergingtechnology.client.container.ContainerShredder
import io.enderdev.emergingtechnology.tiles.TileTissueBioreactor
import io.enderdev.emergingtechnology.tiles.TileShredder
import net.minecraft.inventory.IInventory
import net.minecraft.util.ResourceLocation

class GuiTissueBioreactor(playerInv: IInventory, tile: TileTissueBioreactor) : BaseETGui(ContainerTissueBioreactor(playerInv, tile), tile) {
	override val textureLocation = ResourceLocation(Tags.MODID, "textures/gui/container/tissue_bioreactor_gui.png")

	// TODO the title clips into the power bar

	init {
		displayData.add(CapabilityEnergyDisplayWrapper(129, 22, 39, 9, tile::energyStorage))
		displayData.add(CapabilityFluidDisplayWrapper(129, 7, 39, 9, tile::inputTank))
	}

	override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY)
		drawProgressBar(35, 38, 175, 0, 40, 10)
	}
}
