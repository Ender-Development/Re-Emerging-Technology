package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerSolarCooker
import io.enderdev.emergingtechnology.client.gui.GuiSolarCooker
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.tiles.TileSolarCooker
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.entity.Entity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.ender_development.catalyx.items.TooltipItemBlock
import org.ender_development.catalyx.utils.extensions.translate

class BlockSolarCooker() : RotatableMachineBlock("solar_cooker", TileSolarCooker::class.java,
	EmergingTechnology.guiHandler.registerId(TileSolarCooker::class.java, ContainerSolarCooker::class.java) { GuiSolarCooker::class.java }) {
	init {
		blockHardness = 1f
	}

	override val item =
		TooltipItemBlock(this) { stack, world, flag ->
			ItemUtils.extendedTooltip(
				"tile.${Tags.MODID}:solar_cooker.desc".translate(),
				"tile.${Tags.MODID}:solar_cooker.heat.heating".translate(EmergingTechnologyConfig.SYNTHETICS_MODULE.COOKER.cookerBaseHeatGain),
				"tile.${Tags.MODID}:solar_cooker.heat.dissipation".translate(EmergingTechnologyConfig.SYNTHETICS_MODULE.COOKER.cookerBaseHeatLoss)
			)
		}

	// funny
	override fun onEntityWalk(world: World, pos: BlockPos, entity: Entity) {
		val tile = world.getTileEntity(pos) as? TileSolarCooker ?: return
		if(tile.heat > 150) // celcius
			entity.setFire(tile.heat / 50)
	}
}
