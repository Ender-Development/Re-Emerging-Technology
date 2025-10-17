package io.enderdev.emergingtechnology.blocks.machine

import net.minecraft.block.state.IBlockState
import net.minecraft.entity.Entity
import net.minecraft.util.EnumBlockRenderType
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World

class CustomCollisionMachineBlock(name: String, guiId: Int, vararg val boundingBoxes: AxisAlignedBB) : ModelMachineBlock(name, guiId) {
	@Deprecated("")
	override fun getRenderType(state: IBlockState): EnumBlockRenderType = EnumBlockRenderType.MODEL

	@Deprecated("")
	override fun isOpaqueCube(state: IBlockState) = false

	@Deprecated("")
	override fun isFullCube(state: IBlockState) = false

	@Deprecated("")
	override fun getBoundingBox(state: IBlockState, source: IBlockAccess, pos: BlockPos): AxisAlignedBB = boundingBoxes[0]

	@Deprecated("")
	override fun addCollisionBoxToList(
		state: IBlockState,
		worldIn: World,
		pos: BlockPos,
		entityBox: AxisAlignedBB,
		collidingBoxes: List<AxisAlignedBB>,
		entityIn: Entity?, mysteryboolean: Boolean
	) {
		boundingBoxes.forEach {
			@Suppress("DEPRECATION")
			addCollisionBoxToList(pos, entityBox, collidingBoxes, it)
		}
	}
}
