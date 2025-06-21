package io.enderdev.emergingtechnology.proxy

import crafttweaker.CraftTweakerAPI
import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.capability.AlchemistryDrugInfo
import io.enderdev.emergingtechnology.chemistry.CompoundRegistry
import io.enderdev.emergingtechnology.chemistry.ElementRegistry
import io.enderdev.emergingtechnology.client.gui.GuiHandler
import io.enderdev.emergingtechnology.compat.top.TopHandler
import io.enderdev.emergingtechnology.network.PacketHandler
import io.enderdev.emergingtechnology.recipes.ModRecipes
import net.minecraft.nbt.NBTBase
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.fml.common.Loader
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.network.NetworkRegistry

open class CommonProxy {

	companion object {
		private var stage: LoadingStage = LoadingStage.PRE_INIT

		fun getStage(): LoadingStage {
			return stage
		}
	}

	open fun preInit(e: FMLPreInitializationEvent) {
		stage = LoadingStage.PRE_INIT
		EmergingTechnology.logger = e.modLog

		registerCapabilities()
		if(ElementRegistry.getAllElements().isEmpty()) {
			EmergingTechnology.logger.info("ElementRegistry isn't initialized yet, initializing")
			ElementRegistry.init()
		}
		if(CompoundRegistry.compounds().isEmpty()) {
			EmergingTechnology.logger.info("CompoundRegistry isn't initialized yet, initializing")
			CompoundRegistry.init()
		}
		PacketHandler.registerMessages(Tags.MODID)

		if(Loader.isModLoaded("crafttweaker")) CraftTweakerAPI.tweaker.loadScript(false, Tags.MODID)
	}

	open fun init(e: FMLInitializationEvent) {
		stage = LoadingStage.INIT
		ModRecipes.initOredict()
		NetworkRegistry.INSTANCE.registerGuiHandler(EmergingTechnology, GuiHandler())
		MinecraftForge.EVENT_BUS.register(CommonEventHandler())

		if(Loader.isModLoaded("theoneprobe")) {
			TopHandler.register()
		}
	}

	open fun postInit(e: FMLPostInitializationEvent) {
		stage = LoadingStage.POST_INIT
		ModRecipes.init()
	}

	private fun registerCapabilities() {
		CapabilityManager.INSTANCE.register(
			AlchemistryDrugInfo::class.java, object : Capability.IStorage<AlchemistryDrugInfo> {

				override fun writeNBT(capability: Capability<AlchemistryDrugInfo>, instance: AlchemistryDrugInfo, side: EnumFacing): NBTBase? {
					throw UnsupportedOperationException()
				}

				override fun readNBT(capability: Capability<AlchemistryDrugInfo>, instance: AlchemistryDrugInfo, side: EnumFacing, nbt: NBTBase) {
					throw UnsupportedOperationException()
				}
			}) { throw UnsupportedOperationException() }
	}

	enum class LoadingStage {
		PRE_INIT, INIT, POST_INIT
	}
}
