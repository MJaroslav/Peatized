package mjaroslav.mcmods.peatized.common.block;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import mjaroslav.mcmods.peatized.common.init.PeatizedBlocks;
import mjaroslav.mcmods.peatized.lib.NBTInfo;
import mjaroslav.utils.UtilsColor;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class ItemBlockUpa extends ItemBlock {
    public ItemBlockUpa(Block block) {
        super(block);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        if (stack.hasTagCompound()) {
            String name = stack.getTagCompound().hasKey(NBTInfo.NAME) ? stack.getTagCompound().getString(NBTInfo.NAME)
                    : "";
            String author = stack.getTagCompound().hasKey(NBTInfo.TOOLTIP_HAS)
                    && stack.getTagCompound().getBoolean(NBTInfo.TOOLTIP_HAS)
                    && stack.getTagCompound().hasKey(NBTInfo.AUTHOR) ? stack.getTagCompound().getString(NBTInfo.AUTHOR)
                            : "";
            if (!StringUtils.isEmpty(name)) {
                String[] comment = StatCollector.translateToLocal("tooltip.upa.comment." + name).split("&n");
                for (String string : comment)
                    list.add(string);
                list.add("");
            }
            if (!StringUtils.isEmpty(author))
                ;
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
        list.add(newUpa("common", "original", true, EnumRarity.common, 0xFFFFFF, 0x3D1A1A, 0xFF0000, 0x000000, 0xFF6A00,
                0xFFFFFF, 0x000000, 0x00FF90));
        list.add(newUpa("forest", "original", true, EnumRarity.uncommon, 0x004200, 0x002D00, 0x000000, 0x000C00,
                0x001E00, 0x00DB00, 0x000C00, 0x008700));
    }

    public static ItemStack newUpa(String name, String Author, boolean hasTooltip, EnumRarity rarity, String main,
            String secondary, String nose, String face, String line, String eyes, String ears, String cheeks) {
        return newUpa(name, Author, hasTooltip, rarity, UtilsColor.getColorInt(main), UtilsColor.getColorInt(secondary),
                UtilsColor.getColorInt(nose), UtilsColor.getColorInt(face), UtilsColor.getColorInt(line),
                UtilsColor.getColorInt(eyes), UtilsColor.getColorInt(ears), UtilsColor.getColorInt(cheeks));
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound().hasKey(NBTInfo.RARITY)
                ? EnumRarity.valueOf(stack.getTagCompound().getString(NBTInfo.RARITY)) : super.getRarity(stack);
    }

    public static ItemStack newUpa(String name, String author, boolean hasTooltip, EnumRarity rarity, int main,
            int secondary, int nose, int face, int line, int eyes, int ears, int cheeks) {
        ItemStack stack = new ItemStack(PeatizedBlocks.upa, 1);
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString(NBTInfo.NAME, name);
        nbt.setString(NBTInfo.AUTHOR, author);
        nbt.setBoolean(NBTInfo.TOOLTIP_HAS, true);
        nbt.setInteger(NBTInfo.COLOR_MAIN, main);
        nbt.setInteger(NBTInfo.COLOR_SECONDARY, secondary);
        nbt.setInteger(NBTInfo.COLOR_NOSE, nose);
        nbt.setInteger(NBTInfo.COLOR_CHEEKS, cheeks);
        nbt.setInteger(NBTInfo.COLOR_EARS, ears);
        nbt.setInteger(NBTInfo.COLOR_EYES, eyes);
        nbt.setInteger(NBTInfo.COLOR_LINE, line);
        nbt.setInteger(NBTInfo.COLOR_FACE, face);
        nbt.setString(NBTInfo.RARITY, rarity.name());
        stack.setTagCompound(nbt);
        return stack;
    }

    public static int getCheeksColor(ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound().hasKey(NBTInfo.COLOR_CHEEKS)
                ? stack.getTagCompound().getInteger(NBTInfo.COLOR_CHEEKS) : 0xFFFFFF;
    }

    public static int getEarsColor(ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound().hasKey(NBTInfo.COLOR_EARS)
                ? stack.getTagCompound().getInteger(NBTInfo.COLOR_EARS) : 0xFFFFFF;
    }

    public static int getEyesColor(ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound().hasKey(NBTInfo.COLOR_EYES)
                ? stack.getTagCompound().getInteger(NBTInfo.COLOR_EYES) : 0xFFFFFF;
    }

    public static int getFaceColor(ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound().hasKey(NBTInfo.COLOR_FACE)
                ? stack.getTagCompound().getInteger(NBTInfo.COLOR_FACE) : 0xFFFFFF;
    }

    public static int getLineColor(ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound().hasKey(NBTInfo.COLOR_LINE)
                ? stack.getTagCompound().getInteger(NBTInfo.COLOR_LINE) : 0xFFFFFF;
    }

    public static int getMainColor(ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound().hasKey(NBTInfo.COLOR_MAIN)
                ? stack.getTagCompound().getInteger(NBTInfo.COLOR_MAIN) : 0xFFFFFF;
    }

    public static int getNoseColor(ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound().hasKey(NBTInfo.COLOR_NOSE)
                ? stack.getTagCompound().getInteger(NBTInfo.COLOR_NOSE) : 0xFFFFFF;
    }

    public static int getSecondaryColor(ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound().hasKey(NBTInfo.COLOR_SECONDARY)
                ? stack.getTagCompound().getInteger(NBTInfo.COLOR_SECONDARY) : 0xFFFFFF;
    }
}
