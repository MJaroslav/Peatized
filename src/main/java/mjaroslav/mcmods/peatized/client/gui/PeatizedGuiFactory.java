package mjaroslav.mcmods.peatized.client.gui;

import java.util.Set;

import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.config.GuiConfig;
import mjaroslav.mcmods.peatized.PeatizedMod;
import mjaroslav.mcmods.peatized.common.config.PeatizedConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

public class PeatizedGuiFactory implements IModGuiFactory {
	@Override
	public void initialize(Minecraft minecraftInstance) {
	}

	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass() {
		return PeatizedGuiConfig.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}

	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
		return null;
	}

	public static class PeatizedGuiConfig extends GuiConfig {
		public PeatizedGuiConfig(GuiScreen parentScreen) {
			super(parentScreen,
					new ConfigElement(PeatizedMod.config.getInstance().getCategory(PeatizedConfig.categoryGeneral))
							.getChildElements(),
					PeatizedMod.MODID, false, false, PeatizedMod.NAME);
		}
	}

}