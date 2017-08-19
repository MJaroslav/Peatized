package mjaroslav.mcmods.peatized.common.init;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import mjaroslav.mcmods.peatized.common.item.ItemResource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class PeatizedItems implements IInitBase {
	public static Item resource = new ItemResource().setUnlocalizedName("peatized.resource");

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		GameRegistry.registerItem(resource, "resource");
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
