package io.enderdev.emergingtechnology.items

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.config.hydroponics.HydroponicsModule
import io.enderdev.emergingtechnology.config.synthetics.SyntheticsModule
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraftforge.event.RegistryEvent
import org.ender_development.catalyx.items.BaseItem
import org.ender_development.catalyx.items.IItemProvider
import org.ender_development.catalyx.utils.extensions.translate
import java.awt.Color

object ModItems {
	val items = ArrayList<IItemProvider>()

	val hydroponics: HydroponicsModule = EmergingTechnologyConfig.HYDROPONICS_MODULE
	val synthetics: SyntheticsModule = EmergingTechnologyConfig.SYNTHETICS_MODULE

	// TODO we should keep "Press SHIFT" consistent across items, or, ideally, just remove it altogether, it's stupid

	// Hydroponics
	val bulbRed = ItemBulb("red", hydroponics.GROWLIGHT.energyRedBulbModifier, hydroponics.GROWLIGHT.growthRedBulbModifier, Color.red.brighter().rgb)
	val bulbGreen = ItemBulb("green", hydroponics.GROWLIGHT.energyGreenBulbModifier, hydroponics.GROWLIGHT.growthGreenBulbModifier, Color.green.darker().rgb)
	val bulbBlue = ItemBulb("blue", hydroponics.GROWLIGHT.energyBlueBulbModifier, hydroponics.GROWLIGHT.growthBlueBulbModifier, Color(64, 64, 255).rgb)
	val bulbPurple = ItemBulb("purple", hydroponics.GROWLIGHT.energyPurpleBulbModifier, hydroponics.GROWLIGHT.growthPurpleBulbModifier, Color.pink.darker().rgb)

	val nozzleComponent = object : BaseItem(EmergingTechnology.catalyxSettings, "nozzle_component") {
		override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: List<String?>, flagIn: ITooltipFlag) {
			(tooltip as MutableList).addAll(ItemUtils.extendedTooltip("item.${Tags.MODID}:nozzle_component.desc".translate()))
		}
	}
	val nozzleSmart = ItemNozzle("smart", hydroponics.DIFFUSER.SMART.rangeMultiplier, hydroponics.DIFFUSER.SMART.boostMultiplier)
	val nozzleLong = ItemNozzle("long", hydroponics.DIFFUSER.LONG.rangeMultiplier, hydroponics.DIFFUSER.LONG.boostMultiplier)
	val nozzlePrecise = ItemNozzle("precise", hydroponics.DIFFUSER.PRECISE.rangeMultiplier, hydroponics.DIFFUSER.PRECISE.boostMultiplier)

	val fertilizer = BaseItem(EmergingTechnology.catalyxSettings, "fertilizer")

	// Polymers
	val shreddedPlastic = BaseItem(EmergingTechnology.catalyxSettings, "shredded_plastic")
	val shreddedPlant = object : BaseItem(EmergingTechnology.catalyxSettings, "shredded_plant") {
		override fun getItemBurnTime(itemStack: ItemStack) = 800
	}
	val shreddedStarch = BaseItem(EmergingTechnology.catalyxSettings, "shredded_starch")
	val shreddedPaper = BaseItem(EmergingTechnology.catalyxSettings, "shredded_paper")
	
	val plasticWaste = BaseItem(EmergingTechnology.catalyxSettings, "plastic_waste")
	val paperWaste = BaseItem(EmergingTechnology.catalyxSettings, "paper_waste")
	val paperPulp = BaseItem(EmergingTechnology.catalyxSettings, "paper_pulp")
	
	val filament = BaseItem(EmergingTechnology.catalyxSettings, "filament")
	val plasticRod = BaseItem(EmergingTechnology.catalyxSettings, "plastic_rod")
	val plasticSheet = BaseItem(EmergingTechnology.catalyxSettings, "plastic_sheet")
	val plasticTissueScaffold = BaseItem(EmergingTechnology.catalyxSettings, "plastic_tissue_scaffold")
	val turbine = BaseItem(EmergingTechnology.catalyxSettings, "turbine")

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

	val syntheticLeather = BaseItem(EmergingTechnology.catalyxSettings, "synthetic_leather")
	val syntheticSlime = BaseItem(EmergingTechnology.catalyxSettings, "synthetic_slime")
	val syntheticSilk = BaseItem(EmergingTechnology.catalyxSettings, "synthetic_silk")

	val algae = BaseItem(EmergingTechnology.catalyxSettings, "algae")
	val algaeBar = BaseItem(EmergingTechnology.catalyxSettings, "algae_bar")
	val algaeBarCooked = BaseFoodItem("algae_bar_cooked", synthetics.algaeHunger, synthetics.algaeHungerSaturation.toFloat())
	
	// Electrics
	val biomass = object : BaseItem(EmergingTechnology.catalyxSettings, "biomass") {
		override fun getItemBurnTime(itemStack: ItemStack) = 1600
	}
	val biochar = BaseItem(EmergingTechnology.catalyxSettings, "biochar")

	val circuit = BaseItem(EmergingTechnology.catalyxSettings, "circuit")
	val circuitBasic = ItemCircuit("basic", 4)
	val circuitAdvanced = ItemCircuit("advanced", 8)
	val circuitSuperior = ItemCircuit("superior", 16)


	fun registerItems(event: RegistryEvent.Register<Item>) = items.forEach { it.registerItem(event) }
}
