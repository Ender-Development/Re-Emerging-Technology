package io.enderdev.emergingtechnology.proxy

import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

class ClientProxy: CommonProxy(), IProxy {
    override fun preInit(event: FMLPreInitializationEvent) {
        super.preInit(event)
        // Client-specific pre-initialization code
    }

    override fun init(event: FMLInitializationEvent) {
        super.init(event)
        // Client-specific initialization code
    }

    override fun postInit(event: FMLPostInitializationEvent) {
        super.postInit(event)
        // Client-specific post-initialization code
    }
}