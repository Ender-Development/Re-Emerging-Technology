package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.catalyx.utils.extensions.translate
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.blocks.ModelBlock
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.items.TooltipItemBlock
import io.enderdev.emergingtechnology.tiles.TilePiezoelectricGenerator
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.registry.GameRegistry

class BlockPiezoelectricGenerator() : ModelBlock("piezoelectric_generator"), ITileEntityProvider {
	init {
		GameRegistry.registerTileEntity(TilePiezoelectricGenerator::class.java, ResourceLocation(Tags.MODID, name))
	}

	override fun registerItem(event: RegistryEvent.Register<Item>) {
		event.registry.register(TooltipItemBlock(this) {
			ItemUtils.extendedTooltip("tile.${Tags.MODID}:piezoelectric_generator.desc".translate(
				EmergingTechnologyConfig.ELECTRICS_MODULE.PIEZOELECTRIC.piezoelectricEnergyGenerated,
				EmergingTechnologyConfig.ELECTRICS_MODULE.PIEZOELECTRIC.piezoelectricStepCooldown / 20.0,
				EmergingTechnologyConfig.ELECTRICS_MODULE.PIEZOELECTRIC.piezoelectricEnergyGenerated / EmergingTechnologyConfig.ELECTRICS_MODULE.PIEZOELECTRIC.piezoelectricStepCooldown.toFloat()
			))
		})
	}

	override fun hasTileEntity(state: IBlockState) = true

	override fun createNewTileEntity(worldIn: World, meta: Int) = TilePiezoelectricGenerator()

	override fun onEntityWalk(world: World, pos: BlockPos, entity: Entity) {
		if(!world.isRemote && entity is EntityLivingBase) {
			val tile = world.getTileEntity(pos)
			if(tile is TilePiezoelectricGenerator) // sanity check
				tile.steppedOn()
		}
	}
}
