package mjaroslav.mcmods.peatized.client.render.tileentity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import mjaroslav.mcmods.peatized.client.model.ModelBaseCompressor;
import mjaroslav.mcmods.peatized.client.utils.UtilsRender;
import mjaroslav.mcmods.peatized.common.tileentity.TileBaseCompressor;
import mjaroslav.mcmods.peatized.lib.ModInfo;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileBaseCompressorRenderer extends TileEntitySpecialRenderer {
    public static final ResourceLocation texture = new ResourceLocation(ModInfo.MODID,
            "textures/models/compressor/base.png");

    public static final ModelBaseCompressor model = new ModelBaseCompressor(0.0625F);

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks) {
        renderTileEntityAt((TileBaseCompressor) tile, x, y, z, partialTicks);
    }

    public void renderTileEntityAt(TileBaseCompressor tile, double x, double y, double z, float partialTicks) {
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
        if (tile.platePressed)
            model.pistonStick.rotationPointY = 3;
        else
            model.pistonStick.rotationPointY = -1;
        model.render();
        if (!tile.platePressed) {
            GL11.glTranslated(0, 0.25, 0.165);
            GL11.glRotated(90, 1, 0, 0);
            UtilsRender.renderItemStack(0, 0, 0, tile.getStackInSlot(0));
        }
        GL11.glPopMatrix();
    }
}
