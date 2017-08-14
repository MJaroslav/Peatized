package mjaroslav.mcmods.peatized.common.init;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import mjaroslav.mcmods.fishingcontroller.api.FishingControllerApi;
import mjaroslav.mcmods.peatized.common.config.PeatizedConfig;
import mjaroslav.mcmods.peatized.common.world.BlockSludgeGenerator;
import mjaroslav.mcmods.peatized.common.world.PeathouseGenerator;
import mjaroslav.mcmods.peatized.common.world.VillagePeatmanManager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.FishingHooks.FishableCategory;

public class PeatizedWorldGen implements IInitBase {
	public static final String PEATHOUSE_CHEST = "peathouse";

	public static WeightedRandomChestContent[] peathouseLoot = new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(PeatizedItems.resource, 1, 1, 8, 10),
			new WeightedRandomChestContent(PeatizedItems.resource, 0, 8, 16, 10),
			new WeightedRandomChestContent(PeatizedItems.resource, 2, 2, 6, 10),
			new WeightedRandomChestContent(Items.stick, 0, 1, 16, 10),
			new WeightedRandomChestContent(Item.getItemFromBlock(PeatizedBlocks.bogDirtGenerated), 0, 1, 5, 5),
			new WeightedRandomChestContent(Item.getItemFromBlock(PeatizedBlocks.peat), 0, 1, 10, 5),
			new WeightedRandomChestContent(Item.getItemFromBlock(PeatizedBlocks.peat), 1, 1, 10, 3) };
	public static ChestGenHooks peathouseChestHook = new ChestGenHooks(PEATHOUSE_CHEST, peathouseLoot, 3, 9);
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
	}

	@Override
	public void init(FMLInitializationEvent event) {
		VillagePeatmanManager peatman = new VillagePeatmanManager();
		VillagerRegistry.instance().registerVillagerId(PeatizedConfig.villagerId);
		VillagerRegistry.instance().registerVillageTradeHandler(PeatizedConfig.villagerId, peatman);
		GameRegistry.registerWorldGenerator(new BlockSludgeGenerator(PeatizedBlocks.bogDirtGenerated, 0, 5), 0);
		GameRegistry.registerWorldGenerator(new PeathouseGenerator(), 1);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		FishingControllerApi.addItemToCategory(new ItemStack(PeatizedItems.resource, 1, 0),
				FishingControllerApi.rarityJunk, FishableCategory.JUNK);
		FishingControllerApi.addItemToCategory(new ItemStack(Items.clay_ball, 1), FishingControllerApi.rarityJunk,
				FishableCategory.JUNK);
		FishingControllerApi.addItemToCategory(new ItemStack(PeatizedItems.resource, 1, 1),
				FishingControllerApi.rarityTreasure, FishableCategory.TREASURE);
	}
}
