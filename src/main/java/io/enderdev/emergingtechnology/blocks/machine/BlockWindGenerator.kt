package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.catalyx.utils.extensions.translate
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.blocks.ModelBlock
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.items.TooltipItemBlock
import io.enderdev.emergingtechnology.tiles.TileSolarPanel
import io.enderdev.emergingtechnology.tiles.TileWindGenerator
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.Entity
import net.minecraft.item.Item
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumBlockRenderType
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.property.ExtendedBlockState
import net.minecraftforge.common.property.Properties.AnimationProperty
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.registry.GameRegistry

class BlockWindGenerator() : ModelBlock("wind_generator"), ITileEntityProvider {
	init {
		GameRegistry.registerTileEntity(TileWindGenerator::class.java, ResourceLocation(Tags.MODID, name))
	}

	override fun registerItem(event: RegistryEvent.Register<Item>) {
		event.registry.register(TooltipItemBlock(this) {
			ItemUtils.extendedTooltip(
				"tile.${Tags.MODID}:wind_generator.desc".translate(EmergingTechnologyConfig.ELECTRICS_MODULE.WIND.energyGenerated, EmergingTechnologyConfig.ELECTRICS_MODULE.WIND.minOptimalHeight, EmergingTechnologyConfig.ELECTRICS_MODULE.WIND.maxOptimalHeight),
				"tile.${Tags.MODID}:wind_generator.desc.req".translate(EmergingTechnologyConfig.ELECTRICS_MODULE.WIND.minimumAirBlocks)
			)
		})
	}

	override fun hasTileEntity(state: IBlockState) = true

	override fun createNewTileEntity(worldIn: World, meta: Int) = TileWindGenerator()

	@Deprecated("")
	override fun isFullCube(state: IBlockState) = false

	val collisionAABB = AxisAlignedBB(.0, .0, .0, 1.0, .1, 1.0)

	@Deprecated("")
	override fun getCollisionBoundingBox(blockState: IBlockState, worldIn: IBlockAccess, pos: BlockPos) = collisionAABB

	override fun createBlockState() = ExtendedBlockState(this, emptyArray(), arrayOf(AnimationProperty))

	@Deprecated("")
	override fun getRenderType(state: IBlockState) = EnumBlockRenderType.ENTITYBLOCK_ANIMATED
}
