package mjaroslav.mcmods.peatized.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketParticlesMP extends AbstractPacket<PacketParticlesMP> {
	public PacketParticlesMP() {
	}

	@Override
	public void handleClientSide(final PacketParticlesMP message, final EntityPlayer player) {
	}

	@Override
	public void handleServerSide(final PacketParticlesMP message, EntityPlayer player) {
		NetworkHandler.INSTANCE.sendToAllAround(new PacketFire(player.posX, player.posY, player.posZ), player.worldObj);
	}

	@Override
	public void fromBytes(final ByteBuf buf) {
	}

	@Override
	public void toBytes(final ByteBuf buf) {
	}
}