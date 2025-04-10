package io.enderdev.emergingtechnology.items

import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.utils.extensions.translate
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

// tooltipLine is only a supplier so as to not translate on the server-side
class NozzleItem(name: String, val tooltipLine: () -> String) : BaseItem(name) {
	@SideOnly(Side.CLIENT)
	override fun addInformation(stack: ItemStack, world: World?, tooltip: List<String?>, flag: ITooltipFlag) {
		tooltip as MutableList<String?>
		if(!GuiScreen.isShiftKeyDown()) {
			tooltip.add("info.${Tags.MOD_ID}.interaction.shift".translate())
			return
		}
		tooltip.add("info.${Tags.MOD_ID}.nozzle.description".translate())
		tooltip.add(tooltipLine())
	}
}
