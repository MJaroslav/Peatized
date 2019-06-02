package mjaroslav.mcmods.peatized.common.item;

import mjaroslav.mcmods.peatized.common.lib.ItemNames;
import net.minecraft.item.ItemStack;

public class ItemPeatBall extends ItemBase {
    public ItemPeatBall() {
        super(ItemNames.PEAT_BALL);
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack) {
        return 1000;
    }
}
