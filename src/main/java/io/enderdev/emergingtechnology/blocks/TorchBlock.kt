package io.enderdev.emergingtechnology.blocks

import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess

class TorchBlock() : BaseBlock("torch", Material.GLASS, 1f, SoundType.GLASS) {
	val standingAABB: AxisAlignedBB = AxisAlignedBB(.4, .0, .4, .6, 1.0, .6)

	init {
		lightOpacity = 0
		lightValue = 15
	}

	@Deprecated("")
	override fun isOpaqueCube(iBlockState: IBlockState) = false

	@Deprecated("")
	override fun isFullCube(iBlockState: IBlockState) = false

	@Deprecated("")
	override fun getBoundingBox(state: IBlockState, source: IBlockAccess, pos: BlockPos) = standingAABB

	@Deprecated("")
	override fun getCollisionBoundingBox(blockState: IBlockState, worldIn: IBlockAccess, pos: BlockPos) = NULL_AABB
}
