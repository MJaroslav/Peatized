package mjaroslav.mcmods.peatized.common.init;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import mjaroslav.mcmods.peatized.PeatizedMod;
import mjaroslav.mcmods.peatized.common.item.ItemRenaWeapon;
import mjaroslav.mcmods.peatized.common.item.ItemResource;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class PeatizedItems implements IInitBase {
	public static Item resource = new ItemResource().setUnlocalizedName("peatized.resource");
	public static Item cleaver = new ItemRenaWeapon(ToolMaterial.IRON).setTextureName("minecraft:iron_sword")
			.setUnlocalizedName("peatized.cleaver");
	public static Item cleaverRena = new ItemRenaWeapon(PeatizedMod.rena).setTextureName("minecraft:iron_sword")
			.setUnlocalizedName("peatized.cleaver.rena");

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		GameRegistry.registerItem(resource, "resource");
		GameRegistry.registerItem(cleaver, "cleaver");
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
}
