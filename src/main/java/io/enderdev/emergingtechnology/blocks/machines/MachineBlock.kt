package io.enderdev.emergingtechnology.blocks.machines

import io.enderdev.emergingtechnology.blocks.BaseBlock
import net.minecraft.block.BlockHorizontal.FACING
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.Mirror
import net.minecraft.util.Rotation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

abstract class MachineBlock(name: String, val te: () -> TileEntity) : BaseBlock(name, Material.IRON, hardness = 1f, SoundType.METAL), ITileEntityProvider {
	init {
		defaultState = defaultState.withProperty(FACING, EnumFacing.NORTH)
	}

	override fun createNewTileEntity(worldIn: World, meta: Int) = te()

	override fun createBlockState() = BlockStateContainer(this, FACING)

	override fun onBlockAdded(world: World, pos: BlockPos, state: IBlockState) {
		if(world.isRemote)
			return

		var face = state.getValue(FACING)
		if(world.getBlockState(pos.offset(face)).isFullBlock && !world.getBlockState(pos.offset(face.opposite)).isFullBlock)
			face = face.opposite

		world.setBlockState(pos, state.withProperty(FACING, face))
	}

	override fun getStateForPlacement(world: World, pos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, meta: Int, placer: EntityLivingBase, hand: EnumHand): IBlockState =
		defaultState.withProperty(FACING, placer.horizontalFacing.opposite)

	override fun onBlockPlacedBy(world: World, pos: BlockPos, state: IBlockState, placer: EntityLivingBase, stack: ItemStack) {
		world.setBlockState(pos, defaultState.withProperty(FACING, placer.horizontalFacing.opposite))
	}

	@Deprecated("")
	override fun withRotation(state: IBlockState, rot: Rotation): IBlockState = state.withProperty(FACING, rot.rotate(state.getValue(FACING)))

	@Deprecated("")
	override fun withMirror(state: IBlockState, mirror: Mirror): IBlockState = state.withProperty(FACING, mirror.mirror(state.getValue(FACING)))

	@Deprecated("")
	override fun getStateForPlacement(world: World, pos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, meta: Int, placer: EntityLivingBase): IBlockState =
		defaultState.withProperty(FACING, EnumFacing.byHorizontalIndex(meta))

	override fun getMetaFromState(state: IBlockState) = state.getValue(FACING).horizontalIndex

	@Deprecated("")
	override fun isOpaqueCube(state: IBlockState) = false

	@Deprecated("")
	override fun isFullCube(state: IBlockState) = false
}
