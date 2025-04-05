package io.enderdev.emergingtechnology

import io.enderdev.emergingtechnology.proxy.IProxy
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.SidedProxy
import org.apache.logging.log4j.LogManager

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION, dependencies = EmergingTechnology.DEPENDENCIES)
object EmergingTechnology {
    const val DEPENDENCIES = "required-after:forgelin_continuous@[${Tags.KOTLIN_VERSION},);after:crafttweaker;after:groovyscript@[${Tags.GROOVYSCRIPT_VERSION},)"

    @Mod.Instance(Tags.MOD_ID)
    val instance: EmergingTechnology = this

    @JvmStatic
    val LOGGER = LogManager.getLogger(Tags.MOD_NAME)

    @SidedProxy(clientSide = "io.enderdev.emergingtechnology.proxy.ClientProxy", serverSide = "io.enderdev.emergingtechnology.proxy.CommonProxy")
    val proxy: IProxy? = null

    @Mod.EventHandler
    fun preInit(event: net.minecraftforge.fml.common.event.FMLPreInitializationEvent) = proxy?.preInit(event)

    @Mod.EventHandler
    fun init(event: net.minecraftforge.fml.common.event.FMLInitializationEvent) = proxy?.init(event)

    @Mod.EventHandler
    fun postInit(event: net.minecraftforge.fml.common.event.FMLPostInitializationEvent) = proxy?.postInit(event)
}