package io.enderdev.emergingtechnology.tiles

import io.enderdev.catalyx.tiles.BaseMachineTile
import io.enderdev.catalyx.tiles.helper.EnergyTileImpl
import io.enderdev.catalyx.tiles.helper.IEnergyTile
import io.enderdev.catalyx.tiles.helper.IFluidTile
import io.enderdev.catalyx.tiles.helper.TileStackHandler
import io.enderdev.catalyx.utils.extensions.canMergeWith
import io.enderdev.catalyx.utils.extensions.get
import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.blocks.ModBlocks
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.fluids.ModFluids
import io.enderdev.emergingtechnology.items.ModItems
import io.enderdev.emergingtechnology.recipes.AlgaeBioreactorRecipe
import io.enderdev.emergingtechnology.recipes.ModRecipes
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.fluids.Fluid
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.FluidTank
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate

class TileAlgaeBioreactor : BaseMachineTile<AlgaeBioreactorRecipe>(EmergingTechnology.catalyxSettings), IEnergyTile by EnergyTileImpl(5000), IOptimisableTile by OptimisableTileImpl(), IFluidTile {
	init {
		initInventoryCapability(1, 1)
	}

	override fun initInventoryInputCapability() {
		input = object : TileStackHandler(inputSlots, this) {
			override fun isItemValid(slot: Int, stack: ItemStack) =
				ModRecipes.algaeBioreactorRecipes.recipes.any { it.input.test(stack) }
		}
	}

	val waterTank = object : FluidTank(Fluid.BUCKET_VOLUME * 5) {
		override fun canFillFluidType(fluid: FluidStack?) = fluid?.fluid == FluidRegistry.WATER
	}.apply {
		setTileEntity(this@TileAlgaeBioreactor)
		setCanFill(true)
		setCanDrain(false)
	}

	val gasTank = object : FluidTank(Fluid.BUCKET_VOLUME * 5) {
		override fun canFillFluidType(fluid: FluidStack?) = fluid?.fluid == ModFluids.co2
	}.apply {
		setTileEntity(this@TileAlgaeBioreactor)
		setCanFill(true)
		setCanDrain(false)
	}

	override val fluidTanks = FluidHandlerConcatenate(waterTank, gasTank)

	var bulbCheckTimer = 20
	var bulbRecipeTimeModifier = 1

	override val recipeTime: Int
		get() = getEffectiveRecipeTime(EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.bioreactorBaseTimeTaken) / bulbRecipeTimeModifier
	override val energyPerTick: Int
		get() = getEffectiveEnergyUsage(EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.bioreactorEnergyUsage)
	val waterPerTick: Int
		get() = getEffectiveWaterUsage(EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.bioreactorWaterUsage)
	val gasPerTick: Int
		get() = getEffectiveGasUsage(EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.bioreactorGasUsage)

	override fun onIdleTick() {
		updateRecipe()
		optimisationTick()
	}

	override fun updateRecipe() {
		currentRecipe = if(input[0].isEmpty) null else ModRecipes.algaeBioreactorRecipes.recipes.firstOrNull { it.input.test(input[0]) }
	}

	override fun onProcessComplete() {
		input.decrementSlot(0, 1) // Ingredients don't have any amount
		output.setOrIncrement(0, currentRecipe!!.output.copy())
	}

	override fun onWorkTick() {
		if(bulbCheckTimer-- <= 0) {
			bulbCheckTimer = 20
			bulbRecipeTimeModifier = 1

			for(it in 1..EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.lightBlockRange) {
				val pos = pos.up(it)
				val state = world.getBlockState(pos)
				if(state.block.isAir(state, world, pos))
					continue

				if(state.block !== ModBlocks.hydroponicGrowLight)
					break

				val te = world.getTileEntity(pos) as? TileHydroponicGrowLight ?: break
				if(!te.cachedLit)
					break

				bulbRecipeTimeModifier = when(te.input[0].item) {
					ModItems.bulbRed -> EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.growthRedBulbModifier
					ModItems.bulbGreen -> EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.growthGreenBulbModifier
					ModItems.bulbBlue -> EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.growthBlueBulbModifier
					ModItems.bulbPurple -> EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.growthPurpleBulbModifier
					else -> 1
				}
				break
			}
		}

		energyStorage.extractEnergy(energyPerTick, false)
		waterTank.drainInternal(waterPerTick, true)
		gasTank.drainInternal(gasPerTick, true)
		markDirtyGUI() // looks cool
	}

	override fun shouldTick() = true

	override fun shouldProcess() = currentRecipe!!.output.canMergeWith(output[0], true) && energyStorage.energyStored >= energyPerTick && waterTank.fluidAmount >= waterPerTick && gasTank.fluidAmount >= gasPerTick

	override fun shouldResetProgress() = false

	override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
		super.writeToNBT(compound)
		compound.setTag("WaterTankNBT", waterTank.writeToNBT(NBTTagCompound()))
		compound.setTag("GasTankNBT", gasTank.writeToNBT(NBTTagCompound()))
		compound.setTag("OptimiserData", getOptimisation()?.writeToNBT(NBTTagCompound()) ?: NBTTagCompound())
		compound.setInteger("BulbRecipeTimeModifier", bulbRecipeTimeModifier) // this is here so the GUI renders the progress bar properly
		return compound
	}

	override fun readFromNBT(compound: NBTTagCompound) {
		super.readFromNBT(compound)
		waterTank.readFromNBT(compound.getCompoundTag("WaterTankNBT"))
		gasTank.readFromNBT(compound.getCompoundTag("GasTankNBT"))
		optimise(OptimiserData.readFromNBT(compound.getCompoundTag("OptimiserData")))
		bulbRecipeTimeModifier = compound.getInteger("BulbRecipeTimeModifier")
		if(bulbRecipeTimeModifier == 0) // just in case, sanity check
			bulbRecipeTimeModifier = 1
	}
}
