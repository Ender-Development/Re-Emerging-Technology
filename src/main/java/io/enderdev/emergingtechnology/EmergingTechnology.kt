package io.enderdev.emergingtechnology

import io.enderdev.catalyx.CatalyxSettings
import io.enderdev.catalyx.blocks.BaseBlock
import io.enderdev.catalyx.utils.extensions.toStack
import io.enderdev.emergingtechnology.blocks.ModBlocks
import io.enderdev.emergingtechnology.items.ItemBase
import io.enderdev.emergingtechnology.items.ModItems
import io.enderdev.emergingtechnology.proxy.CommonProxy
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import org.apache.logging.log4j.Logger
import java.text.DecimalFormat

@Mod(
	modid = Tags.MODID,
	name = Tags.MOD_NAME,
	version = Tags.VERSION,
	dependencies = EmergingTechnology.DEPENDENCIES,
	modLanguageAdapter = "io.github.chaosunity.forgelin.KotlinAdapter"
)
object EmergingTechnology {
	const val DEPENDENCIES =
		"required-after:configanytime;required-after:forgelin_continuous@[${Tags.KOTLIN_VERSION},);required-after:catalyx"//;after:crafttweaker;after:groovyscript@[${Tags.GROOVYSCRIPT_VERSION},);before:jei;"
	val DECIMAL_FORMAT = DecimalFormat("#0.00")

	val creativeTab = object : CreativeTabs(Tags.MODID) {
		override fun createIcon() = Items.DIAMOND.toStack()//ModBlocks.chemical_combiner.toStack()
	}

	val catalyxSettings = CatalyxSettings(Tags.MODID, creativeTab, EmergingTechnology, true, { ModBlocks.blocks.add(it as BaseBlock) }, { ModItems.items.add(it as ItemBase) })

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

	@Mod.EventBusSubscriber(modid = Tags.MODID)
	object Registration {
		@SubscribeEvent
		fun registerBlocks(event: RegistryEvent.Register<Block>) {
			ModBlocks.registerBlocks(event)
		}

		@SubscribeEvent
		fun registerItems(event: RegistryEvent.Register<Item>) {
			ModBlocks.registerItems(event)
			ModItems.registerItems(event)
		}

		@SideOnly(Side.CLIENT)
		@SubscribeEvent
		fun registerModels(event: ModelRegistryEvent) {
			ModBlocks.registerModels()
			ModItems.registerModels()
		}
	}
}
