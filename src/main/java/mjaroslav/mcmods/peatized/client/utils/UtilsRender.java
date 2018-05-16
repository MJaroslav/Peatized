package mjaroslav.mcmods.peatized.client.utils;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

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
    
    public static int setBrightness(Tessellator tessellator, IBlockAccess blockAccess, int x, int y, int z, Block block) {
        int mb = block.getMixedBrightnessForBlock(blockAccess, x, y, z);
        tessellator.setBrightness(mb);
        float f = 1.0F;
        int l = block.colorMultiplier(blockAccess, x, y, z);
        float f1 = (l >> 16 & 0xFF) / 255.0F;
        float f2 = (l >> 8 & 0xFF) / 255.0F;
        float f3 = (l & 0xFF) / 255.0F;
        if (EntityRenderer.anaglyphEnable) {
            float f6 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
            float f4 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
            float f7 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
            f1 = f6;
            f2 = f4;
            f3 = f7;
        }
        tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
        return mb;
    }
}
