package mjaroslav.mcmods.peatized.common.inventory;

import cpw.mods.fml.common.FMLCommonHandler;
import mjaroslav.mcmods.peatized.common.gameevent.ItemCompressedEvent;
import mjaroslav.mcmods.peatized.common.item.crafting.CompressorRecipes;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class SlotCompressor extends Slot {
	private EntityPlayer player;
	private int count;

	public SlotCompressor(EntityPlayer player, IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
		this.player = player;
	}

	public boolean isItemValid(ItemStack itemStack) {
		return false;
	}

	public ItemStack decrStackSize(int count) {
		if (this.getHasStack()) {
			this.count += Math.min(count, this.getStack().stackSize);
		}
		return super.decrStackSize(count);
	}

	public void onPickupFromSlot(EntityPlayer player, ItemStack itemStack) {
		this.onCrafting(itemStack);
		super.onPickupFromSlot(player, itemStack);
	}

	protected void onCrafting(ItemStack itemStack, int count) {
		this.count += count;
		this.onCrafting(itemStack);
	}

	protected void onCrafting(ItemStack itemStack) {
		itemStack.onCrafting(this.player.worldObj, this.player, this.count);
		if (!this.player.worldObj.isRemote) {
			int count = this.count;
			float exp = CompressorRecipes.compressing().getExp(itemStack);
			int count1;
			if (exp == 0.0F) {
				count = 0;
			} else if (exp < 1.0F) {
				count1 = MathHelper.floor_float((float) count * exp);
				if (count1 < MathHelper.ceiling_float_int((float) count * exp)
						&& (float) Math.random() < (float) count * exp - (float) count1) {
					++count1;
				}
				count = count1;
			}
			while (count > 0) {
				count1 = EntityXPOrb.getXPSplit(count);
				count -= count1;
				this.player.worldObj.spawnEntityInWorld(new EntityXPOrb(this.player.worldObj, this.player.posX,
						this.player.posY + 0.5D, this.player.posZ + 0.5D, count1));
			}
		}
		this.count = 0;
		FMLCommonHandler.instance().bus().post(new ItemCompressedEvent(player, itemStack));
	}
}
