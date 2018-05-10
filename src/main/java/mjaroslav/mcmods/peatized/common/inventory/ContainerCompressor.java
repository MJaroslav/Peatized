package mjaroslav.mcmods.peatized.common.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mjaroslav.mcmods.peatized.common.item.crafting.CompressorRecipes;
import mjaroslav.mcmods.peatized.common.tileentity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
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

    public ContainerCompressor(InventoryPlayer player, TileBaseCompressor tile) {
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
        crafter.sendProgressBarUpdate(this, 0, this.tile.getCompressor().currentActivations);
        crafter.sendProgressBarUpdate(this, 1, this.tile.getCompressor().activations);
        if (tile instanceof TileRFCompressor
                && this.lastRf != ((TileRFCompressor) this.tile).getEnergyStored(ForgeDirection.UNKNOWN)) {
            crafter.sendProgressBarUpdate(this, 2,
                    ((TileRFCompressor) this.tile).getEnergyStored(ForgeDirection.UNKNOWN));
        }
        if (tile instanceof TileFuelCompressor) {
            crafter.sendProgressBarUpdate(this, 3, ((TileFuelCompressor) this.tile).burnTime);
            crafter.sendProgressBarUpdate(this, 4, ((TileFuelCompressor) this.tile).currentBurnTime);
        }
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);
            if (this.lastCurrentJumps != this.tile.getCompressor().currentActivations) {
                icrafting.sendProgressBarUpdate(this, 0, this.tile.getCompressor().currentActivations);
            }
            if (this.lastJumps != this.tile.getCompressor().activations) {
                icrafting.sendProgressBarUpdate(this, 1, this.tile.getCompressor().activations);
            }
            if (tile instanceof TileRFCompressor
                    && this.lastRf != ((TileRFCompressor) this.tile).getEnergyStored(ForgeDirection.UNKNOWN)) {
                icrafting.sendProgressBarUpdate(this, 2,
                        ((TileRFCompressor) this.tile).getEnergyStored(ForgeDirection.UNKNOWN));
            }
            if (tile instanceof TileFuelCompressor) {
                if (this.lastBurnTime != ((TileFuelCompressor) this.tile).burnTime)
                    icrafting.sendProgressBarUpdate(this, 3, ((TileFuelCompressor) this.tile).burnTime);
                if (this.lastCurrentBurnTime != ((TileFuelCompressor) this.tile).currentBurnTime)
                    icrafting.sendProgressBarUpdate(this, 4, ((TileFuelCompressor) this.tile).currentBurnTime);
            }
        }
        this.lastCurrentJumps = this.tile.getCompressor().currentActivations;
        this.lastJumps = this.tile.getCompressor().activations;
        if (tile instanceof TileRFCompressor)
            this.lastRf = ((TileRFCompressor) this.tile).getEnergyStored(ForgeDirection.UNKNOWN);
        if (tile instanceof TileFuelCompressor) {
            this.lastBurnTime = ((TileFuelCompressor) this.tile).burnTime;
            this.lastCurrentBurnTime = ((TileFuelCompressor) this.tile).currentBurnTime;
        }
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value) {
        switch (id) {
        case 0: {
            this.tile.getCompressor().currentActivations = value;
        }
            break;
        case 1: {
            this.tile.getCompressor().activations = value;
        }
            break;
        case 2: {
            ((TileRFCompressor) this.tile).setEnergyStored(value);
        }
            break;
        case 3: {
            ((TileFuelCompressor) this.tile).burnTime = value;
        }
            break;
        case 4: {
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
        if (tile instanceof TileFuelCompressor)
            return transferFuel(player, id);
        else
            return transferNoFuel(player, id);
    }

    public ItemStack transferNoFuel(EntityPlayer player, int id) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(id);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (id == 1) {
                if (!this.mergeItemStack(itemstack1, 2, 38, true)) {
                    return null;
                }
                slot.onSlotChange(itemstack1, itemstack);
            } else if (id != 0) {
                if (CompressorRecipes.compressing().getCompressingResult(itemstack1) != null) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        return null;
                    }
                } else if (id >= 2 && id < 29) {
                    if (!this.mergeItemStack(itemstack1, 29, 38, false)) {
                        return null;
                    }
                } else if (id >= 29 && id < 38 && !this.mergeItemStack(itemstack1, 2, 29, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 2, 38, false)) {
                return null;
            }
            if (itemstack1.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }
            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(player, itemstack1);
        }
        return itemstack;
    }

    public ItemStack transferFuel(EntityPlayer player, int id) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(id);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (id == 2) {
                if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
                    return null;
                }
                slot.onSlotChange(itemstack1, itemstack);
            } else if (id != 1 && id != 0) {
                if (CompressorRecipes.compressing().getCompressingResult(itemstack1) != null) {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
                        return null;
                    }
                } else if (TileEntityFurnace.isItemFuel(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        return null;
                    }
                } else if (id >= 3 && id < 30) {
                    if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
                        return null;
                    }
                } else if (id >= 30 && id < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
                return null;
            }
            if (itemstack1.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }
            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(player, itemstack1);
        }
        return itemstack;
    }
}
