package io.enderdev.emergingtechnology.blocks

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.blocks.machine.IHasModel
import io.enderdev.catalyx.blocks.BaseBlock
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

open class ModelBlock(val name: String, material: Material = Material.ROCK, soundType: SoundType = SoundType.STONE, hardness: Float = 3f) : BaseBlock(EmergingTechnology.catalyxSettings, name, material), IHasModel {
	init {
		this.soundType = soundType
		blockHardness = hardness
	}

	@SideOnly(Side.CLIENT)
	override fun registerModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, ModelResourceLocation(registryName!!, "inventory"))
	}
}
