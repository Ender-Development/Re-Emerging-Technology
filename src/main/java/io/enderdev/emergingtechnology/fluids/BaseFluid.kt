package io.enderdev.emergingtechnology.fluids

import io.enderdev.emergingtechnology.Tags
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fluids.Fluid
import net.minecraftforge.fluids.FluidRegistry

class BaseFluid(name: String, oneTexture: Boolean) : Fluid(name, ResourceLocation(Tags.MODID, "blocks/fluids/$name${if(oneTexture) "" else "_still"}"), ResourceLocation(Tags.MODID, "blocks/fluids/$name${if(oneTexture) "" else "_flowing"}")) {
	init {
		ModFluids.fluids.add(this)
		unlocalizedName = "${Tags.MODID}:$name"
	}

	fun registerFluid() {
		if(FluidRegistry.registerFluid(this))
			FluidRegistry.addBucketForFluid(this)
	}
}
