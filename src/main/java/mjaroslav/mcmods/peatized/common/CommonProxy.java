package mjaroslav.mcmods.peatized.common;

import forestry.api.fuels.EngineCopperFuel;
import forestry.api.fuels.FuelManager;
import mjaroslav.mcmods.peatized.common.block.BlockBase;
import mjaroslav.mcmods.peatized.common.block.BlockRegistry;
import mjaroslav.mcmods.peatized.common.item.ItemBase;
import mjaroslav.mcmods.peatized.common.item.ItemRegistry;
import mjaroslav.mcmods.peatized.common.util.Proxy;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy implements Proxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        BlockRegistry.forEach(BlockBase::register);
        ItemRegistry.forEach(ItemBase::register);
    }

    @Override
    public void init(FMLInitializationEvent event) {
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        initForestry();
    }

    private static void initForestry() {
        if (!CommonConfigurator.FORESTRY)
            return;
        if (CommonConfigurator.FORESTRY_PEAT_BALL_DURATION > 0) {
            ItemStack peatBall = ItemRegistry.PEAT_BALL.getItemStack();
            FuelManager.copperEngineFuel.put(peatBall, new EngineCopperFuel(peatBall,
                    CommonConfigurator.FORESTRY_PEAT_BALL_POWER, CommonConfigurator.FORESTRY_PEAT_BALL_DURATION));
        }
    }
}
