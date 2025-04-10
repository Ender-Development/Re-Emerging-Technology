package io.enderdev.emergingtechnology.blocks.machines

import io.enderdev.emergingtechnology.gui.GuiSomething
import io.enderdev.emergingtechnology.gui.ResourceType
import io.moonman.emergingtechnology.config.EmergingTechnologyConfig
import io.moonman.emergingtechnology.machines.algaebioreactor.AlgaeBioreactorTileEntity
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class AlgaeBioreactorBlock : InventoryMachineBlock("algae_bioreactor", { AlgaeBioreactorTileEntity() }, GuiSomething.ALGAEBIOREACTOR_GUI_ID) {
	@SideOnly(Side.CLIENT)
	override fun addInformation(stack: ItemStack, world: World?, tooltip: List<String?>, flag: ITooltipFlag) {
		(tooltip as MutableList<String?>).addAll(
			getTooltip(
				listOf(
					EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.bioreactorEnergyUsage to ResourceType.ENERGY,
					EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.bioreactorWaterUsage to ResourceType.WATER,
					EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.bioreactorGasUsage to ResourceType.GAS
				)
			)
		)
	}
}
