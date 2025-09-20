package io.enderdev.emergingtechnology.items

import io.enderdev.emergingtechnology.Tags
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import org.ender_development.catalyx.utils.extensions.translate

class ItemCookedSyntheticMeat(val type: String, val entityId: String, hunger: Int, saturation: Float) : BaseFoodItem("synthetic_${type}_cooked", hunger, saturation) {
	override fun getItemStackDisplayName(stack: ItemStack) =
		"item.${Tags.MODID}:synthetic_meat_cooked.name".translate(type.replaceFirstChar(Char::uppercaseChar))

	override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: List<String?>, flagIn: ITooltipFlag) {
		(tooltip as MutableList).add("item.${Tags.MODID}:synthetic_meat_cooked.desc".translate(type))
	}
}
