package mjaroslav.mcmods.peatized.common.registry;

import mjaroslav.mcmods.peatized.common.block.BlockBogDirt;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PeatizedBlocks {
    public static Block BOG_DIRT = new BlockBogDirt();

    public static void register() {
        setRegister(BOG_DIRT);
    }

    @SideOnly(Side.CLIENT)
    public static void registerRender() {
        setRender(BOG_DIRT);
    }

    private static void setRegister(Block block) {
        ForgeRegistries.BLOCKS.register(block);
        //noinspection ConstantConditions
        ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }

    @SideOnly(Side.CLIENT)
    private static void setRender(Block block) {
        //noinspection ConstantConditions
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0,
                new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }
}
