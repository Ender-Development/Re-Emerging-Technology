package io.enderdev.emergingtechnology.items

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraft.item.ItemFood
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.event.RegistryEvent
import org.ender_development.catalyx.core.IItemProvider
import org.ender_development.catalyx.utils.SideUtils

open class BaseFoodItem(val name: String, hunger: Int, saturation: Float) : ItemFood(hunger, saturation, false), IItemProvider {
	init {
		registryName = ResourceLocation(Tags.MODID, name)
		translationKey = "$registryName"
		creativeTab = EmergingTechnology.creativeTab
		EmergingTechnology.modSettings.items(this)
	}

	override val instance = this

	override val isEnabled = true

	override var modDependencies = ""

	// no-op
	override fun requires(modDependencies: String) =
		this

	override fun register(event: RegistryEvent.Register<Item>) {
		event.registry.register(this)
		if(SideUtils.isClient)
			ModelLoader.setCustomModelResourceLocation(this, 0, ModelResourceLocation(registryName!!, "inventory"))
	}
}
