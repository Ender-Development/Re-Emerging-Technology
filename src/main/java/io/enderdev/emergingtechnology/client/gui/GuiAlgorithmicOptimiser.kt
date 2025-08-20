package io.enderdev.emergingtechnology.client.gui

import io.enderdev.catalyx.client.button.AbstractButtonWrapper
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

class GuiAlgorithmicOptimiser(playerInv: IInventory, val tile: TileAlgorithmicOptimiser) : BaseETGuiTyped<TileAlgorithmicOptimiser>(ContainerAlgorithmicOptimiser(playerInv, tile), tile) {
	override val textureLocation = ResourceLocation(Tags.MODID, "textures/gui/container/algorithmic_optimiser_gui.png")

	init {
		displayData.add(CapabilityEnergyDisplayWrapper(129, 22, 39, 9, tile::energyStorage))
		displayData.add(CapabilityFluidDisplayWrapper(129, 7, 39, 9, tile::inputTank))
	}

	val assignments = MutableOptimiserData(tile.assignments.energy, tile.assignments.water, tile.assignments.gas, tile.assignments.recipeTime, BlockPos.ORIGIN, 1)
	val minusButtons = mutableListOf<TileAlgorithmicOptimiser.AssignButtonWrapper>()
	val plusButtons = mutableListOf<TileAlgorithmicOptimiser.AssignButtonWrapper>()

	override fun initGui() {
		val halfX = ((width - xSize) shr 1)
		val halfY = ((height - ySize) shr 1)
		minusButtons.clear()
		plusButtons.clear()
		arrayOf(OptimiserResource.WATER, OptimiserResource.ENERGY, OptimiserResource.GAS, OptimiserResource.RECIPE_TIME).forEachIndexed { idx, resource ->
			val minus = TileAlgorithmicOptimiser.AssignButtonWrapper(halfX + 49, halfY + 28 + idx * 16, -1, resource)
			buttonList.add(minus.button)
			minusButtons.add(minus)
			val plus = TileAlgorithmicOptimiser.AssignButtonWrapper(halfX + 92, halfY + 28 + idx * 16, 1, resource)
			buttonList.add(plus.button)
			plusButtons.add(plus)
		}
		super.initGui()
		updateButtonVisibility()
	}

	override fun actionPerformed(button: GuiButton) {
		val wrapper = AbstractButtonWrapper.getWrapper<TileAlgorithmicOptimiser.AssignButtonWrapper>(button)
		if(wrapper == null)
			super.actionPerformed(button)
		else {
			updateButtonVisibility()
			// if shift key is down, set to 0 / max out
			if(isShiftKeyDown())
				wrapper.count = if(wrapper.count < 0)
					-assignments[wrapper.resource]
				else
					(tile.getCores() - assignments.sum()).coerceAtMost(5)
			assignments.add(wrapper.resource, wrapper.count)
			updateButtonVisibility()
			PacketHandler.channel.sendToServer(ButtonPacket(tile.pos, wrapper))
		}
	}

	fun updateButtonVisibility() {
		assignments.water = tile.assignments.water
		assignments.energy = tile.assignments.energy
		assignments.gas = tile.assignments.gas
		assignments.recipeTime = tile.assignments.recipeTime

		minusButtons[0].button!!.visible = assignments.water != 0
		minusButtons[1].button!!.visible = assignments.energy != 0
		minusButtons[2].button!!.visible = assignments.gas != 0
		minusButtons[3].button!!.visible = assignments.recipeTime != 0

		if(assignments.sum() >= tile.getCores()) {
			plusButtons.forEach { it.button!!.visible = false }
		} else {
			plusButtons[0].button!!.visible = assignments.water != 5
			plusButtons[1].button!!.visible = assignments.energy != 5
			plusButtons[2].button!!.visible = assignments.gas != 5
			plusButtons[3].button!!.visible = assignments.recipeTime != 5
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
					"tile.${Tags.MODID}:algorithmic_optimiser.tooltip.resource".translate("info.${Tags.MODID}:${minus.resource.name.lowercase()}.name".translate(), value)
				drawHoveringText(text, mouseX, mouseY)
			}
		}
		super.renderTooltips(mouseX, mouseY)
	}
}
