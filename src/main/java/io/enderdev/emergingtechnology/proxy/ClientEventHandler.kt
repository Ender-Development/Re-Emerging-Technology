package io.enderdev.emergingtechnology.proxy

import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
class ClientEventHandler {

	//@SubscribeEvent
	//fun fovEvent(e: FOVUpdateEvent) {
	//	e.entity.getCapability(CapabilityDrugInfo.DRUG_INFO, null)?.let { info ->
	//		if(info.psilocybinTicks > 500) {
	//			e.newfov = info.cumulativeFOVModifier // + e.fov
	//			info.cumulativeFOVModifier -= .002f
	//			--info.psilocybinTicks
	//		} else {
	//			info.cumulativeFOVModifier = 1.0f
	//		}
	//	}
	//}
	//
	//@SubscribeEvent
	//fun tooltipEvent(e: ItemTooltipEvent) {
	//	val stack = e.itemStack
	//	if(stack.item is ItemFood && stack.hasTagCompound()
	//		&& stack.tagCompound!!.hasKey("alchemistryPotion")
	//		&& !stack.tagCompound!!.getBoolean("alchemistrySalted")
	//	) {
	//		val molecule = ItemCompound.Companion.getDankMoleculeForMeta(stack.tagCompound!!.getInteger("alchemistryPotion"))
	//		if(molecule != null) {
	//			val compoundName = CompoundRegistry[molecule.meta]?.toItemStack(1)?.displayName
	//				?: "<Invalid Compound>"
	//			e.toolTip.add("spiked_food.tooltip".translate(compoundName))
	//		}
	//	}
	//}
}
