package io.enderdev.emergingtechnology.blocks

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import net.minecraft.block.Block
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.item.ItemBlock
import net.minecraft.util.ResourceLocation

open class BaseBlock(name: String, material: Material = Material.ROCK, hardness: Float = 2f, sound: SoundType = SoundType.STONE) : Block(material) {
	init {
		blockHardness = hardness
		registryName = ResourceLocation(Tags.MOD_ID, name)
		translationKey = "${Tags.MOD_ID}.$name"
		creativeTab = EmergingTechnology.creativeTab
		soundType = sound
		ModBlocks.blocks.add(this)
	}

	open fun getItemBlock() = ItemBlock(this)
}
