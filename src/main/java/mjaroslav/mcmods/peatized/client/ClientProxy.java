package mjaroslav.mcmods.peatized.client;

import mjaroslav.mcmods.peatized.client.render.model.ModelRegistry;
import mjaroslav.mcmods.peatized.common.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        // Register item and block models.
//        MinecraftForge.EVENT_BUS.register(ModelRegistry.INSTANCE);
    }
}
