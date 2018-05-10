package mjaroslav.mcmods.peatized.client.utils;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

public class UtilsRender {
    public static void renderItemStack(double x, double y, double z, ItemStack slotStack) {
        if (slotStack == null)
            return;
        ItemStack copy = slotStack.copy();
        copy.stackSize = 1;
        EntityItem slotEntity = new EntityItem(null, 0, 0, 0, copy);
        slotEntity.hoverStart = 0F;
        boolean cache = Minecraft.getMinecraft().gameSettings.fancyGraphics;
        Minecraft.getMinecraft().gameSettings.fancyGraphics = true;
        RenderItem.renderInFrame = true;
        GL11.glPushMatrix();
        GL11.glRotated(180, 0, 0, 1);
        RenderManager.instance.renderEntityWithPosYaw(slotEntity, 0, 0, 0, 0, 0);
        GL11.glPopMatrix();
        RenderItem.renderInFrame = false;
        Minecraft.getMinecraft().gameSettings.fancyGraphics = cache;
    }
}
