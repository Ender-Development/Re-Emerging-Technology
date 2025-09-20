package io.enderdev.emergingtechnology.tiles

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.recipes.ModRecipes
import io.enderdev.emergingtechnology.recipes.ShredderRecipe
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import org.ender_development.catalyx.tiles.BaseMachineTile
import org.ender_development.catalyx.tiles.helper.EnergyTileImpl
import org.ender_development.catalyx.tiles.helper.IEnergyTile
import org.ender_development.catalyx.tiles.helper.TileStackHandler
import org.ender_development.catalyx.utils.extensions.canMergeWith
import org.ender_development.catalyx.utils.extensions.get

class TileShredder : BaseMachineTile<ShredderRecipe>(EmergingTechnology.catalyxSettings), IEnergyTile by EnergyTileImpl(10000), IOptimisableTile by OptimisableTileImpl() {
	init {
		initInventoryCapability(1, 1)
	}

	override fun initInventoryInputCapability() {
		input = object : TileStackHandler(inputSlots, this) {
			override fun isItemValid(slot: Int, stack: ItemStack) =
				ModRecipes.shredderRecipes.recipes.any { it.input.test(stack) }
		}
	}

	override val recipeTime: Int
		get() = getEffectiveRecipeTime(EmergingTechnologyConfig.POLYMERS_MODULE.SHREDDER.shredderBaseTimeTaken)
	override val energyPerTick: Int
		get() = getEffectiveEnergyUsage(EmergingTechnologyConfig.POLYMERS_MODULE.SHREDDER.shredderEnergyBaseUsage)

	override fun onIdleTick() {
		updateRecipe()
		optimisationTick()
	}

	override fun updateRecipe() {
		currentRecipe = if(input[0].isEmpty) null else ModRecipes.shredderRecipes.recipes.firstOrNull { it.input.test(input[0]) }
	}

	override fun onProcessComplete() {
		input.decrementSlot(0, 1) // Ingredients don't have any amount
		output.setOrIncrement(0, currentRecipe!!.output.copy())
	}

	override fun onWorkTick() {
		energyStorage.extractEnergy(energyPerTick, false)
		markDirtyGUI() // looks cool
	}

	override fun shouldTick() = true

	override fun shouldProcess() = currentRecipe!!.output.canMergeWith(output[0], true) && energyStorage.energyStored >= energyPerTick

	override fun shouldResetProgress() = false

	override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
		super.writeToNBT(compound)
		compound.setTag("OptimiserData", getOptimisation()?.writeToNBT(NBTTagCompound()) ?: NBTTagCompound())
		return compound
	}

	override fun readFromNBT(compound: NBTTagCompound) {
		super.readFromNBT(compound)
		optimise(OptimiserData.readFromNBT(compound.getCompoundTag("OptimiserData")))
	}
}
