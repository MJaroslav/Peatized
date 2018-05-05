package mjaroslav.mcmods.peatized.common.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mjaroslav.mcmods.peatized.common.init.PeatizedItems;
import mjaroslav.mcmods.peatized.common.item.ItemCleaver;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

public class EventHandlerWorld {
    @SubscribeEvent
    public void onBlockBreakEvent(BreakEvent event) {
        if (event.getPlayer() != null && event.getPlayer().capabilities.isCreativeMode
                && event.getPlayer().getHeldItem() != null
                && event.getPlayer().getHeldItem().getItem() instanceof ItemCleaver)
            event.setCanceled(true);
    }

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
    public void anvilUpdateEvent(AnvilUpdateEvent event) {
        if (event.left != null && event.right != null) {
            if (event.left.getItem() instanceof ItemCleaver && event.left.getItem() != PeatizedItems.cleaverRena) {
                if (event.right.getItem() == Items.spider_eye
                        && event.right.getDisplayName().toLowerCase().replace(" ", "").endsWith("meatball")) {
                    if (event.left.getDisplayName().toLowerCase().replace(" ", "").endsWith("forrena")) {
                        if (event.name.toLowerCase().replace(" ", "").equals("nologic")) {
                            event.output = new ItemStack(PeatizedItems.cleaverRena);
                            event.cost = 30;
                        }
                    }
                }
            }
        }
    }
}
