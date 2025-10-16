package io.enderdev.emergingtechnology.client.gui

import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerFabricator
import io.enderdev.emergingtechnology.recipes.ModRecipes
import io.enderdev.emergingtechnology.tiles.TileFabricator
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.inventory.IInventory
import net.minecraft.util.ResourceLocation
import org.ender_development.catalyx.client.button.AbstractButtonWrapper
import org.ender_development.catalyx.client.gui.wrappers.CapabilityEnergyDisplayWrapper
import org.ender_development.catalyx.network.PacketHandler
import java.awt.Color

class GuiFabricator(playerInv: IInventory, val tile: TileFabricator) : BaseETGui(ContainerFabricator(playerInv, tile), tile) {
	override val textureLocation = ResourceLocation(Tags.MODID, "textures/gui/container/fabricator_gui.png")

	init {
		displayData.add(CapabilityEnergyDisplayWrapper(129, 7, 39, 9, tile::energyStorage))
	}

	lateinit var leftBtn: TileFabricator.UpdateButtonWrapper
	lateinit var stopStartBtn: TileFabricator.UpdateButtonWrapper
	lateinit var rightBtn: TileFabricator.UpdateButtonWrapper

	var stopped = tile.stopped
	var recipeId = tile.recipeId

	var halfX = 0
	var halfY = 0

	override fun initGui() {
		halfX = ((width - xSize) shr 1)
		halfY = ((height - ySize) shr 1)
		leftBtn = TileFabricator.UpdateButtonWrapper(halfX + 54, halfY + 57).apply { drawStyle = TileFabricator.UpdateButtonWrapper.DrawStyle.LEFT }
		stopStartBtn = TileFabricator.UpdateButtonWrapper(leftBtn.x + 16, leftBtn.y).apply { drawStyle = TileFabricator.UpdateButtonWrapper.DrawStyle.STOPPED }
		rightBtn = TileFabricator.UpdateButtonWrapper(stopStartBtn.x + 16, stopStartBtn.y).apply { drawStyle = TileFabricator.UpdateButtonWrapper.DrawStyle.RIGHT }
		buttonList.addAll(arrayOf(leftBtn.button, stopStartBtn.button, rightBtn.button))
		super.initGui()
		updateButtonState()
	}

	fun updateButtonState() {
		stopStartBtn.stopped = stopped
		leftBtn.button!!.visible = stopped
		rightBtn.button!!.visible = stopped
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
		val wrapper = AbstractButtonWrapper.getWrapper<TileFabricator.UpdateButtonWrapper>(button)
		if(wrapper == null)
			super.actionPerformed(button)
		else {
			when(wrapper) {
				leftBtn -> if(--recipeId <= 0) recipeId = lastRecipeId
				rightBtn -> recipeId = (recipeId + 1) % lastRecipeId
				stopStartBtn -> stopped = !stopped
			}
			wrapper.recipeId = recipeId
			wrapper.stopped = stopped
			updateButtonState()
			PacketHandler.sendWrapper(tileEntity.pos, wrapper)
		}
	}

	// on second thought, I'm not entirely sure this is a good idea, as scrolling would interfere with Mouse Tweaks/similar mods and Enter is just stupid
	//// scrolling changes the recipe
	//override fun handleMouseInput() {
	//	super.handleMouseInput()
	//	val scroll = Mouse.getEventDWheel()
	//	if(scroll != 0)
	//		actionPerformed((if(scroll > 0) rightBtn else leftBtn).button!!)
	//}
	//
	//// pressing enter stops & starts the recipe
	//override fun keyTyped(typedChar: Char, keyCode: Int) {
	//	super.keyTyped(typedChar, keyCode)
	//	if(typedChar == '\n')
	//		actionPerformed(stopStartBtn.button!!)
	//}

	// have to do this since the synced tile doesn't tick and thus doesn't update its recipe
	fun getRecipe() = ModRecipes.fabricatorRecipes.recipes.first { it.id == recipeId }

	val lastRecipeId: Int
		get() = ModRecipes.fabricatorRecipes.recipes.last().id
}
