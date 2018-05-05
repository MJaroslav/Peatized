package mjaroslav.mcmods.peatized.common.event;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mjaroslav.mcmods.peatized.ModPeatized;
import mjaroslav.mcmods.peatized.common.item.ItemCleaver;
import mjaroslav.mcmods.peatized.lib.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class EventHandlerRender {
    @SubscribeEvent
    public void onGameOverlayRenderEvent(RenderGameOverlayEvent.Post event) {
        Minecraft mc = ModPeatized.proxy.getMinecraft();
        if (mc.thePlayer != null && mc.thePlayer.getHeldItem() != null
                && mc.thePlayer.getHeldItem().getItem() instanceof ItemCleaver) {
            ItemStack stack = mc.thePlayer.getHeldItem();
            if (ItemCleaver.hasCooldown(stack)) {
                int xCenter = event.resolution.getScaledWidth() / 2 - 50;
                int yCenter = event.resolution.getScaledHeight() / 2 + 30;
                GL11.glPushMatrix();
                GL11.glEnable(GL11.GL_BLEND);
                OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
                mc.renderEngine.bindTexture(new ResourceLocation(ModInfo.MODID, "textures/gui/cooldownbar.png"));
                Tessellator t = Tessellator.instance;
                t.startDrawingQuads();
                t.addVertexWithUV(xCenter, yCenter - 2, 0, 0F, 0.5F);
                t.addVertexWithUV(xCenter, yCenter + 2, 0, 0F, 0F);
                t.addVertexWithUV(xCenter + 100, yCenter + 2, 0, 1F, 0F);
                t.addVertexWithUV(xCenter + 100, yCenter - 2, 0, 1F, 0.5F);
                t.draw();
                short cooldown = (short) ((ItemCleaver.getCooldownTime(stack) - 1
                        + mc.thePlayer.worldObj.getTotalWorldTime() - ItemCleaver.getCooldown(stack))
                        / (double) ItemCleaver.getCooldownTime(stack) * 100);
                t.startDrawingQuads();
                t.addVertexWithUV(xCenter, yCenter - 2, 0, 0F, 1F);
                t.addVertexWithUV(xCenter, yCenter + 2, 0, 0F, 0.5F);
                t.addVertexWithUV(xCenter + cooldown, yCenter + 2, 0, cooldown / 100F, 0.5F);
                t.addVertexWithUV(xCenter + cooldown, yCenter - 2, 0, cooldown / 100F, 1F);
                t.draw();
                GL11.glPopMatrix();
                mc.renderEngine.bindTexture(new ResourceLocation("minecraft", "textures/gui/icons.png"));
            }
        }
    }
}
