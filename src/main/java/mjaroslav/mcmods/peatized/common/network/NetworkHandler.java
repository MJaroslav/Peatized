package mjaroslav.mcmods.peatized.common.network;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import mjaroslav.mcmods.peatized.PeatizedMod;
import mjaroslav.mcmods.peatized.common.init.IInitBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class NetworkHandler implements IInitBase {
	public static final NetworkHandler INSTANCE = new NetworkHandler();
	public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(PeatizedMod.MODID);
	private static int dec = 0;

	public NetworkHandler() {
	}

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
		NETWORK.registerMessage(PacketParticlesMP.class, PacketParticlesMP.class, dec++, Side.SERVER);
		NETWORK.registerMessage(PacketFire.class, PacketFire.class, dec++, Side.CLIENT);
		NETWORK.registerMessage(PacketCompressingRecipes.class, PacketCompressingRecipes.class, dec++, Side.CLIENT);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
	}
}