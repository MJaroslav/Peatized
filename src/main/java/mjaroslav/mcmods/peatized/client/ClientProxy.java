package mjaroslav.mcmods.peatized.client;

import mjaroslav.mcmods.peatized.common.CommonProxy;
import mjaroslav.mcmods.peatized.common.registry.PeatizedBlocks;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        PeatizedBlocks.registerRender();
    }
}
