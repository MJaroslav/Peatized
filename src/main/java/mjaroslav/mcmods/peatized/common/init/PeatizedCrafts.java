package mjaroslav.mcmods.peatized.common.init;

import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.registry.GameRegistry;
import mjaroslav.mcmods.mjutils.lib.handler.AnvilRecipe;
import mjaroslav.mcmods.mjutils.lib.module.IModule;
import mjaroslav.mcmods.mjutils.lib.module.ModModule;
import mjaroslav.mcmods.mjutils.lib.utils.UtilsAnvil;
import mjaroslav.mcmods.mjutils.lib.utils.UtilsFuel;
import mjaroslav.mcmods.peatized.common.item.crafting.CompressorRecipes;
import mjaroslav.mcmods.peatized.lib.ModInfo;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

@ModModule(modid = ModInfo.MODID)
public class PeatizedCrafts implements IModule {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
    }

    @Override
    public void init(FMLInitializationEvent event) {
        OreDictionary.registerOre("bogDirt", PeatizedBlocks.bogDirt);
        OreDictionary.registerOre("bogDirt", PeatizedBlocks.bogDirtGenerated);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
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
        GameRegistry.addRecipe(
                new ShapedOreRecipe(PeatizedItems.cleaverIron, "MM", "M ", "S ", 'M', "ingotIron", 'S', "stickWood"));
        GameRegistry.addRecipe(
                new ShapedOreRecipe(PeatizedItems.cleaverGold, "MM", "M ", "S ", 'M', "ingotGold", 'S', "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(PeatizedItems.cleaverDiamond, "MM", "M ", "S ", 'M', "gemDiamond",
                'S', "stickWood"));
        if (OreDictionary.doesOreNameExist("ingotBronze"))
            GameRegistry.addRecipe(new ShapedOreRecipe(PeatizedItems.cleaverBronze, "MM", "M ", "S ", 'M',
                    "ingotBronze", 'S', "stickWood"));
        UtilsFuel.addFuel(new ItemStack(PeatizedItems.resource, 1, 0), 1F);
        UtilsFuel.addFuel(new ItemStack(PeatizedItems.resource, 1, 1), 10F);
        UtilsFuel.addFuel(new ItemStack(PeatizedItems.resource, 1, 2), 2.5F);
        UtilsAnvil.addRecipe(new ItemStack(PeatizedItems.cleaverRena, 1), new AnvilRecipe(
                new ItemStack(PeatizedItems.cleaverIron, 1, 0), new ItemStack(Items.nether_star, 1, 0), 30));
    }

    @Override
    public String getModuleName() {
        return "Crafts";
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public boolean canLoad() {
        return true;
    }

    @Override
    public String[] modDependencies() {
        return null;
    }
}
