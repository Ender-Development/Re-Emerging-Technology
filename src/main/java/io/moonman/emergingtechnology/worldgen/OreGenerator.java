package io.moonman.emergingtechnology.worldgen;

import net.minecraft.block.Block;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.init.ModBlocks;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OreGenerator implements IWorldGenerator {

    public static final String RETRO_NAME = "EmergingTechnologyOreGen";
    public static OreGenerator instance = new OreGenerator();

    private static List<Biome> validBiomes;

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
            IChunkProvider chunkProvider) {
        generateWorld(random, chunkX, chunkZ, world, true);
    }

    public void generateWorld(Random random, int chunkX, int chunkZ, World world, boolean newGen) {
        if (!newGen && !EmergingTechnologyConfig.POLYMERS_MODULE.WORLDGEN.RETROGEN) {
            return;
        }

        if (world.provider.getDimension() == DimensionType.OVERWORLD.getId()) {

            if (EmergingTechnologyConfig.POLYMERS_MODULE.WORLDGEN.GENERATE_OVERWORLD) {

                validBiomes = new ArrayList<Biome>();

                validBiomes.add(Biomes.BEACH);
                validBiomes.add(Biomes.OCEAN);
                validBiomes.add(Biomes.DEEP_OCEAN);
                validBiomes.add(Biomes.FROZEN_OCEAN);
                validBiomes.add(Biomes.RIVER);
                validBiomes.add(Biomes.FROZEN_RIVER);
                validBiomes.add(Biomes.SWAMPLAND);

                if (EmergingTechnologyConfig.POLYMERS_MODULE.WORLDGEN.GENERATE_DIRT) {
                    addOreSpawn(ModBlocks.polluteddirt, (byte) 0, Blocks.DIRT, world, random, chunkX * 16, chunkZ * 16,
                            EmergingTechnologyConfig.POLYMERS_MODULE.WORLDGEN.DIRT_MIN_VEIN_SIZE,
                            EmergingTechnologyConfig.POLYMERS_MODULE.WORLDGEN.DIRT_MAX_VEIN_SIZE,
                            EmergingTechnologyConfig.POLYMERS_MODULE.WORLDGEN.DIRT_CHANCES_TO_SPAWN,
                            EmergingTechnologyConfig.POLYMERS_MODULE.WORLDGEN.DIRT_MIN_Y,
                            EmergingTechnologyConfig.POLYMERS_MODULE.WORLDGEN.DIRT_MAX_Y,
                            EmergingTechnologyConfig.POLYMERS_MODULE.WORLDGEN.DIRT_BIOME_RESTRICTION);
                }

                if (EmergingTechnologyConfig.POLYMERS_MODULE.WORLDGEN.GENERATE_SAND) {
                    addOreSpawn(ModBlocks.pollutedsand, (byte) 0, Blocks.SAND, world, random, chunkX * 16, chunkZ * 16,
                            EmergingTechnologyConfig.POLYMERS_MODULE.WORLDGEN.SAND_MIN_VEIN_SIZE,
                            EmergingTechnologyConfig.POLYMERS_MODULE.WORLDGEN.SAND_MAX_VEIN_SIZE,
                            EmergingTechnologyConfig.POLYMERS_MODULE.WORLDGEN.SAND_CHANCES_TO_SPAWN,
                            EmergingTechnologyConfig.POLYMERS_MODULE.WORLDGEN.SAND_MIN_Y,
                            EmergingTechnologyConfig.POLYMERS_MODULE.WORLDGEN.SAND_MAX_Y,
                            EmergingTechnologyConfig.POLYMERS_MODULE.WORLDGEN.SAND_BIOME_RESTRICTION);
                }

                if (EmergingTechnologyConfig.POLYMERS_MODULE.WORLDGEN.GENERATE_GRAVEL) {
                    addOreSpawn(ModBlocks.pollutedgravel, (byte) 0, Blocks.GRAVEL, world, random, chunkX * 16, chunkZ * 16,
                            EmergingTechnologyConfig.POLYMERS_MODULE.WORLDGEN.GRAVEL_MIN_VEIN_SIZE,
                            EmergingTechnologyConfig.POLYMERS_MODULE.WORLDGEN.GRAVEL_MAX_VEIN_SIZE,
                            EmergingTechnologyConfig.POLYMERS_MODULE.WORLDGEN.GRAVEL_CHANCES_TO_SPAWN,
                            EmergingTechnologyConfig.POLYMERS_MODULE.WORLDGEN.GRAVEL_MIN_Y,
                            EmergingTechnologyConfig.POLYMERS_MODULE.WORLDGEN.GRAVEL_MAX_Y,
                            EmergingTechnologyConfig.POLYMERS_MODULE.WORLDGEN.GRAVEL_BIOME_RESTRICTION);
                }
            }
        }

        if (!newGen) {
            world.getChunk(chunkX, chunkZ).markDirty();
        }
    }

    public void addOreSpawn(Block block, byte blockMeta, Block targetBlock, World world, Random random, int blockXPos,
            int blockZPos, int minVeinSize, int maxVeinSize, int chancesToSpawn, int minY, int maxY, boolean restrictBiome) {
        WorldGenMinable minable = new WorldGenMinable(block.getStateFromMeta(blockMeta),
                (minVeinSize + random.nextInt(maxVeinSize - minVeinSize + 1)), BlockStateMatcher.forBlock(targetBlock));
        for (int i = 0; i < chancesToSpawn; i++) {
            int posX = blockXPos + random.nextInt(16);
            int posY = minY + random.nextInt(maxY - minY);
            int posZ = blockZPos + random.nextInt(16);

            BlockPos plannedPosition = new BlockPos(posX, posY, posZ);

            Biome blockBiome = world.getBiome(plannedPosition);

            for (Biome biome : validBiomes) {
                if (biome == blockBiome || !restrictBiome) {
                    minable.generate(world, random, new BlockPos(posX, posY, posZ));
                    break;
                }
            }
        }
    }

    @SubscribeEvent
    public void handleChunkSaveEvent(ChunkDataEvent.Save event) {
        NBTTagCompound genTag = event.getData().getCompoundTag(RETRO_NAME);
        if (!genTag.hasKey("generated")) {
            genTag.setBoolean("generated", true);
        }
        event.getData().setTag(RETRO_NAME, genTag);
    }

    @SubscribeEvent
    public void handleChunkLoadEvent(ChunkDataEvent.Load event) {
        int dim = event.getWorld().provider.getDimension();

        boolean regen = false;
        NBTTagCompound tag = (NBTTagCompound) event.getData().getTag(RETRO_NAME);
        ChunkPos coord = event.getChunk().getPos();

        if (tag != null) {
            boolean generated = EmergingTechnologyConfig.POLYMERS_MODULE.WORLDGEN.RETROGEN && !tag.hasKey("generated");
            if (generated) {
                if (EmergingTechnologyConfig.POLYMERS_MODULE.WORLDGEN.VERBOSE) {
                    EmergingTechnology.logger.debug("Queuing Retrogen for chunk: " + coord.toString() + ".");
                }
                regen = true;
            }
        } else {
            regen = EmergingTechnologyConfig.POLYMERS_MODULE.WORLDGEN.RETROGEN;
        }

        if (regen) {
            ArrayDeque<ChunkPos> chunks = WorldTickHandler.chunksToGen.get(dim);

            if (chunks == null) {
                WorldTickHandler.chunksToGen.put(dim, new ArrayDeque<>(128));
                chunks = WorldTickHandler.chunksToGen.get(dim);
            }
            if (chunks != null) {
                chunks.addLast(coord);
                WorldTickHandler.chunksToGen.put(dim, chunks);
            }
        }
    }

}