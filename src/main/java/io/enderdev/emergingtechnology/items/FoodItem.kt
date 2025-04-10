package io.enderdev.emergingtechnology.items

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import net.minecraft.item.ItemFood
import net.minecraft.util.ResourceLocation

class FoodItem(val name: String, healAmount: Int, saturation: Float) : ItemFood(healAmount, saturation, false) {
	init {
		registryName = ResourceLocation(Tags.MOD_ID, name)
		translationKey = "${Tags.MOD_ID}.$name"
		creativeTab = EmergingTechnology.creativeTab
		ModItems.items.add(this)
	}
}
