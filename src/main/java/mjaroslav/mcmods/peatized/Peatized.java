package mjaroslav.mcmods.peatized;

import mjaroslav.mcmods.peatized.common.item.ItemRegistry;
import mjaroslav.mcmods.peatized.common.util.Proxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static mjaroslav.mcmods.peatized.common.lib.ModInfo.*;

@Mod(modid = MOD_ID, name = NAME, version = VERSION)
public class Peatized {
    @Instance
    public static Peatized instance;

    @SidedProxy(modId = MOD_ID, clientSide = CLIENT_PROXY, serverSide = /*SERVER_PROXY*/ COMMON_PROXY)
    private static Proxy proxy;

    public static final CreativeTabs TAB = new CreativeTabs(MOD_ID) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ItemRegistry.PEAT_BALL, 1);
        }
    };

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }
}
