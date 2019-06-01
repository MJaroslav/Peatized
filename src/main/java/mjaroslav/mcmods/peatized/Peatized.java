package mjaroslav.mcmods.peatized;

import mjaroslav.mcmods.peatized.lib.Proxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import static mjaroslav.mcmods.peatized.lib.ModInfo.*;

@Mod(modid = MOD_ID, name = NAME, version = VERSION)
public class Peatized {
    @SidedProxy(modId = MOD_ID, clientSide = CLIENT_PROXY, serverSide = /*SERVER_PROXY*/ COMMON_PROXY)
    private static Proxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }
}
