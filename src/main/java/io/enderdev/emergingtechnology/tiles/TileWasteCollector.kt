package io.enderdev.emergingtechnology.tiles

import io.enderdev.catalyx.client.button.PauseButton
import io.enderdev.catalyx.client.button.RedstoneButton
import io.enderdev.catalyx.tiles.BaseMachineTile
import io.enderdev.catalyx.tiles.BaseTile
import io.enderdev.catalyx.tiles.helper.IButtonTile
import io.enderdev.catalyx.tiles.helper.IGuiTile
import io.enderdev.catalyx.tiles.helper.IItemTile
import io.enderdev.catalyx.utils.extensions.toStack
import io.enderdev.catalyx.utils.extensions.tryInsert
import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import net.minecraft.init.Biomes
import net.minecraft.init.Blocks
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ITickable
import net.minecraft.util.math.BlockPos

// TODO GUI icon and explanation for water requirements and if it's currently working
class TileWasteCollector() : BaseMachineTile<Any>(EmergingTechnology.catalyxSettings) {
	init {
		initInventoryCapability(0, 5)
	}

	override val recipeTime = 100
	override val energyPerTick = 0
	override fun updateRecipe() {}
	override fun onProcessComplete() {}
	override fun onWorkTick() {}
	override fun shouldTick() = true
	override fun shouldProcess() = true

	// I could've absolutely made this to work within the BaseMachineTile Catalyx implementation, but meh
	override fun update() {
		if(world.isRemote)
			return

		if(isPaused || needsRedstonePower != this.world.isBlockPowered(this.pos))
			return

		if(progressTicks++ == 100) {
			progressTicks = 0

			if(!checkSurroundings())
				return

			markDirtyGUI()

			val hit = world.rand.nextInt(1001)
			if(hit < EmergingTechnologyConfig.POLYMERS_MODULE.COLLECTOR.plasticRecoveryProbability * 100) { // * 100 to account for the fact that we're now checking every 100 ticks, TODO is my math right? ;p
				// Ender-TODO - temporary item, pwease make a config for this with configurable chances (you said you wanted to do so earlier ;p)
				output.insert(Blocks.BARRIER.toStack())
			}
		}
	}

	fun checkSurroundings(): Boolean {
		return true // TODO - TESTING
		if(!EmergingTechnologyConfig.POLYMERS_MODULE.COLLECTOR.biomeRequirementDisabled) {
			val biome = world.getBiome(pos)
			if(biome != Biomes.BEACH && biome != Biomes.OCEAN && biome != Biomes.DEEP_OCEAN) // Ender-TODO - config for this
				return false
		}

		if(world.getBlockState(pos.add(0, 1, 0)).block == Blocks.WATER)
			return false

		val count = BlockPos.getAllInBox(pos.x - 2, pos.y, pos.z - 2, pos.x + 2, pos.y, pos.z + 2).count { world.getBlockState(it).block == Blocks.WATER }
		return count >= EmergingTechnologyConfig.POLYMERS_MODULE.COLLECTOR.minimumWaterBlocks
	}
}
