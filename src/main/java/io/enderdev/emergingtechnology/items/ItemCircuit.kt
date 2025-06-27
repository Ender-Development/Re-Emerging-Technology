package io.enderdev.emergingtechnology.items

import io.enderdev.catalyx.utils.extensions.translate
import io.enderdev.emergingtechnology.Tags
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemStack
import net.minecraft.world.World

class ItemCircuit(type: String, val cores: Int) : ItemBase("circuit_$type") {
	override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: List<String?>, flagIn: ITooltipFlag) {
		(tooltip as MutableList).add("item.${Tags.MODID}:circuits.desc".translate(cores))
	}
}
