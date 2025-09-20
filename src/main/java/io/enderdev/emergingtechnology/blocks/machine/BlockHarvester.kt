package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerHarvester
import io.enderdev.emergingtechnology.client.gui.GuiHarvester
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.items.TooltipItemBlock
import io.enderdev.emergingtechnology.tiles.TileHarvester
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.block.BlockHorizontal
import net.minecraft.block.state.IBlockState
import net.minecraft.item.Item
import net.minecraft.util.EnumBlockRenderType
import net.minecraftforge.common.property.ExtendedBlockState
import net.minecraftforge.common.property.Properties.AnimationProperty
import net.minecraftforge.event.RegistryEvent
import org.ender_development.catalyx.utils.extensions.translate

class BlockHarvester() : RotatableMachineBlock("harvester", TileHarvester::class.java,
	EmergingTechnology.guiHandler.registerId(TileHarvester::class.java, ContainerHarvester::class.java) { GuiHarvester::class.java }) {
	init {
		blockHardness = 1f
	}

	override fun registerItem(event: RegistryEvent.Register<Item>) {
		event.registry.register(TooltipItemBlock(this) {
			ItemUtils.extendedTooltip(
				"tile.${Tags.MODID}:harvester.name".translate(),
				"info.${Tags.MODID}:energy.required".translate(EmergingTechnologyConfig.HYDROPONICS_MODULE.HARVESTER.harvesterEnergyBaseUsage)
			)
		})
	}

	override fun createBlockState() = ExtendedBlockState(this, arrayOf(BlockHorizontal.FACING), arrayOf(AnimationProperty))

	@Deprecated("")
	override fun getRenderType(state: IBlockState) = EnumBlockRenderType.ENTITYBLOCK_ANIMATED

	@Deprecated("")
	override fun isFullCube(state: IBlockState) = false
}
