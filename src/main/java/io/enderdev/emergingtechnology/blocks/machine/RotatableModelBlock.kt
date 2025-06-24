package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.emergingtechnology.blocks.ModelBlock
import net.minecraft.block.BlockHorizontal
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

open class RotatableModelBlock(name: String) : ModelBlock(name) {
	init {
		defaultState = blockState.baseState.withProperty(BlockHorizontal.FACING, EnumFacing.NORTH)
	}

	override fun createBlockState() = BlockStateContainer(this, BlockHorizontal.FACING)

	override fun getStateForPlacement(world: World, pos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, meta: Int, placer: EntityLivingBase, hand: EnumHand): IBlockState =
		super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(BlockHorizontal.FACING, placer.horizontalFacing.opposite)

	@Deprecated("")
	override fun getStateFromMeta(meta: Int): IBlockState =
		defaultState.withProperty(BlockHorizontal.FACING, EnumFacing.HORIZONTALS[meta])

	override fun getMetaFromState(state: IBlockState): Int =
		EnumFacing.HORIZONTALS.indexOf(state.getValue(BlockHorizontal.FACING))
}
