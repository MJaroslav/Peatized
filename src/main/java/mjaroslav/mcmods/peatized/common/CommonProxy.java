package mjaroslav.mcmods.peatized.common;

import mjaroslav.mcmods.peatized.common.block.BlockBase;
import mjaroslav.mcmods.peatized.common.block.BlockRegistry;
import mjaroslav.mcmods.peatized.common.item.ItemBase;
import mjaroslav.mcmods.peatized.common.item.ItemRegistry;
import mjaroslav.mcmods.peatized.common.util.Proxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy implements Proxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        BlockRegistry.forEach(BlockBase::register);
        ItemRegistry.forEach(ItemBase::register);
    }

    @Override
    public void init(FMLInitializationEvent event) {
    }
}
