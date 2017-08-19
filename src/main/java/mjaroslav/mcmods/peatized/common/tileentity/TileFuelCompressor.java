package mjaroslav.mcmods.peatized.common.tileentity;

import org.apache.commons.lang3.StringUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mjaroslav.mcmods.peatized.common.block.BlockCompressor;
import mjaroslav.mcmods.peatized.common.init.PeatizedBlocks;
import mjaroslav.mcmods.peatized.common.item.crafting.CompressorRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileFuelCompressor extends TileEntity implements ICompressor, ISidedInventory {
	private static final int[] slotsTop = new int[] { 0 };
	private static final int[] slotsBottom = new int[] { 2 };
	private static final int[] slotsSides = new int[] { 1 };
	public String customName;
	public int cooldown;
	public BaseCompressor compressor = new BaseCompressor(true);
	public ItemStack[] fuelSlot = new ItemStack[1];
	public int burnTime;
	public int currentBurnTime;

	public TileFuelCompressor() {
	}

	public boolean blockIsLit() {
		return getBlockType() == PeatizedBlocks.compressorLit && getBlockMetadata() == 2;
	}

	@Override
	public void updateEntity() {
		boolean flag1 = false;
		if (!worldObj.isRemote) {
			boolean flag = this.currentBurnTime > 0;
			if (this.currentBurnTime > 0)
				this.currentBurnTime--;
			if (this.cooldown > 0)
				this.cooldown--;
			if (!this.isBurning() && !this.compressor.isWorking() && !this.compressor.canWork()) {
				this.compressor.jumps = 0;
				this.compressor.currentJumps = 0;
				flag1 = true;
			}
			if (this.cooldown == 1)
				this.worldObj.playSoundEffect((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D,
						(double) this.zCoord + 0.5D, "tile.piston.in", 0.5F,
						this.worldObj.rand.nextFloat() * 0.15F + 0.6F);
			if (this.cooldown == 11)
				this.worldObj.playSoundEffect((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D,
						(double) this.zCoord + 0.5D, "tile.piston.out", 0.5F,
						this.worldObj.rand.nextFloat() * 0.25F + 0.6F);
			if (this.cooldown <= 0) {
				if (this.isBurning()) {
					if (this.compressor.isWorking() && this.compressor.canWork()) {
						this.compressor.currentJumps++;
						this.cooldown = 20;
						if (this.compressor.currentJumps >= this.compressor.jumps) {
							this.compressor.jumps = 0;
							this.compressor.currentJumps = 0;
							this.compressor.work();
							flag1 = true;
						}
					} else if (this.compressor.canWork()) {
						this.compressor.jumps = CompressorRecipes.compressing().getJumps(this.compressor.inventory[0]);
						if (this.compressor.jumps == 1) {
							this.compressor.jumps = 0;
							this.compressor.currentJumps = 0;
							this.compressor.work();
							this.cooldown = 20;
							flag1 = true;
						} else {
							this.compressor.currentJumps = 1;
							this.cooldown = 20;
							flag1 = true;
						}
					} else {
						this.compressor.jumps = 0;
						this.compressor.currentJumps = 0;
						flag1 = true;
					}
				} else {
					if (this.fuelSlot[0] != null && TileEntityFurnace.isItemFuel(this.fuelSlot[0])
							&& this.compressor.canWork()) {
						if (!this.compressor.isWorking()) {
							this.currentBurnTime = this.burnTime = TileEntityFurnace.getItemBurnTime(this.fuelSlot[0]);
							if (this.currentBurnTime > 0) {
								if (this.fuelSlot[0] != null) {
									if (this.fuelSlot[0].stackSize > 0)
										this.fuelSlot[0].stackSize--;
									if (this.fuelSlot[0].stackSize <= 0)
										this.fuelSlot[0] = this.fuelSlot[0].getItem()
												.getContainerItem(this.fuelSlot[0]);
									flag1 = true;
								}
								this.compressor.jumps = CompressorRecipes.compressing()
										.getJumps(this.compressor.inventory[0]);
								if (this.compressor.jumps == 1) {
									this.compressor.jumps = 0;
									this.compressor.currentJumps = 0;
									this.compressor.work();
									this.cooldown = 20;
									flag1 = true;
								} else {
									this.compressor.currentJumps = 1;
									this.cooldown = 20;
									flag1 = true;
								}
							}
						} else if(!this.isBurning()){
							this.currentBurnTime = this.burnTime = TileEntityFurnace.getItemBurnTime(this.fuelSlot[0]);
							if (this.currentBurnTime > 0) {
								if (this.fuelSlot[0] != null) {
									if (this.fuelSlot[0].stackSize > 0)
										this.fuelSlot[0].stackSize--;
									if (this.fuelSlot[0].stackSize <= 0)
										this.fuelSlot[0] = this.fuelSlot[0].getItem()
												.getContainerItem(this.fuelSlot[0]);
									flag1 = true;
								}
							}
						}
					}
				}
			}
			if (flag) {
				if (flag != this.currentBurnTime > 0)
					flag = true;
				BlockCompressor.updateCompressorBlockState(this.getBlockMetadata() == 2, this.currentBurnTime > 0,
						worldObj, xCoord, yCoord, zCoord);
			}
		}
		if (flag1)
			this.markDirty();
	}

	public boolean hasFuel() {
		return isBurning() || TileEntityFurnace.isItemFuel(this.fuelSlot[0]);
	}

	public boolean isBurning() {
		return this.currentBurnTime > 0;
	}

	@SideOnly(Side.CLIENT)
	public int getScaleForProgressTime(int size) {
		return this.compressor.currentJumps * size / (this.compressor.jumps == 0 ? 1 : this.compressor.jumps);
	}

	@SideOnly(Side.CLIENT)
	public int getScaleForStoredFuel(int size) {
		if (this.burnTime == 0) {
			this.burnTime = 200;
		}
		return this.currentBurnTime * size / this.burnTime;
	}

	@Override
	public int getSizeInventory() {
		return this.compressor.getSizeInventory() + 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if (slot == 0)
			return this.fuelSlot[0];
		else
			return this.compressor.getStackInSlot(slot - 1);
	}

	@Override
	public ItemStack decrStackSize(int slot, int count) {
		if (slot == 0)
			if (this.fuelSlot[0] != null) {
				ItemStack itemStack;
				if (this.fuelSlot[0].stackSize <= count) {
					itemStack = this.fuelSlot[0];
					this.fuelSlot[0] = null;
					return itemStack;
				} else {
					itemStack = this.fuelSlot[0].splitStack(count);
					if (this.fuelSlot[0].stackSize == 0) {
						this.fuelSlot[0] = null;
					}
					return itemStack;
				}
			} else {
				return null;
			}
		else
			return this.compressor.decrStackSize(slot - 1, count);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (slot == 0)
			if (this.fuelSlot[0] != null) {
				ItemStack itemStack = this.fuelSlot[0];
				this.fuelSlot[0] = null;
				return itemStack;
			} else {
				return null;
			}
		else
			return this.compressor.getStackInSlotOnClosing(slot - 1);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemStack) {
		if (slot == 0) {
			this.fuelSlot[0] = itemStack;
			if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
				itemStack.stackSize = this.getInventoryStackLimit();
			}
		} else
			this.compressor.setInventorySlotContents(slot - 1, itemStack);
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.compressor.fuel";
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
		this.cooldown = nbt.getShort("Cooldown");
		this.burnTime = nbt.getShort("BurnTime");
		this.currentBurnTime = nbt.getShort("CurrentBurnTime");
		NBTTagList nbttaglist = nbt.getTagList("FurnaceItems", 10);
		this.fuelSlot = new ItemStack[1];
		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbt1 = nbttaglist.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("Slot");
			if (b0 >= 0 && b0 < this.fuelSlot.length) {
				this.fuelSlot[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
		if (nbt.hasKey("CustomName", 8)) {
			this.customName = nbt.getString("CustomName");
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		writeToNBTS(nbt);
	}

	public void writeToNBTS(NBTTagCompound nbt) {
		nbt.setShort("Cooldown", (short) this.cooldown);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < this.fuelSlot.length; ++i) {
			if (this.fuelSlot[i] != null) {
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("Slot", (byte) i);
				this.fuelSlot[i].writeToNBT(nbt1);
				nbttaglist.appendTag(nbt1);
			}
		}
		nbt.setTag("FurnaceItems", nbttaglist);
		nbt.setShort("Cooldown", (short) this.cooldown);
		nbt.setShort("BurnTime", (short) this.burnTime);
		nbt.setShort("CurrentBurnTime", (short) this.currentBurnTime);
		this.compressor.writeToNBT(nbt);
		if (this.hasCustomInventoryName()) {
			nbt.setString("CustomName", this.customName);
		}
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
		return (slot == 0 && TileEntityFurnace.isItemFuel(itemStack)) || (slot == 1 && itemStack != null
				&& CompressorRecipes.compressing().getCompressingResult(itemStack) != null);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 0 ? slotsBottom : (side == 1 ? slotsTop : slotsSides);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemStack, int side) {
		return this.isItemValidForSlot(slot, itemStack) && (side == 1 || side != 0);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
		return side == 0 && slot == 2 || (itemStack != null && itemStack.getItem() == Items.bucket);
	}

	@Override
	public BaseCompressor getCompressor() {
		return compressor;
	}
}
