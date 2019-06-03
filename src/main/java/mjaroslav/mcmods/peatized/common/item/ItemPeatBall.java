package mjaroslav.mcmods.peatized.common.item;

import mjaroslav.mcmods.peatized.common.CommonConfigurator;
import mjaroslav.mcmods.peatized.common.lib.ItemNames;
import net.minecraft.item.ItemStack;

public class ItemPeatBall extends ItemBase {
    public ItemPeatBall() {
        super(ItemNames.PEAT_BALL);
    }

    @Override
    protected String[] getOreNames() {
        return new String[]{"peatball"};
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack) {
        return CommonConfigurator.PEAT_BALL_BURN_TIME;
    }
}
