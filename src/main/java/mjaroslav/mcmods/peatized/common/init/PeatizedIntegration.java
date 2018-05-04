package mjaroslav.mcmods.peatized.common.init;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.*;
import mjaroslav.mcmods.mjutils.lib.module.IModule;
import mjaroslav.mcmods.mjutils.lib.module.ModModule;
import mjaroslav.mcmods.peatized.common.integration.forestry.PeatizedForestry;
import mjaroslav.mcmods.peatized.lib.ModInfo;

@ModModule(modid = ModInfo.MODID)
public class PeatizedIntegration implements IModule {
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

    @Override
    public String getModuleName() {
        return "Integration";
    }

    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public boolean canLoad() {
        return true;
    }

    @Override
    public String[] modDependencies() {
        return null;
    }
}
