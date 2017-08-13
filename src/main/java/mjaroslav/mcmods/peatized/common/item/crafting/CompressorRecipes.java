package mjaroslav.mcmods.peatized.common.item.crafting;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.annotations.SerializedName;

import mjaroslav.mcmods.peatized.PeatizedMod;
import mjaroslav.mcmods.peatized.common.config.PeatizedConfig;
import mjaroslav.mcmods.peatized.common.init.PeatizedItems;
import mjaroslav.mcmods.peatized.common.item.crafting.CompressorRecipes.CompressorRecipeJSON.LiteItemStackJSON;
import mjaroslav.mcmods.peatized.common.utils.JSONReader;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;
import net.minecraftforge.oredict.OreDictionary;

public class CompressorRecipes {
	private static final CompressorRecipes compressingBase = new CompressorRecipes();

	public Map<ItemStack, CompressorRecipe> recipeList = new HashMap();
	public Map<ItemStack, CompressorRecipe> recipeListCache = new HashMap();

	private CompressorRecipes() {

	}

	public static CompressorRecipes compressing() {
		return compressingBase;
	}

	private static JSONReader<CompressorRecipeJSON[]> reader = new JSONReader<CompressorRecipeJSON[]>(
			new CompressorRecipeJSON[] {}, CompressorRecipeJSON[].class,
			new File(PeatizedConfig.configFolder + "/" + PeatizedMod.MODID + "_compressor_recipes.json"), true);

	private static CompressorRecipeJSON[] defaultRecipes = new CompressorRecipeJSON[] {};

	public static void setNewDefaults() {
		ArrayList<CompressorRecipeJSON> defaults = new ArrayList<CompressorRecipeJSON>();
		defaults.add(new CompressorRecipeJSON(new LiteItemStackJSON(new ItemStack(PeatizedItems.resource, 9, 0)),
				new LiteItemStackJSON(new ItemStack(PeatizedItems.resource, 1, 1)), 0.15F, 5, false));
		defaults.add(new CompressorRecipeJSON(new LiteItemStackJSON("brickPeat", 1),
				new LiteItemStackJSON(new ItemStack(PeatizedItems.resource, 2, 2)), 0.15F, 3, false));
		defaults.add(new CompressorRecipeJSON(new LiteItemStackJSON(new ItemStack(Items.coal, 64)),
				new LiteItemStackJSON(new ItemStack(Items.diamond, 1)), 1F, 100, false));
		defaults.add(new CompressorRecipeJSON(new LiteItemStackJSON(new ItemStack(Blocks.stone_slab, 2, 0)),
				new LiteItemStackJSON(new ItemStack(Blocks.double_stone_slab, 1, 8)), 0.1F, 4, false));
		defaults.add(new CompressorRecipeJSON(new LiteItemStackJSON(new ItemStack(Blocks.stone_slab, 2, 1)),
				new LiteItemStackJSON(new ItemStack(Blocks.double_stone_slab, 1, 9)), 0.1F, 4, false));
		defaults.add(new CompressorRecipeJSON(new LiteItemStackJSON(new ItemStack(Items.blaze_powder, 7)),
				new LiteItemStackJSON(new ItemStack(Items.blaze_rod, 1)), 0.5F, 4, false));
		defaults.add(new CompressorRecipeJSON(new LiteItemStackJSON("dustRedstone", 9),
				new LiteItemStackJSON("blockRedstone", 1), 0.5F, 4, false));
		defaults.add(new CompressorRecipeJSON(new LiteItemStackJSON("dustTinyTin", 9),
				new LiteItemStackJSON("dustLead", 1), 0.5F, 2, false));
		defaults.add(new CompressorRecipeJSON(new LiteItemStackJSON("dustTinyLapis", 9),
				new LiteItemStackJSON("dustLapis", 1), 0.5F, 2, false));
		defaults.add(new CompressorRecipeJSON(new LiteItemStackJSON("dustTinyObsidian", 9),
				new LiteItemStackJSON("dustObsidian", 1), 0.5F, 2, false));
		defaults.add(new CompressorRecipeJSON(new LiteItemStackJSON("dustTinyLithium", 9),
				new LiteItemStackJSON("dustLithium", 1), 0.5F, 2, false));
		defaults.add(new CompressorRecipeJSON(new LiteItemStackJSON("dustTinyCopper", 9),
				new LiteItemStackJSON("dustCopper", 1), 0.5F, 2, false));
		defaults.add(new CompressorRecipeJSON(new LiteItemStackJSON("dustTinyBronze", 9),
				new LiteItemStackJSON("dustBronze", 1), 0.5F, 2, false));
		defaults.add(new CompressorRecipeJSON(new LiteItemStackJSON("dustTinyIron", 9),
				new LiteItemStackJSON("dustIron", 1), 0.5F, 2, false));
		defaults.add(new CompressorRecipeJSON(new LiteItemStackJSON("dustTinyGold", 9),
				new LiteItemStackJSON("dustGold", 1), 0.5F, 2, false));
		defaults.add(new CompressorRecipeJSON(new LiteItemStackJSON("dustTinyTin", 9),
				new LiteItemStackJSON("dustTin", 1), 0.5F, 2, false));
		defaults.add(new CompressorRecipeJSON(new LiteItemStackJSON("dustTinySilver", 9),
				new LiteItemStackJSON("dustSilver", 1), 0.5F, 2, false));
		defaults.add(new CompressorRecipeJSON(new LiteItemStackJSON("ingotBrickNether", 4),
				new LiteItemStackJSON(new ItemStack(Blocks.nether_brick, 1)), 0.5F, 4, false));
		defaults.add(new CompressorRecipeJSON(new LiteItemStackJSON("ingotBrick", 4),
				new LiteItemStackJSON(new ItemStack(Blocks.brick_block, 1)), 0.5F, 4, false));
		defaults.add(new CompressorRecipeJSON(new LiteItemStackJSON(new ItemStack(Items.snowball, 4)),
				new LiteItemStackJSON(new ItemStack(Blocks.snow, 1)), 0.5F, 4, false));
		defaults.add(new CompressorRecipeJSON(new LiteItemStackJSON(new ItemStack(Items.clay_ball, 4)),
				new LiteItemStackJSON(new ItemStack(Blocks.clay, 1)), 0.5F, 4, false));
		defaultRecipes = defaults.toArray(new CompressorRecipeJSON[] {});
		reader.setNewDefault(defaultRecipes);
	}

