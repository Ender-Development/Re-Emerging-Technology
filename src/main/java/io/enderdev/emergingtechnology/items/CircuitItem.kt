package io.enderdev.emergingtechnology.items

import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.utils.extensions.translate
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class CircuitItem(name: String, val cores: Int) : BaseItem(name) {
	@SideOnly(Side.CLIENT)
	override fun addInformation(stack: ItemStack, world: World?, tooltip: List<String?>, flag: ITooltipFlag) {
		(tooltip as MutableList<String?>).add("info.${Tags.MOD_ID}.circuit.description".translate(cores))
	}
}
