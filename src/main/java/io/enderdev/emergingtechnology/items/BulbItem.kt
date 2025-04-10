package io.enderdev.emergingtechnology.items

import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.utils.extensions.translate
import io.moonman.emergingtechnology.config.EmergingTechnologyConfig
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemStack
import net.minecraft.world.World

class BulbItem(name: String, val growthModifier: Int, val energyModifier: Int) : BaseItem(name) {
	override fun addInformation(stack: ItemStack, world: World?, tooltip: List<String?>, flag: ITooltipFlag) {
		tooltip as MutableList<String?>
		if(!GuiScreen.isShiftKeyDown()) {
			tooltip.add("info.${Tags.MOD_ID}.interaction.shift".translate())
			return
		}
		tooltip.add("info.${Tags.MOD_ID}.bulb.description".translate())
		tooltip.add("info.${Tags.MOD_ID}.energy.required".translate(energyModifier * EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.lightEnergyBaseUsage))
		tooltip.add("info.${Tags.MOD_ID}.growth.generated".translate(growthModifier))
	}
}
