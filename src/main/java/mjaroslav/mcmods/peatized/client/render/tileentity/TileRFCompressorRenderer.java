package mjaroslav.mcmods.peatized.client.render.tileentity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import mjaroslav.mcmods.peatized.client.model.ModelRFCompressor;
import mjaroslav.mcmods.peatized.client.utils.UtilsRender;
import mjaroslav.mcmods.peatized.common.tileentity.TileRFCompressor;
import mjaroslav.mcmods.peatized.lib.ModInfo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

public class TileRFCompressorRenderer extends TileEntitySpecialRenderer {
    public static final ResourceLocation texture = new ResourceLocation(ModInfo.MODID,
            "textures/models/compressor/rf.png");
    public static final ResourceLocation textureStorage = new ResourceLocation(ModInfo.MODID,
            "textures/models/compressor/rf_storage.png");

    public static final ModelRFCompressor model = new ModelRFCompressor(0.0625F);

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks) {
        renderTileEntityAt((TileRFCompressor) tile, x, y, z, partialTicks);
    }

    public void renderTileEntityAt(TileRFCompressor tile, double x, double y, double z, float partialTicks) {
        GL11.glPushMatrix();
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        GL11.glRotated(180, 0, 0, 1);
        switch (tile.getBlockMetadata()) {
        case 4:
            GL11.glRotated(90, 0, 1, 0);
            break;
        case 2:
            GL11.glRotated(180, 0, 1, 0);
            break;
        case 5:
            GL11.glRotated(-90, 0, 1, 0);
            break;
        default:
            break;
        }
        GL11.glScalef(1F, 1F, 1F);
        GL11.glPushMatrix();
        if (tile.cooldown >= 1 && tile.cooldown < 11)
            model.pistonStick.rotationPointY = 4;
        else if (tile.cooldown >= 11 && tile.cooldown < 20)
            model.pistonStick.rotationPointY = 0;
        else
            model.pistonStick.rotationPointY = 0;
        model.render();
        GL11.glPushMatrix();
        if (!(tile.cooldown >= 1 && tile.cooldown < 11)) {
            GL11.glTranslated(-0.1, 0.3, 0.05);
            GL11.glRotated(90, 1, 0, 0);
            UtilsRender.renderItemStack(0, 0, 0, tile.getStackInSlot(0));
        }
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslated(0.5 - 0.0625D * 0.5, 0.5, 1);
        GL11.glRotated(180, 0, 0, 1);
        GL11.glRotated(180, 0, 1, 0);
        float diff = (float) tile.getEnergyStored(ForgeDirection.UNKNOWN)
                / tile.getMaxEnergyStored(ForgeDirection.UNKNOWN);
        int storage = (int) (42 * diff);
        Tessellator t = Tessellator.instance;
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(textureStorage);
        double p = 0.0625F;
        t.startDrawingQuads();
        t.addVertexWithUV(0, p + p / 3 * storage, p * 9, 0, 1 - p / 3 * (42 - storage));
        t.addVertexWithUV(0, 1 - p, p * 9, 0, 1);
        t.addVertexWithUV(0, 1 - p, p * 13, 0.5, 1);
        t.addVertexWithUV(0, p + p / 3 * storage, p * 13, 0.5, 1 - p / 3 * (42 - storage));
        t.draw();
        t.startDrawingQuads();
        t.addVertexWithUV(0, p, p * 9, 0.5, 1);
        t.addVertexWithUV(0, p + p / 3 * storage, p * 9, 0.5, 1 - p / 3 * storage);
        t.addVertexWithUV(0, p + p / 3 * storage, p * 13, 1, 1 - p / 3 * storage);
        t.addVertexWithUV(0, p, p * 13, 1, 1);
        t.draw();
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
}
