package io.enderdev.emergingtechnology.items

import io.enderdev.catalyx.items.IItemProvider
import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.blocks.machine.IHasModel
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraft.item.ItemFood
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.event.RegistryEvent

open class BaseFoodItem(val name: String, hunger: Int, saturation: Float) : ItemFood(hunger, saturation, false), IItemProvider, IHasModel {
	init {
		registryName = ResourceLocation(Tags.MODID, name)
		translationKey = "$registryName"
		creativeTab = EmergingTechnology.creativeTab
		ModItems.items.add(this)
	}

	override fun registerItem(event: RegistryEvent.Register<Item>) {
		event.registry.register(this)
	}

	override fun registerModel() {
		ModelLoader.setCustomModelResourceLocation(this, 0, ModelResourceLocation(registryName!!, "inventory"))
	}
}
