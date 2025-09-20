package io.enderdev.emergingtechnology.items

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import org.ender_development.catalyx.items.BaseItem
import org.ender_development.catalyx.utils.extensions.translate

class ItemNozzle(type: String, val range: Int, val boost: Int) : BaseItem(EmergingTechnology.catalyxSettings, "nozzle_$type") {
	override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: List<String?>, flagIn: ITooltipFlag) {
		(tooltip as MutableList).addAll(ItemUtils.extendedTooltip(
			"item.${Tags.MODID}:nozzles.desc".translate(),
			"item.${Tags.MODID}:$name.desc".translate(range, boost)
		))
	}
}
