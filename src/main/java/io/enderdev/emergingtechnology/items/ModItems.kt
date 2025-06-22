package io.enderdev.emergingtechnology.items

import io.enderdev.catalyx.items.IItemProvider
import io.enderdev.catalyx.utils.extensions.translate
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.blocks.machine.IHasModel
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.config.hydroponics.HydroponicsModule
import io.enderdev.emergingtechnology.config.synthetics.SyntheticsModule
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

object ModItems {
	val items = ArrayList<IItemProvider>()

	val hydroponics: HydroponicsModule = EmergingTechnologyConfig.HYDROPONICS_MODULE
	val synthetics: SyntheticsModule = EmergingTechnologyConfig.SYNTHETICS_MODULE

	// TODO: we should keep "Press SHIFT" consistent across items, or, ideally, just remove it altogether, it's stupid

	// Hydroponics
	// roz: it's 02:13 and I'm gonna go insane with this stupid config being so long and annoying to work with
	val bulbRed = ItemBulb("red", hydroponics.GROWLIGHT.energyRedBulbModifier, hydroponics.GROWLIGHT.growthRedBulbModifier)
	val bulbGreen = ItemBulb("green", hydroponics.GROWLIGHT.energyGreenBulbModifier, hydroponics.GROWLIGHT.growthGreenBulbModifier)
	val bulbBlue = ItemBulb("blue", hydroponics.GROWLIGHT.energyBlueBulbModifier, hydroponics.GROWLIGHT.growthBlueBulbModifier)
	val bulbPurple = ItemBulb("purple", hydroponics.GROWLIGHT.energyPurpleBulbModifier, hydroponics.GROWLIGHT.growthPurpleBulbModifier)

	val nozzleComponent = object : ItemBase("nozzle_component") {
		override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: List<String?>, flagIn: ITooltipFlag) {
			(tooltip as MutableList).addAll(ItemUtils.extendedTooltip("item.${Tags.MODID}:nozzle_component.desc".translate()))
		}
	}
	val nozzleSmart = ItemNozzle("smart", hydroponics.DIFFUSER.SMART.rangeMultiplier, hydroponics.DIFFUSER.SMART.boostMultiplier)
	val nozzleLong = ItemNozzle("long", hydroponics.DIFFUSER.LONG.rangeMultiplier, hydroponics.DIFFUSER.LONG.boostMultiplier)
	val nozzlePrecise = ItemNozzle("precise", hydroponics.DIFFUSER.PRECISE.rangeMultiplier, hydroponics.DIFFUSER.PRECISE.boostMultiplier)

	val fertilizer = ItemBase("fertilizer")

	// Polymers
	val shreddedPlastic = ItemBase("shredded_plastic")
	val shreddedPlant = object : ItemBase("shredded_plant") {
		override fun getItemBurnTime(itemStack: ItemStack) = 800
	}
	val shreddedStarch = ItemBase("shredded_starch")
	val shreddedPaper = ItemBase("shredded_paper")
	
	val plasticWaste = ItemBase("plastic_waste")
	val paperWaste = ItemBase("paper_waste")
	val paperPulp = ItemBase("paper_pulp")
	
	val filament = ItemBase("filament")
	val plasticRod = ItemBase("plastic_rod")
	val plasticSheet = ItemBase("plastic_sheet")
	val plasticTissueScaffold = ItemBase("plastic_tissue_scaffold")
	val turbine = ItemBase("turbine")

	// Synthetics
	val syringeEmpty = ItemEmptySyringe()
	val syringeFull = ItemFullSyringe()
	// sample // TODO; if sample is just syringeFull but with a different texture, abstract away the itemid from ItemFullSyringe to a separate class and re-use it

	val synthetic_steak = ItemRawSyntheticMeat("steak", "minecraft:cow")
	val synthetic_chicken = ItemRawSyntheticMeat("chicken", "minecraft:chicken")
	val synthetic_porkchop = ItemRawSyntheticMeat("porkchop", "minecraft:pig")

	val synthetic_steak_cooked = ItemCookedSyntheticMeat("steak", "minecraft:cow", synthetics.beefHunger, synthetics.beefHungerSaturation.toFloat())
	val synthetic_chicken_cooked = ItemCookedSyntheticMeat("chicken", "minecraft:chicken", synthetics.chickenHunger, synthetics.chickenHungerSaturation.toFloat())
	val synthetic_porkchop_cooked = ItemCookedSyntheticMeat("porkchop", "minecraft:pig", synthetics.porkchopHunger, synthetics.porkchopHungerSaturation.toFloat())

	val syntheticLeather = ItemBase("synthetic_leather")
	val syntheticSlime = ItemBase("synthetic_slime")
	val syntheticSilk = ItemBase("synthetic_silk")

	val algae = ItemBase("algae")
	val algaeBar = ItemBase("algae_bar")
	val algaeBarCooked = BaseFoodItem("algae_bar_cooked", synthetics.algaeHunger, synthetics.algaeHungerSaturation.toFloat())
	
	// Electrics
	val biomass = object : ItemBase("biomass") {
		override fun getItemBurnTime(itemStack: ItemStack) = 1600
	}
	val biochar = ItemBase("biochar")

	val circuit = ItemBase("circuit")
	val circuitBasic = ItemCircuit("basic", 4)
	val circuitAdvanced = ItemCircuit("advanced", 8)
	val circuitSuperior = ItemCircuit("superior", 16)


	fun registerItems(event: RegistryEvent.Register<Item>) = items.forEach { it.registerItem(event) }

	@SideOnly(Side.CLIENT)
	fun registerModels() = items.forEach { if(it is IHasModel) it.registerModel() }

	@SideOnly(Side.CLIENT)
	fun initColors() = 1
		//Minecraft.getMinecraft().itemColors.registerItemColorHandler(ItemColorHandler(), compounds, ingots, elements)
}
