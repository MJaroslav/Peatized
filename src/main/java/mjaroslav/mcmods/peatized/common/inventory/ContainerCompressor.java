package mjaroslav.mcmods.peatized.common.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mjaroslav.mcmods.peatized.common.item.crafting.CompressorRecipes;
import mjaroslav.mcmods.peatized.common.tileentity.ICompressor;
import mjaroslav.mcmods.peatized.common.tileentity.TileCompressor;
import mjaroslav.mcmods.peatized.common.tileentity.TileRFCompressor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public class ContainerCompressor extends Container {
	private ICompressor tile;
	private int lastCurrentJumps;
	private int lastJumps;
	private int lastRf;

	public ContainerCompressor(InventoryPlayer player, TileCompressor tile) {
		this(player, (IInventory) tile);
		this.tile = tile;
	}

	public ContainerCompressor(InventoryPlayer player, TileRFCompressor tile) {
		this(player, (IInventory) tile);
		this.tile = tile;
	}

	private ContainerCompressor(InventoryPlayer player, IInventory inventory) {
		this.addSlotToContainer(new Slot(inventory, 0, 44, 21));
		this.addSlotToContainer(new SlotCompressor(player.player, inventory, 1, 44, 47));
		int i;
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 142));
		}
	}

	@Override
	public void addCraftingToCrafters(ICrafting crafter) {
		super.addCraftingToCrafters(crafter);
		crafter.sendProgressBarUpdate(this, 0, this.tile.getCompressor().currentJumps);
		crafter.sendProgressBarUpdate(this, 1, this.tile.getCompressor().jumps);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int i = 0; i < this.crafters.size(); ++i) {
			ICrafting icrafting = (ICrafting) this.crafters.get(i);
			if (this.lastCurrentJumps != this.tile.getCompressor().currentJumps) {
				icrafting.sendProgressBarUpdate(this, 0, this.tile.getCompressor().currentJumps);
			}
			if (this.lastJumps != this.tile.getCompressor().jumps) {
				icrafting.sendProgressBarUpdate(this, 1, this.tile.getCompressor().jumps);
			}
			if (tile instanceof TileRFCompressor
					&& this.lastRf != ((TileRFCompressor) this.tile).getEnergyStored(ForgeDirection.UNKNOWN)) {
				icrafting.sendProgressBarUpdate(this, 2,
						((TileRFCompressor) this.tile).getEnergyStored(ForgeDirection.UNKNOWN));
			}
		}
		this.lastCurrentJumps = this.tile.getCompressor().currentJumps;
		this.lastJumps = this.tile.getCompressor().jumps;
		if (tile instanceof TileRFCompressor)
			this.lastRf = ((TileRFCompressor) this.tile).getEnergyStored(ForgeDirection.UNKNOWN);
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value) {
		switch (id) {
		case 0: {
			this.tile.getCompressor().currentJumps = value;
		}
			break;
		case 1: {
			this.tile.getCompressor().jumps = value;
		}
			break;
		case 2: {
			if (tile instanceof TileRFCompressor)
				((TileRFCompressor) this.tile).setEnergyStored(value);
		}
			break;
		default:
			break;
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return ((IInventory) this.tile).isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int id) {
		ItemStack itemStack = null;
		Slot slot = (Slot) this.inventorySlots.get(id);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemStack1 = slot.getStack();
			itemStack = itemStack1.copy();
			if (id == 1) {
				if (!this.mergeItemStack(itemStack1, 2, 38, true)) {
					return null;
				}
				slot.onSlotChange(itemStack1, itemStack);
			} else if (id != 0) {
				if (itemStack1 != null && itemStack.getItem() != null
						&& CompressorRecipes.compressing().getCompressingResult(itemStack1) != null) {
					if (!this.mergeItemStack(itemStack1, 0, 1, false)) {
						return null;
					}
				} else if (id >= 2 && id < 29) {
					if (!this.mergeItemStack(itemStack1, 29, 38, false)) {
						return null;
					}
				} else if (id >= 29 && id < 38 && !this.mergeItemStack(itemStack1, 2, 29, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemStack1, 2, 38, false)) {
				return null;
			}
			if (itemStack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
			if (itemStack1.stackSize == itemStack.stackSize) {
				return null;
			}
			slot.onPickupFromSlot(player, itemStack1);
		}
		return itemStack;
	}
}
