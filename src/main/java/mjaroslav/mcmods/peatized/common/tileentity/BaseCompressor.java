package mjaroslav.mcmods.peatized.common.tileentity;

import cofh.api.energy.ItemEnergyContainer;
import mjaroslav.mcmods.peatized.common.item.crafting.CompressorRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class BaseCompressor {
	public ItemStack[] inventory = new ItemStack[2];
	public int jumps = 0;
	public int currentJumps = 0;
	public boolean automatic;

	public BaseCompressor(boolean automatic) {
		this.automatic = automatic;
	}

	public int getSizeInventory() {
		return this.inventory.length;
	}

	public ItemStack getStackInSlot(int slot) {
		return this.inventory[slot];
	}

	public ItemStack decrStackSize(int slot, int count) {
		if (this.inventory[slot] != null) {
			ItemStack itemStack;
			if (this.inventory[slot].stackSize <= count) {
				itemStack = this.inventory[slot];
				this.inventory[slot] = null;
				return itemStack;
			} else {
				itemStack = this.inventory[slot].splitStack(count);
				if (this.inventory[slot].stackSize == 0) {
					this.inventory[slot] = null;
				}
				return itemStack;
			}
		} else {
			return null;
		}
	}

	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagList nbttaglist = nbt.getTagList("CompressorItems", 10);
		this.inventory = new ItemStack[this.getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbt1 = nbttaglist.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("Slot");
			if (b0 >= 0 && b0 < this.inventory.length) {
				this.inventory[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}

	public boolean isWorking() {
		return this.currentJumps > 0;
	}

	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < this.inventory.length; ++i) {
			if (this.inventory[i] != null) {
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("Slot", (byte) i);
				this.inventory[i].writeToNBT(nbt1);
				nbttaglist.appendTag(nbt1);
			}
		}
		nbt.setTag("CompressorItems", nbttaglist);
	}

	public ItemStack getStackInSlotOnClosing(int slot) {
		if (this.inventory[slot] != null) {
			ItemStack itemStack = this.inventory[slot];
			this.inventory[slot] = null;
			return itemStack;
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int slot, ItemStack itemStack) {
		this.inventory[slot] = itemStack;
		if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
			itemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
		return (slot == 0 && itemStack != null
				&& CompressorRecipes.compressing().getCompressingResult(itemStack) != null)
				|| (slot == 2 && itemStack != null && itemStack.getItem() != null
						&& itemStack.getItem() instanceof ItemEnergyContainer);
	}

	public boolean canWork() {
		if (this.inventory[0] == null) {
			return false;
		} else {
			ItemStack itemStack = CompressorRecipes.compressing().getCompressingResult(this.inventory[0]);
			if (itemStack == null)
				return false;
			if (this.inventory[0].stackSize < CompressorRecipes.compressing()
					.getStackSizeForCompressing(this.inventory[0]))
				return false;
			if (!this.automatic && CompressorRecipes.compressing().isAutomatic((this.inventory[0])))
				return false;
			if (this.inventory[1] == null)
				return true;
			if (!this.inventory[1].isItemEqual(itemStack))
				return false;
			int result = inventory[1].stackSize + itemStack.stackSize;
			return result <= getInventoryStackLimit() && result <= this.inventory[1].getMaxStackSize();
		}
	}

	public boolean inventoryFull() {
		if (this.inventory[0] == null) {
			if (this.inventory[1] == null) {
				return false;
			} else
				return this.inventory[1].stackSize >= this.inventory[1].getMaxStackSize();
		} else {
			ItemStack itemStack = CompressorRecipes.compressing().getCompressingResult(this.inventory[0]);
			if (itemStack == null)
				return false;
			if (this.inventory[1] == null)
				return false;
			if (!this.inventory[1].isItemEqual(itemStack))
				return true;
			int result = this.inventory[1].stackSize + itemStack.stackSize;
			return result >= getInventoryStackLimit() || result >= this.inventory[1].getMaxStackSize();
		}
	}

	public void work() {
		if (this.canWork()) {
			ItemStack itemStack = CompressorRecipes.compressing().getCompressingResult(this.inventory[0]);
			if (this.inventory[1] == null) {
				this.inventory[1] = itemStack.copy();
			} else if (this.inventory[1].getItem() == itemStack.getItem()) {
				this.inventory[1].stackSize += itemStack.stackSize;
			}
			this.inventory[0].stackSize -= CompressorRecipes.compressing()
					.getStackSizeForCompressing(this.inventory[0]);
			if (this.inventory[0].stackSize <= 0) {
				this.inventory[0] = null;
			}
		}
	}
}
