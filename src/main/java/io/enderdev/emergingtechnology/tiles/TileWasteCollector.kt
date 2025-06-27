package io.enderdev.emergingtechnology.tiles

import io.enderdev.catalyx.tiles.BaseMachineTile
import io.enderdev.catalyx.utils.extensions.get
import io.enderdev.catalyx.utils.extensions.toStack
import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.blocks.machine.BlockWasteCollector.Companion.FULL
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import net.minecraft.init.Biomes
import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos

// TODO GUI icon and explanation for water requirements and if it's currently working
class TileWasteCollector() : BaseMachineTile<Any>(EmergingTechnology.catalyxSettings) {
	init {
		initInventoryCapability(0, 5)
		currentRecipe = 1
	}

	override val recipeTime = 100
	override val energyPerTick = 0

	override fun markDirtyGUI() {
		markDirty()
		val state = world.getBlockState(pos)
		val old = state.getValue(FULL)
		val new = (0..<output.slots).any { !output[it].isEmpty }

		if(old != new)
			world.setBlockState(pos, state.withProperty(FULL, new), 2)
		else
			world.notifyBlockUpdate(pos, state, state, 6)
	}

	override fun updateRecipe() {}

	override fun onProcessComplete() {
		val hit = world.rand.nextInt(1001)
		if(hit < EmergingTechnologyConfig.POLYMERS_MODULE.COLLECTOR.plasticRecoveryProbability * 100 && checkSurroundings()) { // * 100 to account for the fact that we're now checking every 100 ticks, TODO is my math right? ;p
			// Ender-TODO - temporary item, pwease make a config for this with configurable chances (you said you wanted to do so earlier ;p)
			output.insert(Blocks.BARRIER.toStack())
		}
	}

	override fun onWorkTick() {}
	override fun shouldTick() = true
	override fun shouldProcess() = true

	fun checkSurroundings(): Boolean {
		// TODO this is a debug thing!
		if(world.getBlockState(pos.down()).block == Blocks.BEDROCK)
			return true

		if(!EmergingTechnologyConfig.POLYMERS_MODULE.COLLECTOR.biomeRequirementDisabled) {
			val biome = world.getBiome(pos)
			if(biome != Biomes.BEACH && biome != Biomes.OCEAN && biome != Biomes.DEEP_OCEAN) // Ender-TODO - config for this
				return false
		}

		if(world.getBlockState(pos.up()).block == Blocks.WATER)
			return false

		val count = BlockPos.getAllInBox(pos.x - 2, pos.y, pos.z - 2, pos.x + 2, pos.y, pos.z + 2).count { world.getBlockState(it).block == Blocks.WATER }
		return count >= EmergingTechnologyConfig.POLYMERS_MODULE.COLLECTOR.minimumWaterBlocks
	}
}
