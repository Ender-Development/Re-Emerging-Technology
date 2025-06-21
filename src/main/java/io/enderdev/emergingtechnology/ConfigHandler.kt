package io.enderdev.emergingtechnology

import com.cleanroommc.configanytime.ConfigAnytime
import net.minecraftforge.common.config.Config
import net.minecraftforge.common.config.ConfigManager
import net.minecraftforge.fml.client.event.ConfigChangedEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Config(modid = Tags.MODID, name = Tags.MODID)
object ConfigHandler {
	// screams of the damned




	// on a more realistic note though
	// TODO for ender someday

	init {
		ConfigAnytime.register(ConfigHandler::class.java)
	}
}
