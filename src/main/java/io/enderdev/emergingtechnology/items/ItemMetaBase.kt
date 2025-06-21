package io.enderdev.emergingtechnology.items

abstract class ItemMetaBase(name: String) : ItemBase(name) {
	init {
		hasSubtypes = true
	}
}
