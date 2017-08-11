package mjaroslav.mcmods.peatized.common.integration.forestry;

import forestry.api.fuels.EngineCopperFuel;
import forestry.api.fuels.FuelManager;
import forestry.plugins.PluginCore;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class PeatizedForestry {
	public static void init() {
		for (ItemStack peatBall : OreDictionary.getOres("ballPeat")) {
			FuelManager.copperEngineFuel.put(peatBall, new EngineCopperFuel(peatBall, 10, 500));
		}
		for (ItemStack peatBrick : OreDictionary.getOres("brickPeat")) {
			if (peatBrick.getItem() != (PluginCore.items.peat.getItemStack().getItem()))
				FuelManager.copperEngineFuel.put(peatBrick, new EngineCopperFuel(peatBrick, 10, 5000));
		}
		for (ItemStack peatPlate : OreDictionary.getOres("platePeat")) {
			FuelManager.copperEngineFuel.put(peatPlate, new EngineCopperFuel(peatPlate, 10, 2000));
		}
	}
}
