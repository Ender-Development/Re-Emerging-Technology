package io.enderdev.emergingtechnology.proxy

class CommonEventHandler {
	//@SubscribeEvent
	//fun rightClickEvent(e: PlayerInteractEvent.RightClickBlock) {
	//	val target = e.world.getBlockState(e.pos)
	//	if(e.itemStack.item == ModItems.obsidianBreaker && target.block == Blocks.OBSIDIAN) {
	//		e.itemStack.shrink(1)
	//		e.world.setBlockToAir(e.pos)
	//		e.entityPlayer.addItemStackToInventory(ItemStack(Blocks.OBSIDIAN, 1))
	//	}
	//}
	//
	//@SubscribeEvent
	//fun rightClickPainting(e: PlayerInteractEvent.EntityInteract) {
	//	if(e.target is EntityPainting) {
	//		val painting = e.target as EntityPainting
	//		if(painting.art.title == "PeriodicTable" && e.entityPlayer.world.isRemote) {
	//			Minecraft.getMinecraft().displayGuiScreen(GuiPeriodicTable())
	//		}
	//	}
	//}
	//
	//@SubscribeEvent
	//fun finishItemEvent(e: LivingEntityUseItemEvent.Finish) {
	//	if(e.item.hasTagCompound()) {
	//		val tag = e.item.tagCompound
	//		if(tag?.hasKey("alchemistryPotion") == true) {
	//			val moleculeMeta = tag.getInteger("alchemistryPotion")
	//			val molecule: DankMolecule? = ItemCompound.Companion.getDankMoleculeForMeta(moleculeMeta)
	//			if(molecule != null && e.entityLiving is EntityPlayer) molecule.activateForPlayer(e.entityLiving as EntityPlayer)
	//		}
	//	}
	//}
	//
	//@SubscribeEvent
	//fun onEntityConstructing(event: AttachCapabilitiesEvent<Entity>) {
	//	if(event.getObject() is EntityPlayer) {
	//		if(!event.getObject().hasCapability(CapabilityDrugInfo.DRUG_INFO, null)) {
	//			event.addCapability(ResourceLocation(Tags.MODID, "DrugInfo"), AlchemistryDrugDispatcher())
	//		}
	//	}
	//}
	//
	//@SubscribeEvent
	//fun registerFuel(event: FurnaceFuelBurnTimeEvent) {
	//	val hydrogen = 20
	//	val carbon = 200
	//
	//	fun getBurnTime(nCarbon: Int, nHydrogen: Int): Int {
	//		return (nCarbon * carbon) + (nHydrogen * hydrogen)
	//	}
	//
	//	if(event.itemStack.item == ModItems.elements) {
	//		event.burnTime = when(event.itemStack.itemDamage) {
	//			ElementRegistry["hydrogen"]?.meta -> hydrogen
	//			ElementRegistry["carbon"]?.meta -> carbon
	//			else -> 0
	//		}
	//	} else if(event.itemStack.item == ModItems.compounds) {
	//		event.burnTime = when(event.itemStack.itemDamage) {
	//			CompoundRegistry["methane"]?.meta -> getBurnTime(1, 4)
	//			CompoundRegistry["ethane"]?.meta -> getBurnTime(2, 6)
	//			CompoundRegistry["propane"]?.meta -> getBurnTime(3, 8)
	//			CompoundRegistry["butane"]?.meta -> getBurnTime(4, 10)
	//			CompoundRegistry["pentane"]?.meta -> getBurnTime(5, 12)
	//			CompoundRegistry["hexane"]?.meta -> getBurnTime(6, 14)
	//			else -> 0
	//		}
	//	}
	//}
}
