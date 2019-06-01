package mjaroslav.mcmods.peatized.common.block;

import mjaroslav.mcmods.peatized.lib.BlockNames;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockBogDirt extends PeatizedBlock {
    public BlockBogDirt() {
        super(Material.GROUND, BlockNames.BOG_DIRT, SoundType.GROUND, 0, 0.5f);
    }
}
