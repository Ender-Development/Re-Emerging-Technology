package io.enderdev.emergingtechnology.blocks

import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess

class GlassBlock(name: String) : BaseBlock(name) {
	init {
		material = Material.GLASS
		blockHardness = 1f
		lightOpacity = 0
	}

	override fun getRenderLayer() = BlockRenderLayer.TRANSLUCENT

	@Deprecated("")
	override fun isFullCube(state: IBlockState) = false

	@Deprecated("")
	override fun shouldSideBeRendered(state: IBlockState, access: IBlockAccess, pos: BlockPos, side: EnumFacing): Boolean {
		val other = access.getBlockState(pos.offset(side)).block
		return if(other == this) false else super.shouldSideBeRendered(state, access, pos, side)
	}
}
