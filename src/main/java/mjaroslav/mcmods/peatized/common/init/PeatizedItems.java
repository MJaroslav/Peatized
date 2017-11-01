package mjaroslav.mcmods.peatized.common.init;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import mjaroslav.mcmods.mjutils.common.objects.IModModule;
import mjaroslav.mcmods.mjutils.common.objects.ModInitModule;
import mjaroslav.mcmods.mjutils.common.utils.ColorUtils;
import mjaroslav.mcmods.peatized.PeatizedMod;
import mjaroslav.mcmods.peatized.common.item.ItemCleaver;
import mjaroslav.mcmods.peatized.common.item.ItemResource;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

@ModInitModule(modid = PeatizedMod.MODID)
public class PeatizedItems implements IModModule {
	public static Item resource = new ItemResource().setUnlocalizedName("peatized.resource");
	public static Item cleaverIron = new ItemCleaver(ToolMaterial.IRON).setTextureName("minecraft:iron_sword")
			.setUnlocalizedName("peatized.cleaver.iron");
	public static Item cleaverDiamond = new ItemCleaver(ToolMaterial.EMERALD, ColorUtils.getColorInt("00FFFF"))
			.setTextureName("minecraft:diamond_sword").setUnlocalizedName("peatized.cleaver.diamond");
	public static Item cleaverGold = new ItemCleaver(ToolMaterial.GOLD, ColorUtils.getColorInt("FFD800"))
			.setTextureName("minecraft:gold_sword").setUnlocalizedName("peatized.cleaver.gold");
	public static Item cleaverRena = new ItemCleaver(PeatizedMod.rena).setTextureName("minecraft:iron_sword")
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
}
