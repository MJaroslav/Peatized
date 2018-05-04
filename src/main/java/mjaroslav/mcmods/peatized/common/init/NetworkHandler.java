package mjaroslav.mcmods.peatized.common.init;

import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import mjaroslav.mcmods.mjutils.lib.module.IModule;
import mjaroslav.mcmods.mjutils.lib.module.ModModule;
import mjaroslav.mcmods.peatized.common.network.LocationDoublePacket;
import mjaroslav.mcmods.peatized.common.network.PacketCompressingRecipes;
import mjaroslav.mcmods.peatized.lib.ModInfo;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

@ModModule(modid = ModInfo.MODID)
public class NetworkHandler implements IModule {
    public static final NetworkHandler INSTANCE = new NetworkHandler();
    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.MODID);
    private static int dec = 0;

    public static void sendToAll(final IMessage message) {
        NETWORK.sendToAll(message);
    }

    public static void sendTo(final IMessage message, final EntityPlayerMP player) {
        NETWORK.sendTo(message, player);
    }

    public static void sendToAllAround(final LocationDoublePacket message, final World world) {
        sendToAllAround(message, message.getTargetPoint(world));
    }

    public static void sendToAllAround(final IMessage message, final NetworkRegistry.TargetPoint point) {
        NETWORK.sendToAllAround(message, point);
    }

    public static void sendToDimension(final IMessage message, final int dimensionId) {
        NETWORK.sendToDimension(message, dimensionId);
    }

    public static void sendToServer(final IMessage message) {
        NETWORK.sendToServer(message);
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
    }

    @Override
    public void init(FMLInitializationEvent event) {
        NETWORK.registerMessage(PacketCompressingRecipes.class, PacketCompressingRecipes.class, dec++, Side.CLIENT);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
    }

    @Override
    public String getModuleName() {
        return "Network";
    }

    @Override
    public int getPriority() {
        return 4;
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