package io.enderdev.emergingtechnology.tiles

import io.enderdev.catalyx.client.button.AbstractButton
import io.enderdev.catalyx.tiles.BaseMachineTile
import io.enderdev.catalyx.tiles.helper.EnergyTileImpl
import io.enderdev.catalyx.tiles.helper.IEnergyTile
import io.enderdev.catalyx.tiles.helper.TileStackHandler
import io.enderdev.catalyx.utils.extensions.canMergeWith
import io.enderdev.catalyx.utils.extensions.get
import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.items.ModItems
import io.enderdev.emergingtechnology.recipes.FabricatorRecipe
import io.enderdev.emergingtechnology.recipes.ModRecipes
import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ResourceLocation

class TileFabricator : BaseMachineTile<FabricatorRecipe>(EmergingTechnology.catalyxSettings), IEnergyTile by EnergyTileImpl(10000), IOptimisableTile by OptimisableTileImpl() {
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

	override fun handleButtonPress(button: AbstractButton) {
		if(button is UpdateButton) {
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
	}

	override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
		super.writeToNBT(compound)
		compound.setInteger("RecipeId", recipeId)
		compound.setBoolean("Stopped", stopped)
		return compound
	}

	class UpdateButton(x: Int, y: Int) : AbstractButton(x, y) {
		var recipeId = 0
		var stopped = true

		var drawStyle = DrawStyle.NONE

		override fun drawButton(mc: Minecraft, mouseX: Int, mouseY: Int, partialTicks: Float) {
			if(drawStyle == DrawStyle.NONE)
				return

			if(visible) {
				mc.textureManager.bindTexture(ResourceLocation(Tags.MODID, "textures/gui/container/fabricator_gui.png"))
				GlStateManager.color(1F, 1F, 1F)
				val v = when(drawStyle) {
					DrawStyle.LEFT -> 0
					DrawStyle.RIGHT -> 16
					DrawStyle.STOPPED -> if(stopped) 32 else 48
					DrawStyle.NONE -> throw IllegalStateException()
				}
				drawTexturedModalRect(x, y, 175, v, 16, 16)
				super.drawButton(mc, mouseX, mouseY, partialTicks)
			}
		}

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
}
