package io.enderdev.emergingtechnology

import io.enderdev.emergingtechnology.blocks.ModBlocks
import io.enderdev.emergingtechnology.proxy.CommonProxy
import net.minecraft.creativetab.CreativeTabs
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import org.apache.logging.log4j.Logger
import org.ender_development.catalyx.client.gui.CatalyxGuiHandler
import org.ender_development.catalyx.core.CatalyxSettings
import org.ender_development.catalyx.core.ICatalyxMod
import org.ender_development.catalyx.utils.extensions.toStack

@Mod(
	modid = Tags.MODID,
	name = Tags.MOD_NAME,
	version = Tags.VERSION,
	dependencies = ICatalyxMod.CATALYX_ADDON,
	modLanguageAdapter = ICatalyxMod.MOD_LANGUAGE_ADAPTER
)
object EmergingTechnology : ICatalyxMod {
	val creativeTab = object : CreativeTabs(Tags.MODID) {
		override fun createIcon() = ModBlocks.algaeBioreactor.toStack()
	}

 	override val modSettings = CatalyxSettings(Tags.MODID, creativeTab, EmergingTechnology, true)
	val guiHandler = CatalyxGuiHandler()

	//https://github.com/jaredlll08/ModTweaker/blob/1.12/src/main/java/com/blamejared/ModTweaker.java
	//val LATE_REMOVALS: LinkedList<IAction> = LinkedList()
	//val LATE_ADDITIONS: LinkedList<IAction> = LinkedList()

	lateinit var logger: Logger

	@SidedProxy(clientSide = "io.enderdev.emergingtechnology.proxy.ClientProxy", serverSide = "io.enderdev.emergingtechnology.proxy.CommonProxy")
	var proxy: CommonProxy? = null

	@EventHandler
	fun preInit(e: FMLPreInitializationEvent) = proxy!!.preInit(e)

	@EventHandler
	fun init(e: FMLInitializationEvent) = proxy!!.init(e)

	@EventHandler
	fun postInit(e: FMLPostInitializationEvent) = proxy!!.postInit(e)

	init {
		FluidRegistry.enableUniversalBucket()
	}

	//@EventHandler
	//fun loadComplete(e: FMLLoadCompleteEvent) {
	//	try {
	//		LATE_REMOVALS.forEach(CraftTweakerAPI::apply)
	//		LATE_ADDITIONS.forEach(CraftTweakerAPI::apply)
	//	} catch(e: Exception) {
	//		e.printStackTrace()
	//		CraftTweakerAPI.logError("Error while applying actions", e)
	//	}
	//	LATE_REMOVALS.clear()
	//	LATE_ADDITIONS.clear()
	//}
}
