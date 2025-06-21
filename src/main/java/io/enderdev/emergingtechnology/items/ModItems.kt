package io.enderdev.emergingtechnology.items

import io.enderdev.catalyx.items.BaseItem
import io.enderdev.emergingtechnology.blocks.machine.IHasModel
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

object ModItems {
	val items = ArrayList<BaseItem>()

	// Circuits
	val circuit = ItemBase("circuit")
	val circuitBasic = ItemCircuit("basic", 4)
	val circuitAdvanced = ItemCircuit("advanced", 8)
	val circuitSuperior = ItemCircuit("superior", 16)

	fun registerItems(event: RegistryEvent.Register<Item>) = items.forEach { it.registerItem(event) }

	@SideOnly(Side.CLIENT)
	fun registerModels() = items.forEach { if(it is IHasModel) it.registerModel() }

	@SideOnly(Side.CLIENT)
	fun initColors() = 1
		//Minecraft.getMinecraft().itemColors.registerItemColorHandler(ItemColorHandler(), compounds, ingots, elements)
}
