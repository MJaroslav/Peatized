package mjaroslav.mcmods.peatized.common.integration.waila;

import mcp.mobius.waila.api.IWailaRegistrar;
import mjaroslav.mcmods.peatized.common.block.BlockBogDirt;

public class PeatizedWaila {
	public static void onWailaCalled(IWailaRegistrar registrar) {
		registrar.registerStackProvider(new WailaBogDirtHandler(), BlockBogDirt.class);
		registrar.registerBodyProvider(new WailaBogDirtHandler(), BlockBogDirt.class);
	}
}
