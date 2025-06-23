package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.catalyx.blocks.BaseBlock
import io.enderdev.catalyx.utils.extensions.translate
import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.blocks.ModelBlock
import io.enderdev.emergingtechnology.items.TooltipItemBlock
import io.enderdev.emergingtechnology.tiles.TileSolarPanel
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.state.IBlockState
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.registry.GameRegistry

class BlockSolarPanel() : ModelBlock("solar_panel"), ITileEntityProvider {
	init {
		GameRegistry.registerTileEntity(TileSolarPanel::class.java, ResourceLocation(Tags.MODID, name))
	}

	override fun registerItem(event: RegistryEvent.Register<Item>) {
		event.registry.register(TooltipItemBlock(this) { ItemUtils.extendedTooltip("tile.emergingtechnology:solar_panel.desc".translate()) })
	}

	override fun hasTileEntity(state: IBlockState) = true

	override fun createNewTileEntity(worldIn: World, meta: Int) = TileSolarPanel()
}
