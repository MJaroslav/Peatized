package mjaroslav.mcmods.peatized.common;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import mjaroslav.mcmods.peatized.common.init.IInitBase;
import net.minecraft.entity.player.EntityPlayer;

public class PeatizedCommonProxy implements IInitBase {
	public static final int riCompressor = RenderingRegistry.getNextAvailableRenderId();

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
}
