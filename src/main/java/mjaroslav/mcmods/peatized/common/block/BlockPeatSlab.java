package mjaroslav.mcmods.peatized.common.block;

import java.util.List;
import java.util.Random;

import mjaroslav.mcmods.peatized.ModPeatized;
import mjaroslav.mcmods.peatized.common.init.PeatizedBlocks;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockPeatSlab extends BlockSlab {
	public static final String[] types = { "default" };

	public BlockPeatSlab(boolean isDouble) {
		super(isDouble, Material.rock);
		useNeighborBrightness = true;
		setCreativeTab(ModPeatized.tab);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		int type = meta & 7;
		if (this.field_150004_a && (meta & 8) != 0) {
			side = 1;
		}
		return PeatizedBlocks.peat.getIcon(side, type);
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side,
			float hitX, float hitY, float hitZ, int meta) {
		return super.onBlockPlaced(world, x, y, z, side, hitX, hitY,
				hitZ, meta);
	}
	
	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return Item.getItemFromBlock(PeatizedBlocks.peatSlab);
	}

	@Override
	protected ItemStack createStackedBlock(int par1) {
		return new ItemStack(PeatizedBlocks.peatSlab, 2, par1 & 7);
	}

	public String getFullSlabName(int meta) {
		if ((meta < 0) || (meta >= types.length)) {
			meta = 0;
		}
		int type = meta & 7;
		return getUnlocalizedName() + "." + type;
	}

	@Override
	public void getSubBlocks(Item block, CreativeTabs tab, List list) {
		if (block != Item.getItemFromBlock(PeatizedBlocks.peatSlabDouble)) {
			list.add(new ItemStack(block, 1, 0));
		}
	}

	@Override
	public String func_150002_b(int meta) {
		return getFullSlabName(meta);
	}

}