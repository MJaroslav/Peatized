package mjaroslav.mcmods.peatized.common.event;

import com.mojang.realmsclient.gui.ChatFormatting;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.oredict.OreDictionary;

public class EventHandlerWorld {
	@SubscribeEvent
	public void harvestDropsEvent(BlockEvent.HarvestDropsEvent event) {
		if (event.block == Blocks.double_stone_slab && event.blockMetadata == 8) {
			ItemStack drop;
			int id = 0;
			do {
				drop = event.drops.get(id);
				if (drop != null && drop.getItem() == Item.getItemFromBlock(Blocks.stone_slab)
						&& drop.getItemDamage() == 0) {
					event.drops.remove(drop);
					id = 0;
				} else
					id++;
			} while (id < event.drops.size());
			event.drops.add(new ItemStack(Blocks.double_stone_slab, 1, 8));
		}
		if (event.block == Blocks.double_stone_slab && event.blockMetadata == 9) {
			ItemStack drop;
			int id = 0;
			do {
				drop = event.drops.get(id);
				if (drop != null && drop.getItem() == Item.getItemFromBlock(Blocks.stone_slab)
						&& drop.getItemDamage() == 1) {
					event.drops.remove(drop);
					id = 0;
				} else
					id++;
			} while (id < event.drops.size());
			event.drops.add(new ItemStack(Blocks.double_stone_slab, 1, 9));
		}
	}

	@SubscribeEvent
	public void itemTooltipEvent(ItemTooltipEvent event) {
		if (event.showAdvancedItemTooltips && event.itemStack != null && event.itemStack.getItem() != null) {
			boolean flag = false;
			for (int id : OreDictionary.getOreIDs(event.itemStack)) {
				if (!flag) {
					event.toolTip.add("");
					flag = true;
				}
				event.toolTip.add(ChatFormatting.DARK_GRAY.toString() + ChatFormatting.ITALIC.toString()
						+ OreDictionary.getOreName(id));
			}
		}
	}
}
