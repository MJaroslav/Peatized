package mjaroslav.mcmods.peatized.common;

import mjaroslav.mcmods.peatized.common.registry.PeatizedBlocks;
import mjaroslav.mcmods.peatized.lib.Proxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy implements Proxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        PeatizedBlocks.register();
    }

    @Override
    public void init(FMLInitializationEvent event) {
    }
}
