package mjaroslav.mcmods.peatized.lib;

import mjaroslav.mcmods.peatized.common.registry.PeatizedBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class PeatizedTab extends CreativeTabs {
    public static final PeatizedTab INSTANCE = new PeatizedTab();

    private PeatizedTab() {
        super(ModInfo.MOD_ID);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(PeatizedBlocks.BOG_DIRT, 1);
    }
}
