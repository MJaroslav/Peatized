package mjaroslav.mcmods.peatized.common.config;

import org.apache.logging.log4j.Logger;

import mjaroslav.mcmods.mjutils.common.objects.ConfigurationBase;
import mjaroslav.mcmods.peatized.PeatizedMod;
import net.minecraftforge.common.config.Configuration;

public class PeatizedConfig extends ConfigurationBase {
	private static Configuration instance;

	public static final String categoryGeneral = "general";

	public static int baseChance;
	public static int biomeSummand;
	public static int reqWaterMax;
	public static int waterSteps;
	public static int waterStepSummand;
	public static int rainSummand;
	public static boolean generateBogDirt;
	public static int villagerId;
	public static int peathousePercentChance;
	public static boolean altRenaQuote;
	public static boolean renderBlood;

	public static String configFolder;

	@Override
	public Configuration getInstance() {
		return this.instance;
	}

	@Override
	public void setInstance(Configuration newConfig) {
		this.instance = newConfig;
	}

	@Override
	public String getModId() {
		return PeatizedMod.MODID;
	}

	@Override
	public Logger getLogger() {
		return PeatizedMod.log;
	}

	@Override
	public void readFields() {
		instance.addCustomCategoryComment(categoryGeneral, "Mod options");

		baseChance = instance.getInt("base_chance", categoryGeneral, 8, 1, 1000,
				"Base chance of 'growth' bog dirt (Less is better)");
		biomeSummand = instance.getInt("biome_summand", categoryGeneral, -2, -1000, 1000,
				"The max number of water material blocks that affect the 'growth' of bog dirt");
		reqWaterMax = instance.getInt("req_water_max", categoryGeneral, 20, 1, 124,
				"Divisor of the water count. The number obtained is equal to the number of times that a water summand is added to the bog dirt 'growth' chance");
		waterSteps = instance.getInt("water_steps", categoryGeneral, 2, 1, 124,
				"Divisor of the max water count. The number obtained is equal to the number of times that a water summand is added to the bog dirt 'growth' chance");
		waterStepSummand = instance.getInt("water_step_summand", categoryGeneral, -1, -1000, 1000,
				"Adds to the chance of bog dirt 'growth'. Is added for each step from the amount of water (Less is better)");
		rainSummand = instance.getInt("rain_summand", categoryGeneral, -1, -1000, 1000,
				"Add to the chance of bog dirt 'growth', if it's raining and theblock is open to the sky");
		generateBogDirt = instance.getBoolean("generate_bog_dirt", categoryGeneral, true, "Generate bog dirt in swamp");
		villagerId = instance.getInt("villager_id", categoryGeneral, 193, 10, 256, "Peat man villager id");
		peathousePercentChance = instance.getInt("peathome_percent_chance", categoryGeneral, 2, 0, 100,
				"Chance of peathouse generation on chunk. 0 - disable");
		altRenaQuote = instance.getBoolean("alt_rana_quote", categoryGeneral, true,
				"Use an alternative Rena quote (Easter egg)");
		renderBlood = instance.getBoolean("render_blood", categoryGeneral, true, "Render blood on cleavers.");
	}
}
