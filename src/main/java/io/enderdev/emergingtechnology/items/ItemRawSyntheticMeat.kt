package io.enderdev.emergingtechnology.items

import io.enderdev.catalyx.utils.extensions.translate
import io.enderdev.emergingtechnology.Tags
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemStack
import net.minecraft.world.World

class ItemRawSyntheticMeat(val type: String, val entityId: String) : ItemBase("synthetic_${type}") {
	override fun getItemStackDisplayName(stack: ItemStack) =
		"item.${Tags.MODID}:synthetic_meat.name".translate(type.replaceFirstChar(Char::uppercaseChar))

	override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: List<String?>, flagIn: ITooltipFlag) {
		(tooltip as MutableList).add("item.${Tags.MODID}:synthetic_meat.desc".translate(type))
	}
}
