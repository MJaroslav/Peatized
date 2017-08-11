package mjaroslav.mcmods.peatized.common.init;

import cpw.mods.fml.common.registry.GameRegistry;
import mjaroslav.mcmods.peatized.common.world.PeatizedWorldGenerator;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

public class PeatizedWorldGen {
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

	public static void init() {
		GameRegistry.registerWorldGenerator(new PeatizedWorldGenerator(), 0);
	}
}
