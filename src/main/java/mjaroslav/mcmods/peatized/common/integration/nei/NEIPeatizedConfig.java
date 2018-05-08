package mjaroslav.mcmods.peatized.common.integration.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import mjaroslav.mcmods.peatized.client.gui.inventory.GuiCompressor;

public class NEIPeatizedConfig implements IConfigureNEI {

    @Override
    public String getName() {
        return "Peatized NEI plugin";
    }

    @Override
    public String getVersion() {
        return "1.7.10-1";
    }

    @Override
    public void loadConfig() {
        API.registerRecipeHandler(new CompressorRecipeHandler());
        API.registerUsageHandler(new CompressorRecipeHandler());
        API.setGuiOffset(GuiCompressor.class, 0, 0);
    }
}
