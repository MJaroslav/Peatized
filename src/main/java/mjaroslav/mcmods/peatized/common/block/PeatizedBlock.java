package mjaroslav.mcmods.peatized.common.block;

import mjaroslav.mcmods.peatized.lib.ModInfo;
import mjaroslav.mcmods.peatized.lib.PeatizedTab;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

abstract class PeatizedBlock extends Block {
    PeatizedBlock(Material material, String name, SoundType sound, float resistance, float hardness) {
        super(material);
        setCreativeTab(PeatizedTab.INSTANCE);
        setRegistryName(name);
        setUnlocalizedName(ModInfo.createUnlocolizedName(name));
        setSoundType(sound);
        setResistance(resistance);
        setHardness(hardness);
    }
}
