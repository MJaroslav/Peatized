package mjaroslav.mcmods.peatized.common.event;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mjaroslav.mcmods.peatized.PeatizedMod;
import mjaroslav.mcmods.peatized.common.config.PeatizedConfig;

public class PeatizedConfigUpdateEvent {
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equals(PeatizedMod.MODID)) {
			PeatizedConfig.sync();
			PeatizedMod.log.info("Configuration reloaded");
		}
	}
}
