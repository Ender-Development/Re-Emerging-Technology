package io.enderdev.emergingtechnology.blocks.machines

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.gui.ResourceType
import io.enderdev.emergingtechnology.utils.extensions.translate
import net.minecraft.block.state.IBlockState
import net.minecraft.client.gui.GuiScreen
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.InventoryHelper
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
import java.util.*

abstract class InventoryMachineBlock(val name: String, te: () -> TileEntity, val guiID: Int) : MachineBlock(name, te) {
	override fun breakBlock(world: World, pos: BlockPos, state: IBlockState) {
		val cap = world.getTileEntity(pos)?.getCapability(ITEM_HANDLER_CAPABILITY, null)
		if(cap == null)
			return

		(0..<cap.slots).forEach {
			val stack = cap.getStackInSlot(it)
			if(!stack.isEmpty)
				InventoryHelper.spawnItemStack(world, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), stack)
		}
	}

	override fun onBlockActivated(world: World, pos: BlockPos, state: IBlockState, player: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
		if(!world.isRemote)
			player.openGui(EmergingTechnology, guiID, world, pos.x, pos.y, pos.z)
		return true
	}

	fun getTooltip(usage: List<Pair<Int, ResourceType>>): List<String?> {
		if(!GuiScreen.isShiftKeyDown())
			return listOf("info.${Tags.MOD_ID}.interaction.shift".translate())
		return usage.map { (cnt, type) -> "info.${Tags.MOD_ID}.${type.name.lowercase(Locale.getDefault())}.required".translate(cnt) }
	}
}
