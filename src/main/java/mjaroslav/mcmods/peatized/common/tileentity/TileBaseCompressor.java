package mjaroslav.mcmods.peatized.common.tileentity;

import org.apache.commons.lang3.StringUtils;

import cofh.api.energy.ItemEnergyContainer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mjaroslav.mcmods.mjutils.lib.utils.UtilsGame;
import mjaroslav.mcmods.peatized.common.item.crafting.CompressorRecipes;
import mjaroslav.mcmods.peatized.lib.NBTInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileBaseCompressor extends TileEntity implements ISidedInventory, ICompressor {
    private static final int[] slotsTop = new int[] { 0 };
    private static final int[] slotsBottom = new int[] { 1 };
    private static final int[] slotsSides = new int[] { 0 };

    public String customName;
    public boolean hasPlate;
    public boolean platePressed;
    public BaseCompressor compressor = new BaseCompressor(false);

    public TileBaseCompressor() {
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            boolean flag = false;
            if (this.worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord) == Blocks.stone_pressure_plate) {
                if (!this.hasPlate) {
                    this.hasPlate = true;
                    flag = true;
                }
            } else {
                if (this.hasPlate) {
                    this.hasPlate = false;
                    flag = true;
                }
            }
            if (this.hasPlate) {
                boolean isPressed = false;
                if (this.worldObj.getBlockMetadata(this.xCoord, this.yCoord + 1, this.zCoord) > 0) {
                    if (!this.platePressed) {
                        this.platePressed = true;
                        flag = true;
                        isPressed = true;
                        this.worldObj.playSoundEffect((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D,
                                (double) this.zCoord + 0.5D, "tile.piston.out", 0.5F,
                                this.worldObj.rand.nextFloat() * 0.25F + 0.6F);
                    }
                } else {
                    if (this.platePressed) {
                        this.platePressed = false;
                        flag = true;
                        this.worldObj.playSoundEffect((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D,
                                (double) this.zCoord + 0.5D, "tile.piston.in", 0.5F,
                                this.worldObj.rand.nextFloat() * 0.15F + 0.6F);
                    }
                }
                if (isPressed) {
                    if (this.compressor.currentActivations == 0 && this.compressor.canWork()) {
                        this.compressor.activations = CompressorRecipes.compressing()
                                .getJumps(this.compressor.inventory[0]);
                        if (this.compressor.activations == 1) {
                            this.compressor.currentActivations = 0;
                            this.compressor.activations = 0;
                            this.compressor.work();
                            flag = true;
                        } else
                            this.compressor.currentActivations++;
                    } else if (this.compressor.isWorking() && this.compressor.canWork()) {
                        this.compressor.currentActivations++;
                        if (this.compressor.currentActivations >= this.compressor.activations) {
                            this.compressor.currentActivations = 0;
                            this.compressor.activations = 0;
                            this.compressor.work();
                            flag = true;
                        }
                    } else {
                        this.compressor.currentActivations = 0;
                        this.compressor.activations = 0;
                    }
                }

            }
            if (!this.compressor.canWork()
                    && (this.compressor.currentActivations > 0 || this.compressor.activations > 0)) {
                this.compressor.currentActivations = 0;
                this.compressor.activations = 0;
                flag = true;
            }
            if (this.compressor.canWork()) {
                this.compressor.activations = CompressorRecipes.compressing().getJumps(this.compressor.inventory[0]);
                flag = true;
            }
            if (flag) {
                this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                this.markDirty();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public int getScaleForProgressTime(int size) {
        return this.compressor.currentActivations * size
                / (this.compressor.activations == 0 ? 1 : this.compressor.activations);
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
        return this.hasCustomInventoryName() ? this.customName : "container.compressor";
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
        if (nbt.hasKey(NBTInfo.CUSTOM_NAME, 8)) {
            this.customName = nbt.getString(NBTInfo.CUSTOM_NAME);
        }
        this.hasPlate = nbt.getBoolean(NBTInfo.PLATE_EXIST);
        this.platePressed = nbt.getBoolean(NBTInfo.PLATE_PRESSED);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        writeToNBTS(nbt);
    }

    public void writeToNBTS(NBTTagCompound nbt) {
        this.compressor.writeToNBT(nbt);
        if (this.hasCustomInventoryName()) {
            nbt.setString(NBTInfo.CUSTOM_NAME, this.customName);
        }
        nbt.setBoolean(NBTInfo.PLATE_EXIST, this.hasPlate);
        nbt.setBoolean(NBTInfo.PLATE_PRESSED, this.platePressed);
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
        return (slot == 0 && itemStack != null
                && CompressorRecipes.compressing().getCompressingResult(itemStack) != null)
                || (slot == 2 && itemStack != null && itemStack.getItem() != null
                        && itemStack.getItem() instanceof ItemEnergyContainer);
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return side == 0 ? slotsBottom
                : (UtilsGame.getSideFromMeta(getBlockMetadata(), side) == 3 ? slotsSides : new int[] {});
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack itemStack, int side) {
        return this.isItemValidForSlot(slot, itemStack) && side != 0 && side != 1;
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
