package io.enderdev.emergingtechnology.items

import io.enderdev.emergingtechnology.Tags
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.fml.common.registry.ForgeRegistries
import org.ender_development.catalyx.utils.extensions.toStack
import org.ender_development.catalyx.utils.extensions.translate

class ItemEntityThing(name: String) : ItemBase(name) {
	companion object {
		const val ENTITY_NBT = "${Tags.MODID}:entity_id"

		fun getEntityId(stack: ItemStack) = stack.tagCompound?.getString(ENTITY_NBT) ?: "Invalid"

		fun getEntityName(stack: ItemStack): String {
			val entityId = getEntityId(stack)
			return if(entityId == "Invalid")
				entityId
			else
				ForgeRegistries.ENTITIES.getValue(ResourceLocation(entityId))?.name ?: entityId
		}
	}

	override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: List<String?>, flagIn: ITooltipFlag) {
		(tooltip as MutableList).add("item.${Tags.MODID}:$name.desc".translate(getEntityName(stack)))
	}

	override fun getItemStackDisplayName(stack: ItemStack) =
		"item.${Tags.MODID}:$name.name".translate(getEntityName(stack))

	fun getFor(entityId: String) =
		toStack().apply {
			tagCompound = tagCompound ?: NBTTagCompound()
			tagCompound!!.setString(ENTITY_NBT, entityId)
		}
}
