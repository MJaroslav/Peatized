package mjaroslav.mcmods.peatized.common.init;

import java.awt.Color;

import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.registry.GameRegistry;
import mjaroslav.mcmods.mjutils.lib.module.IModule;
import mjaroslav.mcmods.mjutils.lib.module.ModModule;
import mjaroslav.mcmods.peatized.common.item.ItemCleaver;
import mjaroslav.mcmods.peatized.common.item.ItemResource;
import mjaroslav.mcmods.peatized.lib.CategoryGeneralInfo.CategoryCleaversInfo.CategoryCooldownInfo;
import mjaroslav.mcmods.peatized.lib.ModInfo;
import mjaroslav.utils.UtilsColor;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

@ModModule(modid = ModInfo.MODID)
public class PeatizedItems implements IModule {
    public static final ToolMaterial BRONZE = EnumHelper.addToolMaterial("PT_BRONZE", 2, 350, 6.0F, 2.0F, 13);

    public static Item resource = new ItemResource().setUnlocalizedName("peatized.resource");
    public static Item cleaverIron;
    public static Item cleaverDiamond;
    public static Item cleaverGold;
    public static Item cleaverRena;
    public static Item cleaverBronze;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        cleaverIron = new ItemCleaver(ToolMaterial.IRON, Color.white, CategoryCooldownInfo.iron)
                .setUnlocalizedName("peatized.cleaver.iron");
        cleaverDiamond = new ItemCleaver(ToolMaterial.EMERALD, UtilsColor.getColor("00FFFF"),
                CategoryCooldownInfo.diamond).setUnlocalizedName("peatized.cleaver.diamond");
        cleaverGold = new ItemCleaver(ToolMaterial.GOLD, UtilsColor.getColor("FFD800"), CategoryCooldownInfo.gold)
                .setUnlocalizedName("peatized.cleaver.gold");
        cleaverRena = new ItemCleaver(ToolMaterial.IRON, true, Color.white, CategoryCooldownInfo.rena)
                .setUnlocalizedName("peatized.cleaver.rena");
        cleaverBronze = new ItemCleaver(BRONZE, UtilsColor.getColor("FF9600"), CategoryCooldownInfo.bronze)
                .setUnlocalizedName("peatized.cleaver.bronze");

        GameRegistry.registerItem(resource, "resource");
        GameRegistry.registerItem(cleaverIron, "cleaver_iron");
        GameRegistry.registerItem(cleaverDiamond, "cleaver_diamond");
        GameRegistry.registerItem(cleaverGold, "cleaver_gold");
        GameRegistry.registerItem(cleaverRena, "cleaver_rena");
        GameRegistry.registerItem(cleaverBronze, "cleaver_bronze");
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
