package mjaroslav.mcmods.peatized.client.gui;

import cpw.mods.fml.client.config.GuiConfig;
import mjaroslav.mcmods.peatized.PeatizedMod;
import mjaroslav.mcmods.peatized.common.config.PeatizedConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

public class PeatizedGuiConfig extends GuiConfig {
	public PeatizedGuiConfig(GuiScreen parentScreen) {
		super(parentScreen,
				new ConfigElement(PeatizedConfig.config.getCategory(PeatizedConfig.categoryGeneral)).getChildElements(),
				PeatizedMod.MODID, false, false, PeatizedMod.NAME);
	}
}
