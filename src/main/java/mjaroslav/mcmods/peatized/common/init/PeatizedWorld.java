package mjaroslav.mcmods.peatized.common.init;

import static mjaroslav.mcmods.mjutils.lib.constants.ConstantsFishing.*;
import static mjaroslav.mcmods.mjutils.lib.utils.UtilsFishing.*;
import static net.minecraftforge.common.FishingHooks.FishableCategory.*;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import mjaroslav.mcmods.mjutils.lib.module.IModule;
import mjaroslav.mcmods.mjutils.lib.module.ModModule;
import mjaroslav.mcmods.peatized.ModPeatized;
import mjaroslav.mcmods.peatized.client.gui.PeatizedGuiHandler;
import mjaroslav.mcmods.peatized.common.event.EventHandlerWorld;
import mjaroslav.mcmods.peatized.common.network.EventHandlerNetwork;
import mjaroslav.mcmods.peatized.common.world.*;
import mjaroslav.mcmods.peatized.lib.CategoryGeneralInfo;
import mjaroslav.mcmods.peatized.lib.ModInfo;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;

@ModModule(modid = ModInfo.MODID)
public class PeatizedWorld implements IModule {
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
            ModInfo.LOG.error("Peathouse could not be registered.");
        }
    }

    @Override
    public void init(FMLInitializationEvent event) {
        VillagePeatmanManager peathouse = new VillagePeatmanManager();
        VillagerRegistry.instance().registerVillagerId(CategoryGeneralInfo.villagerId);
        VillagerRegistry.instance().registerVillageTradeHandler(CategoryGeneralInfo.villagerId, peathouse);
        VillagerRegistry.instance().registerVillageCreationHandler(peathouse);
        GameRegistry.registerWorldGenerator(new BlockSludgeGenerator(PeatizedBlocks.bogDirtGenerated, 0, 5), 0);
        FMLCommonHandler.instance().bus().register(new EventHandlerNetwork());
        NetworkRegistry.INSTANCE.registerGuiHandler(ModPeatized.instance, new PeatizedGuiHandler());
        MinecraftForge.EVENT_BUS.register(new EventHandlerWorld());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        addItemToCategory(new ItemStack(PeatizedItems.resource, 1, 0), rarityJunk, JUNK);
        addItemToCategory(new ItemStack(Items.clay_ball, 1), rarityJunk, JUNK);
        addItemToCategory(new ItemStack(PeatizedItems.resource, 1, 1), rarityTreasure, TREASURE);
    }

    @Override
    public String getModuleName() {
        return "World";
    }

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public boolean canLoad() {
        return true;
    }

    @Override
    public String[] modDependencies() {
        return null;
    }
}
