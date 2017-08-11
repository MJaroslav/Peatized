package mjaroslav.mcmods.peatized.common.block;

import java.util.List;

import mjaroslav.mcmods.peatized.PeatizedMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockPeat extends Block {
	public static final String[] textures = new String[] { null, "carved" };

	public static IIcon[] icon = new IIcon[2];

	public BlockPeat() {
		super(Material.rock);
		setCreativeTab(PeatizedMod.tab);
		setBlockTextureName(PeatizedMod.MODID + ":peatbrick");
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < 2; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public void registerBlockIcons(IIconRegister register) {
		this.icon = new IIcon[textures.length];
		for (int i = 0; i < this.icon.length; ++i) {
			String s = this.getTextureName();

			if (textures[i] != null) {
				s = s + "_" + textures[i];
			}
			this.icon[i] = register.registerIcon(s);
		}
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (meta < 0 || meta >= icon.length) {
			meta = 0;
		}
		return this.icon[meta];
	}
}
