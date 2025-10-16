package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.blocks.ModelBlock
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.tiles.TileTidalGenerator
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.state.IBlockState
import net.minecraft.util.EnumBlockRenderType
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.common.property.ExtendedBlockState
import net.minecraftforge.common.property.Properties.AnimationProperty
import net.minecraftforge.fml.common.registry.GameRegistry
import org.ender_development.catalyx.items.TooltipItemBlock
import org.ender_development.catalyx.utils.extensions.translate

class BlockTidalGenerator() : ModelBlock("tidal_generator"), ITileEntityProvider {
	init {
		GameRegistry.registerTileEntity(TileTidalGenerator::class.java, ResourceLocation(Tags.MODID, name))
	}

	override val item =
		TooltipItemBlock(this) { stack, world, flag ->
			ItemUtils.extendedTooltip(
				"tile.${Tags.MODID}:tidal_generator.desc".translate(EmergingTechnologyConfig.ELECTRICS_MODULE.TIDALGENERATOR.tidalEnergyGenerated, EmergingTechnologyConfig.ELECTRICS_MODULE.TIDALGENERATOR.minOptimalDepth, EmergingTechnologyConfig.ELECTRICS_MODULE.TIDALGENERATOR.maxOptimalDepth),
				"tile.${Tags.MODID}:tidal_generator.desc.req.water".translate(EmergingTechnologyConfig.ELECTRICS_MODULE.TIDALGENERATOR.minimumWaterBlocks),
				if(!EmergingTechnologyConfig.ELECTRICS_MODULE.TIDALGENERATOR.biomeRequirementDisabled)
					"tile.${Tags.MODID}:tidal_generator.desc.req.biome".translate("TODO")
				else
					""
			)
		}

	override fun hasTileEntity(state: IBlockState) = true

	override fun createNewTileEntity(worldIn: World, meta: Int) = TileTidalGenerator()

	override fun createBlockState() = ExtendedBlockState(this, emptyArray(), arrayOf(AnimationProperty))

	@Deprecated("")
	override fun getRenderType(state: IBlockState) = EnumBlockRenderType.ENTITYBLOCK_ANIMATED
}
