package mjaroslav.mcmods.peatized.common.integration.nei;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import codechicken.nei.NEIClientUtils;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import mjaroslav.mcmods.peatized.PeatizedMod;
import mjaroslav.mcmods.peatized.client.gui.inventory.GuiCompressor;
import mjaroslav.mcmods.peatized.common.item.crafting.CompressorRecipes;
import mjaroslav.mcmods.peatized.common.item.crafting.CompressorRecipes.CompressorRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class CompressorRecipeHandler extends TemplateRecipeHandler {

	@Override
	public String getRecipeName() {
		return NEIClientUtils.translate("recipe.compressor", new Object[0]);
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiCompressor.class;
	}

	@Override
	public void loadTransferRects() {
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(97, 2, 64, 64), "compressing",
				new Object[0]));
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ((outputId.equals("compressing")) && (super.getClass() == CompressorRecipeHandler.class)) {
			Map<ItemStack, CompressorRecipe> recipes = CompressorRecipes.compressing().getRecipeList();
			for (Map.Entry<ItemStack, CompressorRecipe> recipe : recipes.entrySet()) {
				this.arecipes.add(new CompressingPair(recipe.getKey(), recipe.getValue()));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		Map<ItemStack, CompressorRecipe> recipes = CompressorRecipes.compressing().getRecipeList();
		for (Map.Entry<ItemStack, CompressorRecipe> recipe : recipes.entrySet())
			if (NEIServerUtils.areStacksSameType(recipe.getValue().result, result))
				this.arecipes.add(new CompressingPair(recipe.getKey(), recipe.getValue()));
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		Map<ItemStack, CompressorRecipe> recipes = CompressorRecipes.compressing().getRecipeList();
		for (Map.Entry<ItemStack, CompressorRecipe> recipe : recipes.entrySet())
			if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getKey(), ingredient)) {
				CompressingPair arecipe = new CompressingPair(recipe.getKey(), recipe.getValue());
				arecipe.setIngredientPermutation(Arrays.asList(new PositionedStack[] { arecipe.ingred }), ingredient);
				this.arecipes.add(arecipe);
			}
	}

	@Override
	public String getGuiTexture() {
		return new ResourceLocation(PeatizedMod.MODID + ":textures/gui/container/compressor.png").toString();
	}

	@Override
	public void drawExtras(int recipe) {
		drawProgressBar(104, 8, 176, 0, 48, 48, 48, 1);
		FontRenderer fonts = Minecraft.getMinecraft().fontRenderer;
		String s = String.valueOf(((CompressingPair) this.arecipes.get(recipe)).jumps);
		fonts.drawString(s, 64, 31, 4210752);
		s = StatCollector.translateToLocal("nei.recipe.compressor.auto")
				+ String.valueOf(((CompressingPair) this.arecipes.get(recipe)).automatic);
		fonts.drawString(s, 12, -1, 4210752);
	}

	@Override
	public String getOverlayIdentifier() {
		return "compressing";
	}

	public class CompressingPair extends TemplateRecipeHandler.CachedRecipe {
		PositionedStack ingred;
		PositionedStack result;
		public int jumps;
		boolean automatic;

		public CompressingPair(ItemStack ingred, CompressorRecipe recipe) {
			super();
			this.ingred = new PositionedStack(ingred, 39, 10);
			this.result = new PositionedStack(recipe.result, 39, 36);
			this.jumps = recipe.jumps;
			this.automatic = recipe.autimatic;
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(CompressorRecipeHandler.this.cycleticks / 48,
					Arrays.asList(new PositionedStack[] { this.ingred }));
		}

		@Override
		public PositionedStack getResult() {
			return this.result;
		}
	}
}
