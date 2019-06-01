package mjaroslav.mcmods.peatized.lib;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface Proxy {
    void preInit(FMLPreInitializationEvent event);

    void init(FMLInitializationEvent event);
}