	public static void readFromConfig() {
		reader.setFile(new File(PeatizedConfig.configFolder + "/" + PeatizedMod.MODID + "_compressor_recipes.json"));
		setNewDefaults();
		reader.init();
		Map<ItemStack, CompressorRecipe> recipeList = new HashMap();
		for (CompressorRecipeJSON recipeJSON : reader.json) {
			LiteItemStackJSON ingredient = recipeJSON.ingredient;
			LiteItemStackJSON result = recipeJSON.result;
			float experience = recipeJSON.experience;
			if (experience < 0)
				experience = 0;
			int jumps = recipeJSON.jumps;
			if (jumps < 0)
				jumps = 4;
			boolean electrical = recipeJSON.automatic;
			ArrayList<ItemStack> ingredients = ingredient.toStacks();
			ArrayList<ItemStack> results = result.toStacks();
			for (ItemStack key : ingredients)
				for (ItemStack value : results)
					recipeList.put(key, new CompressorRecipe(value, experience, jumps, electrical));
		}
		compressing().recipeList = recipeList;
	}

	public static ArrayList<ItemStack> parseStringToStacks(String input) {
		ArrayList<ItemStack> result = new ArrayList<ItemStack>();
		if (StringUtils.isNullOrEmpty(input))
			return result;
		String[] info = input.split(";");
		if (info.length > 0) {
			String type = info[0];
			if (type.equals("ore") && info.length == 3) {
				String ore = info[1];
				int count = 1;
				try {
					count = Integer.parseInt(info[2]);
				} catch (Exception e) {
				}
				if (OreDictionary.doesOreNameExist(ore)) {
					for (ItemStack stack : OreDictionary.getOres(ore)) {
						ItemStack stack1 = stack.copy();
						stack1.stackSize = count;
						result.add(stack1);
					}
				}
			} else if (info.length == 3) {
				Item item = (Item) Item.itemRegistry.getObject(info[0]);
				if (item != null) {
					int count = 1;
					try {
						count = Integer.parseInt(info[1]);
					} catch (Exception e) {
					}
					int meta = -1;
					try {
						meta = Integer.parseInt(info[2]);
					} catch (Exception e) {
					}
					if (meta < 0)
						meta = OreDictionary.WILDCARD_VALUE;
					result.add(new ItemStack(item, count, meta));
				}
			}
		}
		return result;
	}

