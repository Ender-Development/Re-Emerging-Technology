package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.catalyx.utils.extensions.translate
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.blocks.GlassBlock
import io.enderdev.emergingtechnology.blocks.ModelBlock
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.items.TooltipItemBlock
import io.enderdev.emergingtechnology.tiles.TilePiezoelectricGenerator
import io.enderdev.emergingtechnology.tiles.TileSolarGlass
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.Item
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.registry.GameRegistry

class BlockSolarGlass() : RotatableModelBlock("solar_glass"), ITileEntityProvider {
	init {
		GameRegistry.registerTileEntity(TileSolarGlass::class.java, ResourceLocation(Tags.MODID, name))
		lightOpacity = 0
	}

	override fun registerItem(event: RegistryEvent.Register<Item>) {
		event.registry.register(TooltipItemBlock(this) {
			ItemUtils.extendedTooltip(
				"tile.${Tags.MODID}:solar_glass.desc".translate(EmergingTechnologyConfig.ELECTRICS_MODULE.SOLARGLASS.solarEnergyGenerated),
				if(EmergingTechnologyConfig.ELECTRICS_MODULE.SOLARGLASS.pushEnergyDown) "tile.${Tags.MODID}:solar_glass.desc.spread_down".translate() else ""
			)
		})
	}

	override fun hasTileEntity(state: IBlockState) = true

	override fun createNewTileEntity(worldIn: World, meta: Int) = TileSolarGlass()

	override fun getRenderLayer() = BlockRenderLayer.TRANSLUCENT

	@Deprecated("")
	override fun isFullCube(state: IBlockState) = false

	@Deprecated("")
	override fun shouldSideBeRendered(blockState: IBlockState, blockAccess: IBlockAccess, pos: BlockPos, side: EnumFacing) =
		blockAccess.getBlockState(pos.add(side.directionVec)).block.let { it !is GlassBlock && it !== this }
}
