package mjaroslav.mcmods.peatized.common.item;

import mjaroslav.mcmods.peatized.Peatized;
import mjaroslav.mcmods.peatized.common.lib.ModInfo;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ItemBase extends Item {
    public ItemBase(String name) {
        setRegistryName(name);
        setUnlocalizedName(ModInfo.unlocalizedName(name));
        setCreativeTab(Peatized.TAB);
        ItemRegistry.ITEMS.add(this);
    }

    public int getModelRegistryMeta() {
        return 0;
    }

    public void register() {
        ForgeRegistries.ITEMS.register(this);
    }
}
