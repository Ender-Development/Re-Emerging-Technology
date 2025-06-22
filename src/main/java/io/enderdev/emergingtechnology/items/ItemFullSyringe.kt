package io.enderdev.emergingtechnology.items

import io.enderdev.catalyx.utils.extensions.toStack
import io.enderdev.catalyx.utils.extensions.translate
import io.enderdev.emergingtechnology.Tags
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.fml.common.registry.ForgeRegistries

/** This is a completely different system than used by EMT */
class ItemFullSyringe : ItemBase("syringe_full") {
	companion object {
		const val syringeNBT = "${Tags.MODID}:syringe"
	}

	fun getEntityName(stack: ItemStack): String {
		val entityId = stack.tagCompound?.getString(syringeNBT) ?: return "Invalid"
		return ForgeRegistries.ENTITIES.getValue(ResourceLocation(entityId))?.name ?: entityId
	}

	override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: List<String?>, flagIn: ITooltipFlag) {
		(tooltip as MutableList).add("item.${Tags.MODID}:syringe_full.desc".translate(getEntityName(stack)))
	}

	override fun getItemStackDisplayName(stack: ItemStack) =
		"item.${Tags.MODID}:syringe_full.name".translate(getEntityName(stack))

	fun getFor(entityId: String) =
		toStack().apply {
			if(tagCompound == null)
				tagCompound = NBTTagCompound()
			tagCompound!!.setString(syringeNBT, entityId)
		}
}
