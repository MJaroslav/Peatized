package mjaroslav.mcmods.peatized.common.block;

import mjaroslav.mcmods.peatized.Peatized;
import mjaroslav.mcmods.peatized.common.lib.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BlockBase extends Block {
    public BlockBase(Material material, String name, SoundType sound, float resistance, float hardness) {
        super(material);
        setRegistryName(name);
        setUnlocalizedName(ModInfo.unlocalizedName(name));
        setCreativeTab(Peatized.TAB);
        setSoundType(sound);
        setResistance(resistance);
        setHardness(hardness);
        BlockRegistry.BLOCKS.add(this);
    }

    public int getModelRegistryMeta() {
        return 0;
    }

    // For custom ItemBlock.
    protected ItemBlock createItemBlock() {
        return new ItemBlock(this);
    }

    public void register() {
        ForgeRegistries.BLOCKS.register(this);
        ForgeRegistries.ITEMS.register(createItemBlock().setRegistryName(getRegistryName()));
    }
}
