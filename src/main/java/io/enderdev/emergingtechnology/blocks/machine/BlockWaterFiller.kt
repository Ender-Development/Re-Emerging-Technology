package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.catalyx.utils.extensions.translate
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.blocks.ModelBlock
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.items.TooltipItemBlock
import io.enderdev.emergingtechnology.tiles.TileWaterFiller
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.state.IBlockState
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.registry.GameRegistry

class BlockWaterFiller() : ModelBlock("water_filler"), ITileEntityProvider {
	init {
		GameRegistry.registerTileEntity(TileWaterFiller::class.java, ResourceLocation(Tags.MODID, name))
	}

	override fun registerItem(event: RegistryEvent.Register<Item>) {
		event.registry.register(TooltipItemBlock(this) { ItemUtils.extendedTooltip("tile.${Tags.MODID}:water_filler.desc".translate(EmergingTechnologyConfig.HYDROPONICS_MODULE.FILLER.fillerFluidTransferRate)) })
	}

	override fun hasTileEntity(state: IBlockState) = true

	override fun createNewTileEntity(worldIn: World, meta: Int) = TileWaterFiller()
}
