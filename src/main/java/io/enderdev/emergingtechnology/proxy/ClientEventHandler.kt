package io.enderdev.emergingtechnology.proxy

import io.enderdev.emergingtechnology.capability.CapabilityDrugInfo
import io.enderdev.emergingtechnology.chemistry.CompoundRegistry
import io.enderdev.emergingtechnology.items.ItemCompound
import io.enderdev.catalyx.utils.extensions.translate
import net.minecraft.item.ItemFood
import net.minecraftforge.client.event.FOVUpdateEvent
import net.minecraftforge.event.entity.player.ItemTooltipEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
class ClientEventHandler {

	@SubscribeEvent
	fun fovEvent(e: FOVUpdateEvent) {
		e.entity.getCapability(CapabilityDrugInfo.DRUG_INFO, null)?.let { info ->
			if(info.psilocybinTicks > 500) {
				e.newfov = info.cumulativeFOVModifier // + e.fov
				info.cumulativeFOVModifier -= .002f
				--info.psilocybinTicks
			} else {
				info.cumulativeFOVModifier = 1.0f
			}
		}
	}

	@SubscribeEvent
	fun tooltipEvent(e: ItemTooltipEvent) {
		val stack = e.itemStack
		if(stack.item is ItemFood && stack.hasTagCompound()
			&& stack.tagCompound!!.hasKey("alchemistryPotion")
			&& !stack.tagCompound!!.getBoolean("alchemistrySalted")
		) {
			val molecule = ItemCompound.Companion.getDankMoleculeForMeta(stack.tagCompound!!.getInteger("alchemistryPotion"))
			if(molecule != null) {
				val compoundName = CompoundRegistry[molecule.meta]?.toItemStack(1)?.displayName
					?: "<Invalid Compound>"
				e.toolTip.add("spiked_food.tooltip".translate(compoundName))
			}
		}
	}
}
