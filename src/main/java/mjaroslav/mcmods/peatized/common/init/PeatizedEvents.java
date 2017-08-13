package mjaroslav.mcmods.peatized.common.init;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import mjaroslav.mcmods.peatized.PeatizedMod;
import mjaroslav.mcmods.peatized.client.gui.PeatizedGuiHandler;
import mjaroslav.mcmods.peatized.common.event.EventHandlerWorld;
import mjaroslav.mcmods.peatized.common.event.PeatizedConfigUpdateEvent;
import mjaroslav.mcmods.peatized.common.network.EventHandlerNetwork;
import net.minecraftforge.common.MinecraftForge;

public class PeatizedEvents implements IInitBase {
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(new PeatizedConfigUpdateEvent());
	}

	@Override
	public void init(FMLInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(new EventHandlerNetwork());
		NetworkRegistry.INSTANCE.registerGuiHandler(PeatizedMod.instance, new PeatizedGuiHandler());
		MinecraftForge.EVENT_BUS.register(new EventHandlerWorld());
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
	}
}
