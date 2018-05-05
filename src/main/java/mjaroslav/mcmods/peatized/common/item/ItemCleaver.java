package mjaroslav.mcmods.peatized.common.item;

import java.awt.Color;
import java.util.List;

import mjaroslav.mcmods.mjutils.lib.utils.UtilsGame;
import mjaroslav.mcmods.peatized.ModPeatized;
import mjaroslav.mcmods.peatized.lib.CategoryGeneralInfo.CategoryCleaversInfo;
import mjaroslav.mcmods.peatized.lib.CategoryGeneralInfo.CategoryGraphicsInfo;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemCleaver extends ItemAxe {
    private boolean isEaster;
    private final Color bladeColor;
    private final int cooldown;

    public ItemCleaver(ToolMaterial material, boolean isEaster, Color color, int cooldown) {
        super(material);
        bladeColor = color;
        this.cooldown = UtilsGame.getTicksFromSeconds(cooldown);
        this.isEaster = isEaster;
        if (isEaster)
            setMaxDamage(0);
        setCreativeTab(ModPeatized.tab);
        setTextureName("minecraft:iron_sword");
        setFull3D();
        damageVsEntity = 5F + material.getDamageVsEntity();
        setMaxDamage((int) Math.round(material.getMaxUses() * 1.5));
    }

    public ItemCleaver(ToolMaterial material, Color color, int cooldown) {
        this(material, false, color, cooldown);
    }

    public Color getBladeColor() {
        return bladeColor;
    }

    public int getCooldown() {
        return cooldown;
    }

    public boolean isEaster() {
        return isEaster;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        if (isEaster) {
            list.add(StatCollector
                    .translateToLocal("tooltip.cleaver.rena" + (CategoryGraphicsInfo.altRenaQuote ? ".alt" : "")));
            list.add("");
        }
        list.add(StatCollector.translateToLocal("tooltip.cleaver.cooldown") + " " + (getCooldown() / 20));
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return isEaster ? EnumRarity.epic : super.getRarity(stack);
    }

    @Override
    public float func_150893_a(ItemStack stack, Block block) {
        if (block == Blocks.web)
            return 15.0F;
        else
            return super.func_150893_a(stack, block);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        if (!this.isEaster
                && (attacker instanceof EntityPlayer && !((EntityPlayer) attacker).capabilities.isCreativeMode))
            stack.damageItem(1, target);
        return true;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.block;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.setItemInUse(stack, getMaxItemUseDuration(stack));
        return stack;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("cooldown"))
            if (entity.isEntityAlive()) {
                List entityList = player.worldObj.getEntitiesWithinAABBExcludingEntity(player,
                        entity.boundingBox.expand(1.5D, 1.0D, 1.5D));
                int damage = 0;
                if (entityList.size() > 1) {
                    for (int id = 0; id < entityList.size(); ++id) {
                        Entity target = (Entity) entityList.get(id);
                        if (!(target.isDead)) {
                            if (target instanceof EntityLiving && target.getEntityId() != entity.getEntityId()) {
                                if ((target instanceof EntityTameable && !((EntityTameable) target).func_152113_b()
                                        .equals(player.getCommandSenderName()))
                                        || ((target instanceof EntityPlayer) && (((EntityPlayer) target)
                                                .getCommandSenderName() == player.getCommandSenderName())))
                                    continue;
                                if (target.isEntityAlive()) {
                                    UtilsGame.attackEntity(target, player);
                                    ++damage;
                                }
                            }
                        }
                    }
                    if (!player.worldObj.isRemote && damage > 0)
                        player.worldObj.playSoundAtEntity(entity, "peatized:hit", 1.0F,
                                0.9F + player.worldObj.rand.nextFloat() * 0.2F);
                }
            }
        if (entity instanceof EntityLivingBase) {
            setBlood(stack, entity.worldObj);
            setCooldown(stack, entity.worldObj);
        }
        return super.onLeftClickEntity(stack, player, entity);
    }

    public static boolean isEaster(ItemStack stack) {
        return stack.getItem() instanceof ItemCleaver && ((ItemCleaver) stack.getItem()).isEaster();
    }

    public static int getCooldownTime(ItemStack stack) {
        if (stack.getItem() instanceof ItemCleaver)
            return ((ItemCleaver) stack.getItem()).getCooldown();
        return 0;
    }

    public static void setCooldown(ItemStack stack, World world) {
        if (!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setLong("cooldown", world.getTotalWorldTime() + getCooldownTime(stack));
    }

    public static boolean hasCooldown(ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound().hasKey("cooldown");
    }

    public static long getCooldown(ItemStack stack) {
        return hasCooldown(stack) ? stack.getTagCompound().getLong("cooldown") : 0;
    }

    public static long getBlood(ItemStack stack) {
        return hasBlood(stack) ? stack.getTagCompound().getLong("blood") : 0;
    }

    public static boolean hasBlood(ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound().hasKey("blood");
    }

    public static void removeCooldown(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("cooldown"))
            stack.getTagCompound().removeTag("cooldown");
    }

    public static void checkCooldown(ItemStack stack, World world) {
        if (hasCooldown(stack) && stack.getTagCompound().getLong("cooldown") < world.getTotalWorldTime())
            removeCooldown(stack);
    }

    public static void setBlood(ItemStack stack, World world) {
        if (!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setLong("blood",
                world.getTotalWorldTime() + UtilsGame.getTicksFromSeconds(CategoryCleaversInfo.bloodTime));
    }

    public static void removeBlood(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("blood"))
            stack.getTagCompound().removeTag("blood");
    }

    public static void checkBlood(ItemStack stack, World world) {
        if (hasBlood(stack) && stack.getTagCompound().getLong("blood") < world.getTotalWorldTime())
            removeBlood(stack);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean f) {
        checkBlood(stack, world);
        checkCooldown(stack, world);
    }

    @Override
    public boolean func_150897_b(Block block) {
        return block == Blocks.web;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z,
            EntityLivingBase base) {
        if (!this.isEaster && (double) block.getBlockHardness(world, x, y, z) != 0.0D)
            stack.damageItem(1, base);
        return true;
    }
}
