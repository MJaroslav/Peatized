package mjaroslav.mcmods.peatized.common.world;

import java.util.Random;

import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;
import mjaroslav.mcmods.peatized.common.config.PeatizedConfig;
import mjaroslav.mcmods.peatized.common.init.PeatizedBlocks;
import mjaroslav.mcmods.peatized.common.init.PeatizedItems;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class VillagePeatmanManager implements IVillageTradeHandler {
	@Override
	public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random) {
		if (villager.getProfession() == PeatizedConfig.villagerId) {
			for (int count = 1; count < 5; count++) {
				recipeList.add(new MerchantRecipe(new ItemStack(PeatizedBlocks.peat, 8 + random.nextInt(3), 0),
						new ItemStack(Items.emerald, 1, 0),
						new ItemStack(PeatizedBlocks.peat, count * 2 + random.nextInt(3), 1)));
				recipeList
						.add(new MerchantRecipe(new ItemStack(PeatizedItems.resource, count * 2 + random.nextInt(3), 2),
								new ItemStack(PeatizedItems.resource, count + random.nextInt(3), 1)));
				recipeList.add(new MerchantRecipe(new ItemStack(PeatizedItems.resource, 5 + random.nextInt(6)),
						new ItemStack(Items.emerald, 1, 0)));
			}
		}
	}
}
