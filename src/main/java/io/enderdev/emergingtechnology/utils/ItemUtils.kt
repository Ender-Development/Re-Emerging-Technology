package io.enderdev.emergingtechnology.utils

import io.enderdev.catalyx.utils.extensions.translate
import io.enderdev.emergingtechnology.Tags
import net.minecraft.client.gui.GuiScreen

object ItemUtils {
	private val pressShift = listOf("info.${Tags.MODID}.interaction.shift".translate()) // don't create an array and translate this string every time
	fun extendedTooltip(vararg tooltip: String): List<String> =
		if(GuiScreen.isShiftKeyDown()) tooltip.filter { it != "" } else pressShift
}
