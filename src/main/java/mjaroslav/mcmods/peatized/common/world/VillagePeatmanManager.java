package mjaroslav.mcmods.peatized.common.world;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.registry.VillagerRegistry.IVillageCreationHandler;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;
import mjaroslav.mcmods.peatized.common.init.PeatizedBlocks;
import mjaroslav.mcmods.peatized.common.init.PeatizedItems;
import mjaroslav.mcmods.peatized.lib.CategoryGeneralInfo;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;

public class VillagePeatmanManager implements IVillageTradeHandler, IVillageCreationHandler {
    @Override
    public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random) {
        if (villager.getProfession() == CategoryGeneralInfo.villagerId)
            for (int count = 1; count < 5; count++) {
                recipeList.add(new MerchantRecipe(new ItemStack(PeatizedBlocks.peat, 8 + random.nextInt(3), 0),
                        new ItemStack(Items.emerald, 1, 0),
                        new ItemStack(PeatizedBlocks.peat, count * 2 + random.nextInt(3), 1)));
                recipeList
                        .add(new MerchantRecipe(new ItemStack(PeatizedItems.resource, count * 2 + random.nextInt(3), 2),
                                new ItemStack(PeatizedItems.resource, count + random.nextInt(3), 1)));
                recipeList.add(new MerchantRecipe(new ItemStack(PeatizedItems.resource, 5 + random.nextInt(6)),
                        new ItemStack(Items.emerald, 1, 0)));
            }
    }

    @Override
    public PieceWeight getVillagePieceWeight(Random random, int i) {
        return new StructureVillagePieces.PieceWeight(ComponentPeathouse.class, 20,
                MathHelper.getRandomIntegerInRange(random, 0 + i, 1 + i));
    }

    @Override
    public Class<?> getComponentClass() {
        return ComponentPeathouse.class;
    }

    @Override
    public Object buildComponent(StructureVillagePieces.PieceWeight villagePiece,
            StructureVillagePieces.Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4,
            int p5) {
        return ComponentPeathouse.buildComponent(startPiece, pieces, random, p1, p2, p3, p4, p5);
    }
}
