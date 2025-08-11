package io.enderdev.emergingtechnology.fluids

import net.minecraft.init.SoundEvents.BLOCK_FIRE_EXTINGUISH
import net.minecraftforge.fluids.Fluid
import java.awt.Color

object ModFluids {
	val fluids = mutableListOf<BaseFluid>()

	val nutrient: Fluid = BaseFluid("nutrient", false).setColor(Color(7, 164, 255))
	val co2: Fluid = BaseFluid("co2", true).setDensity(-10).setGaseous(true).setViscosity(40).setEmptySound(BLOCK_FIRE_EXTINGUISH).setFillSound(BLOCK_FIRE_EXTINGUISH).setColor(Color(132, 119, 119))

	fun registerFluids() = fluids.forEach { it.registerFluid() }
}
