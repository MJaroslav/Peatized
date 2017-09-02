package mjaroslav.mcmods.peatized.client.render.item;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import mjaroslav.mcmods.peatized.PeatizedMod;
import mjaroslav.mcmods.peatized.common.item.ItemCleaver;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ItemCleaverRenderer implements IItemRenderer {
	public static final IModelCustom model = AdvancedModelLoader
			.loadModel(new ResourceLocation(PeatizedMod.MODID, "models/cleaver.obj"));

	public static ResourceLocation textureBase = new ResourceLocation(PeatizedMod.MODID,
			"textures/models/cleaver/cleaver_base.png");
	public static ResourceLocation textureBlade = new ResourceLocation(PeatizedMod.MODID,
			"textures/models/cleaver/cleaver_blade.png");
	public static ResourceLocation textureBlood = new ResourceLocation(PeatizedMod.MODID,
			"textures/models/cleaver/cleaver_blade_blood.png");

	public boolean useBlood;

	public ItemCleaverRenderer(boolean useBlood) {
		this.useBlood = useBlood;
	}

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
			entityClientPlayerMP = Minecraft.getMinecraft().thePlayer;
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
		if (this.useBlood)
			Minecraft.getMinecraft().renderEngine.bindTexture(textureBlood);
		else {
			Minecraft.getMinecraft().renderEngine.bindTexture(textureBlade);
			int intColor = ((ItemCleaver) item.getItem()).getBladeColor();
			if (intColor != 16777215) {
				Color color = new Color(intColor);
				GL11.glColor3d(color.getRed(), color.getGreen(), color.getBlue());
			}
		}
		model.renderPart("blade");
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glColor3d(256, 256, 256);
		Minecraft.getMinecraft().renderEngine.bindTexture(textureBase);
		model.renderPart("base");
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}
