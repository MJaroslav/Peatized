package mjaroslav.mcmods.peatized.common.block;

import mjaroslav.mcmods.peatized.ModPeatized;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockPeat extends ItemBlock {
	public ItemBlockPeat(Block block) {
		super(block);
		setCreativeTab(ModPeatized.tab);
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
