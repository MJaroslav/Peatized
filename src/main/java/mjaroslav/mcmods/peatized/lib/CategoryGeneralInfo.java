package mjaroslav.mcmods.peatized.lib;

import mjaroslav.mcmods.mjutils.lib.module.ConfigCategory;
import mjaroslav.mcmods.mjutils.lib.module.ConfigField;

@ConfigCategory(modid = ModInfo.MODID, name = CategoryGeneralInfo.NAME, comment = CategoryGeneralInfo.COMMENT)
public class CategoryGeneralInfo {
    public static final String NAME = "general";
    public static final String COMMENT = "Main options.";

    @ConfigField(defaultInt = 193, minInt = 20, maxInt = 256, comment = "Peat man villager id.", requiresMcRestart = true)
    public static int villagerId;

    @ConfigCategory(modid = ModInfo.MODID, name = CategoryCleaversInfo.NAME, comment = CategoryCleaversInfo.COMMENT)
    public static class CategoryCleaversInfo {
        public static final String NAME = CategoryGeneralInfo.NAME + ".cleavers";
        public static final String COMMENT = "Cleavers settings.";

        @ConfigField(defaultInt = 10, minInt = 1, maxInt = 60, comment = "Time of blood render on cleaver (is seconds).")
        public static int bloodTime;

        @ConfigCategory(modid = ModInfo.MODID, name = CategoryCooldownInfo.NAME, comment = CategoryCooldownInfo.COMMENT, requiresMcRestart = true)
        public static class CategoryCooldownInfo {
            public static final String NAME = CategoryCleaversInfo.NAME + ".cooldown";
            public static final String COMMENT = "Cleavers AOE cooldown settings.";

            @ConfigField(defaultInt = 6, minInt = 1, maxInt = 30, comment = "Cooldown for iron cleaver (in seconds).")
            public static int iron;
            @ConfigField(defaultInt = 8, minInt = 1, maxInt = 30, comment = "Cooldown for gold cleaver (in seconds).")
            public static int gold;
            @ConfigField(defaultInt = 5, minInt = 1, maxInt = 30, comment = "Cooldown for diamond cleaver (in seconds).")
            public static int diamond;
            @ConfigField(defaultInt = 5, minInt = 1, maxInt = 30, comment = "Cooldown for bronze cleaver (in seconds).")
            public static int bronze;
            @ConfigField(defaultInt = 6, minInt = 1, maxInt = 30, comment = "Cooldown for Rena's cleaver (in seconds).")
            public static int rena;
        }
    }

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
        @ConfigField(defaultBoolean = true, comment = "Always render blood on Rena's cleaver.")
        public static boolean renaAlwaysBlood;
        @ConfigField(minInt = 0, maxInt = 1, comment = "Mode of upa render. 0 - 8 models, 1 - shader.")
        public static int upaItemRenderMode;
        @ConfigField(minInt = 0, maxInt = 2, comment = "Mode of upa render. 0 - 8 models, 1 - shader, 2 - itemStack.")
        public static int upaRenderMode;
    }
}
