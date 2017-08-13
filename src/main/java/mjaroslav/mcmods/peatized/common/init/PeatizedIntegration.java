package mjaroslav.mcmods.peatized.common.init;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mjaroslav.mcmods.peatized.common.integration.forestry.PeatizedForestry;

public class PeatizedIntegration implements IInitBase {
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		FMLInterModComms.sendMessage("Waila", "register",
				"mjaroslav.mcmods.peatized.common.integration.waila.PeatizedWaila.onWailaCalled");
	}

	@Override
	public void init(FMLInitializationEvent event) {
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		if (Loader.isModLoaded("Forestry")) {
			PeatizedForestry.init();
		}
	}
}
