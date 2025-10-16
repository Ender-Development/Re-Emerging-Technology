package io.enderdev.emergingtechnology.tiles

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.items.ItemBulb
import net.minecraft.block.IGrowable
import net.minecraft.block.state.IBlockState
import net.minecraft.init.Blocks
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fluids.FluidStack
import org.ender_development.catalyx.tiles.BaseMachineTile
import org.ender_development.catalyx.tiles.helper.EnergyTileImpl
import org.ender_development.catalyx.tiles.helper.IEnergyTile
import org.ender_development.catalyx.tiles.helper.IFluidTile
import org.ender_development.catalyx.tiles.helper.TileStackHandler
import org.ender_development.catalyx.utils.extensions.get

class TileHydroponicGrowBed : BaseMachineTile<Any>(EmergingTechnology), IEnergyTile by EnergyTileImpl(5000), IFluidTile {
	init {
		initInventoryCapability(1, 0)
		currentRecipe = 1
	}

	override fun initInventoryInputCapability() {
		input = object : TileStackHandler(inputSlots, this) {
			override fun isItemValid(slot: Int, stack: ItemStack) = stack.item is ItemBulb || stack.item == Item.getItemFromBlock(Blocks.GLOWSTONE)

			override fun getStackLimit(slot: Int, stack: ItemStack) = 1
			override fun getSlotLimit(slot: Int) = 1
		}
	}

	// replace with Catalyx FluidTankUtils
	//val inputTank = object : FluidTank(Fluid.BUCKET_VOLUME * 10) {
	//	override fun canFillFluidType(fluid: FluidStack?) = true // RN-TODO ASJDKLASJDLKAJDKL
	//
	//	//override fun fillInternal(resource: FluidStack?, doFill: Boolean): Int {
	//	//	val ret = super.fillInternal(resource, doFill)
	//	//	markDirtyClient() // update renderer
	//	//	return ret
	//	//}
	//	//
	//	//override fun drainInternal(maxDrain: Int, doDrain: Boolean): FluidStack? {
	//	//	val ret = super.drainInternal(maxDrain, doDrain)
	//	//	markDirtyClient() // update renderer
	//	//	return ret
	//	//}
	//}.apply {
	//	setTileEntity(this@TileHydroponicGrowBed)
	//	setCanFill(true)
	//	setCanDrain(false)
	//}
	override val fluidHandler = TODO()//FluidHandlerConcatenate(inputTank)


	// TODO EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.growBedsRequireEnergy
	override val recipeTime = Int.MAX_VALUE
	override val energyPerTick = 0
	val fluidPerTick = EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.growBedWaterUsePerCycle

	override fun updateRecipe() {}

	override fun onProcessComplete() {}

	override fun onWorkTick() {
		// if(doGrowthMultiplierProcess()) doMediumDestroyProcess()
		// if(requireEnergy) dobullshit
		// finally doWaterUsageProcess(succeeded ^)
		//energyStorage.extractEnergy(energyPerTick * energyMult, false)
		//
		//if(growthMult > 0)
		//	for(it in 1..EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.lightBlockRange) {
		//		val pos = pos.down(it)
		//		val state = world.getBlockState(pos)
		//		if(state.block !is IGrowable)
		//			if(state.block.isAir(state, world, pos))
		//				continue
		//			else
		//				break
		//
		//		val probability = growthMult + /* LightHelper.getSpecificPlantGrowthBoostForId(bulbTypeId, blockStateBelow) TODO I'm too lazy to figure this out */ - EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.lightBlockRangeDropoff * it
		//
		//		if(probability <= 0)
		//			break
		//
		//		if(world.rand.nextInt(101) < probability)
		//			state.block.randomTick(world, pos, state, world.rand)
		//	}
		//
		//markDirtyGUI() // looks cool
	}

	// TODO I'm not insane enough to load fluid growth boosts from a json file like EMT does (see HydroponicTileEntity#L266 & ModFluidProvider#L27)
	// TODO same for mediums (see HydroponicTileEntity#L281 & CustomMediumProvider#L19)
	// and so this TE is basically impossible to re-create at this point, at least before Ender hopefully comes along and makes a sensible config for this bullshit, after which I can consider remaking this fully
	// and also as such, I'm too lazy to re-write the TESR & rendering stuff, as such this is staying as a broken TODO block for the forseeable future.

	fun growthMultiplierProcessRewrite() {
		val plant = world.getBlockState(pos.up())
		val block = plant.block
		if(block !is IGrowable)
			return


	}

	fun getSpecificPlantGrowthBoostForFluidStack(fluid: FluidStack, state: IBlockState): Int {
		if(fluid == FluidRegistry.LAVA && state.block === Blocks.NETHER_WART)
			return EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWBED.lavaGrowthBoost

		if(fluid.amount <= 0)
			return 0

		// TODO decipher ModFluidProvider#getSpecificPlantGrowthBoostFromFluidStack(FluidStack, String)
		return 0
	}

	override fun onIdleTick() {
		markDirtyGUIEvery(5)

	}

	override fun shouldTick() = true

	override fun shouldProcess() = !input[0].isEmpty && energyStorage.energyStored >= energyPerTick // TODO
}
