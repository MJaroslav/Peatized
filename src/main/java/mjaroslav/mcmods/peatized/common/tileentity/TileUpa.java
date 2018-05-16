package mjaroslav.mcmods.peatized.common.tileentity;

import org.apache.commons.lang3.StringUtils;

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
    private String name;
    private String author;
    private boolean hasTooltip;
    private String rarity;

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
        if (!StringUtils.isEmpty(author))
            nbt.setString(NBTInfo.AUTHOR, author);
        if (!StringUtils.isEmpty(name))
            nbt.setString(NBTInfo.NAME, name);
        if (!StringUtils.isEmpty(rarity))
            nbt.setString(NBTInfo.RARITY, rarity);
        nbt.setBoolean(NBTInfo.TOOLTIP_HAS, hasTooltip);
    }

    public void readFromNBTS(NBTTagCompound nbt) {
        hasTooltip = nbt.getBoolean(NBTInfo.TOOLTIP_HAS);
        if (nbt.hasKey(NBTInfo.AUTHOR))
            author = nbt.getString(NBTInfo.AUTHOR);
        if (nbt.hasKey(NBTInfo.NAME))
            name = nbt.getString(NBTInfo.NAME);
        if (nbt.hasKey(NBTInfo.RARITY))
            rarity = nbt.getString(NBTInfo.RARITY);
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

    public String getName() {
        return name;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public String getRarity() {
        return rarity;
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
