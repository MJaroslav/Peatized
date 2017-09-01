package mjaroslav.mcmods.peatized.common.config;

import java.io.File;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mjaroslav.mcmods.peatized.PeatizedMod;
import mjaroslav.mcmods.peatized.common.init.IInitBase;
import net.minecraftforge.common.config.Configuration;

public class PeatizedConfig implements IInitBase {
	public static Configuration config;

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

	public static String configFolder;

	public static void createOrReadConfig() {
		if (config == null)
			config = new Configuration(new File(configFolder + "/" + PeatizedMod.MODID + ".cfg"));
		try {
			config.load();
		} catch (Exception e) {
			PeatizedMod.log.error("Unable to load configuration!");
		} finally {
			if (config.hasChanged()) {
				config.save();
			}
		}
		sync();
	}

	public static void sync() {
		config.addCustomCategoryComment(categoryGeneral, "Mod options");

		baseChance = config.getInt("base_chance", categoryGeneral, 8, 1, 1000,
				"Base chance of 'growth' bog dirt (Less is better)");
		biomeSummand = config.getInt("biome_summand", categoryGeneral, -2, -1000, 1000,
				"The max number of water material blocks that affect the 'growth' of bog dirt");
		reqWaterMax = config.getInt("req_water_max", categoryGeneral, 20, 1, 124,
				"Divisor of the water count. The number obtained is equal to the number of times that a water summand is added to the bog dirt 'growth' chance");
		waterSteps = config.getInt("water_steps", categoryGeneral, 2, 1, 124,
				"Divisor of the max water count. The number obtained is equal to the number of times that a water summand is added to the bog dirt 'growth' chance");
		waterStepSummand = config.getInt("water_step_summand", categoryGeneral, -1, -1000, 1000,
				"Adds to the chance of bog dirt 'growth'. Is added for each step from the amount of water (Less is better)");
		rainSummand = config.getInt("rain_summand", categoryGeneral, -1, -1000, 1000,
				"Add to the chance of bog dirt 'growth', if it's raining and theblock is open to the sky");
		generateBogDirt = config.getBoolean("generate_bog_dirt", categoryGeneral, true, "Generate bog dirt in swamp");
		villagerId = config.getInt("villager_id", categoryGeneral, 193, 10, 256, "Peat man villager id");
		peathousePercentChance = config.getInt("peathome_percent_chance", categoryGeneral, 2, 0, 100,
				"Chance of peathouse generation on chunk. 0 - disable");
		altRenaQuote = config.getBoolean("alt_rana_quote", categoryGeneral, true,
				"Use an alternative Rena quote (Easter egg)");

		if (config.hasChanged())
			config.save();
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		this.configFolder = event.getModConfigurationDirectory().toString();
		createOrReadConfig();
	}

	@Override
	public void init(FMLInitializationEvent event) {
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
	}
}
