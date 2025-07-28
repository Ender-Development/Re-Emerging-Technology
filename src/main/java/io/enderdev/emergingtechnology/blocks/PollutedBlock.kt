package io.enderdev.emergingtechnology.blocks

import io.enderdev.catalyx.utils.extensions.toStack
import io.enderdev.emergingtechnology.items.ModItems
import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.InventoryHelper
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.NonNullList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World

@Suppress("DEPRECATION") // Block#soundType
class PollutedBlock(name: String, val block: Block) : ModelBlock("polluted_$name", block.defaultState.material, block.soundType, 1f) {
	override fun onBlockActivated(world: World, pos: BlockPos, state: IBlockState, player: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
		if(facing != EnumFacing.UP)
			return false

		if(world.isRemote)
			return true

		world.setBlockState(pos, block.defaultState, 3)
		InventoryHelper.spawnItemStack(world, pos.x.toDouble(), pos.y.toDouble() + 1, pos.z.toDouble(), ModItems.plasticWaste.toStack())

		return true
	}

	override fun getDrops(drops: NonNullList<ItemStack>, world: IBlockAccess, pos: BlockPos, state: IBlockState, fortune: Int) {
		drops.add(block.toStack())
		drops.add(ModItems.plasticWaste.toStack())
	}
}
