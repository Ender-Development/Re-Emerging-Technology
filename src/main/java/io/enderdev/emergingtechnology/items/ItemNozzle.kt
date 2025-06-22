package io.enderdev.emergingtechnology.items

import io.enderdev.catalyx.utils.extensions.translate
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.config.hydroponics.diffuser.HydroponicsModuleDiffuser
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemStack
import net.minecraft.world.World

class ItemNozzle(type: String, val range: Int, val boost: Int) : ItemBase("nozzle_$type") {
	override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: List<String?>, flagIn: ITooltipFlag) {
		(tooltip as MutableList).addAll(ItemUtils.extendedTooltip(
			"item.${Tags.MODID}:nozzles.desc".translate(),
			"item.${Tags.MODID}:$name.desc".translate(range, boost)
		))
	}
}
