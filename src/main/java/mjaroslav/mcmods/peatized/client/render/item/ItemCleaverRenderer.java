package mjaroslav.mcmods.peatized.client.render.item;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import mjaroslav.mcmods.mjutils.lib.utils.UtilsGame;
import mjaroslav.mcmods.peatized.common.item.ItemCleaver;
import mjaroslav.mcmods.peatized.lib.CategoryGeneralInfo.CategoryCleaversInfo;
import mjaroslav.mcmods.peatized.lib.CategoryGeneralInfo.CategoryGraphicsInfo;
import mjaroslav.mcmods.peatized.lib.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ItemCleaverRenderer implements IItemRenderer {
    public static final IModelCustom model = AdvancedModelLoader
            .loadModel(new ResourceLocation(ModInfo.MODID, "models/cleaver.obj"));

    public static ResourceLocation textureBase = new ResourceLocation(ModInfo.MODID,
            "textures/models/cleaver/cleaver_base.png");
    public static ResourceLocation textureBlade = new ResourceLocation(ModInfo.MODID,
            "textures/models/cleaver/cleaver_blade.png");
    public static ResourceLocation textureBlood = new ResourceLocation(ModInfo.MODID,
            "textures/models/cleaver/cleaver_blade_blood.png");
    public static ResourceLocation textureBloodAlt = new ResourceLocation(ModInfo.MODID,
            "textures/models/cleaver/cleaver_blade_blood_alt.png");

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        if (!(item.getItem() instanceof ItemCleaver))
            return;
        EntityClientPlayerMP entityClientPlayerMP = null;
        GL11.glPushMatrix();
        GL11.glScaled(0.25, 0.25, 0.25);
        switch (type) {
        case EQUIPPED: {
            if (data.length == 2 && data[1] instanceof EntityClientPlayerMP)
                entityClientPlayerMP = (EntityClientPlayerMP) data[1];
            GL11.glRotated(135, 0, 1, 0);
            GL11.glRotated(-65, 0, 0, 1);
            GL11.glTranslatef(-4.0F, -4.0F, 0.5F);
            if (entityClientPlayerMP != null && entityClientPlayerMP.getItemInUseCount() > 0
                    && item.getItemUseAction() == EnumAction.block) {
                GL11.glRotated(90, 0, 1, 0);
                GL11.glRotated(-30, 1, 0, 0);
                GL11.glRotated(-45, 0, 0, 1);
                GL11.glTranslatef(1.0F, 0.0F, 0.0F);
            }
        }
            break;
        case ENTITY: {
            GL11.glScaled(0.25, 0.25, 0.25);
            GL11.glRotated(90, 0, 1, 0);
            GL11.glRotated(-45, 0, 0, 1);
            GL11.glTranslated(-1, -3, 0);
        }
            break;
        case EQUIPPED_FIRST_PERSON: {
            entityClientPlayerMP = (EntityClientPlayerMP) data[1];
            GL11.glRotated(45, 0, 1, 0);
            GL11.glTranslatef(-3F, 3.0F, 0.5F);
            GL11.glScaled(0.5, 0.5, 0.5);
            if (entityClientPlayerMP != null && entityClientPlayerMP.getItemInUseCount() > 0
                    && item.getItemUseAction() == EnumAction.block) {
                GL11.glRotated(30, 0, 0, 1);
                GL11.glTranslatef(3F, -3.0F, 0.0F);
            }
        }
            break;
        case INVENTORY: {
            GL11.glScaled(0.6, 0.6, 0.6);
            GL11.glRotated(45, 0, 1, 0);
            GL11.glRotated(-45, 0, 0, 1);
            GL11.glTranslatef(0.0F, -4.0F, 0.0F);
        }
            break;
        default:
            break;
        }
        GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(textureBlade);
        Color color = ((ItemCleaver) item.getItem()).getBladeColor();
        if (!color.equals(Color.white))
            GL11.glColor3d(color.getRed() / 255D, color.getGreen() / 255D, color.getBlue() / 255D);
        model.renderPart("blade");
        GL11.glPopMatrix();
        short value = 0;
        if (entityClientPlayerMP != null && ItemCleaver.hasBlood(item))
            value = (short) (ItemCleaver.getBlood(item) - entityClientPlayerMP.worldObj.getTotalWorldTime());
        if ((CategoryGraphicsInfo.renaAlwaysBlood && ItemCleaver.isEaster(item))
                || CategoryGraphicsInfo.renderBlood && value > 0) {
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            if (!CategoryGraphicsInfo.renaAlwaysBlood || !ItemCleaver.isEaster(item))
                GL11.glColor4d(1D, 1D, 1D,
                        (double) value / UtilsGame.getTicksFromSeconds(CategoryCleaversInfo.bloodTime));
            Minecraft.getMinecraft().renderEngine
                    .bindTexture(CategoryGraphicsInfo.altBlood ? textureBloodAlt : textureBlood);
            model.renderPart("blade");
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
        }
        GL11.glPushMatrix();
        GL11.glColor3d(256, 256, 256);
        Minecraft.getMinecraft().renderEngine.bindTexture(textureBase);
        model.renderPart("base");
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        if (item.hasEffect(0)) {
            GL11.glDepthFunc(GL11.GL_EQUAL);
            GL11.glDisable(GL11.GL_LIGHTING);
            Minecraft.getMinecraft().renderEngine
                    .bindTexture(new ResourceLocation("textures/misc/enchanted_item_glint.png"));
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(768, 1, 1, 0);
            float f7 = 0.76F;
            GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
            GL11.glMatrixMode(GL11.GL_TEXTURE);
            GL11.glPushMatrix();
            float f8 = 0.125F;
            GL11.glScalef(f8, f8, f8);
            float f9 = (float) (Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
            GL11.glTranslatef(f9, 0.0F, 0.0F);
            GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
            model.renderAll();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(f8, f8, f8);
            f9 = (float) (Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
            GL11.glTranslatef(-f9, 0.0F, 0.0F);
            GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
            model.renderAll();
            GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDepthFunc(GL11.GL_LEQUAL);
        }
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
}
