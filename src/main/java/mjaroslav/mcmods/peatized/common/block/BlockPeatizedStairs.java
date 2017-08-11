package mjaroslav.mcmods.peatized.common.block;

import mjaroslav.mcmods.peatized.PeatizedMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class BlockPeatizedStairs extends BlockStairs {
	public BlockPeatizedStairs(Block block, int meta) {
		super(block, meta);
		setCreativeTab(PeatizedMod.tab);
	}
}
