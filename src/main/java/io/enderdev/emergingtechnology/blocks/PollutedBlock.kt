package io.enderdev.emergingtechnology.blocks

import io.enderdev.emergingtechnology.items.ModItems
import io.enderdev.emergingtechnology.utils.extensions.stack
import net.minecraft.block.Block
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
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

class PollutedBlock(name: String, material: Material, soundType: SoundType, val baseBlock: Block) : BaseBlock(name, material, 1f, soundType) {
	override fun onBlockActivated(world: World, pos: BlockPos, state: IBlockState, player: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
		if(world.isRemote)
			return true

		if(facing == EnumFacing.UP) {
			world.setBlockState(pos, baseBlock.defaultState, 3)
			InventoryHelper.spawnItemStack(world, pos.x.toDouble(), pos.y + 1.0, pos.z.toDouble(), ModItems.plasticWaste.stack())
			return true
		}

		return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ)
	}

	override fun getDrops(drops: NonNullList<ItemStack?>, world: IBlockAccess, pos: BlockPos, state: IBlockState, fortune: Int) {
		drops.add(ModItems.plasticWaste.stack())
		drops.add(baseBlock.stack())
	}
}
