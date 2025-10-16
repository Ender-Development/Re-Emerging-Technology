package io.enderdev.emergingtechnology.blocks

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.client.renderer.block.statemap.StateMapperBase
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fluids.BlockFluidClassic
import net.minecraftforge.fluids.Fluid
import org.ender_development.catalyx.core.IBlockProvider
import org.ender_development.catalyx.utils.SideUtils

class BaseFluidBlock(fluid: Fluid, material: Material) : BlockFluidClassic(fluid, material), IBlockProvider {
	init {
		registryName = ResourceLocation(Tags.MODID, fluid.name)
		translationKey = "$registryName"
		creativeTab = EmergingTechnology.creativeTab
		EmergingTechnology.modSettings.blocks(this)
	}

	override val instance = this

	override var modDependencies = ""

	override val item: Item
		get() = Item.getItemFromBlock(this)

	override val isEnabled = true

	override fun register(event: RegistryEvent.Register<Block>) =
		event.registry.register(this)

	override fun registerItemBlock(event: RegistryEvent.Register<Item>) {
		if(!SideUtils.isClient)
			return

		val resourceLocation = ModelResourceLocation(registryName!!, "fluid")

		ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(this)) { resourceLocation }
		ModelLoader.setCustomStateMapper(this, object : StateMapperBase() {
			override fun getModelResourceLocation(state: IBlockState) =
				resourceLocation
		})
	}

	// no-op
	override fun requires(modDependencies: String) =
		this
}
