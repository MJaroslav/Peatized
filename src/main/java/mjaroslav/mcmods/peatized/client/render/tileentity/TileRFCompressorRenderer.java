package mjaroslav.mcmods.peatized.client.render.tileentity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import mjaroslav.mcmods.peatized.client.model.ModelRFCompressor;
import mjaroslav.mcmods.peatized.common.tileentity.TileRFCompressor;
import mjaroslav.mcmods.peatized.lib.ModInfo;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileRFCompressorRenderer extends TileEntitySpecialRenderer {
    public static final ResourceLocation texture = new ResourceLocation(ModInfo.MODID,
            "textures/models/compressor/rf.png");

    public ModelRFCompressor model = new ModelRFCompressor(0.0625F);

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
        if (tile.cooldown >= 1 && tile.cooldown < 11)
            model.pistonStick.rotationPointY = 4;
        else if (tile.cooldown >= 11 && tile.cooldown < 20)
            model.pistonStick.rotationPointY = 0;
        else
            model.pistonStick.rotationPointY = 0;
        model.render();
        GL11.glPopMatrix();
    }
}
