package io.enderdev.emergingtechnology.client.gui

import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerHydroponicGrowLight
import io.enderdev.emergingtechnology.tiles.TileHydroponicGrowLight
import net.minecraft.inventory.IInventory
import net.minecraft.util.ResourceLocation
import org.ender_development.catalyx.client.gui.wrappers.CapabilityEnergyDisplayWrapper
import org.ender_development.catalyx.utils.extensions.translate
import java.awt.Color

class GuiHydroponicGrowLight(playerInv: IInventory, tile: TileHydroponicGrowLight) : BaseETGui(ContainerHydroponicGrowLight(playerInv, tile), tile) {
	override val textureLocation = ResourceLocation(Tags.MODID, "textures/gui/container/hydroponic_grow_light_gui.png")

	@Suppress("CanBePrimaryConstructorProperty")
	val tile = tile

	override val displayName = "tile.${Tags.MODID}:hydroponic_grow_light.name.short".translate()

	init {
		displayData.add(CapabilityEnergyDisplayWrapper(129, 7, 39, 9, tile::energyStorage))
	}

	override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY)
		fontRenderer.drawString("tile.${Tags.MODID}:hydroponic_grow_light.growth".translate(tile.growthMult), 38, 34, Color.darkGray.rgb)
		fontRenderer.drawString("info.${Tags.MODID}:energy.required".translate(tile.energyPerTick * tile.energyMult), 38, 34 + fontRenderer.FONT_HEIGHT, Color.darkGray.rgb)
	}
}
