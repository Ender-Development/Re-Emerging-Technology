package io.enderdev.emergingtechnology.blocks

import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.utils.extensions.translate
import net.minecraft.block.BlockHorizontal.FACING
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyDirection
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemStack
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumBlockRenderType
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World

class FrameBlock() : BaseBlock("frame", Material.ANVIL, 1f, SoundType.METAL) {
	init {
		lightOpacity = 0
		defaultState = defaultState.withProperty(FACING, EnumFacing.NORTH)
	}

	val northAABB = AxisAlignedBB(.0, .0, .0, 1.0, 1.0, .0625)
	val eastAABB = AxisAlignedBB(.9375, .0, .0, 1.0, 1.0, 1.0)
	val southAABB = AxisAlignedBB(1.0, .0, .9375, .0, 1.0, 1.0)
	val westAABB = AxisAlignedBB(.0625, .0, 1.0, .0, 1.0, .0)

	override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: List<String?>, flagIn: ITooltipFlag) {
		(tooltip as MutableList).add("info.${Tags.MOD_ID}.frame.description".translate())
	}

	override fun getRenderLayer(): BlockRenderLayer = BlockRenderLayer.CUTOUT;

	@Deprecated("")
	override fun isOpaqueCube(state: IBlockState) = false

	@Deprecated("")
	override fun isFullCube(state: IBlockState) = false

	@Deprecated("")
	override fun getRenderType(state: IBlockState) = EnumBlockRenderType.MODEL

	@Deprecated("")
	override fun getCollisionBoundingBox(state: IBlockState, world: IBlockAccess, pos: BlockPos) = getBox(state)

	@Deprecated("")
	override fun getBoundingBox(state: IBlockState, source: IBlockAccess, pos: BlockPos) = getBox(state)

	fun getBox(state: IBlockState): AxisAlignedBB =
		when(state.getValue(FACING)) {
			EnumFacing.NORTH -> northAABB
			EnumFacing.EAST -> eastAABB
			EnumFacing.SOUTH -> southAABB
			EnumFacing.WEST -> westAABB
			else -> FULL_BLOCK_AABB
		}

	@Deprecated("")
	override fun getStateFromMeta(meta: Int): IBlockState {
		val facing = EnumFacing.byHorizontalIndex(meta)
		return defaultState.withProperty(FACING, facing)
	}

	override fun getMetaFromState(state: IBlockState) = state.getValue(FACING).horizontalIndex

	override fun createBlockState() = BlockStateContainer(this, FACING)

	override fun getStateForPlacement(world: World, pos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, meta: Int, placer: EntityLivingBase, hand: EnumHand): IBlockState = defaultState.withProperty(FACING, EnumFacing.fromAngle(placer.rotationYaw.toDouble()))
}
