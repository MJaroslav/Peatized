package mjaroslav.mcmods.peatized.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileUpa extends TileEntity {
    public int mainColor = 0xFFFFFF;
    public int secondaryColor = 0x3D1A1A;
    public int lineColor = 0xFF6A00;
    public int cheekColor = 0x00FF90;
    public int eyeColor = 0xFFFFFF;
    public int noseColor = 0xFF0000;
    public int earsColor = 0x000000;

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
    }

    public void writeToNBTS(NBTTagCompound nbt) {
        nbt.setInteger("main_color", mainColor);
        nbt.setInteger("secondary_color", secondaryColor);
        nbt.setInteger("line_color", lineColor);
        nbt.setInteger("cheek_color", cheekColor);
        nbt.setInteger("eye_color", eyeColor);
        nbt.setInteger("nose_color", noseColor);
        nbt.setInteger("ear_color", earsColor);
    }

    public void readFromNBTS(NBTTagCompound nbt) {
        mainColor = nbt.getInteger("main_color");
        secondaryColor = nbt.getInteger("secondary_color");
        lineColor = nbt.getInteger("line_color");
        cheekColor = nbt.getInteger("cheek_color");
        eyeColor = nbt.getInteger("eye_color");
        noseColor = nbt.getInteger("nose_color");
        earsColor = nbt.getInteger("ears_color");
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound syncData = new NBTTagCompound();
        this.writeToNBTS(syncData);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, syncData);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.readFromNBTS(pkt.func_148857_g());
    }
}
