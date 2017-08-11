package mjaroslav.mcmods.peatized.common.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class ItemBlockBogDirt extends ItemBlock {
	private boolean generated;

	public ItemBlockBogDirt(Block block) {
		super(block);
		if (block != null && block instanceof BlockBogDirt) {
			boolean generated = ((BlockBogDirt) block).isGenerated();
			this.generated = generated;
			setHasSubtypes(!generated);
		}
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean b) {
		if (!generated) {
			int meta = itemStack.getItemDamage();
			String humidity = StatCollector.translateToLocal("tooltip.bogDirt.humidity");
			BlockBogDirt block = (BlockBogDirt) Block.getBlockFromItem(itemStack.getItem());
			String value = block.getGrowth(meta);
			if (block.isBad(meta)) {
				value = EnumChatFormatting.RED + value + EnumChatFormatting.RESET;
				list.add(EnumChatFormatting.RED + StatCollector.translateToLocal("tooltip.error.meta")
						+ EnumChatFormatting.RESET);
			} else if (block.isMature(meta))
				value = EnumChatFormatting.GREEN + value + EnumChatFormatting.RESET;
			else
				value = EnumChatFormatting.YELLOW + value + EnumChatFormatting.RESET;
			list.add(humidity.replace("{value}", value));
		}
	}

	public boolean isGenerated() {
		return generated;
	}

	@Override
	public int getMetadata(int meta) {
		if (isGenerated())
			return 0;
		return meta;
	}

	// Meta < 3 - block is dry
	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		if (!generated) {
			BlockBogDirt block = (BlockBogDirt) Block.getBlockFromItem(itemStack.getItem());
			int meta = itemStack.getItemDamage();
			if (block.isBad(meta))
				return super.getUnlocalizedName() + ".error";
			else if (block.isDry(meta))
				return super.getUnlocalizedName() + ".dry";
		}
		return super.getUnlocalizedName();
	}
}
