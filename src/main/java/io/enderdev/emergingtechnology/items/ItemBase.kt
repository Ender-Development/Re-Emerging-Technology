package io.enderdev.emergingtechnology.items

import io.enderdev.catalyx.items.BaseItem
import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.blocks.machine.IHasModel
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

open class ItemBase(val name: String) : BaseItem(EmergingTechnology.catalyxSettings, name), IHasModel {
	@SideOnly(Side.CLIENT)
	override fun registerModel() {
		ModelLoader.setCustomModelResourceLocation(this, 0, ModelResourceLocation(registryName!!, "inventory"))
	}
}
