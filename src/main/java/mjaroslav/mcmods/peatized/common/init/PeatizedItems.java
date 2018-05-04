package mjaroslav.mcmods.peatized.common.init;

import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.registry.GameRegistry;
import mjaroslav.mcmods.mjutils.lib.module.IModule;
import mjaroslav.mcmods.mjutils.lib.module.ModModule;
import mjaroslav.mcmods.peatized.ModPeatized;
import mjaroslav.mcmods.peatized.common.item.ItemCleaver;
import mjaroslav.mcmods.peatized.common.item.ItemResource;
import mjaroslav.mcmods.peatized.lib.ModInfo;
import mjaroslav.utils.UtilsColor;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

@ModModule(modid = ModInfo.MODID)
public class PeatizedItems implements IModule {
    public static Item resource = new ItemResource().setUnlocalizedName("peatized.resource");
    public static Item cleaverIron = new ItemCleaver(ToolMaterial.IRON).setTextureName("minecraft:iron_sword")
            .setUnlocalizedName("peatized.cleaver.iron");
    public static Item cleaverDiamond = new ItemCleaver(ToolMaterial.EMERALD, UtilsColor.getColorInt("00FFFF"))
            .setTextureName("minecraft:diamond_sword").setUnlocalizedName("peatized.cleaver.diamond");
    public static Item cleaverGold = new ItemCleaver(ToolMaterial.GOLD, UtilsColor.getColorInt("FFD800"))
            .setTextureName("minecraft:gold_sword").setUnlocalizedName("peatized.cleaver.gold");
    public static Item cleaverRena = new ItemCleaver(ModPeatized.rena).setTextureName("minecraft:iron_sword")
            .setUnlocalizedName("peatized.cleaver.rena");

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        GameRegistry.registerItem(resource, "resource");
        GameRegistry.registerItem(cleaverIron, "cleaver_iron");
        GameRegistry.registerItem(cleaverDiamond, "cleaver_diamond");
        GameRegistry.registerItem(cleaverGold, "cleaver_gold");
        GameRegistry.registerItem(cleaverRena, "cleaver_rena");
    }

    @Override
    public void init(FMLInitializationEvent event) {
        OreDictionary.registerOre("ballPeat", new ItemStack(resource, 1, 0));
        OreDictionary.registerOre("brickPeat", new ItemStack(resource, 1, 1));
        OreDictionary.registerOre("platePeat", new ItemStack(resource, 1, 2));
        OreDictionary.registerOre("gearStone", new ItemStack(resource, 1, 3));
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
    }

    @Override
    public String getModuleName() {
        return "Items";
    }

    @Override
    public int getPriority() {
        return 1;
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
