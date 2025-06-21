package io.enderdev.emergingtechnology.blocks.machine

//class ChemicalCombinerBlock(name: String, tileClass: Class<out TileEntity>, guiID: Int) : ModelMachineBlock(name, tileClass, guiID, AxisAlignedBB(.0, .0, .0, 1.0, .875, 1.0)) {
//	override fun registerItem(event: RegistryEvent.Register<Item>) {
//		event.registry.register(
//			TooltipItemBlock(
//				this,
//				"tooltip.alchemistry.energy_requirement".translate(ConfigHandler.COMBINER.energyPerTick)
//			)
//				.setRegistryName(this.registryName)
//		)
//	}
//
//	override fun onBlockPlacedBy(world: World, pos: BlockPos, state: IBlockState, placer: EntityLivingBase, stack: ItemStack) {
//		super.onBlockPlacedBy(world, pos, state, placer, stack)
//		val tile = world.getTileEntity(pos) as? TileChemicalCombiner
//		tile?.owner = placer.name ?: ""
//	}
//}
