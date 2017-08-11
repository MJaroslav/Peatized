package mjaroslav.mcmods.peatized.common.block;

import mjaroslav.mcmods.peatized.common.init.PeatizedBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;

public class ItemPeatSlab extends ItemSlab {
	public ItemPeatSlab(Block block) {
		super(block, (BlockSlab) PeatizedBlocks.peatSlab, (BlockSlab) PeatizedBlocks.peatSlabDouble, false);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}
}