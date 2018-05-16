package mjaroslav.mcmods.peatized.client.render.item;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import mjaroslav.mcmods.peatized.client.model.ModelUpa;
import mjaroslav.mcmods.peatized.lib.ResInfo;
import net.minecraft.item.ItemStack;
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
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(ResInfo.getUpaTexture(item));
        model.render();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }
}
