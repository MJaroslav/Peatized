package mjaroslav.mcmods.peatized.common;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;

public class PeatizedCommonProxy {
	public static final int riCompressor = RenderingRegistry.getNextAvailableRenderId();
	
	public EntityPlayer getEntityPlayer(MessageContext ctx) {
		return ctx.getServerHandler().playerEntity;
	}

	public void init() {

	}
}
