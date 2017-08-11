package mjaroslav.mcmods.peatized.common.gameevent;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemCompressedEvent extends Event {
	public final EntityPlayer player;
	public final ItemStack compressing;

	public ItemCompressedEvent(EntityPlayer player, ItemStack crafting) {
		this.player = player;
		this.compressing = crafting;
	}
}
