package mjaroslav.mcmods.peatized.client;

import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;
import mjaroslav.mcmods.peatized.client.render.item.ItemBlockCompressorRenderer;
import mjaroslav.mcmods.peatized.client.render.item.ItemCleaverRenderer;
import mjaroslav.mcmods.peatized.client.render.tileentity.TileCompressorRenderer;
import mjaroslav.mcmods.peatized.common.PeatizedCommonProxy;
import mjaroslav.mcmods.peatized.common.event.EventHandlerRender;
import mjaroslav.mcmods.peatized.common.init.PeatizedBlocks;
import mjaroslav.mcmods.peatized.common.init.PeatizedItems;
import mjaroslav.mcmods.peatized.common.tileentity.*;
import mjaroslav.mcmods.peatized.lib.CategoryGeneralInfo;
import mjaroslav.mcmods.peatized.lib.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

public class PeatizedClientProxy extends PeatizedCommonProxy {
    @Override
    public Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }

    @Override
    public EntityPlayer getEntityPlayer(MessageContext ctx) {
        return ctx.side == Side.CLIENT ? Minecraft.getMinecraft().thePlayer : super.getEntityPlayer(ctx);
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
    }

    @Override
    public void init(FMLInitializationEvent event) {
        rendererTileEntity(TileCompressor.class, new TileCompressorRenderer(ModInfo.MODID, "compressor", false));
        rendererItem(Item.getItemFromBlock(PeatizedBlocks.compressor), new ItemBlockCompressorRenderer());
        rendererTileEntity(TileRFCompressor.class, new TileCompressorRenderer(ModInfo.MODID, "compressor_rf", false));
        rendererTileEntity(TileFuelCompressor.class,
                new TileCompressorRenderer(ModInfo.MODID, "compressor_fuel", true));
        VillagerRegistry.instance().registerVillagerSkin(CategoryGeneralInfo.villagerId,
                new ResourceLocation(ModInfo.MODID, "textures/entity/villager/peatman.png"));
        rendererItem(PeatizedItems.cleaverIron, new ItemCleaverRenderer());
        rendererItem(PeatizedItems.cleaverDiamond, new ItemCleaverRenderer());
        rendererItem(PeatizedItems.cleaverGold, new ItemCleaverRenderer());
        rendererItem(PeatizedItems.cleaverRena, new ItemCleaverRenderer());
        rendererItem(PeatizedItems.cleaverBronze, new ItemCleaverRenderer());
        MinecraftForge.EVENT_BUS.register(new EventHandlerRender());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
    }

    @Override
    public void spawnParticle(String name, double x, double y, double z, Object... args) {
    }
}
