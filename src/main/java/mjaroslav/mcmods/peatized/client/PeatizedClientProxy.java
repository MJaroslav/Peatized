package mjaroslav.mcmods.peatized.client;

import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;
import mjaroslav.mcmods.peatized.client.render.item.*;
import mjaroslav.mcmods.peatized.client.render.tileentity.*;
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
        VillagerRegistry.instance().registerVillagerSkin(CategoryGeneralInfo.villagerId,
                new ResourceLocation(ModInfo.MODID, "textures/entity/villager/peatman.png"));
        rendererItem(PeatizedItems.cleaverIron, new ItemCleaverRenderer());
        rendererItem(PeatizedItems.cleaverDiamond, new ItemCleaverRenderer());
        rendererItem(PeatizedItems.cleaverGold, new ItemCleaverRenderer());
        rendererItem(PeatizedItems.cleaverRena, new ItemCleaverRenderer());
        rendererItem(PeatizedItems.cleaverBronze, new ItemCleaverRenderer());
        MinecraftForge.EVENT_BUS.register(new EventHandlerRender());
        rendererTileEntity(TileUpa.class, new TileUpaRenderer());
        rendererTileEntity(TileBaseCompressor.class, new TileBaseCompressorRenderer());
        rendererTileEntity(TileFuelCompressor.class, new TileFuelCompressorRenderer());
        rendererTileEntity(TileRFCompressor.class, new TileRFCompressorRenderer());
        rendererItem(Item.getItemFromBlock(PeatizedBlocks.baseCompressor), new ItemBlockCompressorRenderer());
        rendererItem(Item.getItemFromBlock(PeatizedBlocks.fuelCompressor), new ItemBlockCompressorRenderer());
        rendererItem(Item.getItemFromBlock(PeatizedBlocks.rfCompressor), new ItemBlockCompressorRenderer());
        rendererItem(Item.getItemFromBlock(PeatizedBlocks.upa), new ItemBlockUpaRenderer());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
    }

    @Override
    public void spawnParticle(String name, double x, double y, double z, Object... args) {
    }
}
