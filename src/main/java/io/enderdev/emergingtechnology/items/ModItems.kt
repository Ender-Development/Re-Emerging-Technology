package io.enderdev.emergingtechnology.items

import io.enderdev.catalyx.items.BaseItem
import io.enderdev.catalyx.utils.extensions.translate
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.blocks.machine.IHasModel
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

object ModItems {
	val items = ArrayList<BaseItem>()

	// TODO: we should keep "Press SHIFT" consistent across items, or, ideally, just remove it altogether, it's stupid

	// Hydroponics
	// roz: it's 02:13 and I'm gonna go insane with this stupid config being so long and annoying to work with
	val bulbRed = ItemBulb("red", EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.energyRedBulbModifier, EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.growthRedBulbModifier)
	val bulbGreen = ItemBulb("green", EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.energyGreenBulbModifier, EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.growthGreenBulbModifier)
	val bulbBlue = ItemBulb("blue", EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.energyBlueBulbModifier, EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.growthBlueBulbModifier)
	val bulbPurple = ItemBulb("purple", EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.energyPurpleBulbModifier, EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.growthPurpleBulbModifier)

	val nozzleComponent = object : ItemBase("nozzle_component") {
		override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: List<String?>, flagIn: ITooltipFlag) {
			(tooltip as MutableList).addAll(ItemUtils.extendedTooltip("item.${Tags.MODID}:nozzle_component.desc".translate()))
		}
	}
	val nozzleSmart = ItemNozzle("smart", EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.SMART.rangeMultiplier, EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.SMART.boostMultiplier)
	val nozzleLong = ItemNozzle("long", EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.LONG.rangeMultiplier, EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.LONG.boostMultiplier)
	val nozzlePrecise = ItemNozzle("precise", EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.PRECISE.rangeMultiplier, EmergingTechnologyConfig.HYDROPONICS_MODULE.DIFFUSER.PRECISE.boostMultiplier)

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
	// syringe_empty // weird!
	// syringe_full // weird!
	// sample // weird!

	// synthetic_cow_raw // rename to steak?
	// synthetic_chicken_raw
	// synthetic_pig_raw // rename to porkchop?

	// synthetic_cow_cooked // rename to steak?
	// synthetic_chicken_cooked
	// synthetic_pig_cooked // rename to porkchop?

	// synthetic_leather
	// synthetic_slime
	// synthetic_silk

	// algae
	// algae_bar_raw
	// algae_bar_cooked
	
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
