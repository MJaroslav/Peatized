package mjaroslav.mcmods.peatized.lib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModInfo {
    public static final String MODID = "peatized";
    public static final String NAME = "Peatized";
    public static final String VERSION = "1.0.0";
    public static final String DEPENDENCIES = "required-after:mjutils@[1.4.0,);";
    public static final String GUIFACTORY = "mjaroslav.mcmods.peatized.client.gui.PeatizedGuiFactory";
    public static final String SERVERPROXY = "mjaroslav.mcmods.peatized.common.PeatizedCommonProxy";
    public static final String CLIENTPROXY = "mjaroslav.mcmods.peatized.client.PeatizedClientProxy";

    public static final Logger LOG = LogManager.getLogger(NAME);
}
