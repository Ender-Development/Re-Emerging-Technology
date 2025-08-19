package io.enderdev.emergingtechnology.utils

import net.minecraft.block.BlockCactus
import net.minecraft.block.BlockCrops
import net.minecraft.block.BlockReed
import net.minecraft.block.IGrowable
import net.minecraft.block.state.IBlockState
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

object PlantUtils {
	fun isPlantGrown(state: IBlockState, world: World, pos: BlockPos): Boolean {
		val block = state.block
		return when(block) {
			is BlockReed, is BlockCactus -> world.getBlockState(pos.up()).block === block
			is IGrowable -> (Blocks.POTATOES as BlockCrops).isMaxAge(state)
			else -> false
		}
	}

	fun harvestPlant(state: IBlockState, world: World, pos: BlockPos): NonNullList<ItemStack> {
		val drops = NonNullList.create<ItemStack>()
		val block = state.block
		if(block is BlockReed || block is BlockCactus) {
			val pos = pos.up()
			val stateAbove = world.getBlockState(pos)
			if(stateAbove.block is BlockReed || stateAbove.block is BlockCactus) {
				stateAbove.block.getDrops(drops, world, pos, stateAbove, 0)
				world.destroyBlock(pos, false)
			}
		} else {
			block.getDrops(drops, world, pos, state, 0)
			world.destroyBlock(pos, false)
		}
		return drops
	}
}
