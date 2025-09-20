package io.enderdev.emergingtechnology.tiles

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.items.ModItems
import io.enderdev.emergingtechnology.recipes.FabricatorRecipe
import io.enderdev.emergingtechnology.recipes.ModRecipes
import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ResourceLocation
import org.ender_development.catalyx.client.button.AbstractButtonWrapper
import org.ender_development.catalyx.tiles.BaseMachineTile
import org.ender_development.catalyx.tiles.helper.EnergyTileImpl
import org.ender_development.catalyx.tiles.helper.ICopyPasteExtraTile
import org.ender_development.catalyx.tiles.helper.IEnergyTile
import org.ender_development.catalyx.tiles.helper.TileStackHandler
import org.ender_development.catalyx.utils.extensions.canMergeWith
import org.ender_development.catalyx.utils.extensions.get

class TileFabricator : BaseMachineTile<FabricatorRecipe>(EmergingTechnology.catalyxSettings), IEnergyTile by EnergyTileImpl(10000), IOptimisableTile by OptimisableTileImpl(), ICopyPasteExtraTile {
	init {
		initInventoryCapability(1, 1)
	}

	override fun initInventoryInputCapability() {
		input = object : TileStackHandler(inputSlots, this) {
			override fun isItemValid(slot: Int, stack: ItemStack) = stack.item === ModItems.filament
		}
	}

	override val recipeTime: Int
		get() = getEffectiveRecipeTime(EmergingTechnologyConfig.POLYMERS_MODULE.FABRICATOR.fabricatorBaseTimeTaken)
	override val energyPerTick: Int
		get() = getEffectiveEnergyUsage(EmergingTechnologyConfig.POLYMERS_MODULE.FABRICATOR.fabricatorEnergyBaseUsage)

	override fun onIdleTick() {
		updateRecipe()
		optimisationTick()
	}

	var recipeId = 0
	var stopped = true

	override fun updateRecipe() {
		currentRecipe = ModRecipes.fabricatorRecipes.recipes.first { it.id == this@TileFabricator.recipeId }
	}

	override fun onProcessComplete() {
		input.decrementSlot(0, currentRecipe!!.filamentCount)
		output.setOrIncrement(0, currentRecipe!!.output.copy())
	}

	override fun onWorkTick() {
		energyStorage.extractEnergy(energyPerTick, false)
		markDirtyGUI() // looks cool
	}

	override fun shouldTick() = true

	override fun shouldProcess() = !stopped && input[0].count >= currentRecipe!!.filamentCount && currentRecipe!!.output.canMergeWith(output[0], true) && energyStorage.energyStored >= energyPerTick

	override fun shouldResetProgress() = false

	override fun handleButtonPress(button: AbstractButtonWrapper) {
		if(button is UpdateButtonWrapper) {
			recipeId = button.recipeId
			val lastId = ModRecipes.fabricatorRecipes.recipes.last().id // please ftlog be sequential
			// sanity check
			if(recipeId < 0)
				recipeId = lastId - 1
			else if(recipeId >= lastId)
				recipeId %= lastId
			stopped = button.stopped
		} else
			super.handleButtonPress(button)
	}

	override fun readFromNBT(compound: NBTTagCompound) {
		super.readFromNBT(compound)
		recipeId = compound.getInteger("RecipeId")
		if(compound.hasKey("Stopped"))
			stopped = compound.getBoolean("Stopped")
		optimise(OptimiserData.readFromNBT(compound.getCompoundTag("OptimiserData")))
	}

	override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
		super.writeToNBT(compound)
		compound.setInteger("RecipeId", recipeId)
		compound.setBoolean("Stopped", stopped)
		compound.setTag("OptimiserData", getOptimisation()?.writeToNBT(NBTTagCompound()) ?: NBTTagCompound())
		return compound
	}

	class UpdateButtonWrapper(x: Int, y: Int) : AbstractButtonWrapper(x, y) {
		var recipeId = 0
		var stopped = true

		var drawStyle = DrawStyle.NONE

		override val drawButton: () -> GuiButton.(Minecraft, Int, Int, Float) -> Unit = { drawButton@{ mc, mouseX, mouseY, partialTicks ->
			if(drawStyle == DrawStyle.NONE)
				return@drawButton

			mc.textureManager.bindTexture(ResourceLocation(Tags.MODID, "textures/gui/container/fabricator_gui.png"))
			GlStateManager.color(1F, 1F, 1F)
			val v = when(drawStyle) {
				DrawStyle.LEFT -> 0
				DrawStyle.RIGHT -> 16
				DrawStyle.STOPPED -> if(stopped) 32 else 48
				DrawStyle.NONE -> throw IllegalStateException()
			}
			drawTexturedModalRect(x, y, 175, v, 16, 16)
		} }

		override fun readExtraData(buf: ByteBuf) {
			recipeId = buf.readInt()
			stopped = buf.readBoolean()
		}

		override fun writeExtraData(buf: ByteBuf) {
			buf.writeInt(recipeId)
			buf.writeBoolean(stopped)
		}

		constructor(x: Int, y: Int, recipeId: Int, stopped: Boolean) : this(x, y) {
			this.recipeId = recipeId
			this.stopped = stopped
		}

		enum class DrawStyle {
			NONE, LEFT, RIGHT, STOPPED;
		}
	}

	init {
		AbstractButtonWrapper.registerWrapper(UpdateButtonWrapper::class.java)
	}

	// ICopyPasteExtraTile

	override fun copyData(tag: NBTTagCompound) {
		tag.setInteger("RecipeId", recipeId)
		tag.setBoolean("Stopped", stopped)
	}

	override fun pasteData(tag: NBTTagCompound) {
		if(tag.hasKey("RecipeId"))
			recipeId = tag.getInteger("RecipeId")

		if(tag.hasKey("Stopped"))
			stopped = tag.getBoolean("Stopped")
	}
}
