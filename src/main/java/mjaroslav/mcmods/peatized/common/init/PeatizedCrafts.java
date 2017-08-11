package mjaroslav.mcmods.peatized.common.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class PeatizedCrafts {
	public static void init() {
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.stone_slab, 2, 0),
				new ItemStack(Blocks.double_stone_slab, 1, 8));
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.stone_slab, 2, 1),
				new ItemStack(Blocks.double_stone_slab, 1, 9));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PeatizedBlocks.compressor, 1, 0), "SPS", "SHS", "SOS",
				'S', "stone", 'P', Blocks.piston, 'H', Blocks.hopper, 'O', Blocks.obsidian));
		if (OreDictionary.getOres("gearStone").size() > 0)
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PeatizedBlocks.compressor, 1, 1), "CHC", "rBr",
					"GRG", 'C', Blocks.hardened_clay, 'H', Blocks.hopper, 'r', "dustRedstone", 'R', "blockRedstone",
					'G', "gearStone", 'B', new ItemStack(PeatizedBlocks.compressor, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PeatizedBlocks.peat, 8, 0), "BBB", "BPB", "BBB", 'B',
				new ItemStack(Blocks.stonebrick, 1, 0), 'P', "platePeat"));
		GameRegistry.addShapedRecipe(new ItemStack(PeatizedBlocks.peatStairs, 4, 0), "B  ", "BB ", "BBB", 'B',
				new ItemStack(PeatizedBlocks.peat, 1, 0));
		GameRegistry.addShapedRecipe(new ItemStack(PeatizedBlocks.peatSlab, 6, 0), "BBB", 'B',
				new ItemStack(PeatizedBlocks.peat, 1, 0));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PeatizedBlocks.peat, 8, 1), "BBB", "BPB", "BBB", 'B',
				new ItemStack(Blocks.stonebrick, 1, 3), 'P', "platePeat"));
	}
}
