package mjaroslav.mcmods.peatized.common.creativetab;

import mjaroslav.mcmods.peatized.common.init.PeatizedItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class PeatizedTab extends CreativeTabs {
    public PeatizedTab(String lable) {
        super(lable);
    }

    @Override
    public Item getTabIconItem() {
        return PeatizedItems.resource;
    }
}
