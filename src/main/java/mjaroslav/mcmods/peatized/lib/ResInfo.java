package mjaroslav.mcmods.peatized.lib;

import mjaroslav.mcmods.peatized.common.tileentity.TileUpa;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ResInfo {
    public static ResourceLocation getUpaTexture(Object upa) {
        if (upa instanceof ItemStack) {
            if (((ItemStack) upa).hasTagCompound() && ((ItemStack) upa).getTagCompound().hasKey(NBTInfo.NAME))
                return new ResourceLocation(ModInfo.MODID,
                        "textures/models/upa/" + ((ItemStack) upa).getTagCompound().getString(NBTInfo.NAME) + ".png");
            else
                return new ResourceLocation(ModInfo.MODID, "textures/models/upa/common.png");
        } else if (upa instanceof TileUpa) {
            return new ResourceLocation(ModInfo.MODID, "textures/models/upa/" + ((TileUpa) upa).getName() + ".png");
        } else
            return new ResourceLocation(ModInfo.MODID, "textures/models/upa/common.png");
    }
}
