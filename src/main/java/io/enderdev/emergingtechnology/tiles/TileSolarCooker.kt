package io.enderdev.emergingtechnology.tiles

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.recipes.ModRecipes
import io.enderdev.emergingtechnology.recipes.SolarCookerRecipe
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import org.ender_development.catalyx.tiles.BaseMachineTile
import org.ender_development.catalyx.tiles.helper.TileStackHandler
import org.ender_development.catalyx.utils.extensions.canMergeWith
import org.ender_development.catalyx.utils.extensions.get

class TileSolarCooker : BaseMachineTile<SolarCookerRecipe>(EmergingTechnology.catalyxSettings) {
	init {
		initInventoryCapability(1, 1)
	}

	companion object {
		const val MAX_HEAT = 675 // value copied from EMT
	}

	override fun initInventoryInputCapability() {
		input = object : TileStackHandler(inputSlots, this) {
			override fun isItemValid(slot: Int, stack: ItemStack) =
				ModRecipes.solarCookerRecipes.recipes.any { it.input.test(stack) }
		}
	}

	override val recipeTime = EmergingTechnologyConfig.SYNTHETICS_MODULE.COOKER.cookerBaseTimeTaken
	override val energyPerTick = 0
	var heat = 0
		set(value) {
			field = value.coerceIn(0, MAX_HEAT)
		}

	override fun updateRecipe() {
		currentRecipe = if(input[0].isEmpty) null else ModRecipes.solarCookerRecipes.recipes.firstOrNull { it.input.test(input[0]) }
	}

	override fun onProcessComplete() {
		input.decrementSlot(0, 1) // Ingredients don't have any amount
		output.setOrIncrement(0, currentRecipe!!.output.copy())
		markDirtyGUI()
	}

	override fun onWorkTick() {}

	override fun onIdleTick() {
		updateRecipe()

		if(world.canSeeSky(pos) && world.isDaytime)
			heat += EmergingTechnologyConfig.SYNTHETICS_MODULE.COOKER.cookerBaseHeatGain
		else
			heat -= EmergingTechnologyConfig.SYNTHETICS_MODULE.COOKER.cookerBaseHeatLoss // this should really be called heat dissipation

		markDirtyGUIEvery(5)
	}

	override fun shouldTick() = true

	override fun shouldProcess() = currentRecipe!!.output.canMergeWith(output[0], true) && heat >= EmergingTechnologyConfig.SYNTHETICS_MODULE.COOKER.cookerRequiredCookingHeat

	override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
		super.writeToNBT(compound)
		compound.setInteger("Heat", heat)
		return compound
	}

	override fun readFromNBT(compound: NBTTagCompound) {
		super.readFromNBT(compound)
		heat = compound.getInteger("Heat")
	}
}
