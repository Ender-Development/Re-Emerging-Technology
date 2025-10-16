package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
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
import org.ender_development.catalyx.core.IBlockProvider
import org.ender_development.catalyx.utils.SideUtils

class LadderBlock(name: String, soundType: SoundType = SoundType.STONE, hardness: Float = 3f) : BlockLadder(), IBlockProvider {
	init {
		registryName = ResourceLocation(Tags.MODID, name)
		translationKey = "$registryName"
		blockHardness = hardness
		this.soundType = soundType
		creativeTab = EmergingTechnology.creativeTab
		EmergingTechnology.modSettings.blocks(this)
	}

	override val instance = this

	override var modDependencies = ""

	override val item = ItemBlock(this)

	override val isEnabled = true

	override fun register(event: RegistryEvent.Register<Block>) =
		event.registry.register(this)

	override fun registerItemBlock(event: RegistryEvent.Register<Item>) {
		item.registryName = registryName
		event.registry.register(item)
		if(!SideUtils.isClient)
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, ModelResourceLocation(registryName!!, "inventory"))
	}

	// no-op
	override fun requires(modDependencies: String) =
		this

	@Deprecated("")
	override fun isOpaqueCube(state: IBlockState) = false

	@Deprecated("")
	override fun shouldSideBeRendered(blockState: IBlockState, blockAccess: IBlockAccess, pos: BlockPos, side: EnumFacing) =
		blockAccess.getBlockState(pos.offset(side)).block !== this
}
