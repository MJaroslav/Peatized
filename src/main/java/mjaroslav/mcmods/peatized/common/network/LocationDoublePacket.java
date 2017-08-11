package mjaroslav.mcmods.peatized.common.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;

public abstract class LocationDoublePacket<REQ extends IMessage> extends AbstractPacket<REQ> {
	protected double x, y, z;

	public LocationDoublePacket() {
	}

	public LocationDoublePacket(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void toBytes(final ByteBuf buf) {
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
	}

	@Override
	public void fromBytes(final ByteBuf buf) {
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
	}

	public NetworkRegistry.TargetPoint getTargetPoint(final World world) {
		return getTargetPoint(world, 64);
	}

	public NetworkRegistry.TargetPoint getTargetPoint(final World world, final double updateDistance) {
		return new NetworkRegistry.TargetPoint(world.provider.dimensionId, x, y, z, updateDistance);
	}
}