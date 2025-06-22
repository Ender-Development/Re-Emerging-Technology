package io.enderdev.emergingtechnology.items

import io.enderdev.catalyx.utils.extensions.translate
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.EntityList
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand
import net.minecraft.world.World

class ItemEmptySyringe : ItemBase("syringe_empty") {
	override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: List<String?>, flagIn: ITooltipFlag) {
		tooltip as MutableList
		tooltip.add("item.${Tags.MODID}:syringe_empty.desc".translate())
		tooltip.add("info.${Tags.MODID}.interaction.rmb".translate())
	}

	override fun itemInteractionForEntity(stack: ItemStack, player: EntityPlayer, target: EntityLivingBase, hand: EnumHand): Boolean {
		val entityId = EntityList.getKey(target)!!.toString()
		if(!EmergingTechnologyConfig.SYNTHETICS_MODULE.syringeWhitelist.contains(entityId))
			return false

		stack.shrink(1)
		player.addItemStackToInventory(ModItems.syringeFull.getFor(entityId))
		return true
	}
}
