package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.catalyx.blocks.BaseMachineBlock
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.entity.Entity
import net.minecraft.item.Item
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumBlockRenderType
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.client.model.ModelLoader

open class ModelMachineBlock(name: String, tileClass: Class<out TileEntity>, guiID: Int) : BaseMachineBlock(EmergingTechnology.catalyxSettings, name, tileClass, guiID), IHasModel {
	override fun registerModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, ModelResourceLocation(registryName!!, "inventory"))
	}

	@Deprecated("")
	override fun isOpaqueCube(state: IBlockState) = false

	@Deprecated("")
	override fun shouldSideBeRendered(blockState: IBlockState, blockAccess: IBlockAccess, pos: BlockPos, side: EnumFacing) =
		blockAccess.getBlockState(pos.offset(side)).block !== this
}
