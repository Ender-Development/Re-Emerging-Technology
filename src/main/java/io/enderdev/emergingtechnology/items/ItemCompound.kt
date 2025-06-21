package io.enderdev.emergingtechnology.items

import io.enderdev.emergingtechnology.ConfigHandler
import io.enderdev.emergingtechnology.capability.CapabilityDrugInfo
import io.enderdev.emergingtechnology.chemistry.ChemicalCompound
import io.enderdev.emergingtechnology.chemistry.CompoundRegistry
import io.enderdev.catalyx.utils.extensions.toPotion
import io.enderdev.catalyx.utils.extensions.translate
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.EnumAction
import net.minecraft.item.ItemStack
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionEffect
import net.minecraft.util.*
import net.minecraft.world.World
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly


class ItemCompound(name: String) : ItemMetaBase(name) {

	override fun onItemUseFinish(stack: ItemStack, worldIn: World, entity: EntityLivingBase): ItemStack {
		if(entity is EntityPlayer) {
			val molecule = dankMolecules.firstOrNull { it.meta == stack.metadata }
			molecule?.let {
				it.activateForPlayer(entity)
				stack.shrink(1)
			}
		}
		return stack
	}

	override fun getMaxItemUseDuration(stack: ItemStack): Int {
		return if(metaHasDankMolecule(stack.metadata)) 36
		else super.getMaxItemUseDuration(stack)
	}

	override fun getItemUseAction(stack: ItemStack): EnumAction {
		return if(metaHasDankMolecule(stack.metadata)) EnumAction.DRINK
		else super.getItemUseAction(stack)
	}

	override fun onItemRightClick(worldIn: World, playerIn: EntityPlayer, handIn: EnumHand): ActionResult<ItemStack> {
		playerIn.activeHand = handIn
		val stack = playerIn.getHeldItem(handIn)
		return if(metaHasDankMolecule(stack.metadata))
			ActionResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn))
		else
			ActionResult(EnumActionResult.PASS, stack)
	}

	@SideOnly(Side.CLIENT)
	override fun registerModel() {
		CompoundRegistry.keys().forEach {
			ModelLoader.setCustomModelResourceLocation(
				this, it,
				ModelResourceLocation("$registryName", "inventory")
			)
		}
	}

	@SideOnly(Side.CLIENT)
	override fun addInformation(stack: ItemStack, playerIn: World?, tooltip: List<String>, advanced: ITooltipFlag) {
		val compound: ChemicalCompound? = CompoundRegistry[stack.itemDamage]
		compound?.let {
			(tooltip as MutableList).apply {
				add(compound.toAbbreviatedString())
				if(metaHasDankMolecule(compound.meta)) add("generic_potion_compound.tooltip".translate())
			}
		}
	}

	@SideOnly(Side.CLIENT)
	override fun getSubItems(tab: CreativeTabs, stacks: NonNullList<ItemStack>) {
		if(!isInCreativeTab(tab)) return
		CompoundRegistry.keys().forEach { stacks.add(ItemStack(this, 1, it)) }
	}

	override fun getItemStackDisplayName(stack: ItemStack): String {
		val compound = CompoundRegistry[stack.metadata]
		return if(stack.item == ModItems.compounds && compound != null && !(compound.isInternalCompound)) {
			"${getTranslationKey(stack)}.name".translate()
		} else super.getItemStackDisplayName(stack)
	}

	override fun getTranslationKey(stack: ItemStack): String {
		var i = stack.itemDamage
		if(!CompoundRegistry.keys().contains(i)) i = 0
		val key = "${super.getTranslationKey()}_${CompoundRegistry[i]!!.name}"
		return if(ConfigHandler.GENERAL.familyFriendlyMode
			&& (i == CompoundRegistry["cocaine"]!!.meta
					|| i == CompoundRegistry["psilocybin"]!!.meta
					|| i == CompoundRegistry["mescaline"]!!.meta)
		)
			"${key}_family"
		else
			key
	}

	companion object {
		val dankMolecules = ArrayList<DankMolecule>().apply {
			add(
				DankMolecule(
					CompoundRegistry["potassium_cyanide"]!!.meta, 500, 2,
					listOf(
						"wither".toPotion(), "poison".toPotion(), "nausea".toPotion(),
						"slowness".toPotion(), "hunger".toPotion()
					)
				)
				{ e ->
					e.foodStats.foodLevel = 0
					e.attackEntityFrom(DamageSource.STARVE, 12.0f)
				})

			add(
				DankMolecule(
					CompoundRegistry["psilocybin"]!!.meta, 600, 2,
					listOf("night_vision".toPotion(), "glowing".toPotion(), "slowness".toPotion())
				)
				{ e: EntityPlayer -> e.getCapability(CapabilityDrugInfo.DRUG_INFO, null)?.psilocybinTicks = 1100 })

			add(
				DankMolecule(CompoundRegistry["penicillin"]!!.meta, 0, 0, listOf())
				{ e -> e.clearActivePotions(); e.heal(2.0f) })

			add(
				DankMolecule(
					CompoundRegistry["epinephrine"]!!.meta, 400, 0,
					listOf("night_vision".toPotion(), "speed".toPotion(), "haste".toPotion())
				)
			)

			add(
				DankMolecule(
					CompoundRegistry["cocaine"]!!.meta, 400, 2,
					listOf("night_vision".toPotion(), "speed".toPotion(), "haste".toPotion(), "jump_boost".toPotion())
				)
			)

			add(
				DankMolecule(CompoundRegistry["acetylsalicylic_acid"]!!.meta, 0, 0, listOf())
				{ e -> e.heal(5.0f) })

			add(
				DankMolecule(
					CompoundRegistry["caffeine"]!!.meta, 400, 0,
					listOf("night_vision".toPotion(), "speed".toPotion(), "haste".toPotion())
				)
			)
		}

		fun metaHasDankMolecule(meta: Int) = dankMolecules.any { it.meta == meta }

		fun getDankMoleculeForMeta(meta: Int): DankMolecule? = dankMolecules.firstOrNull { it.meta == meta }
	}
}

data class DankMolecule(
	val meta: Int, val duration: Int, val amplifier: Int, val potionEffects: List<Potion>,
	val entityEffects: (EntityPlayer) -> Unit = {}
) {

	fun activateForPlayer(player: EntityPlayer) {
		for(effect in this.potionEffects) {
			player.addPotionEffect(PotionEffect(effect, duration, amplifier))
		}
		entityEffects.invoke(player)
	}
}


