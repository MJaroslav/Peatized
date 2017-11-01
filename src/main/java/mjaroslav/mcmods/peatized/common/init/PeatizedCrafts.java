package mjaroslav.mcmods.peatized.common.init;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import mjaroslav.mcmods.mjutils.common.objects.IModModule;
import mjaroslav.mcmods.mjutils.common.objects.ModInitModule;
import mjaroslav.mcmods.peatized.PeatizedMod;
import mjaroslav.mcmods.peatized.common.item.crafting.CompressorRecipes;
import mjaroslav.mcmods.peatized.common.utils.PeatizedFuelHandler;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

@ModInitModule(modid = PeatizedMod.MODID)
public class PeatizedCrafts implements IModModule {
	@Override
	public void preInit(FMLPreInitializationEvent event) {
	}

	@Override
	public void init(FMLInitializationEvent event) {
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		GameRegistry.registerFuelHandler(new PeatizedFuelHandler());
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.stone_slab, 2, 0),
				new ItemStack(Blocks.double_stone_slab, 1, 8));
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.stone_slab, 2, 1),
				new ItemStack(Blocks.double_stone_slab, 1, 9));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PeatizedBlocks.compressor, 1, 0), "CPC", "CHC", "COC",
				'C', "cobblestone", 'P', Blocks.piston, 'H', Blocks.hopper, 'O', Blocks.obsidian));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PeatizedItems.resource, 1, 3), " C ", "CSC", " C ",
				'C', "cobblestone", 'S', "stone"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PeatizedBlocks.compressor, 1, 1), "CHC", "rBr", "GRG",
				'C', Blocks.hardened_clay, 'H', Blocks.hopper, 'r', "dustRedstone", 'R', "blockRedstone", 'G',
				"gearStone", 'B', new ItemStack(PeatizedBlocks.compressor, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PeatizedBlocks.compressor, 1, 2), "CHC", "rFr", "GBG",
				'C', "cobblestone", 'H', Blocks.hopper, 'r', "dustRedstone", 'G', "gearStone", 'F', Blocks.furnace, 'B',
				new ItemStack(PeatizedBlocks.compressor, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PeatizedBlocks.peat, 8, 0), "BBB", "BPB", "BBB", 'B',
				new ItemStack(Blocks.stonebrick, 1, 0), 'P', "platePeat"));
		GameRegistry.addShapedRecipe(new ItemStack(PeatizedBlocks.peatStairs, 4, 0), "B  ", "BB ", "BBB", 'B',
				new ItemStack(PeatizedBlocks.peat, 1, 0));
		GameRegistry.addShapedRecipe(new ItemStack(PeatizedBlocks.peatSlab, 6, 0), "BBB", 'B',
				new ItemStack(PeatizedBlocks.peat, 1, 0));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PeatizedBlocks.peat, 8, 1), "BBB", "BPB", "BBB", 'B',
				new ItemStack(Blocks.stonebrick, 1, 3), 'P', "platePeat"));
		CompressorRecipes.readFromConfig();
	}

	@Override
	public String getModuleName() {
		return "Crafts";
	}

	@Override
	public int getPriority() {
		return 2;
	}
}
