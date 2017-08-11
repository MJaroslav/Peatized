package mjaroslav.mcmods.peatized.common.block;

import mjaroslav.mcmods.peatized.PeatizedMod;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemPeatizedBlock extends ItemBlock {

	public ItemPeatizedBlock(Block block) {
		super(block);
		setHasSubtypes(true);
		setMaxDamage(0);
		setCreativeTab(PeatizedMod.tab);
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return super.getUnlocalizedName() + "." + itemStack.getItemDamage();
	}
}
