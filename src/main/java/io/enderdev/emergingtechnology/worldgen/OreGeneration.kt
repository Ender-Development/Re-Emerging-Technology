package io.enderdev.emergingtechnology.worldgen

import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.blocks.ModBlocks
import io.enderdev.emergingtechnology.blocks.PollutedBlock
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.config.polymers.PolymersModuleWorldGen
import net.minecraft.block.state.pattern.BlockStateMatcher
import net.minecraft.init.Biomes
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.chunk.IChunkProvider
import net.minecraft.world.gen.IChunkGenerator
import net.minecraft.world.gen.feature.WorldGenMinable
import net.minecraftforge.event.world.ChunkDataEvent
import net.minecraftforge.fml.common.IWorldGenerator
import java.util.*

class OreGeneration : IWorldGenerator {
	companion object {
		val instance = OreGeneration()
	}

	val worldgenConfig: PolymersModuleWorldGen = EmergingTechnologyConfig.POLYMERS_MODULE.WORLDGEN
	val validBiomes = arrayOf(Biomes.BEACH, Biomes.OCEAN, Biomes.DEEP_OCEAN, Biomes.FROZEN_OCEAN, Biomes.RIVER, Biomes.FROZEN_RIVER, Biomes.SWAMPLAND) // Ender-TODO this should probably be configurable

	override fun generate(random: Random, chunkX: Int, chunkZ: Int, world: World, chunkGenerator: IChunkGenerator, chunkProvider: IChunkProvider) =
		generate(random, chunkX, chunkZ, world, false)

	fun generate(random: Random, chunkX: Int, chunkZ: Int, world: World, retrogen: Boolean) {
		if(world.provider.dimension != 0 || !worldgenConfig.GENERATE_OVERWORLD)
			return

		if(worldgenConfig.GENERATE_DIRT)
			addOreSpawn(ModBlocks.pollutedDirt, world, random, chunkX, chunkZ,
				worldgenConfig.DIRT_MIN_VEIN_SIZE, worldgenConfig.DIRT_MAX_VEIN_SIZE,
				worldgenConfig.DIRT_CHANCES_TO_SPAWN,
				worldgenConfig.DIRT_MIN_Y, worldgenConfig.DIRT_MAX_Y,
				worldgenConfig.DIRT_BIOME_RESTRICTION)

		if(worldgenConfig.GENERATE_SAND)
			addOreSpawn(ModBlocks.pollutedSand, world, random, chunkX, chunkZ,
				worldgenConfig.SAND_MIN_VEIN_SIZE, worldgenConfig.SAND_MAX_VEIN_SIZE,
				worldgenConfig.SAND_CHANCES_TO_SPAWN,
				worldgenConfig.SAND_MIN_Y, worldgenConfig.SAND_MAX_Y,
				worldgenConfig.SAND_BIOME_RESTRICTION)

		if(worldgenConfig.GENERATE_GRAVEL)
			addOreSpawn(ModBlocks.pollutedGravel, world, random, chunkX, chunkZ,
				worldgenConfig.GRAVEL_MIN_VEIN_SIZE, worldgenConfig.GRAVEL_MAX_VEIN_SIZE,
				worldgenConfig.GRAVEL_CHANCES_TO_SPAWN,
				worldgenConfig.GRAVEL_MIN_Y, worldgenConfig.GRAVEL_MAX_Y,
				worldgenConfig.GRAVEL_BIOME_RESTRICTION)

		if(retrogen)
			world.getChunk(chunkX, chunkZ).markDirty()
	}

	fun addOreSpawn(block: PollutedBlock, world: World, random: Random, chunkX: Int, chunkZ: Int, minVeinSize: Int, maxVeinSize: Int, repeat: Int, minY: Int, maxY: Int, restrictBiome: Boolean) {
		val minable = WorldGenMinable(block.defaultState, minVeinSize + random.nextInt(maxVeinSize - minVeinSize), BlockStateMatcher.forBlock(block.block))
		repeat(repeat) {
			val pos = BlockPos(chunkX * 16 + random.nextInt(16), minY + random.nextInt(maxY - minY), chunkZ * 16 + random.nextInt(16))
			if(!restrictBiome || validBiomes.contains(world.getBiome(pos)))
				minable.generate(world, random, pos)
		}
	}

	val retrogenNBT = "${Tags.MODID}_retrogen"

	fun chunkSave(ev: ChunkDataEvent.Save) =
		ev.data.setBoolean(retrogenNBT, true)

	// TODO no-op because I'm too lazy to implement the way EMT did it
	fun chunkLoad(ev: ChunkDataEvent.Load) {
		if(!worldgenConfig.RETROGEN || ev.data.getBoolean(retrogenNBT) || true)
			return

		// TODO validate if this works (it doesn't, it causes a stackoverflow of stacktraces lmao), original EMT did this a very weird way (like a lot of other things, lets be honest)
		//generate(ev.world.rand, ev.chunk.x, ev.chunk.z, ev.world, true)
	}
}
