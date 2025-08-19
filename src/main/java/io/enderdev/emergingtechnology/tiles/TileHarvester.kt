package io.enderdev.emergingtechnology.tiles

import io.enderdev.catalyx.animation.NoopAnimationStateMachine
import io.enderdev.catalyx.client.button.AbstractButtonWrapper
import io.enderdev.catalyx.client.button.PauseButtonWrapper
import io.enderdev.catalyx.client.button.RedstoneButtonWrapper
import io.enderdev.catalyx.client.gui.BaseGuiTyped
import io.enderdev.catalyx.tiles.BaseTile
import io.enderdev.catalyx.tiles.helper.*
import io.enderdev.catalyx.utils.extensions.canMergeWith
import io.enderdev.catalyx.utils.extensions.get
import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.utils.PlantUtils
import net.minecraft.block.BlockHorizontal
import net.minecraft.block.IGrowable
import net.minecraft.inventory.InventoryHelper
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.IPlantable
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.model.animation.IAnimationStateMachine

class TileHarvester : BaseTile(EmergingTechnology.catalyxSettings), ITickable, IGuiTile, IItemTile, IButtonTile, BaseGuiTyped.IDefaultButtonVariables, IEnergyTile by EnergyTileImpl(5000), IAnimatedTile {	override var isPaused = false
	override var needsRedstonePower = false

	init {
		initInventoryCapability(1, 3)
	}

	override fun initInventoryInputCapability() {
		input = object : TileStackHandler(inputSlots, this) {
			override fun isItemValid(slot: Int, stack: ItemStack) = stack.item is IPlantable
		}
	}

	private companion object {
		// roz: this was based on the animation
		// original EMT basically used the client-side animation as the timer, but I cannot rely on that as that would break if no player is around and/or there's no players online and the server is unpaused
		// relying on clients for timing also opens up the possibility for packet lag and people also just sending packets manually, so, not a very great solution
		// as such, this is just a constant
		// what this also means is, well, the animation and this is ever so slightly offset, and so the animation is kinda glitchy/jittery in some places because the time value doesn't reset, but nothing I can do with my current knowledge of the animation system
		// I tried to solve this but I cannot fucking wrap my head around the stupidity that is the forge animation system, soooooooooo, if you can fix it, feel free. https://docs.minecraftforge.net/en/1.12.x/animation/intro/
		const val HARVEST_DELAY = 40
	}

	val energyPerTick = EmergingTechnologyConfig.HYDROPONICS_MODULE.HARVESTER.harvesterEnergyBaseUsage
	var currentlyHarvesting: EnumFacing? = null
	var currentHarvestDelay = 0
	var plantDelay = 5

	override fun update() {
		if(world.isRemote)
			return

		markDirtyGUIEvery(5)

		if(energyStorage.energyStored < energyPerTick)
			return

		energyStorage.extractEnergy(energyPerTick, false)

		if(currentlyHarvesting == null) {
			if(plantDelay-- == 0) {
				plantDelay = 5
				plant()
			}
			updateCurrentlyHarvesting()
		} else if(currentHarvestDelay-- == 0)
			actuallyHarvest()
	}

	fun outputFull() =
		(0..<output.slots).all {
			output[it].count == output[it].maxStackSize
		}

	fun updateCurrentlyHarvesting() {
		if(outputFull())
			return

		EnumFacing.HORIZONTALS.forEach { facing ->
			val pos = pos.offset(facing)
			val state = world.getBlockState(pos)
			if(state.block !is IGrowable && state.block !is IPlantable)
				return@forEach

			if(!PlantUtils.isPlantGrown(state, world, pos))
				return@forEach

			currentlyHarvesting = facing
			blockFacing = facing
			currentHarvestDelay = HARVEST_DELAY
			if(asm.currentState() != "harvest") // if server crashes perfectly or any stupidity happens
				asm.transition("harvest")
			markDirtyClient()
			return
		}
	}

	fun actuallyHarvest() {
		val pos = pos.offset(currentlyHarvesting!!)
		val state = world.getBlockState(pos)
		val drops = PlantUtils.harvestPlant(state, world, pos)

		if(state.block.isAir(state, world, pos))
			for(drop in drops) {
				val item = drop.item
				if(item !is IPlantable)
					continue

				val plant = item.getPlant(world, pos)
				world.setBlockState(pos, plant, 3)
				drop.shrink(1)
				if(drop.isEmpty)
					drops.remove(drop)
				break
			}

		for(drop in drops) {
			val item = drop.item
			if(item is IPlantable && input[0].canMergeWith(drop, true)) {
				input.setOrIncrement(0, drop)
				continue
			}

			var foundSpace = false
			for(slot in 0..<output.slots) {
				if(output[slot].canMergeWith(drop, true)) {
					output.setOrIncrement(slot, drop)
					foundSpace = true
					break
				}
			}

			if(!foundSpace)
				InventoryHelper.spawnItemStack(world, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), drop)
		}

		currentlyHarvesting = null
		if(asm.currentState() != "idle") // if server crashes perfectly or any stupidity happens
			asm.transition("idle")
		markDirtyClient()
	}

	fun plant() {
		if(input[0].isEmpty)
			return

		val item = input[0].item
		if(item !is IPlantable) // sanity check
			return

		val plant = item.getPlant(world, pos)

		for(facing in EnumFacing.HORIZONTALS) {
			val pos = pos.offset(facing)
			val state = world.getBlockState(pos)
			if(!state.block.isAir(state, world, pos))
				continue

			val stateBelow = world.getBlockState(pos.down())
			if(!stateBelow.block.canSustainPlant(stateBelow, world, pos.down(), EnumFacing.UP, item))
				continue

			blockFacing = facing
			world.setBlockState(pos, plant, 3)
			markDirtyClient()
			input[0].shrink(1)
			return // only plant 1 thing per tick
		}
	}

	var blockFacing: EnumFacing
		get() = world.getBlockState(pos).getValue(BlockHorizontal.FACING)
		set(value) {
			world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockHorizontal.FACING, value), 3)
		}

	override fun handleButtonPress(button: AbstractButtonWrapper) {
		when(button) {
			is RedstoneButtonWrapper -> needsRedstonePower = !needsRedstonePower
			is PauseButtonWrapper -> isPaused = !isPaused
		}
	}

	override fun hasCapability(capability: Capability<*>, facing: EnumFacing?) =
		if(facing == EnumFacing.UP) false else super.hasCapability(capability, facing)

	override fun <T : Any> getCapability(capability: Capability<T>, facing: EnumFacing?) =
		if(facing == EnumFacing.UP) null else super.getCapability(capability, facing)

	override fun readFromNBT(compound: NBTTagCompound) {
		super.readFromNBT(compound)
		val newState = compound.getString("AnimationState")
		val currentState = asm.currentState()
		if(newState != currentState)
			asm.transition(newState)
	}

	override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
		val ret = super.writeToNBT(compound)
		ret.setString("AnimationState", asm.currentState())
		// currentlyHarvesting/currentHarvestDelay have not been included here due to possible desync with the model animations, /shrug
		return ret
	}

	override val asm: IAnimationStateMachine = NoopAnimationStateMachine.loadASM(ResourceLocation(Tags.MODID, "asms/block/harvester.json"), emptyMap())

	override fun hasFastRenderer() = true
}
