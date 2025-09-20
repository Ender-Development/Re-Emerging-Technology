package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerWasteCollector
import io.enderdev.emergingtechnology.client.gui.GuiWasteCollector
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.tiles.TileWasteCollector
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.block.properties.PropertyBool
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

class BlockWasteCollector() : ModelMachineBlock("waste_collector", TileWasteCollector::class.java,
	EmergingTechnology.guiHandler.registerId(TileWasteCollector::class.java, ContainerWasteCollector::class.java) { GuiWasteCollector::class.java }) {
	init {
		blockHardness = 1f
		defaultState = blockState.baseState.withProperty(FULL, false)
	}

	override fun createBlockState() = BlockStateContainer(this, FULL)

	override fun getStateForPlacement(world: World, pos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, meta: Int, placer: EntityLivingBase, hand: EnumHand): IBlockState =
		super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(FULL, false)

	@Deprecated("")
	override fun getStateFromMeta(meta: Int): IBlockState =
		defaultState.withProperty(FULL, meta == 1)

	override fun getMetaFromState(state: IBlockState) =
		if(state.getValue(FULL)) 1 else 0

	override fun createItemBlock(): Item =
		TooltipItemBlock(this) { stack, world, flag ->
			ItemUtils.extendedTooltip(
				"tile.${Tags.MODID}:waste_collector.desc".translate(),
				if(EmergingTechnologyConfig.POLYMERS_MODULE.COLLECTOR.biomeRequirementDisabled) "" else "tile.${Tags.MODID}:waste_collector.req.biome".translate(),
				"tile.${Tags.MODID}:waste_collector.req.water".translate(EmergingTechnologyConfig.POLYMERS_MODULE.COLLECTOR.minimumWaterBlocks),
				"tile.${Tags.MODID}:waste_collector.req.surface".translate()
			)
		}

	companion object {
		val FULL: PropertyBool = PropertyBool.create("full")
	}
}
