package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.catalyx.IBothProvider
import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.blocks.ModBlocks
import net.minecraft.block.Block
import net.minecraft.block.BlockLadder
import net.minecraft.block.SoundType
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.util.EnumFacing
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class LadderBlock(name: String, soundType: SoundType = SoundType.STONE, hardness: Float = 3f) : BlockLadder(), IBothProvider, IHasModel {
	init {
		registryName = ResourceLocation(Tags.MODID, name)
		translationKey = "$registryName"
		blockHardness = hardness
		this.soundType = soundType
		creativeTab = EmergingTechnology.creativeTab
		ModBlocks.blocks.add(this)
	}

	override fun registerBlock(event: RegistryEvent.Register<Block>) {
		event.registry.register(this)
	}

	override fun registerItem(event: RegistryEvent.Register<Item>) {
		event.registry.register(ItemBlock(this).setRegistryName(registryName))
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
