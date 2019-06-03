package mjaroslav.mcmods.peatized.common;

import mjaroslav.mcmods.peatized.common.util.Configurator;
import net.minecraftforge.common.config.Configuration;

public class CommonConfigurator extends Configurator {
    public static int PEAT_BALL_BURN_TIME;
    public static boolean FORESTRY;
    public static int FORESTRY_PEAT_BALL_DURATION;
    public static int FORESTRY_PEAT_BALL_POWER;

    @Override
    protected void initFields(Configuration instance) {
        instance.addCustomCategoryComment("general", "Peatized mod options.");
        instance.addCustomCategoryComment("general.fuels", "Furnace fuel options.");
        PEAT_BALL_BURN_TIME = instance.getInt("peat_ball", "general.fuels", 1000, 0, Integer.MAX_VALUE,
                "Peat ball burn time. Set zero for disable.");
        instance.addCustomCategoryComment("general.integrations", "Mod integration options.");
        instance.addCustomCategoryComment("general.integrations.forestry", "Forestry integration options.");
        FORESTRY = instance.get("general.integrations.forestry", "enabled", true, "Enable forestry integration.")
                .setRequiresMcRestart(true).getBoolean();
        FORESTRY_PEAT_BALL_DURATION = instance.get("general.integrations.forestry", "peat_ball_engine_duration", 1250,
                "Burn time in ticks of peat ball in forestry peat engine. Set zero for disable.", 0, Integer.MAX_VALUE)
                .setRequiresMcRestart(true).getInt();
        FORESTRY_PEAT_BALL_POWER = instance.get("general.integrations.forestry", "peat_ball_engine_power", 10,
                "RF per tick from peat ball in forestry peat engine.", 1, Integer.MAX_VALUE).setRequiresMcRestart(true)
                .getInt();
    }
}
