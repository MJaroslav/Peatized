package mjaroslav.mcmods.peatized.common;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import mjaroslav.mcmods.mjutils.common.objects.ProxyBase;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class PeatizedCommonProxy extends ProxyBase {
	public static final int riCompressor = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public EntityPlayer getEntityPlayer(MessageContext ctx) {
		return ctx.getServerHandler().playerEntity;
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
	}

	@Override
	public void init(FMLInitializationEvent event) {
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
	}

	@Override
	public void spawnParticle(String string, double x, double y, double z, Object... args) {
	}

	@Override
	public Minecraft getMinecraft() {
		return null;
	}
}
