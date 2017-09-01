package mjaroslav.mcmods.peatized.client.render.item;

import org.lwjgl.opengl.GL11;

import mjaroslav.mcmods.peatized.PeatizedMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ItemRenaWeaponRenderer implements IItemRenderer {
	public static final IModelCustom model = AdvancedModelLoader
			.loadModel(new ResourceLocation(PeatizedMod.MODID, "models/rena_s_weapon.obj"));
	public final ResourceLocation texture;

	public ItemRenaWeaponRenderer(ResourceLocation texture) {
		this.texture = texture;
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
		} break;
		default:
			break;
		}
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		model.renderAll();
		GL11.glPopMatrix();
	}
}
