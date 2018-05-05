package mjaroslav.mcmods.peatized;

import static mjaroslav.mcmods.peatized.lib.ModInfo.*;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import mjaroslav.mcmods.mjutils.lib.module.*;
import mjaroslav.mcmods.peatized.common.command.CompressorRecipesReloadCommand;
import mjaroslav.mcmods.peatized.common.creativetab.PeatizedTab;

@Mod(modid = MODID, name = NAME, version = VERSION, guiFactory = GUIFACTORY, dependencies = DEPENDENCIES)
public class ModPeatized {
    @Instance(MODID)
    public static ModPeatized instance;

    @SidedProxy(clientSide = CLIENTPROXY, serverSide = SERVERPROXY)
    public static ProxyBase proxy;

    public static PeatizedTab tab = new PeatizedTab(MODID);

    public static ConfigurationHandler config = new ConfigurationHandler(MODID, LOG);
    public static ModuleHandler moduleHandler;

    public static String configFolder;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configFolder = event.getModConfigurationDirectory().getAbsolutePath();
        moduleHandler.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        moduleHandler.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        moduleHandler.postInit(event);
    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new CompressorRecipesReloadCommand());
    }

    @EventHandler
    public void constr(FMLConstructionEvent event) {
        moduleHandler = new ModuleHandler(MODID, config, proxy);
        moduleHandler.findModules(event);
    }
}
