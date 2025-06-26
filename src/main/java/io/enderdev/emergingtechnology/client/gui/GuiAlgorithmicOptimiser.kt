package io.enderdev.emergingtechnology.client.gui

import io.enderdev.catalyx.client.gui.wrappers.CapabilityEnergyDisplayWrapper
import io.enderdev.catalyx.client.gui.wrappers.CapabilityFluidDisplayWrapper
import io.enderdev.catalyx.network.ButtonPacket
import io.enderdev.catalyx.network.PacketHandler
import io.enderdev.catalyx.utils.extensions.translate
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerAlgorithmicOptimiser
import io.enderdev.emergingtechnology.tiles.MutableOptimiserData
import io.enderdev.emergingtechnology.tiles.OptimiserResource
import io.enderdev.emergingtechnology.tiles.TileAlgorithmicOptimiser
import net.minecraft.client.gui.GuiButton
import net.minecraft.inventory.IInventory
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import java.awt.Color

class GuiAlgorithmicOptimiser(playerInv: IInventory, tile: TileAlgorithmicOptimiser) : BaseETGuiTyped<TileAlgorithmicOptimiser>(ContainerAlgorithmicOptimiser(playerInv, tile), tile) {
	override val textureLocation = ResourceLocation(Tags.MODID, "textures/gui/container/algorithmic_optimiser_gui.png")

	@Suppress("CanBePrimaryConstructorProperty") // do not.
	val tile = tile

	init {
		displayData.add(CapabilityEnergyDisplayWrapper(129, 22, 39, 9, tile::energyStorage))
		displayData.add(CapabilityFluidDisplayWrapper(129, 7, 39, 9, tile::inputTank))
	}

	override val displayName = "tile.${Tags.MODID}:algorithmic_optimiser.name.short".translate()

	val assignments = MutableOptimiserData(tile.assignments.energy, tile.assignments.water, tile.assignments.gas, tile.assignments.recipeTime, BlockPos.ORIGIN, 1)
	val minusButtons = mutableListOf<TileAlgorithmicOptimiser.AssignButton>()
	val plusButtons = mutableListOf<TileAlgorithmicOptimiser.AssignButton>()

	override fun initGui() {
		val halfX = ((width - xSize) shr 1)
		val halfY = ((height - ySize) shr 1)
		minusButtons.clear()
		plusButtons.clear()
		for(resource in arrayOf(OptimiserResource.WATER, OptimiserResource.ENERGY, OptimiserResource.GAS, OptimiserResource.RECIPE_TIME)) {
			val minus = TileAlgorithmicOptimiser.AssignButton(halfX + 49, halfY + 28 + resource.ordinal * 16, -1, resource)
			buttonList.add(minus)
			minusButtons.add(minus)
			val plus = TileAlgorithmicOptimiser.AssignButton(halfX + 92, halfY + 28 + resource.ordinal * 16, 1, resource)
			buttonList.add(plus)
			plusButtons.add(plus)
		}
		super.initGui()
		updateButtonVisibility()
	}

	override fun actionPerformed(button: GuiButton) {
		if(button is TileAlgorithmicOptimiser.AssignButton) {
			updateButtonVisibility()
			var btn = button
			// if shift key is down, set to 0 / max out
			if(isShiftKeyDown())
				btn = TileAlgorithmicOptimiser.AssignButton(button.x, button.y, if(button.count < 0) -assignments[button.resource] else (tile.getCores() - assignments.sum()).coerceAtMost(5), button.resource)
			assignments.add(button.resource, btn.count)
			updateButtonVisibility()
			PacketHandler.channel.sendToServer(ButtonPacket(tile.pos, btn))
		} else
			super.actionPerformed(button)
	}

	fun updateButtonVisibility() {
		assignments.water = tile.assignments.water
		assignments.energy = tile.assignments.energy
		assignments.gas = tile.assignments.gas
		assignments.recipeTime = tile.assignments.recipeTime

		minusButtons[0].visible = assignments.water != 0
		minusButtons[1].visible = assignments.energy != 0
		minusButtons[2].visible = assignments.gas != 0
		minusButtons[3].visible = assignments.recipeTime != 0

		if(assignments.sum() >= tile.getCores()) {
			plusButtons.forEach { it.visible = false }
		} else {
			plusButtons[0].visible = assignments.water != 5
			plusButtons[1].visible = assignments.energy != 5
			plusButtons[2].visible = assignments.gas != 5
			plusButtons[3].visible = assignments.recipeTime != 5
		}
	}

	var updateTimer = 0

	override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY)

		val halfX = (width - xSize) shr 1
		val halfY = (height - ySize) shr 1
		fontRenderer.drawString(assignments.water.toString(), plusButtons[0].x - 12 - halfX, plusButtons[0].y + 4 - halfY, Color.LIGHT_GRAY.rgb)
		fontRenderer.drawString(assignments.energy.toString(), plusButtons[1].x - 12 - halfX, plusButtons[1].y + 4 - halfY, Color.LIGHT_GRAY.rgb)
		fontRenderer.drawString(assignments.gas.toString(), plusButtons[2].x - 12 - halfX, plusButtons[2].y + 4 - halfY, Color.LIGHT_GRAY.rgb)
		fontRenderer.drawString(assignments.recipeTime.toString(), plusButtons[3].x - 12 - halfX, plusButtons[3].y + 4 - halfY, Color.LIGHT_GRAY.rgb)

		if(++updateTimer == 31) { // TODO this is the best way I could think of (at almost 2 am) to update the button visibility when the item is put into the GUI
			updateTimer = 0
			updateButtonVisibility()
		}
	}

	override fun renderTooltips(mouseX: Int, mouseY: Int) {
		minusButtons.indices.forEach {
			val minus = minusButtons[it]
			val plus = plusButtons[it]
			// would use isHovered but that takes x,y,w,h instead of x1,y1,x2,y2
			if(mouseX > minus.x + minus.width && mouseX < plus.x - 1 && mouseY >= minus.y && mouseY < plus.y + plus.height) {
				val value = assignments[minus.resource] * 10
				val text = if(minus.resource == OptimiserResource.RECIPE_TIME)
					"tile.${Tags.MODID}:algorithmic_optimiser.tooltip.recipe_time".translate(value)
				else
					"tile.${Tags.MODID}:algorithmic_optimiser.tooltip.resource".translate("info.${Tags.MODID}.${minus.resource.name.lowercase()}.name".translate(), value)
				drawHoveringText(text, mouseX, mouseY)
			}
		}
		super.renderTooltips(mouseX, mouseY)
	}
}
