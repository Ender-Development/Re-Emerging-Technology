package io.enderdev.emergingtechnology.client.gui

import io.enderdev.catalyx.client.gui.wrappers.CapabilityEnergyDisplayWrapper
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerFabricator
import io.enderdev.emergingtechnology.recipes.ModRecipes
import io.enderdev.emergingtechnology.tiles.TileFabricator
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.inventory.IInventory
import net.minecraft.util.ResourceLocation
import java.awt.Color

class GuiFabricator(playerInv: IInventory, tile: TileFabricator) : BaseETGui(ContainerFabricator(playerInv, tile), tile) {
	override val textureLocation = ResourceLocation(Tags.MODID, "textures/gui/container/fabricator_gui.png")

	@Suppress("CanBePrimaryConstructorProperty") // do not.
	val tile = tile

	init {
		displayData.add(CapabilityEnergyDisplayWrapper(129, 7, 39, 9, tile::energyStorage))
	}

	lateinit var leftBtn: TileFabricator.UpdateButton
	lateinit var stopStartBtn: TileFabricator.UpdateButton
	lateinit var rightBtn: TileFabricator.UpdateButton

	var stopped = tile.stopped
	var recipeId = tile.recipeId

	var halfX = 0
	var halfY = 0

	override fun initGui() {
		halfX = ((width - xSize) shr 1)
		halfY = ((height - ySize) shr 1)
		leftBtn = TileFabricator.UpdateButton(halfX + 54, halfY + 57).apply { drawStyle = TileFabricator.UpdateButton.DrawStyle.LEFT }
		stopStartBtn = TileFabricator.UpdateButton(leftBtn.x + 16, leftBtn.y).apply { drawStyle = TileFabricator.UpdateButton.DrawStyle.STOPPED }
		rightBtn = TileFabricator.UpdateButton(stopStartBtn.x + 16, stopStartBtn.y).apply { drawStyle = TileFabricator.UpdateButton.DrawStyle.RIGHT }
		buttonList.addAll(arrayOf(leftBtn, stopStartBtn, rightBtn))
		super.initGui()
		updateButtonState()
	}

	fun updateButtonState() {
		stopStartBtn.stopped = stopped
		leftBtn.visible = stopped
		rightBtn.visible = stopped
	}

	override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY)

		// 70,35 is item display
		val recipe = getRecipe()
		fontRenderer.drawString(recipe.filamentCount.toString(), 46, 31, Color.DARK_GRAY.rgb)
		RenderHelper.enableGUIStandardItemLighting()
		GlStateManager.color(1f, 1f, 1f, 1f)
		itemRender.zLevel = 0f
		itemRender.renderItemAndEffectIntoGUI(recipe.output, 70, 35)
		itemRender.renderItemOverlayIntoGUI(fontRenderer, recipe.output, 70, 35, null)
		if(!stopped) {
			mc.textureManager.bindTexture(textureLocation)
			// needed to draw over the thing that we're printing
			zLevel = itemRender.zLevel + 100f
			GlStateManager.translate(0f, 0f, zLevel)
			drawTexturedModalRect(70, 35, 70, 35, 16, 16 - getBarScaled(16, tile.progressTicks, tile.recipeTime))
			zLevel = 0f
			GlStateManager.translate(0f, 0f, zLevel)
		}
	}

	override fun renderTooltips(mouseX: Int, mouseY: Int) {
		super.renderTooltips(mouseX, mouseY)
		if(isHovered(halfX + 70, halfY + 35, 16, 16, mouseX, mouseY))
			renderToolTip(getRecipe().output, mouseX, mouseY)
	}

	override fun actionPerformed(button: GuiButton) {
		if(button is TileFabricator.UpdateButton) {
			if(button == leftBtn) {
				if(--recipeId <= 0)
					recipeId = lastRecipeId
			} else if(button == rightBtn)
				recipeId = (recipeId + 1) % lastRecipeId
			else if(button == stopStartBtn)
				stopped = !stopped
			updateButtonState()
			super.actionPerformed(TileFabricator.UpdateButton(button.x, button.y, recipeId, stopped))
		} else
			super.actionPerformed(button)
	}

	// TODO for me later, scrolling on the item selector should act like button clicks ;p

	// have to do this since the synced tile doesn't tick and thus doesn't update its recipe
	fun getRecipe() = ModRecipes.fabricatorRecipes.recipes.first { it.id == recipeId }

	val lastRecipeId: Int
		get() = ModRecipes.fabricatorRecipes.recipes.last().id
}
