package mjaroslav.mcmods.peatized.common.init;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import mjaroslav.mcmods.mjutils.common.fishing.FishingUtils;
import mjaroslav.mcmods.mjutils.common.objects.IModModule;
import mjaroslav.mcmods.mjutils.common.objects.ModInitModule;
import mjaroslav.mcmods.mjutils.lib.MJInfo;
import mjaroslav.mcmods.peatized.PeatizedMod;
import mjaroslav.mcmods.peatized.client.gui.PeatizedGuiHandler;
import mjaroslav.mcmods.peatized.common.config.PeatizedConfig;
import mjaroslav.mcmods.peatized.common.event.EventHandlerWorld;
import mjaroslav.mcmods.peatized.common.network.EventHandlerNetwork;
import mjaroslav.mcmods.peatized.common.world.BlockSludgeGenerator;
import mjaroslav.mcmods.peatized.common.world.ComponentPeathouse;
import mjaroslav.mcmods.peatized.common.world.VillagePeatmanManager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.FishingHooks.FishableCategory;
import net.minecraftforge.common.MinecraftForge;

@ModInitModule(modid = PeatizedMod.MODID)
public class PeatizedWorld implements IModModule {
	public static final String PEATHOUSE_CHEST = "peathouse";

	public static WeightedRandomChestContent[] peathouseLoot = new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(PeatizedItems.resource, 1, 1, 8, 10),
			new WeightedRandomChestContent(PeatizedItems.resource, 0, 8, 16, 10),
			new WeightedRandomChestContent(PeatizedItems.resource, 2, 2, 6, 10),
			new WeightedRandomChestContent(Items.stick, 0, 1, 16, 10),
			new WeightedRandomChestContent(Item.getItemFromBlock(PeatizedBlocks.bogDirtGenerated), 0, 1, 5, 5),
			new WeightedRandomChestContent(Item.getItemFromBlock(PeatizedBlocks.peat), 0, 1, 10, 5),
			new WeightedRandomChestContent(Item.getItemFromBlock(PeatizedBlocks.peat), 1, 1, 10, 3) };
	public static ChestGenHooks peathouseChestHook = new ChestGenHooks(PEATHOUSE_CHEST, peathouseLoot, 3, 9);

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		try {
			MapGenStructureIO.func_143031_a(ComponentPeathouse.class, "PTVillagePeathouse");
		} catch (Throwable e) {
			PeatizedMod.log.error("Peathouse could not be registered.");
		}
	}

	@Override
	public void init(FMLInitializationEvent event) {
		VillagePeatmanManager peathouse = new VillagePeatmanManager();
		VillagerRegistry.instance().registerVillagerId(PeatizedConfig.villagerId);
		VillagerRegistry.instance().registerVillageTradeHandler(PeatizedConfig.villagerId, peathouse);
		VillagerRegistry.instance().registerVillageCreationHandler(peathouse);
		GameRegistry.registerWorldGenerator(new BlockSludgeGenerator(PeatizedBlocks.bogDirtGenerated, 0, 5), 0);
		FMLCommonHandler.instance().bus().register(new EventHandlerNetwork());
		NetworkRegistry.INSTANCE.registerGuiHandler(PeatizedMod.instance, new PeatizedGuiHandler());
		MinecraftForge.EVENT_BUS.register(new EventHandlerWorld());
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		FishingUtils.addItemToCategory(new ItemStack(PeatizedItems.resource, 1, 0), MJInfo.fishingRarityJunk,
				FishableCategory.JUNK);
		FishingUtils.addItemToCategory(new ItemStack(Items.clay_ball, 1), MJInfo.fishingRarityJunk, FishableCategory.JUNK);
		FishingUtils.addItemToCategory(new ItemStack(PeatizedItems.resource, 1, 1), MJInfo.fishingRarityTreasure,
				FishableCategory.TREASURE);
	}

	@Override
	public String getModuleName() {
		return "World";
	}

	@Override
	public int getPriority() {
		return 3;
	}
}
