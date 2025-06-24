package io.enderdev.emergingtechnology.blocks

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.blocks.machine.IHasModel
import io.enderdev.catalyx.blocks.BaseBlock
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

open class ModelBlock(val name: String, material: Material = Material.ROCK, soundType: SoundType = SoundType.STONE, hardness: Float = 3f) : BaseBlock(EmergingTechnology.catalyxSettings, name, material), IHasModel {
	init {
		this.soundType = soundType
		blockHardness = hardness
	}

	@SideOnly(Side.CLIENT)
	override fun registerModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, ModelResourceLocation(registryName!!, "inventory"))
	}

	@Deprecated("")
	override fun isOpaqueCube(state: IBlockState) = false

	@Deprecated("")
	override fun shouldSideBeRendered(blockState: IBlockState, blockAccess: IBlockAccess, pos: BlockPos, side: EnumFacing) =
		blockAccess.getBlockState(pos.offset(side)).block !== this
}
