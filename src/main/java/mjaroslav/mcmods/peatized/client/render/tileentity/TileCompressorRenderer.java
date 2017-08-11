package mjaroslav.mcmods.peatized.client.render.tileentity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import mjaroslav.mcmods.peatized.PeatizedMod;
import mjaroslav.mcmods.peatized.client.model.ModelCompressor;
import mjaroslav.mcmods.peatized.common.config.PeatizedConfig;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileCompressorRenderer extends TileEntitySpecialRenderer {
	public ResourceLocation texture = new ResourceLocation(PeatizedMod.MODID + ":textures/blocks/compressor.png");
	public ResourceLocation textureModel = new ResourceLocation(
			PeatizedMod.MODID + ":textures/entity/compressor/compressor.png");
	public ModelCompressor model = new ModelCompressor(0.0625F);

	public TileCompressorRenderer(String resourceId, String blockTexture) {
		texture = new ResourceLocation(resourceId + ":textures/blocks/" + blockTexture + ".png");
		textureModel = new ResourceLocation(resourceId + ":textures/entity/compressor/" + blockTexture + ".png");
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks) {
		GL11.glPushMatrix();
		if (PeatizedConfig.altCompressorRemderer) {
			FMLClientHandler.instance().getClient().renderEngine.bindTexture(textureModel);
			GL11.glTranslated(x + 0.5, y + 1.5, z + 0.5);
			GL11.glRotated(180, 0, 0, 1);
			GL11.glScalef(1F, 1F, 1F);
			model.render();
		} else {
			FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
			GL11.glTranslated(x, y, z);
			GL11.glScalef(1F, 1F, 1F);
			Tessellator tessellator = Tessellator.instance;
			int brightness = tile.getBlockType().getMixedBrightnessForBlock(tile.getWorldObj(), tile.xCoord,
					tile.yCoord, tile.zCoord);
			tessellator.setBrightness(brightness);
			float f = 1.0F;
			int l = tile.getBlockType().colorMultiplier(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
			float f1 = (l >> 16 & 0xFF) / 255.0F;
			float f2 = (l >> 8 & 0xFF) / 255.0F;
			float f3 = (l & 0xFF) / 255.0F;
			if (EntityRenderer.anaglyphEnable) {
				float f6 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
				float f4 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
				float f7 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
				f1 = f6;
				f2 = f4;
				f3 = f7;
			}
			tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			tessellator.startDrawingQuads();
			tessellator.setBrightness(brightness);
			tessellator.addVertexWithUV(1, 1, 1, 0.5, 0.5);
			tessellator.addVertexWithUV(1, 1, 0, 0.5, 0);
			tessellator.addVertexWithUV(0, 1, 0, 0, 0);
			tessellator.addVertexWithUV(0, 1, 1, 0, 0.5);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setBrightness(brightness);
			tessellator.addVertexWithUV(1, 0.0625, 1, 1, 0.5);
			tessellator.addVertexWithUV(1, 0.0625, 0, 1, 0);
			tessellator.addVertexWithUV(0, 0.0625, 0, 0.5, 0);
			tessellator.addVertexWithUV(0, 0.0625, 1, 0.5, 0.5);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setBrightness(brightness);
			tessellator.addVertexWithUV(0, 0, 1, 0.5, 1);
			tessellator.addVertexWithUV(0, 0, 0, 0.5, 0.5);
			tessellator.addVertexWithUV(1, 0, 0, 0, 0.5);
			tessellator.addVertexWithUV(1, 0, 1, 0, 1);
			tessellator.draw();
			GL11.glPushMatrix();
			GL11.glTranslated(0.5, 0, 0.5);
			for (int side = 0; side < 4; side++) {
				GL11.glPushMatrix();
				GL11.glRotatef(-90 * side, 0, 1, 0);
				tessellator.startDrawingQuads();
				tessellator.setBrightness(brightness);
				tessellator.addVertexWithUV(1 - 0.5, 0.9375, 1 - 0.5, 1, 0.53125);
				tessellator.addVertexWithUV(1 - 0.5, 1, 1 - 0.5, 1, 0.5);
				tessellator.addVertexWithUV(0 - 0.5, 1, 1 - 0.5, 0.5, 0.5);
				tessellator.addVertexWithUV(0 - 0.5, 0.9375, 1 - 0.5, 0.5, 0.53125);
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setBrightness(brightness);
				tessellator.addVertexWithUV(1 - 0.5, 0, 1 - 0.5, 1, 1);
				tessellator.addVertexWithUV(1 - 0.5, 0.0625, 1 - 0.5, 1, 0.96875);
				tessellator.addVertexWithUV(0 - 0.5, 0.0625, 1 - 0.5, 0.5, 0.96875);
				tessellator.addVertexWithUV(0 - 0.5, 0, 1 - 0.5, 0.5, 1);
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setBrightness(brightness);
				tessellator.addVertexWithUV(1 - 0.5 - 0.1875, 0.5, 1 - 0.5 - 0.1875, 0.8125, 0.75);
				tessellator.addVertexWithUV(1 - 0.5, 0.9375, 1 - 0.5, 0.8125, 0.53125);
				tessellator.addVertexWithUV(0 - 0.5, 0.9375, 1 - 0.5, 0.5, 0.53125);
				tessellator.addVertexWithUV(0 - 0.5 + 0.1875, 0.5, 1 - 0.5 - 0.1875, 0.5, 0.75);
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setBrightness(brightness);
				tessellator.addVertexWithUV(1 - 0.5 - 0.1875, 0.0625, 1 - 0.5 - 0.1875, 0.8125, 0.96875);
				tessellator.addVertexWithUV(1 - 0.5 - 0.1875, 0.5, 1 - 0.5 - 0.1875, 0.8125, 0.75);
				tessellator.addVertexWithUV(0 - 0.5 + 0.1875, 0.5, 1 - 0.5 - 0.1875, 0.5, 0.75);
				tessellator.addVertexWithUV(0 - 0.5 + 0.1875, 0.0625, 1 - 0.5 - 0.1875, 0.5, 00.96875);
				tessellator.draw();
				GL11.glPushMatrix();
				tessellator.startDrawingQuads();
				tessellator.setBrightness(brightness);
				tessellator.addVertexWithUV(1 - 0.5 - 0.34375, 0 + 0.34375, 1 - 0.5, 0.96875, 0.6875);
				tessellator.addVertexWithUV(1 - 0.5 - 0.34375, 1 - 0.34375, 1 - 0.5, 0.96875, 0.53125);
				tessellator.addVertexWithUV(0 - 0.5 + 0.34375, 1 - 0.34375, 1 - 0.5, 0.8125, 0.53125);
				tessellator.addVertexWithUV(0 - 0.5 + 0.34375, 0 + 0.34375, 1 - 0.5, 0.8125, 0.6875);
				tessellator.draw();
				GL11.glPopMatrix();
				GL11.glPushMatrix();
				for (int side1 = 0; side1 < 4; side1++) {
					GL11.glPushMatrix();
					GL11.glRotatef(-90, 0, 1, 0);
					GL11.glPushMatrix();
					GL11.glTranslated(0.35, 0.5, 0);
					GL11.glScaled(0.3125, 0.3125, 0.3125);
					GL11.glRotatef(-90 * side1, 1, 0, 0);
					tessellator.startDrawingQuads();
					tessellator.setBrightness(brightness);
					tessellator.addVertexWithUV(0.5, 0.5, 0.5, 0.90625, 0.84375);
					tessellator.addVertexWithUV(0.5, 0.5, -0.5, 0.90625, 0.6875);
					tessellator.addVertexWithUV(-0.15, 0.5, -0.5, 0.8125, 0.6875);
					tessellator.addVertexWithUV(-0.15, 0.5, 0.5, 0.8125, 0.84375);
					tessellator.draw();
					GL11.glPopMatrix();
					GL11.glPopMatrix();
				}
				GL11.glPopMatrix();
				GL11.glPopMatrix();
			}
			GL11.glPopMatrix();
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		}
		GL11.glPopMatrix();
	}
}
