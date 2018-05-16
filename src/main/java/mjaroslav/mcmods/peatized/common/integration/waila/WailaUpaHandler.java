package mjaroslav.mcmods.peatized.common.integration.waila;

import java.util.List;

import mcp.mobius.waila.api.*;
import mjaroslav.mcmods.peatized.common.tileentity.TileUpa;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class WailaUpaHandler implements IWailaDataProvider {
    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return accessor.getTileEntity() instanceof TileUpa ? ((TileUpa) accessor.getTileEntity()).toStack()
                : accessor.getStack();
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        currenttip.addAll(itemStack.getTooltip(accessor.getPlayer(), false));
        currenttip.remove(0);
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x,
            int y, int z) {
        return tag;
    }
}
