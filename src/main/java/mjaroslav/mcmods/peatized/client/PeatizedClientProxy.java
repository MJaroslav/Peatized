package mjaroslav.mcmods.peatized.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;
import mjaroslav.mcmods.peatized.PeatizedMod;
import mjaroslav.mcmods.peatized.client.render.item.ItemBlockCompressorRenderer;
import mjaroslav.mcmods.peatized.client.render.tileentity.TileCompressorRenderer;
import mjaroslav.mcmods.peatized.common.PeatizedCommonProxy;
import mjaroslav.mcmods.peatized.common.config.PeatizedConfig;
import mjaroslav.mcmods.peatized.common.init.PeatizedBlocks;
import mjaroslav.mcmods.peatized.common.tileentity.TileCompressor;
import mjaroslav.mcmods.peatized.common.tileentity.TileRFCompressor;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;

public class PeatizedClientProxy extends PeatizedCommonProxy {
	@Override
	public EntityPlayer getEntityPlayer(MessageContext ctx) {
		return ctx.side == Side.CLIENT ? Minecraft.getMinecraft().thePlayer : super.getEntityPlayer(ctx);
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
	}

	@Override
	public void init(FMLInitializationEvent event) {
		ClientRegistry.bindTileEntitySpecialRenderer(TileCompressor.class,
				new TileCompressorRenderer(PeatizedMod.MODID, "compressor"));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(PeatizedBlocks.compressor),
				new ItemBlockCompressorRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileRFCompressor.class,
				new TileCompressorRenderer(PeatizedMod.MODID, "compressor_rf"));
		VillagerRegistry.instance().registerVillagerSkin(PeatizedConfig.villagerId,
				new ResourceLocation(PeatizedMod.MODID + ":textures/entity/villager/peatman.png"));
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
	}
}
