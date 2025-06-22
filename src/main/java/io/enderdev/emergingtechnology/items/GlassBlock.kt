package io.enderdev.emergingtechnology.items

import io.enderdev.emergingtechnology.blocks.ModelBlock
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess

class GlassBlock(name: String, hardness: Float = 1f) : ModelBlock(name, Material.GLASS, SoundType.GLASS, hardness) {
	init {
		lightOpacity = 0
	}

	override fun getRenderLayer() = BlockRenderLayer.TRANSLUCENT

	@Deprecated("")
	override fun isFullCube(state: IBlockState) = false

	@Deprecated("")
	override fun isOpaqueCube(state: IBlockState) = false

	@Deprecated("")
	override fun shouldSideBeRendered(blockState: IBlockState, blockAccess: IBlockAccess, pos: BlockPos, side: EnumFacing) =
		blockAccess.getBlockState(pos.add(side.directionVec)).block !is GlassBlock
}
