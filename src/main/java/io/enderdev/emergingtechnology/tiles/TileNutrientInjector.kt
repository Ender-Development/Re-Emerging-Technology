package io.enderdev.emergingtechnology.tiles

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.fluids.ModFluids
import io.enderdev.emergingtechnology.recipes.ModRecipes
import io.enderdev.emergingtechnology.recipes.NutrientInjectorRecipe
import io.enderdev.emergingtechnology.utils.CapabilityUtils
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.fluids.Fluid
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate
import org.ender_development.catalyx.tiles.BaseMachineTile
import org.ender_development.catalyx.tiles.helper.EnergyTileImpl
import org.ender_development.catalyx.tiles.helper.IEnergyTile
import org.ender_development.catalyx.tiles.helper.IFluidTile
import org.ender_development.catalyx.tiles.helper.TileStackHandler
import org.ender_development.catalyx.utils.FluidTankUtils
import org.ender_development.catalyx.utils.extensions.canMergeWith
import org.ender_development.catalyx.utils.extensions.get

class TileNutrientInjector : BaseMachineTile<NutrientInjectorRecipe>(EmergingTechnology), IEnergyTile by EnergyTileImpl(10000), IFluidTile, IOptimisableTile by OptimisableTileImpl() {
	init {
		initInventoryCapability(1, 1)
	}

	override fun initInventoryInputCapability() {
		input = object : TileStackHandler(inputSlots, this) {
			override fun isItemValid(slot: Int, stack: ItemStack) =
				ModRecipes.nutrientInjectorRecipes.recipes.any { it.input.test(stack) }
		}
	}

	val waterTank = FluidTankUtils.create(this, Fluid.BUCKET_VOLUME * 10, true, false, FluidRegistry.WATER, onContentsChangedCallback = this::markDirtyGUI)
	val nutrientTank = FluidTankUtils.create(this, Fluid.BUCKET_VOLUME * 10, false, true, ModFluids.nutrient, onContentsChangedCallback = this::markDirtyGUI)

	override val fluidHandler = FluidHandlerConcatenate(waterTank, nutrientTank)

	override val recipeTime: Int
		get() = getEffectiveRecipeTime(EmergingTechnologyConfig.HYDROPONICS_MODULE.INJECTOR.injectorBaseTimeTaken)
	override val energyPerTick: Int
		get() = getEffectiveEnergyUsage(EmergingTechnologyConfig.HYDROPONICS_MODULE.INJECTOR.injectorEnergyBaseUsage)
	val waterPerTick: Int
		get() = getEffectiveWaterUsage(EmergingTechnologyConfig.HYDROPONICS_MODULE.INJECTOR.injectorWaterBaseUsage)
	val nutrientGained = EmergingTechnologyConfig.HYDROPONICS_MODULE.INJECTOR.injectorFluidGenerated

	override fun onIdleTick() {
		updateRecipe()
		optimisationTick()
		CapabilityUtils.spreadLiquid(world, pos, nutrientTank, *EnumFacing.VALUES)
	}

	override fun updateRecipe() {
		currentRecipe = if(input[0].isEmpty) null else ModRecipes.nutrientInjectorRecipes.recipes.firstOrNull { it.input.test(input[0]) }
	}

	override fun onProcessComplete() {
		input.decrementSlot(0, 1) // Ingredients don't have any amount
		output.setOrIncrement(0, currentRecipe!!.output.copy())
		nutrientTank.fillInternal(FluidStack(ModFluids.nutrient, nutrientGained), true)
	}

	override fun onWorkTick() {
		energyStorage.extractEnergy(energyPerTick, false)
		waterTank.drainInternal(waterPerTick, true)
		markDirtyGUI() // looks cool
	}

	override fun shouldTick() = true

	override fun shouldProcess() = currentRecipe!!.output.canMergeWith(output[0], true) && energyStorage.energyStored >= energyPerTick && waterTank.fluidAmount >= waterPerTick && nutrientTank.fluidAmount <= nutrientTank.capacity - nutrientGained

	override fun shouldResetProgress() = false

	override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
		super.writeToNBT(compound)
		compound.setTag("OptimiserData", getOptimisation()?.writeToNBT(NBTTagCompound()) ?: NBTTagCompound())
		compound.setTag("InputTankNBT", waterTank.writeToNBT(NBTTagCompound()))
		compound.setTag("OutputTankNBT", nutrientTank.writeToNBT(NBTTagCompound()))
		return compound
	}

	override fun readFromNBT(compound: NBTTagCompound) {
		super.readFromNBT(compound)
		optimise(OptimiserData.readFromNBT(compound.getCompoundTag("OptimiserData")))
		waterTank.readFromNBT(compound.getCompoundTag("InputTankNBT"))
		nutrientTank.readFromNBT(compound.getCompoundTag("OutputTankNBT"))
	}
}
