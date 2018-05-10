package mjaroslav.mcmods.peatized.common.tileentity;

import org.apache.commons.lang3.StringUtils;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mjaroslav.mcmods.mjutils.lib.utils.UtilsGame;
import mjaroslav.mcmods.peatized.common.item.crafting.CompressorRecipes;
import mjaroslav.mcmods.peatized.lib.NBTInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileRFCompressor extends TileEntity implements ISidedInventory, IEnergyHandler, ICompressor {
    private static final int[] slotsLeft = new int[] { 0 };
    private static final int[] slotsBottom = new int[] { 1 };
    public static final int rfToJumpRate = 20;
    public static final int rfRecive = rfToJumpRate * 4;
    public static final int rfMax = rfToJumpRate * 1000;
    private EnergyStorage storage = new EnergyStorage(10000, 1000, 0);

    public String customName;
    public int cooldown;
    public BaseCompressor compressor = new BaseCompressor(true);

    public TileRFCompressor() {
    }

    public boolean hasEnergy() {
        return this.storage.getEnergyStored() >= rfToJumpRate;
    }

    public void setEnergyStored(int value) {
        this.storage.setEnergyStored(value);
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        return canConnectEnergy(from) ? this.storage.extractEnergy(maxExtract, simulate) : 0;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return from == ForgeDirection.UNKNOWN || UtilsGame.getSideFromMeta(getBlockMetadata(), from.ordinal()) == 3;
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        return canConnectEnergy(from) ? this.storage.receiveEnergy(maxReceive, simulate) : 0;
    }

    @Override
    public int getEnergyStored(ForgeDirection from) {
        return canConnectEnergy(from) ? this.storage.getEnergyStored() : 0;
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return canConnectEnergy(from) ? this.storage.getMaxEnergyStored() : 0;
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            if (this.storage.getEnergyStored() >= rfToJumpRate) {
                if (this.compressor.currentActivations == 0 && this.compressor.canWork()) {
                    this.compressor.activations = CompressorRecipes.compressing()
                            .getJumps(this.compressor.inventory[0]);
                    if (this.cooldown <= 0)
                        if (this.compressor.activations == 1) {
                            this.compressor.currentActivations = 0;
                            this.compressor.activations = 0;
                            this.storage.setEnergyStored(this.storage.getEnergyStored() - rfToJumpRate);
                            this.compressor.work();
                            this.cooldown = 20;
                        } else {
                            this.compressor.currentActivations++;
                            this.storage.setEnergyStored(this.storage.getEnergyStored() - rfToJumpRate);
                            this.cooldown = 20;
                        }
                } else if (this.compressor.isWorking() && this.compressor.canWork()) {
                    if (this.cooldown <= 0) {
                        this.cooldown = 20;
                        this.storage.setEnergyStored(this.storage.getEnergyStored() - rfToJumpRate);
                        this.compressor.currentActivations++;
                        if (this.compressor.currentActivations >= this.compressor.activations) {
                            this.compressor.currentActivations = 0;
                            this.compressor.activations = 0;
                            this.compressor.work();
                        }
                    }
                } else {
                    this.compressor.currentActivations = 0;
                    this.compressor.activations = 0;
                }
            }
            if (this.cooldown == 1)
                this.worldObj.playSoundEffect((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D,
                        (double) this.zCoord + 0.5D, "tile.piston.in", 0.5F,
                        this.worldObj.rand.nextFloat() * 0.15F + 0.6F);
            if (this.cooldown == 11)
                this.worldObj.playSoundEffect((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D,
                        (double) this.zCoord + 0.5D, "tile.piston.out", 0.5F,
                        this.worldObj.rand.nextFloat() * 0.25F + 0.6F);
            if (this.cooldown > 0)
                this.cooldown--;
            if (!this.compressor.canWork()
                    && (this.compressor.currentActivations > 0 || this.compressor.activations > 0)) {
                this.compressor.currentActivations = 0;
                this.compressor.activations = 0;
            }
            if (this.compressor.canWork()) {
                this.compressor.activations = CompressorRecipes.compressing().getJumps(this.compressor.inventory[0]);
            }
        }
        this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        this.markDirty();
    }

    @SideOnly(Side.CLIENT)
    public int getScaleForProgressTime(int size) {
        return this.compressor.currentActivations * size
                / (this.compressor.activations == 0 ? 1 : this.compressor.activations);
    }

    @SideOnly(Side.CLIENT)
    public int getScaleForStoredEnergy(int size) {
        return this.storage.getEnergyStored() * size
                / (this.storage.getMaxEnergyStored() == 0 ? 1 : this.storage.getMaxEnergyStored());
    }

    @Override
    public int getSizeInventory() {
        return this.compressor.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.compressor.getStackInSlot(slot);
    }

    @Override
    public ItemStack decrStackSize(int slot, int count) {
        return this.compressor.decrStackSize(slot, count);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return this.compressor.getStackInSlotOnClosing(slot);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack) {
        this.compressor.setInventorySlotContents(slot, itemStack);
    }

    @Override
    public String getInventoryName() {
        return this.hasCustomInventoryName() ? this.customName : "container.compressor.rf";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return !StringUtils.isEmpty(this.customName);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        readFromNBTS(nbt);
    }

    public void readFromNBTS(NBTTagCompound nbt) {
        this.compressor.readFromNBT(nbt);
        this.cooldown = nbt.getShort(NBTInfo.COOLDOWN);
        if (nbt.hasKey(NBTInfo.CUSTOM_NAME, 8))
            this.customName = nbt.getString(NBTInfo.CUSTOM_NAME);
        this.storage.readFromNBT(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        writeToNBTS(nbt);
    }

    public void writeToNBTS(NBTTagCompound nbt) {
        nbt.setShort(NBTInfo.COOLDOWN, (short) this.cooldown);
        this.compressor.writeToNBT(nbt);
        if (this.hasCustomInventoryName()) {
            nbt.setString(NBTInfo.CUSTOM_NAME, this.customName);
        }
        this.storage.writeToNBT(nbt);
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

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false
                : player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D,
                        (double) this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
        return slot == 0 && itemStack != null
                && CompressorRecipes.compressing().getCompressingResult(itemStack) != null;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return side == 0 ? slotsBottom
                : (UtilsGame.getSideFromMeta(getBlockMetadata(), side) == 4 ? new int[] { 0 } : new int[] {});
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack itemStack, int side) {
        return this.isItemValidForSlot(slot, itemStack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
        return side == 0 && slot == 1;
    }

    @Override
    public BaseCompressor getCompressor() {
        return compressor;
    }
}
