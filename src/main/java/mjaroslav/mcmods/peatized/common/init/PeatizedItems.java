package mjaroslav.mcmods.peatized.common.init;

import cpw.mods.fml.common.registry.GameRegistry;
import mjaroslav.mcmods.peatized.common.item.ItemResource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class PeatizedItems {
	public static Item resource = new ItemResource().setUnlocalizedName("peatized.resource");

	public static void init() {
		GameRegistry.registerItem(resource, "resource");
		OreDictionary.registerOre("ballPeat", new ItemStack(resource, 1, 0));
		OreDictionary.registerOre("brickPeat", new ItemStack(resource, 1, 1));
		OreDictionary.registerOre("platePeat", new ItemStack(resource, 1, 2));
	}
}
