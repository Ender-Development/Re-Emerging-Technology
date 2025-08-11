package io.enderdev.emergingtechnology.proxy

import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.blocks.ModBlocks
import io.enderdev.emergingtechnology.tiles.TileCo2Scrubber
import io.enderdev.emergingtechnology.tiles.TileTidalGenerator
import io.enderdev.emergingtechnology.tiles.TileWindGenerator
import net.minecraftforge.client.model.animation.AnimationTESR
import net.minecraftforge.client.model.obj.OBJLoader
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
class ClientProxy : CommonProxy() {

	override fun preInit(e: FMLPreInitializationEvent) {
		super.preInit(e)
		OBJLoader.INSTANCE.addDomain(Tags.MODID)
	}

	override fun postInit(e: FMLPostInitializationEvent) {
		super.postInit(e)
		ModBlocks.initColours()
		MinecraftForge.EVENT_BUS.register(ClientEventHandler())

		ClientRegistry.bindTileEntitySpecialRenderer(TileWindGenerator::class.java, AnimationTESR())
		ClientRegistry.bindTileEntitySpecialRenderer(TileTidalGenerator::class.java, AnimationTESR())
		ClientRegistry.bindTileEntitySpecialRenderer(TileCo2Scrubber::class.java, AnimationTESR())
	}
}
