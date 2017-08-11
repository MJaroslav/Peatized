package mjaroslav.mcmods.peatized.common.utils;

import cpw.mods.fml.common.IFuelHandler;
import mjaroslav.mcmods.peatized.common.item.ItemResource;
import net.minecraft.item.ItemStack;

public class PeatizedFuelHandler implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {
		if (fuel != null && fuel.getItem() != null) {
			if (fuel.getItem() instanceof ItemResource) {
				int meta = fuel.getItemDamage();
				switch (meta) {
				case 0:
					return GameUtils.getTicksFromSmelting(1);
				case 1:
					return GameUtils.getTicksFromSmelting(10);
				case 2:
					return GameUtils.getTicksFromSmelting(2.5F);
				default:
					break;
				}
			}
		}
		return 0;
	}

}