	public static String stackToString(ItemStack stack) {
		if (stack == null || stack.getItem() == null)
			return "";
		int meta = stack.getItemDamage();
		if (meta < 0 || meta == OreDictionary.WILDCARD_VALUE)
			meta = -1;
		return Item.itemRegistry.getNameForObject(stack.getItem()) + ";" + stack.stackSize + ";" + meta;
	}

	public void addCompressingRecipe(String ore, int count, String oreResult, int resultCount, float exp, int jumps,
			boolean electrical) {
		if (OreDictionary.doesOreNameExist(ore) && OreDictionary.doesOreNameExist(oreResult)) {
			for (ItemStack itemStack : OreDictionary.getOres(ore)) {
				ItemStack newStack = itemStack.copy();
				newStack.stackSize = count;
				for (ItemStack result : OreDictionary.getOres(oreResult)) {
					ItemStack newResult = result.copy();
					newStack.stackSize = count;
					newResult.stackSize = resultCount;
					this.addCompressingRecipe(newStack, newResult, exp, jumps, electrical);
					break;
				}
			}
		}
	}

	public void addCompressingRecipe(ItemStack itemStack, String oreResult, int resultCount, float exp, int jumps,
			boolean electrical) {
		if (OreDictionary.doesOreNameExist(oreResult)) {
			for (ItemStack result : OreDictionary.getOres(oreResult)) {
				ItemStack newResult = result.copy();
				newResult.stackSize = resultCount;
				this.addCompressingRecipe(itemStack, newResult, exp, jumps, electrical);
				break;
			}
		}
	}

	public void addCompressingRecipe(String ore, int count, ItemStack result, float exp, int jumps,
			boolean electrical) {
		if (OreDictionary.doesOreNameExist(ore)) {
			for (ItemStack itemStack : OreDictionary.getOres(ore)) {
				ItemStack newStack = itemStack.copy();
				newStack.stackSize = count;
				this.addCompressingRecipe(newStack, result, exp, jumps, electrical);
			}
		}
	}

	public void addCompressingRecipe(ItemStack itemStack, ItemStack result, float exp, int jumps, boolean electrical) {
		this.recipeList.put(result, new CompressorRecipe(result, exp, jumps, electrical));
	}

	public ItemStack getCompressingResult(ItemStack itemStack) {
		Iterator iterator = this.recipeList.entrySet().iterator();
		Entry entry;
		do {
			if (!iterator.hasNext()) {
				return null;
			}
			entry = (Entry) iterator.next();
		} while (!this.itemStacksEquals(itemStack, (ItemStack) entry.getKey()));
		return ((CompressorRecipe) entry.getValue()).result;
	}

	public int getStackSizeForCompressing(ItemStack itemStack) {
		Iterator iterator = this.recipeList.entrySet().iterator();
		Entry entry;
		do {
			if (!iterator.hasNext()) {
				return 1;
			}
			entry = (Entry) iterator.next();
		} while (!this.itemStacksEquals(itemStack, (ItemStack) entry.getKey()));
		int count = ((ItemStack) entry.getKey()).stackSize;
		return count > 0 ? count : 1;
	}

	private boolean itemStacksEquals(ItemStack itemStack, ItemStack itemStack1) {
		return itemStack1.getItem() == itemStack.getItem()
				&& (itemStack1.getItemDamage() == 32767 || itemStack1.getItemDamage() == itemStack.getItemDamage());
	}

	public Map getRecipeList() {
		return this.recipeList;
	}

