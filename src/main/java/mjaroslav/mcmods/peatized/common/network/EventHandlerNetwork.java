package mjaroslav.mcmods.peatized.common.network;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.relauncher.Side;
import mjaroslav.mcmods.peatized.common.init.NetworkHandler;
import mjaroslav.mcmods.peatized.common.item.crafting.CompressorRecipes;
import mjaroslav.mcmods.peatized.lib.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class EventHandlerNetwork {
    @SubscribeEvent
    public void playerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        Side side = FMLCommonHandler.instance().getEffectiveSide();
        if (side != Side.SERVER)
            return;
        EntityPlayer p = event.player;
        CompressorRecipes.readFromConfig();
        NetworkHandler.INSTANCE.sendTo(new PacketCompressingRecipes(), (EntityPlayerMP) event.player);
        ModInfo.LOG.info("Loading compressor recipes.");
    }

    @SubscribeEvent
    public void clientLoggedIn(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        if ((FMLClientHandler.instance().getClient().theWorld != null) && (Minecraft.getMinecraft().theWorld != null)) {
            CompressorRecipes.readFromConfig();
            ModInfo.LOG.info("Resetting compressor recipes.");
        }
    }

    @SubscribeEvent
    public void clientLogsOut(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        if (FMLClientHandler.instance().getClient().theWorld != null) {
            CompressorRecipes.compressing().recipeList = CompressorRecipes.compressing().recipeListCache;
            ModInfo.LOG.info("Restoring client compressor recipes.");
        }
    }
}
