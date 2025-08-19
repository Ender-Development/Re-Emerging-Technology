package io.enderdev.emergingtechnology.client.gui

import io.enderdev.catalyx.client.gui.wrappers.CapabilityEnergyDisplayWrapper
import io.enderdev.catalyx.client.gui.wrappers.CapabilityFluidDisplayWrapper
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerAlgaeBioreactor
import io.enderdev.emergingtechnology.tiles.TileAlgaeBioreactor
import net.minecraft.inventory.IInventory
import net.minecraft.util.ResourceLocation

class GuiAlgaeBioreactor(playerInv: IInventory, tile: TileAlgaeBioreactor) : BaseETGui(ContainerAlgaeBioreactor(playerInv, tile), tile) {
	override val textureLocation = ResourceLocation(Tags.MODID, "textures/gui/container/algae_bioreactor_gui.png")

	init {
		displayData.add(CapabilityEnergyDisplayWrapper(129, 22, 39, 9, tile::energyStorage))
		displayData.add(CapabilityFluidDisplayWrapper(129, 7, 39, 9, tile::waterTank))
		displayData.add(CapabilityFluidDisplayWrapper(68, 66, 39, 9, tile::gasTank))
	}

	override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY)
		drawProgressBar(35, 38, 175, 0, 41, 10)
	}
}