	public float getExp(ItemStack itemStack) {
		Iterator iterator = this.recipeList.entrySet().iterator();
		Entry entry;
		do {
			if (!iterator.hasNext()) {
				return 0.0F;
			}
			entry = (Entry) iterator.next();
		} while (!this.itemStacksEquals(itemStack, ((CompressorRecipe) entry.getValue()).result));
		return ((CompressorRecipe) entry.getValue()).experience;
	}

	public int getJumps(ItemStack itemStack) {
		Iterator iterator = this.recipeList.entrySet().iterator();
		Entry entry;
		do {
			if (!iterator.hasNext()) {
				return 10;
			}
			entry = (Entry) iterator.next();
		} while (!this.itemStacksEquals(itemStack, (ItemStack) entry.getKey()));
		int jumps = ((CompressorRecipe) entry.getValue()).jumps;
		return jumps > 0 ? jumps : 10;
	}

	public boolean isAutomatic(ItemStack itemStack) {
		Iterator iterator = this.recipeList.entrySet().iterator();
		Entry entry;
		do {
			if (!iterator.hasNext()) {
				return false;
			}
			entry = (Entry) iterator.next();
		} while (!this.itemStacksEquals(itemStack, (ItemStack) entry.getKey()));
		return ((CompressorRecipe) entry.getValue()).autimatic;
	}

	public static class CompressorRecipe {
		public ItemStack result;

		public float experience;

		public int jumps;

		public boolean autimatic;

		public CompressorRecipe(ItemStack result, float exp, int jumps, boolean automatic) {
			this.result = result;
			this.experience = exp;
			this.jumps = jumps;
			this.autimatic = automatic;
		}
	}

	public static class CompressorRecipeJSON {
		@SerializedName("ingredient")
		public LiteItemStackJSON ingredient = null;

		@SerializedName("result")
		public LiteItemStackJSON result = null;

		@SerializedName("experience")
		public float experience = 0.15F;

		@SerializedName("jumps")
		public int jumps = 4;

		@SerializedName("automatic")
		public boolean automatic = false;

		public CompressorRecipeJSON(ItemStack ingredient, ItemStack result, float exp, int jumps, boolean automatic) {
			this(new LiteItemStackJSON(ingredient), new LiteItemStackJSON(result), exp, jumps, automatic);
		}

		public CompressorRecipeJSON(LiteItemStackJSON ingredient, LiteItemStackJSON result, float exp, int jumps,
				boolean automatic) {
			this.ingredient = ingredient;
			this.result = result;
			this.experience = exp;
			this.jumps = jumps;
			this.automatic = automatic;
		}

		public static class LiteItemStackJSON {
			@SerializedName("type")
			public String type;

			@SerializedName("id")
			public String id;

			@SerializedName("count")
			public int count;

			@SerializedName("meta")
			public int meta;

			public LiteItemStackJSON(ItemStack item) {
				this("item", Item.itemRegistry.getNameForObject(item.getItem()), item.stackSize, item.getItemDamage());
			}

			public LiteItemStackJSON(String id, int count) {
				this("ore", id, count, -1);
			}

			public LiteItemStackJSON(String type, String id, int count, int meta) {
				this.type = type;
				if (!type.equals("ore") && !type.equals("item"))
					this.type = "error";
				this.id = id;
				this.count = count;
				if (this.count < 1)
					this.count = 1;
				this.meta = meta;
				if (this.meta == OreDictionary.WILDCARD_VALUE)
					this.meta = -1;
			}

			public ArrayList<ItemStack> toStacks() {
				ArrayList<ItemStack> result = new ArrayList<ItemStack>();
				if (this.type.equals("ore")) {
					if (OreDictionary.doesOreNameExist(this.id))
						for (ItemStack stack : OreDictionary.getOres(this.id)) {
							ItemStack stack1 = stack.copy();
							stack1.stackSize = this.count;
							result.add(stack1);
						}
				} else if (this.type.equals("item")) {
					Item item = (Item) Item.itemRegistry.getObject(this.id);
					if (item != null) {
						int meta = this.meta;
						if (meta < 0)
							meta = OreDictionary.WILDCARD_VALUE;
						result.add(new ItemStack(item, this.count, meta));
					}
				}
				return result;
			}
		}
	}
}
