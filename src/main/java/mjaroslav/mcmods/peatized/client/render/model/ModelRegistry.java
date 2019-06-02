package mjaroslav.mcmods.peatized.client.render.model;

import mjaroslav.mcmods.peatized.common.block.BlockRegistry;
import mjaroslav.mcmods.peatized.common.item.ItemRegistry;
import mjaroslav.mcmods.peatized.common.lib.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = ModInfo.MOD_ID)
public class ModelRegistry {
    @SubscribeEvent
    public static void modelRegistryEvent(ModelRegistryEvent event) {
        registerBlockRenders();
        registerItemRenders();
    }

    private static void registerItemRenders() {
        ItemRegistry.forEach(item -> registerItem(item, item.getModelRegistryMeta()));
    }

    private static void registerBlockRenders() {
        BlockRegistry.forEach(block -> registerBlock(block, block.getModelRegistryMeta()));
    }

    private static void registerItem(Item item, int meta) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(),
                "inventory"));
    }

    private static void registerBlock(Block block, int meta) {
        registerItem(Item.getItemFromBlock(block), meta);
    }
}
