package io.enderdev.emergingtechnology.items

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import org.ender_development.catalyx.items.BaseItem
import org.ender_development.catalyx.utils.extensions.translate

class ItemBulb(type: String, val energyMult: Int, val growthMult: Int, val colour: Int) : BaseItem(EmergingTechnology, "bulb_$type") {
	override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: List<String?>, flagIn: ITooltipFlag) {
		(tooltip as MutableList).addAll(ItemUtils.extendedTooltip(
			"item.${Tags.MODID}:bulb.desc".translate(),
			"info.${Tags.MODID}:energy.required".translate(EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.lightEnergyBaseUsage * energyMult),
			"info.${Tags.MODID}:growth.generated".translate(growthMult)
		))
	}
}
