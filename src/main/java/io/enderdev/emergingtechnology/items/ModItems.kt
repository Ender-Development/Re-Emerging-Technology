package io.enderdev.emergingtechnology.items

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.config.hydroponics.HydroponicsModule
import io.enderdev.emergingtechnology.config.synthetics.SyntheticsModule
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import org.ender_development.catalyx.items.BaseItem
import org.ender_development.catalyx.utils.extensions.translate
import java.awt.Color

object ModItems {
	val hydroponics: HydroponicsModule = EmergingTechnologyConfig.HYDROPONICS_MODULE
	val synthetics: SyntheticsModule = EmergingTechnologyConfig.SYNTHETICS_MODULE

	// TODO we should keep "Press SHIFT" consistent across items, or, ideally, just remove it altogether, it's stupid

	// Hydroponics
	val bulbRed = ItemBulb("red", hydroponics.GROWLIGHT.energyRedBulbModifier, hydroponics.GROWLIGHT.growthRedBulbModifier, Color.red.brighter().rgb)
	val bulbGreen = ItemBulb("green", hydroponics.GROWLIGHT.energyGreenBulbModifier, hydroponics.GROWLIGHT.growthGreenBulbModifier, Color.green.darker().rgb)
	val bulbBlue = ItemBulb("blue", hydroponics.GROWLIGHT.energyBlueBulbModifier, hydroponics.GROWLIGHT.growthBlueBulbModifier, Color(64, 64, 255).rgb)
	val bulbPurple = ItemBulb("purple", hydroponics.GROWLIGHT.energyPurpleBulbModifier, hydroponics.GROWLIGHT.growthPurpleBulbModifier, Color.pink.darker().rgb)

	val nozzleComponent = object : BaseItem(EmergingTechnology.modSettings, "nozzle_component") {
		override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: List<String?>, flagIn: ITooltipFlag) {
			(tooltip as MutableList).addAll(ItemUtils.extendedTooltip("item.${Tags.MODID}:nozzle_component.desc".translate()))
		}
	}
	val nozzleSmart = ItemNozzle("smart", hydroponics.DIFFUSER.SMART.rangeMultiplier, hydroponics.DIFFUSER.SMART.boostMultiplier)
	val nozzleLong = ItemNozzle("long", hydroponics.DIFFUSER.LONG.rangeMultiplier, hydroponics.DIFFUSER.LONG.boostMultiplier)
	val nozzlePrecise = ItemNozzle("precise", hydroponics.DIFFUSER.PRECISE.rangeMultiplier, hydroponics.DIFFUSER.PRECISE.boostMultiplier)

	val fertilizer = BaseItem(EmergingTechnology.modSettings, "fertilizer")

	// Polymers
	val shreddedPlastic = BaseItem(EmergingTechnology.modSettings, "shredded_plastic")
	val shreddedPlant = object : BaseItem(EmergingTechnology.modSettings, "shredded_plant") {
		override fun getItemBurnTime(itemStack: ItemStack) = 800
	}
	val shreddedStarch = BaseItem(EmergingTechnology.modSettings, "shredded_starch")
	val shreddedPaper = BaseItem(EmergingTechnology.modSettings, "shredded_paper")
	
	val plasticWaste = BaseItem(EmergingTechnology.modSettings, "plastic_waste")
	val paperWaste = BaseItem(EmergingTechnology.modSettings, "paper_waste")
	val paperPulp = BaseItem(EmergingTechnology.modSettings, "paper_pulp")
	
	val filament = BaseItem(EmergingTechnology.modSettings, "filament")
	val plasticRod = BaseItem(EmergingTechnology.modSettings, "plastic_rod")
	val plasticSheet = BaseItem(EmergingTechnology.modSettings, "plastic_sheet")
	val plasticTissueScaffold = BaseItem(EmergingTechnology.modSettings, "plastic_tissue_scaffold")
	val turbine = BaseItem(EmergingTechnology.modSettings, "turbine")

	// Synthetics
	val syringeEmpty = ItemEmptySyringe()
	val syringeFull = ItemEntityThing("syringe_full")
	val sample = ItemEntityThing("sample")

	val syntheticSteak = ItemRawSyntheticMeat("steak", "minecraft:cow")
	val syntheticChicken = ItemRawSyntheticMeat("chicken", "minecraft:chicken")
	val syntheticPorkchop = ItemRawSyntheticMeat("porkchop", "minecraft:pig")

	val syntheticSteakCooked = ItemCookedSyntheticMeat("steak", "minecraft:cow", synthetics.beefHunger, synthetics.beefHungerSaturation.toFloat())
	val syntheticChickenCooked = ItemCookedSyntheticMeat("chicken", "minecraft:chicken", synthetics.chickenHunger, synthetics.chickenHungerSaturation.toFloat())
	val syntheticPorkchopCooked = ItemCookedSyntheticMeat("porkchop", "minecraft:pig", synthetics.porkchopHunger, synthetics.porkchopHungerSaturation.toFloat())

	val syntheticLeather = BaseItem(EmergingTechnology.modSettings, "synthetic_leather")
	val syntheticSlime = BaseItem(EmergingTechnology.modSettings, "synthetic_slime")
	val syntheticSilk = BaseItem(EmergingTechnology.modSettings, "synthetic_silk")

	val algae = BaseItem(EmergingTechnology.modSettings, "algae")
	val algaeBar = BaseItem(EmergingTechnology.modSettings, "algae_bar")
	val algaeBarCooked = BaseFoodItem("algae_bar_cooked", synthetics.algaeHunger, synthetics.algaeHungerSaturation.toFloat())
	
	// Electrics
	val biomass = object : BaseItem(EmergingTechnology.modSettings, "biomass") {
		override fun getItemBurnTime(itemStack: ItemStack) = 1600
	}
	val biochar = BaseItem(EmergingTechnology.modSettings, "biochar")

	val circuit = BaseItem(EmergingTechnology.modSettings, "circuit")
	val circuitBasic = ItemCircuit("basic", 4)
	val circuitAdvanced = ItemCircuit("advanced", 8)
	val circuitSuperior = ItemCircuit("superior", 16)
}
