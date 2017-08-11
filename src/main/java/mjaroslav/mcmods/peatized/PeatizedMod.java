package mjaroslav.mcmods.peatized;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import mjaroslav.mcmods.peatized.client.gui.PeatizedGuiHandler;
import mjaroslav.mcmods.peatized.common.PeatizedCommonProxy;
import mjaroslav.mcmods.peatized.common.command.CompressorRecipesReloadCommand;
import mjaroslav.mcmods.peatized.common.config.PeatizedConfig;
import mjaroslav.mcmods.peatized.common.creativetab.PeatizedTab;
import mjaroslav.mcmods.peatized.common.event.EventHandlerWorld;
import mjaroslav.mcmods.peatized.common.event.PeatizedConfigUpdateEvent;
import mjaroslav.mcmods.peatized.common.init.PeatizedBlocks;
import mjaroslav.mcmods.peatized.common.init.PeatizedCrafts;
import mjaroslav.mcmods.peatized.common.init.PeatizedItems;
import mjaroslav.mcmods.peatized.common.init.PeatizedWorldGen;
import mjaroslav.mcmods.peatized.common.integration.forestry.PeatizedForestry;
import mjaroslav.mcmods.peatized.common.item.crafting.CompressorRecipes;
import mjaroslav.mcmods.peatized.common.network.EventHandlerNetwork;
import mjaroslav.mcmods.peatized.common.network.NetworkHandler;
import mjaroslav.mcmods.peatized.common.utils.PeatizedFuelHandler;
import mjaroslav.mcmods.peatized.common.world.VillagePeatmanManager;
import net.minecraftforge.common.MinecraftForge;

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
	public static PeatizedCommonProxy proxy = new PeatizedCommonProxy();

	public static String configFolderPath;

	public static PeatizedTab tab = new PeatizedTab(MODID);

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		instance = this;
		configFolderPath = event.getModConfigurationDirectory().toString();
		FMLCommonHandler.instance().bus().register(new PeatizedConfigUpdateEvent());
		PeatizedConfig.init();
		PeatizedBlocks.init();
		PeatizedItems.init();
		FMLInterModComms.sendMessage("Waila", "register",
				"mjaroslav.mcmods.peatized.common.integration.waila.PeatizedWaila.onWailaCalled");
		NetworkHandler.init();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		PeatizedWorldGen.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new PeatizedGuiHandler());
		MinecraftForge.EVENT_BUS.register(new EventHandlerWorld());
		FMLCommonHandler.instance().bus().register(new EventHandlerNetwork());
		GameRegistry.registerFuelHandler(new PeatizedFuelHandler());
		VillagePeatmanManager peatman = new VillagePeatmanManager();
		VillagerRegistry.instance().registerVillagerId(PeatizedConfig.villagerId);
		VillagerRegistry.instance().registerVillageTradeHandler(PeatizedConfig.villagerId, peatman);
		proxy.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		PeatizedCrafts.init();
		CompressorRecipes.readFromConfig();
		if (Loader.isModLoaded("Forestry")) {
			PeatizedForestry.init();
		}
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		event.registerServerCommand(new CompressorRecipesReloadCommand());
	}
}
