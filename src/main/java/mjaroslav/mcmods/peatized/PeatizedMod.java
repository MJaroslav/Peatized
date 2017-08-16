package mjaroslav.mcmods.peatized;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import mjaroslav.mcmods.peatized.common.PeatizedCommonProxy;
import mjaroslav.mcmods.peatized.common.command.CompressorRecipesReloadCommand;
import mjaroslav.mcmods.peatized.common.config.PeatizedConfig;
import mjaroslav.mcmods.peatized.common.creativetab.PeatizedTab;
import mjaroslav.mcmods.peatized.common.init.IInitBase;
import mjaroslav.mcmods.peatized.common.init.PeatizedBlocks;
import mjaroslav.mcmods.peatized.common.init.PeatizedCrafts;
import mjaroslav.mcmods.peatized.common.init.PeatizedEvents;
import mjaroslav.mcmods.peatized.common.init.PeatizedIntegration;
import mjaroslav.mcmods.peatized.common.init.PeatizedItems;
import mjaroslav.mcmods.peatized.common.init.PeatizedWorldGen;
import mjaroslav.mcmods.peatized.common.network.NetworkHandler;
import net.minecraft.util.DamageSource;

@Mod(modid = PeatizedMod.MODID, name = PeatizedMod.NAME, version = PeatizedMod.VERSION, guiFactory = PeatizedMod.GUIFACTORY)
public class PeatizedMod {
	public static final String MODID = "peatized";
	public static final String NAME = "Peatized";
	public static final String VERSION = "1.7.10-1";
	public static final String GUIFACTORY = "mjaroslav.mcmods.peatized.client.gui.PeatizedGuiFactory";
	public static final String SERVERPROXY = "mjaroslav.mcmods.peatized.common.PeatizedCommonProxy";
	public static final String CLIENTPROXY = "mjaroslav.mcmods.peatized.client.PeatizedClientProxy";

	@Instance(MODID)
	public static PeatizedMod instance;

	public static final Logger log = LogManager.getLogger(NAME);

	@SidedProxy(clientSide = CLIENTPROXY, serverSide = SERVERPROXY)
	public static PeatizedCommonProxy proxyModule = new PeatizedCommonProxy();

	public static PeatizedTab tab = new PeatizedTab(MODID);

	public static ArrayList<IInitBase> modules = new ArrayList<IInitBase>();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		instance = this;
		for (IInitBase base : modules)
			base.preInit(event);
		proxyModule.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		for (IInitBase base : modules)
			base.init(event);
		proxyModule.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		for (IInitBase base : modules)
			base.postInit(event);
		proxyModule.postInit(event);
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		event.registerServerCommand(new CompressorRecipesReloadCommand());
	}

	static {
		modules.add(new PeatizedConfig());
		modules.add(new PeatizedBlocks());
		modules.add(new PeatizedItems());
		modules.add(new PeatizedCrafts());
		modules.add(new PeatizedEvents());
		modules.add(new PeatizedWorldGen());
		modules.add(new PeatizedIntegration());
		modules.add(new NetworkHandler());
	}
}
