package mjaroslav.mcmods.peatized.client.render.item;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import mjaroslav.mcmods.peatized.client.model.ModelUpa;
import mjaroslav.mcmods.peatized.client.render.tileentity.TileUpaRenderer;
import mjaroslav.mcmods.peatized.common.block.ItemBlockUpa;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemBlockUpaRenderer implements IItemRenderer {
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    public static final ModelUpa model = new ModelUpa(0.0625F);

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();
        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.EQUIPPED)
            GL11.glTranslated(0.5, 0.5, 0.5);
        else
            GL11.glTranslated(0, 0, 0);
        GL11.glRotated(180, 0, 0, 1);
        GL11.glRotated(180, 0, 1, 0);
        renderPart(ItemBlockUpa.getCheeksColor(item), TileUpaRenderer.textureCheeks);
        renderPart(ItemBlockUpa.getMainColor(item), TileUpaRenderer.textureMain);
        renderPart(ItemBlockUpa.getSecondaryColor(item), TileUpaRenderer.textureSecondary);
        renderPart(ItemBlockUpa.getLineColor(item), TileUpaRenderer.textureLine);
        renderPart(ItemBlockUpa.getNoseColor(item), TileUpaRenderer.textureNose);
        renderPart(ItemBlockUpa.getFaceColor(item), TileUpaRenderer.textureFace);
        renderPart(ItemBlockUpa.getEarsColor(item), TileUpaRenderer.textureEars);
        renderPart(ItemBlockUpa.getEyesColor(item), TileUpaRenderer.textureEyes);
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
