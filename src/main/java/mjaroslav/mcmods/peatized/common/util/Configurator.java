package mjaroslav.mcmods.peatized.common.util;

import mjaroslav.mcmods.peatized.common.lib.ModInfo;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public abstract class Configurator {
    private Configuration instance;

    public void load(File file) {
        instance = new Configuration(file);
        initFields(instance);
        sync();
        MinecraftForge.EVENT_BUS.register(new Handler());
    }

    protected abstract void initFields(Configuration instance);

    private void sync() {
        if (instance != null && instance.hasChanged())
            instance.save();
    }

    private class Handler {
        @SubscribeEvent
        public void onUpdate(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(ModInfo.MOD_ID))
                sync();
        }
    }
}
