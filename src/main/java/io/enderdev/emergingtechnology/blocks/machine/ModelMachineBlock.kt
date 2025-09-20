package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.emergingtechnology.EmergingTechnology
import net.minecraft.block.state.IBlockState
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import org.ender_development.catalyx.blocks.BaseMachineBlock

open class ModelMachineBlock(name: String, tileClass: Class<out TileEntity>, guiID: Int) : BaseMachineBlock(EmergingTechnology.catalyxSettings, name, tileClass, guiID) {
	@Deprecated("")
	override fun isOpaqueCube(state: IBlockState) = false

	@Deprecated("")
	override fun shouldSideBeRendered(blockState: IBlockState, blockAccess: IBlockAccess, pos: BlockPos, side: EnumFacing) =
		blockAccess.getBlockState(pos.offset(side)).block !== this
}
