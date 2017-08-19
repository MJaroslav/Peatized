package mjaroslav.mcmods.peatized.common.item;

import java.util.List;

import mjaroslav.mcmods.peatized.PeatizedMod;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemResource extends Item {
	private IIcon[] icon = new IIcon[4];

	public ItemResource() {
		super();
		setHasSubtypes(true);
		setMaxDamage(0);
		setMaxStackSize(64);
		setCreativeTab(PeatizedMod.tab);
	}

	@Override
	public void registerIcons(IIconRegister register) {
		icon[0] = register.registerIcon(PeatizedMod.MODID + ":peat_ball");
		icon[1] = register.registerIcon(PeatizedMod.MODID + ":peat_brick");
		icon[2] = register.registerIcon(PeatizedMod.MODID + ":peat_plate");
		icon[3] = register.registerIcon(PeatizedMod.MODID + ":gear_stone");
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return super.getUnlocalizedName() + "." + itemStack.getItemDamage();
	}

	@Override
	public IIcon getIconFromDamage(int meta) {
		if (meta < icon.length)
			return icon[meta];
		else
			return icon[0];
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int meta = 0; meta < 4; meta++)
			list.add(new ItemStack(item, 1, meta));
	}
}
