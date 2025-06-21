package io.enderdev.emergingtechnology.client.gui

import io.enderdev.emergingtechnology.client.container.*
import io.enderdev.emergingtechnology.tiles.*
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler

class GuiHandler : IGuiHandler {

	companion object {
		const val NO_GUI = -1
		const val ELECTROLYZER_ID = 0
		const val CHEMICAL_DISSOLVER_ID = 1
		const val CHEMICAL_COMBINER_ID = 2
		const val EVAPORATOR_ID = 3
		const val ATOMIZER_ID = 4
		const val LIQUIFIER_ID = 5
		const val FISSION_CONTROLLER_ID = 6
		const val FUSION_CONTROLLER_ID = 7
	}

	override fun getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {
		val entity = world.getTileEntity(BlockPos(x, y, z))
		when(ID) {
			ELECTROLYZER_ID -> {
				if(entity is TileElectrolyzer) return ContainerElectrolyzer(player.inventory, entity)
			}

			CHEMICAL_DISSOLVER_ID -> {
				if(entity is TileChemicalDissolver) return ContainerChemicalDissolver(player.inventory, entity)
			}

			CHEMICAL_COMBINER_ID -> {
				if(entity is TileChemicalCombiner) return ContainerChemicalCombiner(player.inventory, entity)
			}

			EVAPORATOR_ID -> {
				if(entity is TileEvaporator) return ContainerEvaporator(player.inventory, entity)
			}

			ATOMIZER_ID -> {
				if(entity is TileAtomizer) return ContainerAtomizer(player.inventory, entity)
			}

			LIQUIFIER_ID -> {
				if(entity is TileLiquifier) return ContainerLiquifier(player.inventory, entity)
			}

			FISSION_CONTROLLER_ID -> {
				if(entity is TileFissionController) return ContainerFissionController(player.inventory, entity)
			}

			FUSION_CONTROLLER_ID -> {
				if(entity is TileFusionController) return ContainerFusionController(player.inventory, entity)
			}
		}
		return null
	}

	override fun getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {
		val entity = world.getTileEntity(BlockPos(x, y, z))
		when(ID) {
			ELECTROLYZER_ID -> {
				if(entity is TileElectrolyzer) return GuiElectrolyzer(player.inventory, entity)
			}

			CHEMICAL_DISSOLVER_ID -> {
				if(entity is TileChemicalDissolver) return GuiChemicalDissolver(player.inventory, entity)
			}

			CHEMICAL_COMBINER_ID -> {
				if(entity is TileChemicalCombiner) return GuiChemicalCombiner(player.inventory, entity)
			}

			EVAPORATOR_ID -> {
				if(entity is TileEvaporator) return GuiEvaporator(player.inventory, entity)
			}

			ATOMIZER_ID -> {
				if(entity is TileAtomizer) return GuiAtomizer(player.inventory, entity)
			}

			LIQUIFIER_ID -> {
				if(entity is TileLiquifier) return GuiLiquifier(player.inventory, entity)
			}

			FISSION_CONTROLLER_ID -> {
				if(entity is TileFissionController) return GuiFissionController(player.inventory, entity)
			}

			FUSION_CONTROLLER_ID -> {
				if(entity is TileFusionController) return GuiFusionController(player.inventory, entity)
			}
		}
		return null
	}
}
