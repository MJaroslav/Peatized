package mjaroslav.mcmods.peatized.client.render.item;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import mjaroslav.mcmods.peatized.PeatizedMod;
import mjaroslav.mcmods.peatized.client.render.tileentity.TileCompressorRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemBlockCompressorRenderer implements IItemRenderer {
	public static ResourceLocation getRes(int meta) {
		switch (meta) {
		default:
			return new ResourceLocation(PeatizedMod.MODID + ":textures/entity/compressor/compressor.png");
		case 1:
			return new ResourceLocation(PeatizedMod.MODID + ":textures/entity/compressor/compressor_rf.png");
		case 2:
			return new ResourceLocation(PeatizedMod.MODID + ":textures/entity/compressor/compressor_fuel.png");
		}
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();
		if (item != null)
			FMLClientHandler.instance().getClient().renderEngine.bindTexture(getRes(item.getItemDamage()));
		if (type == ItemRenderType.INVENTORY)
			GL11.glTranslated(0, 1, 0);
		else
			GL11.glTranslated(0.5, 1.5, 0.5);
		GL11.glRotated(180, 0, 0, 1);
		TileCompressorRenderer.model.render();
		GL11.glPopMatrix();
	}
}
