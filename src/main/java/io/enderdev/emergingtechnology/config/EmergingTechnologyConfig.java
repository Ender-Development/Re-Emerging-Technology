package io.enderdev.emergingtechnology.config;

import io.enderdev.emergingtechnology.Tags;
import io.enderdev.emergingtechnology.config.electrics.ElectricsModule;
import io.enderdev.emergingtechnology.config.hydroponics.HydroponicsModule;
import io.enderdev.emergingtechnology.config.polymers.PolymersModule;
import io.enderdev.emergingtechnology.config.synthetics.SyntheticsModule;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
The main configuration class for Emerging Technology
*/
@Config(modid = Tags.MODID, name = "emergingtechnology/" + Tags.MOD_NAME)
@LangKey("config.emergingtechnology.title")
public class EmergingTechnologyConfig {
	
	@Name("Hydroponics Module")
	@LangKey("config.emergingtechnology.hydroponics.title")
	public static final HydroponicsModule HYDROPONICS_MODULE = new HydroponicsModule();

	@Name("Polymers Module")
	@LangKey("config.emergingtechnology.polymers.title")
	public static final PolymersModule POLYMERS_MODULE = new PolymersModule();

	@Name("Synthetics Module")
	@LangKey("config.emergingtechnology.synthetics.title")
	public static final SyntheticsModule SYNTHETICS_MODULE = new SyntheticsModule();

	@Name("Electrics Module")
	@LangKey("config.emergingtechnology.electrics.title")
	public static final ElectricsModule ELECTRICS_MODULE = new ElectricsModule();

	@Mod.EventBusSubscriber(modid = Tags.MODID)
	private static class ClientConfigEventHandler {

		/**
		 * Inject the new values and save to the config file when the config has been changed from the GUI.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(Tags.MODID)) {
				ConfigManager.sync(Tags.MODID, Config.Type.INSTANCE);
			}
		}
	}
}
