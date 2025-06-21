package io.enderdev.emergingtechnology.items

import io.enderdev.emergingtechnology.Tags
import io.enderdev.catalyx.utils.extensions.translate
import net.minecraft.client.Minecraft
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

object ModItems {
	val items = ArrayList<ItemBase>()

	// TODO add over items

	fun registerItems(event: RegistryEvent.Register<Item>) = items.forEach { it.registerItem(event) }

	@SideOnly(Side.CLIENT)
	fun registerModels() = items.forEach { it.registerModel() }

	@SideOnly(Side.CLIENT)
	fun initColors() = 1
		//Minecraft.getMinecraft().itemColors.registerItemColorHandler(ItemColorHandler(), compounds, ingots, elements)
}
