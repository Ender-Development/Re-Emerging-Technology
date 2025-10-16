package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerCreativeFiller
import io.enderdev.emergingtechnology.client.gui.GuiCreativeFiller
import io.enderdev.emergingtechnology.tiles.TileCreativeFiller
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.block.state.IBlockState
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import org.ender_development.catalyx.items.TooltipItemBlock
import org.ender_development.catalyx.utils.extensions.translate

class BlockCreativeFiller() : ModelMachineBlock("creative_filler", TileCreativeFiller::class.java,
	EmergingTechnology.guiHandler.registerId(TileCreativeFiller::class.java, ContainerCreativeFiller::class.java) { GuiCreativeFiller::class.java }) {
	init {
		blockHardness = 1f
	}

	override val item =
		TooltipItemBlock(this) { stack, world, flag ->
			ItemUtils.extendedTooltip(
				"tile.${Tags.MODID}:creative_filler.desc".translate(),
			)
		}

	override fun getDrops(drops: NonNullList<ItemStack>, world: IBlockAccess, pos: BlockPos, state: IBlockState, fortune: Int) {} // no-op
}
