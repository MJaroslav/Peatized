package mjaroslav.mcmods.peatized.common.block;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.mojang.realmsclient.gui.ChatFormatting;

import mjaroslav.mcmods.peatized.common.init.PeatizedBlocks;
import mjaroslav.mcmods.peatized.lib.NBTInfo;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class ItemBlockUpa extends ItemBlock {
    public ItemBlockUpa(Block block) {
        super(block);
        setFull3D();
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        if (stack.hasTagCompound()) {
            String name = stack.getTagCompound().hasKey(NBTInfo.NAME) ? stack.getTagCompound().getString(NBTInfo.NAME)
                    : "";
            String author = stack.getTagCompound().hasKey(NBTInfo.AUTHOR)
                    ? stack.getTagCompound().getString(NBTInfo.AUTHOR) : "";
            if (!StringUtils.isEmpty(name) && stack.getTagCompound().hasKey(NBTInfo.TOOLTIP_HAS)
                    && stack.getTagCompound().getBoolean(NBTInfo.TOOLTIP_HAS)) {
                String[] comment = StatCollector.translateToLocal("tooltip.upa.comment." + name).split("&n");
                for (String string : comment)
                    list.add(string);
                list.add("");
            }
            if (!StringUtils.isEmpty(author))
                list.add(StatCollector.translateToLocal("tooltip.upa.author") + " " + author);
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + (stack.hasTagCompound() && stack.getTagCompound().hasKey(NBTInfo.NAME)
                ? "." + stack.getTagCompound().getString(NBTInfo.NAME) : "");
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        list.add(newUpa("common", "Steins;Gate", true, EnumRarity.common));
        list.add(newUpa("forest", "Steins;Gate 0", true, EnumRarity.uncommon));
        list.add(newUpa("bloody", "something rnadom [vk]", true, EnumRarity.epic)); // Yes, "rnadom"
        list.add(newUpa("metal", "Steins;Gate", true, EnumRarity.rare));
        list.add(newUpa("alexsocol", "AlexSocol", true, EnumRarity.epic));
        list.add(newUpa("sumo", ChatFormatting.OBFUSCATED + "Chaos;Child & Steins;Gate", true, EnumRarity.rare));
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound().hasKey(NBTInfo.RARITY)
                ? EnumRarity.valueOf(stack.getTagCompound().getString(NBTInfo.RARITY)) : super.getRarity(stack);
    }

    public static ItemStack newUpa(String name, String author, boolean hasTooltip, EnumRarity rarity) {
        ItemStack stack = new ItemStack(PeatizedBlocks.upa, 1);
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString(NBTInfo.NAME, name);
        nbt.setString(NBTInfo.AUTHOR, author);
        nbt.setString(NBTInfo.RARITY, rarity.name());
        nbt.setBoolean(NBTInfo.TOOLTIP_HAS, hasTooltip);
        stack.setTagCompound(nbt);
        return stack;
    }
}
