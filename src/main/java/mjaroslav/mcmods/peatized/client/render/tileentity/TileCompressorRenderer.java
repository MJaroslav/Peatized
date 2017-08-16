package mjaroslav.mcmods.peatized.client.render.tileentity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import mjaroslav.mcmods.peatized.client.model.ModelCompressor;
import mjaroslav.mcmods.peatized.common.init.PeatizedBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileCompressorRenderer extends TileEntitySpecialRenderer {
	public ResourceLocation texture;
	public ResourceLocation textureLit;

	public static ModelCompressor model = new ModelCompressor(0.0625F);
	public boolean useLit;

	public TileCompressorRenderer(String resourceId, String blockTexture, boolean useLit) {
		this.texture = new ResourceLocation(resourceId + ":textures/entity/compressor/" + blockTexture + ".png");
		this.useLit = useLit;
		if (this.useLit)
			this.textureLit = new ResourceLocation(
					resourceId + ":textures/entity/compressor/" + blockTexture + "_on.png");
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks) {
		GL11.glPushMatrix();
		if (this.useLit && tile.getBlockType() == PeatizedBlocks.compressorLit)
			FMLClientHandler.instance().getClient().renderEngine.bindTexture(textureLit);
		else
			FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
		GL11.glTranslated(x + 0.5, y + 1.5, z + 0.5);
		GL11.glRotated(180, 0, 0, 1);
		GL11.glScalef(1F, 1F, 1F);
		model.render();
		GL11.glPopMatrix();
	}
}
