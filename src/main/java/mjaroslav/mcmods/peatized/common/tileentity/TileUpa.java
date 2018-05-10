package mjaroslav.mcmods.peatized.common.tileentity;

import mjaroslav.mcmods.peatized.common.init.PeatizedBlocks;
import mjaroslav.mcmods.peatized.lib.NBTInfo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileUpa extends TileEntity {
    private int mainColor;
    private int secondaryColor;
    private int lineColor;
    private int cheeksColor;
    private int eyesColor;
    private int noseColor;
    private int earsColor;
    private int faceColor;
    private int mainColorOld;
    private int secondaryColorOld;
    private int lineColorOld;
    private int cheeksColorOld;
    private int eyesColorOld;
    private int noseColorOld;
    private int earsColorOld;
    private int faceColorOld;
    private String name;
    private String author;
    private boolean hasTooltip;

    @Override
    public void updateEntity() {
        if (mainColor != mainColorOld || secondaryColor != secondaryColorOld || lineColor != lineColorOld
                || cheeksColor != cheeksColorOld || eyesColor != eyesColorOld || noseColor != noseColorOld
                || earsColor != earsColorOld || faceColor != faceColorOld) {
            this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            markDirty();
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        writeToNBTS(nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        readFromNBTS(nbt);
    }

    public void writeToNBTS(NBTTagCompound nbt) {
        nbt.setInteger(NBTInfo.COLOR_MAIN, mainColor);
        nbt.setInteger(NBTInfo.COLOR_SECONDARY, secondaryColor);
        nbt.setInteger(NBTInfo.COLOR_LINE, lineColor);
        nbt.setInteger(NBTInfo.COLOR_CHEEKS, cheeksColor);
        nbt.setInteger(NBTInfo.COLOR_EYES, eyesColor);
        nbt.setInteger(NBTInfo.COLOR_NOSE, noseColor);
        nbt.setInteger(NBTInfo.COLOR_EARS, earsColor);
        nbt.setInteger(NBTInfo.COLOR_FACE, faceColor);
        nbt.setString(NBTInfo.AUTHOR, author);
        nbt.setString(NBTInfo.NAME, name);
        nbt.setBoolean(NBTInfo.TOOLTIP_HAS, hasTooltip);
    }

    public void readFromNBTS(NBTTagCompound nbt) {
        mainColor = nbt.getInteger(NBTInfo.COLOR_MAIN);
        secondaryColor = nbt.getInteger(NBTInfo.COLOR_SECONDARY);
        lineColor = nbt.getInteger(NBTInfo.COLOR_LINE);
        cheeksColor = nbt.getInteger(NBTInfo.COLOR_CHEEKS);
        eyesColor = nbt.getInteger(NBTInfo.COLOR_EYES);
        noseColor = nbt.getInteger(NBTInfo.COLOR_NOSE);
        earsColor = nbt.getInteger(NBTInfo.COLOR_EARS);
        faceColor = nbt.getInteger(NBTInfo.COLOR_FACE);
        hasTooltip = nbt.getBoolean(NBTInfo.TOOLTIP_HAS);
        author = nbt.getString(NBTInfo.AUTHOR);
        name = nbt.getString(NBTInfo.NAME);
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

    public int getCheeksColor() {
        return cheeksColor;
    }

    public int getEarsColor() {
        return earsColor;
    }

    public int getEyesColor() {
        return eyesColor;
    }

    public int getFaceColor() {
        return faceColor;
    }

    public int getLineColor() {
        return lineColor;
    }

    public int getMainColor() {
        return mainColor;
    }

    public int getNoseColor() {
        return noseColor;
    }

    public int getSecondaryColor() {
        return secondaryColor;
    }

    public String getCheeksColorString() {
        return String.format("#%06X", (0xFFFFFF & this.cheeksColor));
    }

    public String getEarsColorString() {
        return String.format("#%06X", (0xFFFFFF & this.earsColor));
    }

    public String getEyesColorString() {
        return String.format("#%06X", (0xFFFFFF & this.eyesColor));
    }

    public String getFaceColorString() {
        return String.format("#%06X", (0xFFFFFF & this.faceColor));
    }

    public String getLineColorString() {
        return String.format("#%06X", (0xFFFFFF & this.lineColor));
    }

    public String getMainColorString() {
        return String.format("#%06X", (0xFFFFFF & this.mainColor));
    }

    public String getNoseColorString() {
        return String.format("#%06X", (0xFFFFFF & this.noseColor));
    }

    public String getSecondaryColorString() {
        return String.format("#%06X", (0xFFFFFF & this.secondaryColor));
    }

    public void setCheeksColor(int cheeksColor) {
        this.cheeksColorOld = this.cheeksColor;
        this.cheeksColor = cheeksColor;
    }

    public void setEarsColor(int earsColor) {
        this.earsColorOld = this.earsColor;
        this.earsColor = earsColor;
    }

    public void setEyesColor(int eyesColor) {
        this.eyesColorOld = this.eyesColor;
        this.eyesColor = eyesColor;
    }

    public void setFaceColor(int faceColor) {
        this.faceColorOld = this.faceColor;
        this.faceColor = faceColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColorOld = this.lineColor;
        this.lineColor = lineColor;
    }

    public void setMainColor(int mainColor) {
        this.mainColorOld = this.mainColor;
        this.mainColor = mainColor;
    }

    public void setNoseColor(int noseColor) {
        this.noseColorOld = this.noseColor;
        this.noseColor = noseColor;
    }

    public void setSecondaryColor(int secondaryColor) {
        this.secondaryColorOld = this.secondaryColor;
        this.secondaryColor = secondaryColor;
    }

    public ItemStack toStack() {
        NBTTagCompound nbt = new NBTTagCompound();
        writeToNBTS(nbt);
        ItemStack stack = new ItemStack(PeatizedBlocks.upa, 1);
        stack.setTagCompound(nbt);
        return stack;
    }

    public void fromStack(ItemStack stack) {
        if (stack != null && stack.getItem() == Item.getItemFromBlock(PeatizedBlocks.upa) && stack.hasTagCompound())
            readFromNBTS(stack.getTagCompound());
    }
}
