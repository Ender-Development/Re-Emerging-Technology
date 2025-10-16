package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.blocks.GlassBlock
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.tiles.TileSolarGlass
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.state.IBlockState
import net.minecraft.item.Item
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.fml.common.registry.GameRegistry
import org.ender_development.catalyx.items.TooltipItemBlock
import org.ender_development.catalyx.utils.extensions.translate

class BlockSolarGlass() : RotatableModelBlock("solar_glass"), ITileEntityProvider {
	init {
		GameRegistry.registerTileEntity(TileSolarGlass::class.java, ResourceLocation(Tags.MODID, name))
		lightOpacity = 0
	}

	override val item =
		TooltipItemBlock(this) { stack, world, flag ->
			ItemUtils.extendedTooltip(
				"tile.${Tags.MODID}:solar_glass.desc".translate(EmergingTechnologyConfig.ELECTRICS_MODULE.SOLARGLASS.solarEnergyGenerated),
				if(EmergingTechnologyConfig.ELECTRICS_MODULE.SOLARGLASS.pushEnergyDown) "tile.${Tags.MODID}:solar_glass.desc.spread_down".translate() else ""
			)
		}

	override fun hasTileEntity(state: IBlockState) = true

	override fun createNewTileEntity(worldIn: World, meta: Int) = TileSolarGlass()

	override fun getRenderLayer() = BlockRenderLayer.TRANSLUCENT

	@Deprecated("")
	override fun isFullCube(state: IBlockState) = false

	@Deprecated("")
	override fun shouldSideBeRendered(blockState: IBlockState, blockAccess: IBlockAccess, pos: BlockPos, side: EnumFacing) =
		blockAccess.getBlockState(pos.offset(side)).block.let { it !is GlassBlock && it !== this }
}
