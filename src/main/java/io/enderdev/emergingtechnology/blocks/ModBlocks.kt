package io.enderdev.emergingtechnology.blocks

import io.enderdev.emergingtechnology.ConfigHandler
import io.enderdev.emergingtechnology.blocks.machine.*
import io.enderdev.emergingtechnology.client.gui.GuiHandler
import io.enderdev.emergingtechnology.tiles.*
import io.enderdev.catalyx.blocks.BaseBlock
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

object ModBlocks {
	val blocks = mutableListOf<BaseBlock>()

	// TODO add over blocks

	fun registerBlocks(event: RegistryEvent.Register<Block>) = blocks.forEach { it.registerBlock(event) }

	fun registerItems(event: RegistryEvent.Register<Item>) = blocks.forEach { it.registerItem(event) }

	@SideOnly(Side.CLIENT)
	fun registerModels() = blocks.forEach { if(it is IHasModel) it.registerModel() }
}
