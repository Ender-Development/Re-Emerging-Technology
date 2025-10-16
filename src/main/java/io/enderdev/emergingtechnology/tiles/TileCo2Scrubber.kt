package io.enderdev.emergingtechnology.tiles

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.blocks.ModBlocks
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.fluids.ModFluids
import io.enderdev.emergingtechnology.recipes.Co2ScrubberRecipe
import io.enderdev.emergingtechnology.recipes.ModRecipes
import io.enderdev.emergingtechnology.utils.CapabilityUtils
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.model.animation.IAnimationStateMachine
import net.minecraftforge.fluids.Fluid
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate
import org.ender_development.catalyx.animation.NoopAnimationStateMachine
import org.ender_development.catalyx.tiles.BaseMachineTile
import org.ender_development.catalyx.tiles.helper.*
import org.ender_development.catalyx.utils.FluidTankUtils
import org.ender_development.catalyx.utils.extensions.canMergeWith
import org.ender_development.catalyx.utils.extensions.get
import java.util.*

class TileCo2Scrubber : BaseMachineTile<Co2ScrubberRecipe>(EmergingTechnology), IEnergyTile by EnergyTileImpl(10000), IFluidTile, IOptimisableTile by OptimisableTileImpl(), IAnimatedTile {
	init {
		initInventoryCapability(1, 1)
	}

	override fun initInventoryInputCapability() {
		input = object : TileStackHandler(inputSlots, this) {
			override fun isItemValid(slot: Int, stack: ItemStack) =
				ModRecipes.co2ScrubberRecipes.recipes.any { it.input.test(stack) }
		}
	}

	val waterTank = FluidTankUtils.create(this, Fluid.BUCKET_VOLUME * 10, true, false, FluidRegistry.WATER, onContentsChangedCallback = this::markDirtyGUI)
	val co2Tank = FluidTankUtils.create(this, Fluid.BUCKET_VOLUME * 10, false, true, ModFluids.co2, onContentsChangedCallback = this::markDirtyGUI)

	override val fluidHandler = FluidHandlerConcatenate(waterTank, co2Tank)

	var surroundingDelay = 1
	var surroundingBoost = 0

	override val recipeTime: Int
		get() = getEffectiveRecipeTime(EmergingTechnologyConfig.HYDROPONICS_MODULE.SCRUBBER.scrubberBaseTimeTaken)
	override val energyPerTick: Int
		get() = getEffectiveEnergyUsage(EmergingTechnologyConfig.HYDROPONICS_MODULE.SCRUBBER.scrubberEnergyBaseUsage)
	val waterPerTick: Int
		get() = getEffectiveWaterUsage(EmergingTechnologyConfig.HYDROPONICS_MODULE.SCRUBBER.scrubberWaterBaseUsage)
	val co2Gained: Int
		get() = EmergingTechnologyConfig.HYDROPONICS_MODULE.SCRUBBER.scrubberGasGenerated + (currentRecipe?.gas ?: 0) + surroundingBoost

	override fun onIdleTick() {
		updateRecipe()
		optimisationTick()
		CapabilityUtils.spreadLiquid(world, pos, co2Tank, EnumFacing.DOWN, EnumFacing.UP)
	}

	override fun updateRecipe() {
		currentRecipe = if(input[0].isEmpty)
			ModRecipes.co2ScrubberRecipes.emptyRecipe
		else
			ModRecipes.co2ScrubberRecipes.recipes.firstOrNull { it.input.test(input[0]) }
	}

	override fun onProcessComplete() {
		input.decrementSlot(0, 1) // Ingredients don't have any amount
		output.setOrIncrement(0, currentRecipe!!.output.copy())
		co2Tank.fillInternal(FluidStack(ModFluids.co2, co2Gained), true)
	}

	override fun onWorkTick() {
		energyStorage.extractEnergy(energyPerTick, false)
		waterTank.drainInternal(waterPerTick, true)
		if(--surroundingDelay == 0) {
			surroundingDelay = 100
			surroundingBoost = 0
			BlockPos.getAllInBox(pos.x - 2, pos.y - 2, pos.z - 2, pos.x + 2, pos.y + 2, pos.z + 2).forEach {
				val state = world.getBlockState(it)
				if(state.block == Blocks.LIT_FURNACE)
					surroundingBoost += 100

				if(state.block != ModBlocks.biomassGenerator)
					return@forEach

				val te = world.getTileEntity(it) as? TileBiomassGenerator ?: return@forEach
				if(te.shouldProcess())
					surroundingBoost += 50
			}
		}
		markDirtyGUI() // looks cool
	}

	override fun shouldTick() = true

	override fun shouldProcess(): Boolean {
		val process = currentRecipe!!.output.canMergeWith(output[0], true) && energyStorage.energyStored >= energyPerTick && waterTank.fluidAmount >= waterPerTick && co2Tank.fluidAmount <= co2Tank.capacity - co2Gained
		setAnimationState(if(process) AnimationState.FAST else AnimationState.OFF)
		return process
	}

	override fun shouldResetProgress() = false

	override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
		super.writeToNBT(compound)
		compound.setTag("OptimiserData", getOptimisation()?.writeToNBT(NBTTagCompound()) ?: NBTTagCompound())
		compound.setTag("InputTankNBT", waterTank.writeToNBT(NBTTagCompound()))
		compound.setTag("OutputTankNBT", co2Tank.writeToNBT(NBTTagCompound()))
		compound.setString("AnimationState", asm.currentState() ?: "off")
		return compound
	}

	override fun readFromNBT(compound: NBTTagCompound) {
		super.readFromNBT(compound)
		optimise(OptimiserData.readFromNBT(compound.getCompoundTag("OptimiserData")))
		waterTank.readFromNBT(compound.getCompoundTag("InputTankNBT"))
		co2Tank.readFromNBT(compound.getCompoundTag("OutputTankNBT"))
		setAnimationState(if(compound.getString("AnimationState") == "fast") AnimationState.FAST else AnimationState.OFF)
	}

	override fun hasCapability(capability: Capability<*>, facing: EnumFacing?) =
		capability == ANIMATION_CAP || (facing == null || facing == EnumFacing.UP || facing == EnumFacing.DOWN) && super.hasCapability(capability, facing)

	override fun <T : Any> getCapability(capability: Capability<T>, facing: EnumFacing?) =
		if(capability == ANIMATION_CAP || facing == null || facing == EnumFacing.UP || facing == EnumFacing.DOWN)
			super.getCapability(capability, facing)
		else
			null

	// Rendering stuff
	override val asm: IAnimationStateMachine = NoopAnimationStateMachine.loadASM(ResourceLocation(Tags.MODID, "asms/block/co2_scrubber.json"), emptyMap())

	override fun hasFastRenderer() = true

	fun setAnimationState(state: AnimationState) {
		val newState = state.name.lowercase(Locale.ENGLISH)
		if(asm.currentState() != newState)
			asm.transition(newState)
	}

	enum class AnimationState {
		OFF, FAST;
	}
}
