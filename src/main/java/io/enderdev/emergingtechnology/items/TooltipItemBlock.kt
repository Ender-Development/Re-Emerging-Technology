package io.enderdev.emergingtechnology.items

import net.minecraft.block.Block
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraft.world.World

class TooltipItemBlock(block: Block, val tooltip: () -> Collection<String>) : ItemBlock(block) {
	init {
		registryName = block.registryName
	}

	override fun addInformation(stack: ItemStack, world: World?, tooltips: List<String>, flag: ITooltipFlag) {
		(tooltips as MutableList).addAll(tooltip())
	}
}
