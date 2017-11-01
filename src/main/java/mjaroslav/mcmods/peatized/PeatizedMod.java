package mjaroslav.mcmods.peatized;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import mjaroslav.mcmods.mjutils.common.objects.ModInitHandler;
import mjaroslav.mcmods.peatized.common.PeatizedCommonProxy;
import mjaroslav.mcmods.peatized.common.command.CompressorRecipesReloadCommand;
import mjaroslav.mcmods.peatized.common.config.PeatizedConfig;
import mjaroslav.mcmods.peatized.common.creativetab.PeatizedTab;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

@Mod(modid = PeatizedMod.MODID, name = PeatizedMod.NAME, version = PeatizedMod.VERSION, guiFactory = PeatizedMod.GUIFACTORY, dependencies = PeatizedMod.DEPENDENCIES)
public class PeatizedMod {
	public static final String MODID = "peatized";
	public static final String NAME = "Peatized";
	public static final String VERSION = "1.7.10-1";
	public static final String DEPENDENCIES = "required-after:mjutils@[1.7.10-3,);";
	public static final String GUIFACTORY = "mjaroslav.mcmods.peatized.client.gui.PeatizedGuiFactory";
	public static final String SERVERPROXY = "mjaroslav.mcmods.peatized.common.PeatizedCommonProxy";
	public static final String CLIENTPROXY = "mjaroslav.mcmods.peatized.client.PeatizedClientProxy";

	@Instance(MODID)
	public static PeatizedMod instance;

	public static final Logger log = LogManager.getLogger(NAME);

	@SidedProxy(clientSide = CLIENTPROXY, serverSide = SERVERPROXY)
	public static PeatizedCommonProxy proxy = new PeatizedCommonProxy();

	public static PeatizedTab tab = new PeatizedTab(MODID);

	public static ToolMaterial rena = EnumHelper.addToolMaterial("rena", 4, 2500, 9.0F, 4.0F, 30);

	public static PeatizedConfig config = new PeatizedConfig();

	public static ModInitHandler initHandler = new ModInitHandler(MODID);

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		this.config.preInit(event);
		this.initHandler.preInit(event);
		this.proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		this.config.init(event);
		this.initHandler.init(event);
		this.proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		this.config.postInit(event);
		this.initHandler.postInit(event);
		this.proxy.postInit(event);
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		event.registerServerCommand(new CompressorRecipesReloadCommand());
	}

	@EventHandler
	public void constr(FMLConstructionEvent event) {
		this.initHandler.findModules(event);
	}
}
