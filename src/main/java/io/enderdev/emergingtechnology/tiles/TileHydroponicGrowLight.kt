package io.enderdev.emergingtechnology.tiles

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.blocks.ModBlocks
import io.enderdev.emergingtechnology.blocks.machine.BlockHydroponicGrowLight.Companion.LIT
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.items.ItemBulb
import net.minecraft.block.BlockHorizontal
import net.minecraft.block.IGrowable
import net.minecraft.init.Blocks
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import org.ender_development.catalyx.tiles.BaseMachineTile
import org.ender_development.catalyx.tiles.helper.EnergyTileImpl
import org.ender_development.catalyx.tiles.helper.IEnergyTile
import org.ender_development.catalyx.tiles.helper.TileStackHandler
import org.ender_development.catalyx.utils.extensions.get
import java.awt.Color
import kotlin.math.absoluteValue

class TileHydroponicGrowLight : BaseMachineTile<Any>(EmergingTechnology.catalyxSettings), IEnergyTile by EnergyTileImpl(10000) {
	init {
		initInventoryCapability(1, 0)
		currentRecipe = 1
	}

	override fun initInventoryInputCapability() {
		input = object : TileStackHandler(inputSlots, this) {
			override fun isItemValid(slot: Int, stack: ItemStack) = stack.item is ItemBulb || stack.item == Item.getItemFromBlock(Blocks.GLOWSTONE)

			override fun getStackLimit(slot: Int, stack: ItemStack) = 1
			override fun getSlotLimit(slot: Int) = 1

			// update colour handler
			override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean): ItemStack {
				val result = super.insertItem(slot, stack, simulate)
				markDirtyClient()
				return result
			}

			override fun extractItem(slot: Int, amount: Int, simulate: Boolean): ItemStack {
				val result = super.extractItem(slot, amount, simulate)
				markDirtyClient()
				return result
			}
		}
	}

	override val recipeTime = Int.MAX_VALUE
	override val energyPerTick = EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.lightEnergyBaseUsage

	override fun updateRecipe() {}

	override fun onProcessComplete() {}

	override fun onWorkTick() {
		energyStorage.extractEnergy(energyPerTick * energyMult, false)

		if(growthMult > 0)
			for(it in 1..EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.lightBlockRange) {
				val pos = pos.down(it)
				val state = world.getBlockState(pos)
				if(state.block !is IGrowable)
					if(state.block.isAir(state, world, pos))
						continue
					else
						break

				val probability = growthMult + /* LightHelper.getSpecificPlantGrowthBoostForId(bulbTypeId, blockStateBelow) TODO I'm too lazy to figure this out */ - EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.lightBlockRangeDropoff * it

				if(probability <= 0)
					break

				if(world.rand.nextInt(101) < probability)
					state.block.randomTick(world, pos, state, world.rand)
			}

		markDirtyGUI() // looks cool
	}

	override fun onIdleTick() {
		val facing = world.getBlockState(pos).getValue(BlockHorizontal.FACING)

		// Equalise with neighbouring grow lights, slightly better logic than original EMT (which in my testing somehow duplicated energy lol)
		// TODO try to improve this logic slightly, this puts a hard cap on how many grow lights can be in a row, after which the energy transfer just becomes way too slow to keep up with demand
		if(world.getBlockState(pos.offset(facing)).block === ModBlocks.hydroponicGrowLight)
			world.getTileEntity(pos.offset(facing))?.getCapability(ENERGY_CAP, facing.opposite)?.let {
				val toEqualise = ((energyStorage.energyStored - it.energyStored) shr 1).absoluteValue
				if(it.energyStored > energyStorage.energyStored)
					energyStorage.receiveEnergy(it.extractEnergy(toEqualise, false), false)
				else
					energyStorage.extractEnergy(it.receiveEnergy(toEqualise, false), false)
			}
	}

	override fun shouldTick() = true

	override fun shouldProcess() = !input[0].isEmpty && energyStorage.energyStored >= energyMult * energyPerTick

	var cachedLit = false

	// this is basically the same as the original, but with LIT added, hopefully ender won't scream at me for this
	override fun update() {
		if(world.isRemote) return
		markDirtyGUIEvery(5)

		if(isPaused || needsRedstonePower != this.world.isBlockPowered(this.pos)) return
		if(!shouldTick()) {
			progressTicks = 0
			return
		}
		onIdleTick()
		if(currentRecipe == null) {
			progressTicks = 0
			return
		}
		if(!shouldProcess()) {
			if(cachedLit)
				world.setBlockState(pos, world.getBlockState(pos).withProperty(LIT, false), 3)
			cachedLit = false

			if(shouldResetProgress())
				progressTicks = 0
			return
		}

		if(!cachedLit)
			world.setBlockState(pos, world.getBlockState(pos).withProperty(LIT, true), 3)
		cachedLit = true

		onWorkTick()
		if(progressTicks++ == recipeTime) {
			progressTicks = 0
			onProcessComplete()
		}
	}

	val energyMult
		get() = input[0].item.let {
			if(it is ItemBulb)
				it.energyMult
			else
				0
		}

	val growthMult
		get() = input[0].item.let {
			if(it is ItemBulb)
				it.growthMult
			else
				0
		}

	fun getColour() = input[0].item.let {
		if(it is ItemBulb)
			it.colour
		else if(it === Item.getItemFromBlock(Blocks.GLOWSTONE))
			Color.yellow.rgb
		else
			Color.lightGray.rgb
	}
}
