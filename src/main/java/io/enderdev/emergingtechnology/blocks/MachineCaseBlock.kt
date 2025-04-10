package io.enderdev.emergingtechnology.blocks

import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState

class MachineCaseBlock() : BaseBlock("machine_case", Material.IRON, 1f, SoundType.METAL) {
	@Deprecated("")
	override fun isOpaqueCube(state: IBlockState) = false
	@Deprecated("")
	override fun isFullCube(state: IBlockState) = false
}
