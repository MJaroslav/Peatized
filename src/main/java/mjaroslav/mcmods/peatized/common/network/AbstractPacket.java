package mjaroslav.mcmods.peatized.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import mjaroslav.mcmods.peatized.PeatizedMod;
import net.minecraft.entity.player.EntityPlayer;

public abstract class AbstractPacket<REQ extends IMessage> implements IMessage, IMessageHandler<REQ, REQ> {
	@Override
	public REQ onMessage(final REQ message, final MessageContext ctx) {
		if (ctx.side == Side.SERVER) {
			this.handleServerSide(message, PeatizedMod.proxy.getEntityPlayer(ctx));
		} else {
			this.handleClientSide(message, PeatizedMod.proxy.getEntityPlayer(ctx));
		}
		return null;
	}

	public abstract void handleClientSide(final REQ message, final EntityPlayer player);

	public abstract void handleServerSide(final REQ message, final EntityPlayer player);
}