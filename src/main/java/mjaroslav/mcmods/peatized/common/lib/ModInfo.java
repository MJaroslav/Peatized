package mjaroslav.mcmods.peatized.common.lib;

public class ModInfo {
    public static final String MOD_ID = "peatized";
    public static final String NAME = "Peatized";
    public static final String VERSION = "@VERSION@";

    public static final String CLIENT_PROXY = "mjaroslav.mcmods.peatized.client.ClientProxy";
    public static final String COMMON_PROXY = "mjaroslav.mcmods.peatized.common.CommonProxy";
//    public static final String SERVER_PROXY = "mjaroslav.mcmods.peatized.server.ServerProxy";

    public static final String CLIENT_CONFIGURATOR = "mjaroslav.mcmods.peatized.client.ClientConfigurator";
    public static final String COMMON_CONFIGURATOR = "mjaroslav.mcmods.peatized.common.CommonConfigurator";
//    public static final String SERVER_CONFIGURATOR = "mjaroslav.mcmods.peatized.server.ServerConfigurator";

    private static final String UNLOCALIZED_NAME = MOD_ID + ".%s";

    public static String unlocalizedName(String name) {
        return String.format(UNLOCALIZED_NAME, name);
    }
}
