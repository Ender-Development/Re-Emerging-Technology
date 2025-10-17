package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerCo2Scrubber
import io.enderdev.emergingtechnology.client.gui.GuiCo2Scrubber
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.tiles.TileCo2Scrubber
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.block.BlockHorizontal
import net.minecraft.block.state.IBlockState
import net.minecraft.util.EnumBlockRenderType
import net.minecraftforge.common.property.ExtendedBlockState
import net.minecraftforge.common.property.Properties.AnimationProperty
import org.ender_development.catalyx.items.TooltipItemBlock
import org.ender_development.catalyx.utils.extensions.translate

class BlockCo2Scrubber() : RotatableMachineBlock("co2_scrubber", EmergingTechnology.guiHandler.registerId(TileCo2Scrubber::class.java, ContainerCo2Scrubber::class.java) { GuiCo2Scrubber::class.java }) {
	init {
		blockHardness = 1f
	}

	override val item =
		TooltipItemBlock(this) { stack, world, flag ->
			ItemUtils.extendedTooltip(
				"tile.${Tags.MODID}:co2_scrubber.desc".translate(),
				"info.${Tags.MODID}:energy.required".translate(EmergingTechnologyConfig.HYDROPONICS_MODULE.SCRUBBER.scrubberEnergyBaseUsage),
				"info.${Tags.MODID}:water.required".translate(EmergingTechnologyConfig.HYDROPONICS_MODULE.SCRUBBER.scrubberWaterBaseUsage)
			)
		}

	override fun createBlockState() = ExtendedBlockState(this, arrayOf(BlockHorizontal.FACING), arrayOf(AnimationProperty))

	@Deprecated("")
	override fun getRenderType(state: IBlockState) = EnumBlockRenderType.ENTITYBLOCK_ANIMATED

	@Deprecated("")
	override fun isFullCube(state: IBlockState) = false
}
