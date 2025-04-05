package io.enderdev.emergingtechnology.proxy

import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

open class CommonProxy: IProxy {
    override fun preInit(event: FMLPreInitializationEvent) {
        // Common pre-initialization code
    }

    override fun init(event: FMLInitializationEvent) {
        // Common initialization code
    }

    override fun postInit(event: FMLPostInitializationEvent) {
        // Common post-initialization code
    }
}