package mjaroslav.mcmods.peatized.client.render.tileentity;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import mjaroslav.mcmods.peatized.client.model.ModelUpa;
import mjaroslav.mcmods.peatized.common.tileentity.TileUpa;
import mjaroslav.mcmods.peatized.lib.ModInfo;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileUpaRenderer extends TileEntitySpecialRenderer {
    public static final ResourceLocation textureMain = new ResourceLocation(ModInfo.MODID,
            "textures/models/upa/main.png");
    public static final ResourceLocation textureSecondary = new ResourceLocation(ModInfo.MODID,
            "textures/models/upa/secondary.png");
    public static final ResourceLocation textureLine = new ResourceLocation(ModInfo.MODID,
            "textures/models/upa/line.png");
    public static final ResourceLocation textureCheeks = new ResourceLocation(ModInfo.MODID,
            "textures/models/upa/cheeks.png");
    public static final ResourceLocation textureEyes = new ResourceLocation(ModInfo.MODID,
            "textures/models/upa/eyes.png");
    public static final ResourceLocation textureNose = new ResourceLocation(ModInfo.MODID,
            "textures/models/upa/nose.png");
    public static final ResourceLocation textureFace = new ResourceLocation(ModInfo.MODID,
            "textures/models/upa/face.png");
    public static final ResourceLocation textureEars = new ResourceLocation(ModInfo.MODID,
            "textures/models/upa/ears.png");

    public static final ModelUpa model = new ModelUpa(0.0625F);

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks) {
        renderTileEntityAt((TileUpa) tile, x, y, z, partialTicks);
    }

    public void renderTileEntityAt(TileUpa tile, double x, double y, double z, float partialTicks) {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        GL11.glRotated(180, 0, 0, 1);
        GL11.glRotated(((tile.getBlockMetadata() * 360) / 16.0F), 0, 1, 0);
        GL11.glScalef(1F, 1F, 1F);
        renderPart(tile.getMainColor(), textureMain);
        renderPart(tile.getSecondaryColor(), textureSecondary);
        renderPart(tile.getLineColor(), textureLine);
        renderPart(tile.getCheeksColor(), textureCheeks);
        renderPart(tile.getFaceColor(), textureFace);
        renderPart(tile.getEyesColor(), textureEyes);
        renderPart(tile.getEarsColor(), textureEars);
        renderPart(tile.getNoseColor(), textureNose);
        GL11.glPopMatrix();
    }

    private void renderPart(int intColor, ResourceLocation texture) {
        Color color = new Color(intColor);
        GL11.glPushMatrix();
        GL11.glColor3d(color.getRed() / 255D, color.getGreen() / 255D, color.getBlue() / 255D);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
        model.render();
        GL11.glPopMatrix();
    }
}
