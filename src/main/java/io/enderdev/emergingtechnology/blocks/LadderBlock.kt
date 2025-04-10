package io.enderdev.emergingtechnology.blocks

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import net.minecraft.block.BlockLadder
import net.minecraft.block.SoundType
import net.minecraft.util.ResourceLocation

class LadderBlock() : BlockLadder() {
	init {
		registryName = ResourceLocation(Tags.MOD_ID, "ladder")
		translationKey = "${Tags.MOD_ID}.ladder"
		creativeTab = EmergingTechnology.creativeTab
		soundType = SoundType.STONE
	}
}
