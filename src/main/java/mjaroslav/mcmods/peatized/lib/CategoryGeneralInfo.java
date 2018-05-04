package mjaroslav.mcmods.peatized.lib;

import mjaroslav.mcmods.mjutils.lib.module.ConfigCategory;
import mjaroslav.mcmods.mjutils.lib.module.ConfigField;

@ConfigCategory(modid = ModInfo.MODID, name = CategoryGeneralInfo.NAME, comment = CategoryGeneralInfo.COMMENT)
public class CategoryGeneralInfo {
	public static final String NAME = "general";
	public static final String COMMENT = "Main options.";

	@ConfigField(defaultInt = 193, minInt = 20, maxInt = 256, comment = "Peat man villager id.")
	public static int villagerId;

	@ConfigCategory(modid = ModInfo.MODID, name = CategoryGenerationInfo.NAME, comment = CategoryGenerationInfo.COMMENT)
	public static class CategoryGenerationInfo {
		public static final String NAME = CategoryGeneralInfo.NAME + ".generation";
		public static final String COMMENT = "World generation settings.";

		@ConfigField(defaultBoolean = true, comment = "Generate bog dirt in swamp.")
		public static boolean generateBogDirt;
		@ConfigField(defaultInt = 2, minInt = 0, maxInt = 100, comment = "Chance of peathouse generation on chunk. 0 - disable.")
		public static int peathousePercentChance;
	}

	@ConfigCategory(modid = ModInfo.MODID, name = CategoryPeatgrowthingInfo.NAME, comment = CategoryPeatgrowthingInfo.COMMENT)
	public static class CategoryPeatgrowthingInfo {
		public static final String NAME = CategoryGeneralInfo.NAME + ".peatgrowthing";
		public static final String COMMENT = "Peat moisturizing settings.";

		@ConfigField(defaultInt = 8, minInt = 1, maxInt = 1000, comment = "Base chance of 'growth' bog dirt (Less is better).")
		public static int baseChance;
		@ConfigField(defaultInt = -2, minInt = -1000, maxInt = 1000, comment = "The max number of water material blocks that affect the 'growth' of bog dirt.")
		public static int biomeSummand;
		@ConfigField(defaultInt = 20, minInt = 1, maxInt = 124, comment = "Divisor of the water count. The number obtained is equal to the number of times that a water summand is added to the bog dirt 'growth' chance.")
		public static int reqWaterMax;
		@ConfigField(defaultInt = 2, minInt = 1, maxInt = 124, comment = "Divisor of the max water count. The number obtained is equal to the number of times that a water summand is added to the bog dirt 'growth' chance.")
		public static int waterSteps;
		@ConfigField(defaultInt = -1, minInt = -1000, maxInt = 1000, comment = "Adds to the chance of bog dirt 'growth'. Is added for each step from the amount of water (Less is better).")
		public static int waterStepSummand;
		@ConfigField(defaultInt = -1, minInt = -1000, maxInt = 1000, comment = "Add to the chance of bog dirt 'growth', if it's raining and theblock is open to the sky.")
		public static int rainSummand;
	}

	@ConfigCategory(modid = ModInfo.MODID, name = CategoryGraphicsInfo.NAME, comment = CategoryGraphicsInfo.COMMENT)
	public static class CategoryGraphicsInfo {
		public static final String NAME = CategoryGeneralInfo.NAME + ".graphics";
		public static final String COMMENT = "Cosmetic settings.";

		@ConfigField(defaultBoolean = true, comment = "Use an alternative Rena quote (Easter egg).")
		public static boolean altRenaQuote;
		@ConfigField(defaultBoolean = true, comment = "Render blood on cleavers.")
		public static boolean renderBlood;
		@ConfigField(comment = "Use alternative blood texture.")
		public static boolean altBlood;
	}
}
