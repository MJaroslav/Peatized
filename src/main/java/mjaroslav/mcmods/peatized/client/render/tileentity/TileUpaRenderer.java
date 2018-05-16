package mjaroslav.mcmods.peatized.client.render.tileentity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import mjaroslav.mcmods.peatized.client.model.ModelUpa;
import mjaroslav.mcmods.peatized.client.utils.UtilsRender;
import mjaroslav.mcmods.peatized.common.tileentity.TileUpa;
import mjaroslav.mcmods.peatized.lib.ResInfo;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class TileUpaRenderer extends TileEntitySpecialRenderer {
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
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(ResInfo.getUpaTexture(tile));
        model.render();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    public static void renderMode2(double x, double y, double z, TileUpa tile) {
        GL11.glTranslated(0, 0.5, 0);
        GL11.glScalef(3F, 3F, 3F);
        GL11.glRotated(90, 0, 1, 0);
        UtilsRender.renderItemStack(x, y, z, tile.toStack());
    }
}
