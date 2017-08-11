package mjaroslav.mcmods.peatized.common.network;

import net.minecraft.entity.player.EntityPlayer;

public class PacketFire extends LocationDoublePacket<PacketFire> {
	private static double x, y, z;

	public PacketFire() {
	}

	public PacketFire(final double x, final double y, final double z) {
		super(x, y, z);
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void handleClientSide(final PacketFire message, final EntityPlayer player) {
		double pos_x, pos_z, xSpeed, zSpeed;
		final double r = 0.4;
		for (float l = 0; l < 2 * Math.PI; l += 2 * Math.PI / 50) {
			pos_x = this.x + r * Math.cos(l);
			pos_z = this.z + r * Math.sin(l);
			xSpeed = (pos_x - this.x) * 0.2D;
			zSpeed = (pos_z - this.z) * 0.2D;
			player.worldObj.spawnParticle("flame", pos_x, this.y, pos_z, xSpeed, 0.0D, zSpeed);
		}
	}

	@Override
	public void handleServerSide(final PacketFire message, final EntityPlayer player) {
	}
}