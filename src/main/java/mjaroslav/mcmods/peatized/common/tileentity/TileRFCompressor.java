package mjaroslav.mcmods.peatized.common.tileentity;

import org.apache.commons.lang3.StringUtils;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mjaroslav.mcmods.peatized.common.item.crafting.CompressorRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileRFCompressor extends TileEntity implements ISidedInventory, IEnergyHandler, ICompressor {
	private static final int[] slotsTop = new int[] { 0 };
	private static final int[] slotsBottom = new int[] { 1 };
	private static final int[] slotsSides = new int[] {};
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
		return (from.equals(ForgeDirection.DOWN) || from.equals(ForgeDirection.UP)) ? false : true;
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
			boolean flag = false;
			if (this.storage.getEnergyStored() >= rfToJumpRate) {
				if (this.compressor.currentJumps == 0 && this.compressor.canWork()) {
					this.compressor.jumps = CompressorRecipes.compressing().getJumps(this.compressor.inventory[0]);
					if (this.cooldown <= 0)
						if (this.compressor.jumps == 1) {
							this.compressor.currentJumps = 0;
							this.compressor.jumps = 0;
							this.storage.setEnergyStored(this.storage.getEnergyStored() - rfToJumpRate);
							this.compressor.work();
							flag = true;
							this.cooldown = 20;
						} else {
							this.compressor.currentJumps++;
							this.storage.setEnergyStored(this.storage.getEnergyStored() - rfToJumpRate);
							this.cooldown = 20;
						}
				} else if (this.compressor.isWorking() && this.compressor.canWork()) {
					if (this.cooldown <= 0) {
						this.cooldown = 20;
						this.storage.setEnergyStored(this.storage.getEnergyStored() - rfToJumpRate);
						this.compressor.currentJumps++;
						if (this.compressor.currentJumps >= this.compressor.jumps) {
							this.compressor.currentJumps = 0;
							this.compressor.jumps = 0;
							this.compressor.work();
							flag = true;
						}
					}
				} else {
					this.compressor.currentJumps = 0;
					this.compressor.jumps = 0;
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
			if (!this.compressor.canWork() && (this.compressor.currentJumps > 0 || this.compressor.jumps > 0)) {
				this.compressor.currentJumps = 0;
				this.compressor.jumps = 0;
				flag = true;
			}
			if (this.compressor.canWork()) {
				this.compressor.jumps = CompressorRecipes.compressing().getJumps(this.compressor.inventory[0]);
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
		return this.compressor.currentJumps * size / (this.compressor.jumps == 0 ? 1 : this.compressor.jumps);
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
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		this.compressor.readFromNBT(nbt);
		this.cooldown = nbt.getShort("Cooldown");
		if (nbt.hasKey("CustomName", 8)) {
			this.customName = nbt.getString("CustomName");
		}
		this.storage.readFromNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		writeToNBTS(nbt);
	}

	public void writeToNBTS(NBTTagCompound nbt) {
		nbt.setShort("Cooldown", (short) this.cooldown);
		this.compressor.writeToNBT(nbt);
		if (this.hasCustomInventoryName()) {
			nbt.setString("CustomName", this.customName);
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
		return side == 0 ? slotsBottom : (side == 1 ? slotsTop : slotsSides);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemStack, int side) {
		return this.isItemValidForSlot(slot, itemStack) && side == 1;
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
