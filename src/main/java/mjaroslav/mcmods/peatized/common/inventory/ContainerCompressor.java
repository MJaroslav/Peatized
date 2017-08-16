package mjaroslav.mcmods.peatized.common.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mjaroslav.mcmods.peatized.common.item.crafting.CompressorRecipes;
import mjaroslav.mcmods.peatized.common.tileentity.ICompressor;
import mjaroslav.mcmods.peatized.common.tileentity.TileCompressor;
import mjaroslav.mcmods.peatized.common.tileentity.TileFuelCompressor;
import mjaroslav.mcmods.peatized.common.tileentity.TileRFCompressor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.util.ForgeDirection;

public class ContainerCompressor extends Container {
	private ICompressor tile;
	private int lastCurrentJumps;
	private int lastJumps;
	private int lastRf;
	private int lastBurnTime;
	private int lastCurrentBurnTime;

	public ContainerCompressor(InventoryPlayer player, TileCompressor tile) {
		this(player, (IInventory) tile);
		this.tile = tile;
	}

	public ContainerCompressor(InventoryPlayer player, TileRFCompressor tile) {
		this(player, (IInventory) tile);
		this.tile = tile;
	}

	public ContainerCompressor(InventoryPlayer player, TileFuelCompressor tile) {
		this(player, (IInventory) tile);
		this.tile = tile;
	}

	private ContainerCompressor(InventoryPlayer player, IInventory inventory) {
		int i = 0;
		if (inventory instanceof TileFuelCompressor) {
			this.addSlotToContainer(new Slot(inventory, i, 8, 38));
			i++;
		}
		this.addSlotToContainer(new Slot(inventory, i, 44, 21));
		i++;
		this.addSlotToContainer(new SlotCompressor(player.player, inventory, i, 44, 47));
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
		if (this.tile instanceof TileRFCompressor
				&& this.lastRf != ((TileRFCompressor) this.tile).getEnergyStored(ForgeDirection.UNKNOWN)) {
			crafter.sendProgressBarUpdate(this, 2,
					((TileRFCompressor) this.tile).getEnergyStored(ForgeDirection.UNKNOWN));
		}
		if (this.tile instanceof TileFuelCompressor) {
			crafter.sendProgressBarUpdate(this, 3, ((TileFuelCompressor) this.tile).burnTime);
			crafter.sendProgressBarUpdate(this, 4, ((TileFuelCompressor) this.tile).currentBurnTime);
		}
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
			if (this.tile instanceof TileRFCompressor
					&& this.lastRf != ((TileRFCompressor) this.tile).getEnergyStored(ForgeDirection.UNKNOWN)) {
				icrafting.sendProgressBarUpdate(this, 2,
						((TileRFCompressor) this.tile).getEnergyStored(ForgeDirection.UNKNOWN));
			}
			if (this.tile instanceof TileFuelCompressor) {
				if (this.lastBurnTime != ((TileFuelCompressor) this.tile).burnTime)
					icrafting.sendProgressBarUpdate(this, 3, ((TileFuelCompressor) this.tile).burnTime);
				if (this.lastCurrentBurnTime != ((TileFuelCompressor) this.tile).currentBurnTime)
					icrafting.sendProgressBarUpdate(this, 4, ((TileFuelCompressor) this.tile).currentBurnTime);
			}
		}
		this.lastCurrentJumps = this.tile.getCompressor().currentJumps;
		this.lastJumps = this.tile.getCompressor().jumps;
		if (tile instanceof TileRFCompressor)
			this.lastRf = ((TileRFCompressor) this.tile).getEnergyStored(ForgeDirection.UNKNOWN);
		if (this.tile instanceof TileFuelCompressor) {
			this.lastBurnTime = ((TileFuelCompressor) this.tile).burnTime;
			this.lastCurrentBurnTime = ((TileFuelCompressor) this.tile).currentBurnTime;
		}
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
		case 3: {
			if (this.tile instanceof TileFuelCompressor)
				((TileFuelCompressor) this.tile).burnTime = value;
		}
			break;
		case 4: {
			if (this.tile instanceof TileFuelCompressor)
				((TileFuelCompressor) this.tile).currentBurnTime = value;
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
		int a = 0;
		boolean lit = false;
		if (this.tile instanceof TileFuelCompressor) {
			a = 1;
			lit = true;
		}
		ItemStack itemStack = null;
		Slot slot = (Slot) this.inventorySlots.get(id);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemStack1 = slot.getStack();
			itemStack = itemStack1.copy();
			if (id == 2) {
				if (!this.mergeItemStack(itemStack1, 3, 39, true)) {
					return null;
				}
				slot.onSlotChange(itemStack1, itemStack);
			} else if (id != 1 && id != 0) {
				if (CompressorRecipes.compressing().getCompressingResult(itemStack1) != null) {
					if (!this.mergeItemStack(itemStack1, 0 + a, 1 + a, false)) {
						return null;
					}
				} else if (lit && TileEntityFurnace.isItemFuel(itemStack1)) {
					if (!this.mergeItemStack(itemStack1, 0, 1, false)) {
						return null;
					}
				} else if (id >= 2 + a && id < 29 + a) {
					if (!this.mergeItemStack(itemStack1, 29 + a, 38 + a, false)) {
						return null;
					}
				} else if (id >= 29 + a && id < 38 + a && !this.mergeItemStack(itemStack1, 2 + a, 29 + a, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemStack1, 2 + a, 38 + a, false)) {
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