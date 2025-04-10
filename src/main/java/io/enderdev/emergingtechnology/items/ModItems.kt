package io.enderdev.emergingtechnology.items

import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.utils.extensions.translate
import io.moonman.emergingtechnology.config.EmergingTechnologyConfig
import net.minecraft.item.Item

object ModItems {
	// Hydroponics Items
	val bulbRed = BulbItem("bulb_red", EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.growthRedBulbModifier, EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.energyRedBulbModifier)
	val bulbGreen = BulbItem("bulb_green", EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.growthGreenBulbModifier, EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.energyGreenBulbModifier)
	val bulbBlue = BulbItem("bulb_blue", EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.growthBlueBulbModifier, EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.energyBlueBulbModifier)
	val bulbPurple =
		BulbItem("bulb_purple", EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.growthPurpleBulbModifier, EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.energyPurpleBulbModifier)
	val nozzleComponent = NozzleComponentItem()
	val nozzleSmart = NozzleItem("nozzle_smart") {
		"info.${Tags.MOD_ID}.nozzlesmart.description".translate(
			EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.SMART.rangeMultiplier,
			EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.SMART.boostMultiplier
		)
	}
	val nozzleLong = NozzleItem("nozzle_long") { "info.${Tags.MOD_ID}.nozzlelong.description".translate(EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.LONG.rangeMultiplier) }
	val nozzlePrecise = NozzleItem("nozzle_precise") { "info.${Tags.MOD_ID}.nozzleprecise.description".translate(EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.PRECISE.boostMultiplier) }
	val fertilizer = BaseItem("fertilizer")

	// Polymers Items
	val shreddedPlastic = BaseItem("shredded_plastic")
	val shreddedPlant = BurnableItem("shredded_plant", 800)
	val shreddedStarch = BaseItem("shredded_starch")
	val shreddedPaper = BaseItem("shredded_paper")
	val plasticWaste = BaseItem("plastic_waste")
	val paperWaste = BaseItem("paper_waste")
	val paperPulp = BaseItem("paper_pulp")
	val filament = BaseItem("filament")
	val plasticRod = BaseItem("plastic_rod")
	val plasticSheet = BaseItem("plastic_sheet")
	val plasticTissueScaffold = BaseItem("plastic_tissue_scaffold")
	val turbine = BaseItem("turbine")

	// Synthetics Items
	//public static final Item emptysyringe = null;
	//public static final Item fullsyringe = null;
	//public static final Item sample = null;
	//public static final Item syntheticcowraw = null;
	//public static final Item syntheticchickenraw = null;
	//public static final Item syntheticpigraw = null;
	//public static final Item syntheticcowcooked = null;
	//public static final Item syntheticchickencooked = null;
	//public static final Item syntheticpigcooked = null;
	//public static final Item syntheticleather = null;
	//public static final Item syntheticslime = null;
	//public static final Item syntheticsilk = null;
	//public static final Item algae = null;
	var algaeBarRaw = BaseItem("algae_bar_raw")
	//public static final Item algaebarcooked = null;

	// Electrics Items
	val biomass = BurnableItem("biomass", 1600)
	val biochar = BaseItem("biochar")
	val circuit = BaseItem("circuit")
	val circuitBasic = CircuitItem("circuit_basic", 4)
	val circuitAdvanced = CircuitItem("circuit_advanced", 8)
	val circuitSuperior = CircuitItem("circuit_superior", 16)

	val items = mutableListOf<Item>()
}
