package mjaroslav.mcmods.peatized.client.render.item;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import mjaroslav.mcmods.peatized.PeatizedMod;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemBlockCompressorRenderer implements IItemRenderer {
	private ResourceLocation texture = new ResourceLocation(PeatizedMod.MODID + ":textures/blocks/compressor.png");
	private ResourceLocation textureRf = new ResourceLocation(PeatizedMod.MODID + ":textures/blocks/compressor_rf.png");

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
			switch (item.getItemDamage()) {
			case 0:
				FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
				break;
			case 1:
				FMLClientHandler.instance().getClient().renderEngine.bindTexture(textureRf);
				break;
			default:
				break;
			}
		switch (type) {
		case ENTITY:
			GL11.glTranslated(-0.5, -0.5, -0.5);
			break;
		case INVENTORY:
			GL11.glTranslated(-0.5, -0.5, -0.5);
			break;
		default:
			GL11.glTranslated(0.0, 0.0, 0.0);
			break;
		}
		GL11.glScalef(1F, 1F, 1F);
		Tessellator tessellator = Tessellator.instance;
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(1, 1, 1, 0.5, 0.5);
		tessellator.addVertexWithUV(1, 1, 0, 0.5, 0);
		tessellator.addVertexWithUV(0, 1, 0, 0, 0);
		tessellator.addVertexWithUV(0, 1, 1, 0, 0.5);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(1, 0.0625, 1, 1, 0.5);
		tessellator.addVertexWithUV(1, 0.0625, 0, 1, 0);
		tessellator.addVertexWithUV(0, 0.0625, 0, 0.5, 0);
		tessellator.addVertexWithUV(0, 0.0625, 1, 0.5, 0.5);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(0, 0, 1, 0.5, 1);
		tessellator.addVertexWithUV(0, 0, 0, 0.5, 0.5);
		tessellator.addVertexWithUV(1, 0, 0, 0, 0.5);
		tessellator.addVertexWithUV(1, 0, 1, 0, 1);
		tessellator.draw();
		GL11.glPushMatrix();
		GL11.glTranslated(0.5, 0, 0.5);
		for (int side = 0; side < 4; side++) {
			GL11.glPushMatrix();
			GL11.glRotatef(90 * side, 0, 1, 0);
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(1 - 0.5, 0.9375, 1 - 0.5, 1, 0.53125);
			tessellator.addVertexWithUV(1 - 0.5, 1, 1 - 0.5, 1, 0.5);
			tessellator.addVertexWithUV(0 - 0.5, 1, 1 - 0.5, 0.5, 0.5);
			tessellator.addVertexWithUV(0 - 0.5, 0.9375, 1 - 0.5, 0.5, 0.53125);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(1 - 0.5, 0, 1 - 0.5, 1, 1);
			tessellator.addVertexWithUV(1 - 0.5, 0.0625, 1 - 0.5, 1, 0.96875);
			tessellator.addVertexWithUV(0 - 0.5, 0.0625, 1 - 0.5, 0.5, 0.96875);
			tessellator.addVertexWithUV(0 - 0.5, 0, 1 - 0.5, 0.5, 1);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(1 - 0.5 - 0.1875, 0.5, 1 - 0.5 - 0.1875, 0.8125, 0.75);
			tessellator.addVertexWithUV(1 - 0.5, 0.9375, 1 - 0.5, 0.8125, 0.53125);
			tessellator.addVertexWithUV(0 - 0.5, 0.9375, 1 - 0.5, 0.5, 0.53125);
			tessellator.addVertexWithUV(0 - 0.5 + 0.1875, 0.5, 1 - 0.5 - 0.1875, 0.5, 0.75);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(1 - 0.5 - 0.1875, 0.0625, 1 - 0.5 - 0.1875, 0.8125, 0.96875);
			tessellator.addVertexWithUV(1 - 0.5 - 0.1875, 0.5, 1 - 0.5 - 0.1875, 0.8125, 0.75);
			tessellator.addVertexWithUV(0 - 0.5 + 0.1875, 0.5, 1 - 0.5 - 0.1875, 0.5, 0.75);
			tessellator.addVertexWithUV(0 - 0.5 + 0.1875, 0.0625, 1 - 0.5 - 0.1875, 0.5, 00.96875);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(1 - 0.5 - 0.34375, 0 + 0.34375, 1 - 0.5, 0.96875, 0.6875);
			tessellator.addVertexWithUV(1 - 0.5 - 0.34375, 1 - 0.34375, 1 - 0.5, 0.96875, 0.53125);
			tessellator.addVertexWithUV(0 - 0.5 + 0.34375, 1 - 0.34375, 1 - 0.5, 0.8125, 0.53125);
			tessellator.addVertexWithUV(0 - 0.5 + 0.34375, 0 + 0.34375, 1 - 0.5, 0.8125, 0.6875);
			tessellator.draw();
			GL11.glPushMatrix();
			GL11.glTranslated(0.35, 0.5, 0);
			for (int side1 = 0; side1 < 4; side1++) {
				GL11.glPushMatrix();
				GL11.glScaled(0.3125, 0.3125, 0.3125);
				GL11.glRotatef(90 * side1, 1, 0, 0);
				tessellator.startDrawingQuads();
				tessellator.addVertexWithUV(0.5, 0.5, 0.5, 0.90625, 0.84375);
				tessellator.addVertexWithUV(0.5, 0.5, -0.5, 0.90625, 0.6875);
				tessellator.addVertexWithUV(-0.15, 0.5, -0.5, 0.8125, 0.6875);
				tessellator.addVertexWithUV(-0.15, 0.5, 0.5, 0.8125, 0.84375);
				tessellator.draw();
				GL11.glPopMatrix();
			}
			GL11.glPopMatrix();
			GL11.glPopMatrix();
		}
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}
