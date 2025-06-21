package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.emergingtechnology.ConfigHandler
import io.enderdev.emergingtechnology.items.TooltipItemBlock
import io.enderdev.emergingtechnology.tiles.TileChemicalCombiner
import io.enderdev.catalyx.utils.extensions.translate
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.event.RegistryEvent


class ChemicalCombinerBlock(name: String, tileClass: Class<out TileEntity>, guiID: Int) : ModelMachineBlock(name, tileClass, guiID, AxisAlignedBB(.0, .0, .0, 1.0, .875, 1.0)) {
	override fun registerItem(event: RegistryEvent.Register<Item>) {
		event.registry.register(
			TooltipItemBlock(
				this,
				"tooltip.alchemistry.energy_requirement".translate(ConfigHandler.COMBINER.energyPerTick)
			)
				.setRegistryName(this.registryName)
		)
	}

	override fun onBlockPlacedBy(world: World, pos: BlockPos, state: IBlockState, placer: EntityLivingBase, stack: ItemStack) {
		super.onBlockPlacedBy(world, pos, state, placer, stack)
		val tile = world.getTileEntity(pos) as? TileChemicalCombiner
		tile?.owner = placer.name ?: ""
	}
}
