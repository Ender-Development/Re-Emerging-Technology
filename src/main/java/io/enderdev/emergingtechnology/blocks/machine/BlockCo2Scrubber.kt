package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.catalyx.utils.extensions.translate
import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerCo2Scrubber
import io.enderdev.emergingtechnology.client.gui.GuiCo2Scrubber
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.items.TooltipItemBlock
import io.enderdev.emergingtechnology.tiles.TileCo2Scrubber
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.block.BlockHorizontal
import net.minecraft.block.state.IBlockState
import net.minecraft.item.Item
import net.minecraft.util.EnumBlockRenderType
import net.minecraftforge.common.property.ExtendedBlockState
import net.minecraftforge.common.property.Properties.AnimationProperty
import net.minecraftforge.event.RegistryEvent

class BlockCo2Scrubber() : RotatableMachineBlock("co2_scrubber", TileCo2Scrubber::class.java,
	EmergingTechnology.guiHandler.registerId(TileCo2Scrubber::class.java, ContainerCo2Scrubber::class.java) { GuiCo2Scrubber::class.java }) {
	init {
		blockHardness = 1f
	}

	override fun registerItem(event: RegistryEvent.Register<Item>) {
		event.registry.register(TooltipItemBlock(this) {
			ItemUtils.extendedTooltip(
				"tile.${Tags.MODID}:co2_scrubber.desc".translate(),
				"info.${Tags.MODID}.energy.required".translate(EmergingTechnologyConfig.HYDROPONICS_MODULE.SCRUBBER.scrubberEnergyBaseUsage),
				"info.${Tags.MODID}.water.required".translate(EmergingTechnologyConfig.HYDROPONICS_MODULE.SCRUBBER.scrubberWaterBaseUsage)
			)
		})
	}

	override fun createBlockState() = ExtendedBlockState(this, arrayOf(BlockHorizontal.FACING), arrayOf(AnimationProperty))

	@Deprecated("")
	override fun getRenderType(state: IBlockState) = EnumBlockRenderType.ENTITYBLOCK_ANIMATED

	@Deprecated("")
	override fun isFullCube(state: IBlockState) = false
}
