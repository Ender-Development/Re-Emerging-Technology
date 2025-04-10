package io.enderdev.emergingtechnology.items

import net.minecraft.item.ItemStack

class BurnableItem(name: String, val time: Int) : BaseItem(name) {
	override fun getItemBurnTime(itemStack: ItemStack) = time
}
