package mjaroslav.mcmods.peatized.client.render.item;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import mjaroslav.mcmods.peatized.client.model.*;
import mjaroslav.mcmods.peatized.client.render.tileentity.*;
import mjaroslav.mcmods.peatized.common.init.PeatizedBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemBlockCompressorRenderer implements IItemRenderer {
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    public static final ModelBaseCompressor modelBase = new ModelBaseCompressor(0.0625F);
    public static final ModelRFCompressor modelRf = new ModelRFCompressor(0.0625F);
    public static final ModelFuelCompressor modelFuel = new ModelFuelCompressor(0.0625F);

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();
        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.EQUIPPED)
            GL11.glTranslated(0.5, 0.5, 0.5);
        else
            GL11.glTranslated(0, 0, 0);
        GL11.glRotated(180, 0, 0, 1);
        if (item.getItem() == Item.getItemFromBlock(PeatizedBlocks.baseCompressor)) {
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(TileBaseCompressorRenderer.texture);
            modelBase.render();
        }
        if (item.getItem() == Item.getItemFromBlock(PeatizedBlocks.fuelCompressor)
                || item.getItem() == Item.getItemFromBlock(PeatizedBlocks.fuelCompressorLit)) {
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(TileFuelCompressorRenderer.texture);
            modelFuel.render();
        }
        if (item.getItem() == Item.getItemFromBlock(PeatizedBlocks.rfCompressor)) {
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(TileRFCompressorRenderer.texture);
            modelRf.render();
        }
        GL11.glPopMatrix();
    }
}
