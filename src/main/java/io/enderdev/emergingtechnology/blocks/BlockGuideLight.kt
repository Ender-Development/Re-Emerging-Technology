package io.enderdev.emergingtechnology.blocks

import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess

class BlockGuideLight : ModelBlock("guide_light", Material.GLASS, SoundType.GLASS, 1f) {
	init {
		lightValue = 15
		lightOpacity = 0
	}

	val standingAABB = AxisAlignedBB(.4, .0, .4, .6, .6, .6) // this is BlockTorch.STANDING_AABB

	@Deprecated("")
	override fun isFullCube(state: IBlockState) = false

	@Deprecated("")
	override fun getBoundingBox(state: IBlockState, source: IBlockAccess, pos: BlockPos) = standingAABB

	@Deprecated("")
	override fun getCollisionBoundingBox(blockState: IBlockState, worldIn: IBlockAccess, pos: BlockPos) = NULL_AABB
}
