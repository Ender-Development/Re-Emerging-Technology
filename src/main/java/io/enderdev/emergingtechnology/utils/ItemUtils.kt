package io.enderdev.emergingtechnology.utils

import io.enderdev.catalyx.utils.extensions.translate
import io.enderdev.emergingtechnology.Tags
import net.minecraft.client.gui.GuiScreen

object ItemUtils {
	fun extendedTooltip(vararg tooltip: String): Array<out String> =
		if(GuiScreen.isShiftKeyDown())
			tooltip
		else
			arrayOf("info.${Tags.MODID}.interaction.shift".translate())
}
