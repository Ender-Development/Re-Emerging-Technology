package io.enderdev.emergingtechnology.items

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import org.ender_development.catalyx.items.BaseItem
import org.ender_development.catalyx.utils.extensions.translate

class ItemCircuit(type: String, val cores: Int) : BaseItem(EmergingTechnology.catalyxSettings, "circuit_$type") {
	override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: List<String?>, flagIn: ITooltipFlag) {
		(tooltip as MutableList).add("item.${Tags.MODID}:circuits.desc".translate(cores))
	}
}
