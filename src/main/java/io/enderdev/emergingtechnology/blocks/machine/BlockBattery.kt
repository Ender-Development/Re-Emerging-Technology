package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerBattery
import io.enderdev.emergingtechnology.client.gui.GuiBattery
import io.enderdev.emergingtechnology.tiles.TileBattery
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.block.BlockDirectional
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.Item
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.ender_development.catalyx.items.TooltipItemBlock
import org.ender_development.catalyx.utils.extensions.translate

class BlockBattery() : ModelMachineBlock("battery", TileBattery::class.java,
	EmergingTechnology.guiHandler.registerId(TileBattery::class.java, ContainerBattery::class.java) { GuiBattery::class.java }) {
	init {
		blockHardness = 1f
		defaultState = blockState.baseState.withProperty(BlockDirectional.FACING, EnumFacing.NORTH)
	}

	override val item =
		TooltipItemBlock(this) { stack, world, flag ->
			ItemUtils.extendedTooltip("tile.${Tags.MODID}:battery.desc".translate())
		}

	override fun createBlockState() = BlockStateContainer(this, BlockDirectional.FACING)

	override fun getStateForPlacement(world: World, pos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, meta: Int, placer: EntityLivingBase, hand: EnumHand): IBlockState =
		defaultState.withProperty(BlockDirectional.FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer))

	@Deprecated("")
	override fun getStateFromMeta(meta: Int): IBlockState =
		defaultState.withProperty(BlockDirectional.FACING, EnumFacing.byIndex(meta))

	override fun getMetaFromState(state: IBlockState): Int = EnumFacing.VALUES.indexOf(state.getValue(BlockDirectional.FACING))
}
