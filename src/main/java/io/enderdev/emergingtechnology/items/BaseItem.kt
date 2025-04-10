package io.enderdev.emergingtechnology.items

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation

open class BaseItem(val name: String) : Item() {
	init {
		registryName = ResourceLocation(Tags.MOD_ID, name)
		translationKey = "${Tags.MOD_ID}.$name"
		creativeTab = EmergingTechnology.creativeTab
		ModItems.items.add(this)
	}
}
