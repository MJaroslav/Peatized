package mjaroslav.mcmods.peatized.client.render.tileentity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import mjaroslav.mcmods.peatized.client.model.ModelFuelCompressor;
import mjaroslav.mcmods.peatized.client.utils.UtilsRender;
import mjaroslav.mcmods.peatized.common.tileentity.TileFuelCompressor;
import mjaroslav.mcmods.peatized.lib.ModInfo;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileFuelCompressorRenderer extends TileEntitySpecialRenderer {
    public static final ResourceLocation texture = new ResourceLocation(ModInfo.MODID,
            "textures/models/compressor/fuel.png");
    public static final ResourceLocation textureLit = new ResourceLocation(ModInfo.MODID,
            "textures/models/compressor/fuel_lit.png");

    public static final ModelFuelCompressor model = new ModelFuelCompressor(0.0625F);

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks) {
        renderTileEntityAt((TileFuelCompressor) tile, x, y, z, partialTicks);
    }

    public void renderTileEntityAt(TileFuelCompressor tile, double x, double y, double z, float partialTicks) {
        GL11.glPushMatrix();
        if (tile.blockIsLit())
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(textureLit);
        else
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
        if (!(tile.cooldown >= 1 && tile.cooldown < 11)) {
            GL11.glTranslated(-0.1, 0.3, 0.05);
            GL11.glRotated(90, 1, 0, 0);
            UtilsRender.renderItemStack(0, 0, 0, tile.getStackInSlot(1));
        }
        GL11.glPopMatrix();
    }
}
