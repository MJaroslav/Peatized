package mjaroslav.mcmods.peatized.common.network;

import io.netty.buffer.ByteBuf;
import mjaroslav.mcmods.peatized.common.tileentity.TileUpa;
import net.minecraft.entity.player.EntityPlayer;

public class PacketUpa extends LocationDoublePacket<PacketUpa> {
    public int mainColor;
    public int secondaryColor;
    public int lineColor;
    public int cheeksColor;
    public int eyesColor;
    public int noseColor;
    public int earsColor;
    public int faceColor;

    public PacketUpa() {
        super();
    }

    public PacketUpa(double x, double y, double z, int main, int secondary, int line, int cheeks, int eyes, int nose,
            int ears, int face) {
        super(x, y, z);
        mainColor = main;
        secondaryColor = secondary;
        lineColor = line;
        cheeksColor = cheeks;
        eyesColor = eyes;
        noseColor = nose;
        earsColor = ears;
        faceColor = face;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        mainColor = buf.readInt();
        secondaryColor = buf.readInt();
        lineColor = buf.readInt();
        cheeksColor = buf.readInt();
        eyesColor = buf.readInt();
        noseColor = buf.readInt();
        earsColor = buf.readInt();
        faceColor = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        buf.writeInt(mainColor);
        buf.writeInt(secondaryColor);
        buf.writeInt(lineColor);
        buf.writeInt(cheeksColor);
        buf.writeInt(eyesColor);
        buf.writeInt(noseColor);
        buf.writeInt(earsColor);
        buf.writeInt(faceColor);
    }

    @Override
    public void handleClientSide(final PacketUpa message, EntityPlayer player) {
    }

    @Override
    public void handleServerSide(final PacketUpa message, EntityPlayer player) {
        TileUpa tile = (TileUpa) player.worldObj.getTileEntity((int) message.x, (int) message.y, (int) message.z);
        if (tile != null) {
            tile.setMainColor(message.mainColor);
            tile.setSecondaryColor(message.secondaryColor);
            tile.setLineColor(message.lineColor);
            tile.setNoseColor(message.noseColor);
            tile.setCheeksColor(message.cheeksColor);
            tile.setEarsColor(message.earsColor);
            tile.setFaceColor(message.faceColor);
            tile.setEyesColor(message.eyesColor);
        }
    }
}
