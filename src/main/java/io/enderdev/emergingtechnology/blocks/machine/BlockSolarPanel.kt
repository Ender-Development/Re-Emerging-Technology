package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.catalyx.utils.extensions.translate
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.items.TooltipItemBlock
import io.enderdev.emergingtechnology.tiles.TileSolarPanel
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.block.BlockHorizontal
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.Entity
import net.minecraft.item.Item
import net.minecraft.util.EnumFacing
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.registry.GameRegistry

class BlockSolarPanel() : RotatableModelBlock("solar_panel"), ITileEntityProvider {
	init {
		GameRegistry.registerTileEntity(TileSolarPanel::class.java, ResourceLocation(Tags.MODID, name))
	}

	override fun registerItem(event: RegistryEvent.Register<Item>) {
		event.registry.register(TooltipItemBlock(this) { ItemUtils.extendedTooltip("tile.${Tags.MODID}:solar_panel.desc".translate(EmergingTechnologyConfig.ELECTRICS_MODULE.SOLAR.solarEnergyGenerated)) })
	}

	override fun hasTileEntity(state: IBlockState) = true

	override fun createNewTileEntity(worldIn: World, meta: Int) = TileSolarPanel()

	val aabbSlabBottom = AxisAlignedBB(.0, .0, .0, 1.0, .5, 1.0)
	val aabbNorthQuarter = AxisAlignedBB(.0, .5, .0, 1.0, 1.0, .5)
	val aabbEastQuarter = AxisAlignedBB(.5, .5, .0, 1.0, 1.0, 1.0)
	val aabbSouthQuarter = AxisAlignedBB(.0, .5, .5, 1.0, 1.0, 1.0)
	val aabbWestQuarter = AxisAlignedBB(.0, .5, .0, .5, 1.0, 1.0)

	@Deprecated("")
	@Suppress("DEPRECATION")
	override fun addCollisionBoxToList(state: IBlockState, worldIn: World, pos: BlockPos, entityBox: AxisAlignedBB, collidingBoxes: List<AxisAlignedBB?>, entityIn: Entity?, isActualState: Boolean) {
		addCollisionBoxToList(pos, entityBox, collidingBoxes, aabbSlabBottom)
		addCollisionBoxToList(pos, entityBox, collidingBoxes, when(state.getValue(BlockHorizontal.FACING)) {
			EnumFacing.NORTH -> aabbSouthQuarter
			EnumFacing.EAST -> aabbWestQuarter
			EnumFacing.SOUTH -> aabbNorthQuarter
			EnumFacing.WEST -> aabbEastQuarter
			else -> throw IllegalStateException()
		})
	}
}
