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
import org.ender_development.catalyx.IBothProvider

class BaseFluidBlock(fluid: Fluid, material: Material) : BlockFluidClassic(fluid, material), IBothProvider {
	init {
		registryName = ResourceLocation(Tags.MODID, fluid.name)
		translationKey = "$registryName"
		creativeTab = EmergingTechnology.creativeTab
		ModBlocks.blocks.add(this)
	}

	override fun registerBlock(event: RegistryEvent.Register<Block>) = event.registry.register(this)

	override fun registerItem(event: RegistryEvent.Register<Item>) {
		// don't register any actual item, Forge adds a bucket because of fluid registration and universal bucket

		val resourceLocation = ModelResourceLocation(registryName!!, "fluid")

		ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(this)) { resourceLocation }
		ModelLoader.setCustomStateMapper(this, object : StateMapperBase() {
			override fun getModelResourceLocation(state: IBlockState) =
				resourceLocation
		})
	}
}
