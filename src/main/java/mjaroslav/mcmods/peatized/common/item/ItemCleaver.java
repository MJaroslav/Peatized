package mjaroslav.mcmods.peatized.common.item;

import java.util.List;

import mjaroslav.mcmods.mjutils.common.utils.GameUtils;
import mjaroslav.mcmods.peatized.PeatizedMod;
import mjaroslav.mcmods.peatized.common.config.PeatizedConfig;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemCleaver extends ItemAxe {
	private boolean isUnbreakable;
	private int bladeColor;
	public int blooded;

	public ItemCleaver(ToolMaterial material, boolean isUnbreakable, int color) {
		super(material);
		this.bladeColor = color;
		this.isUnbreakable = isUnbreakable;
		if (this.isUnbreakable)
			this.setMaxDamage(0);
		this.setCreativeTab(PeatizedMod.tab);
		this.isFull3D();
		this.damageVsEntity = 6.0F + material.getDamageVsEntity();
		this.setMaxDamage((int) Math.round(material.getMaxUses() * 1.5));
	}

	public int getBladeColor() {
		return bladeColor;
	}

	public ItemCleaver(ToolMaterial material, int color) {
		this(material, material == PeatizedMod.rena, color);
	}

	public ItemCleaver(ToolMaterial material) {
		this(material, material == PeatizedMod.rena, 16777215);
	}

	public boolean isUnbreakable() {
		return isUnbreakable;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
		if (this.toolMaterial == PeatizedMod.rena)
			list.add(StatCollector.translateToLocal("tooltip.cleaver.rena" + (PeatizedConfig.altRenaQuote ? ".alt" : "")));
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return this.toolMaterial == PeatizedMod.rena ? EnumRarity.epic : super.getRarity(stack);
	}

	@Override
	public float func_150893_a(ItemStack stack, Block block) {
		if (block == Blocks.web) {
			return 15.0F;
		} else {
			return super.func_150893_a(stack, block);
		}
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (!this.isUnbreakable
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
		player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		return stack;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		if (this.toolMaterial == PeatizedMod.rena)
			if (entity.isEntityAlive()) {
				List entityList = player.worldObj.getEntitiesWithinAABBExcludingEntity(player,
						entity.boundingBox.expand(2D, 1.5D, 2D));
				int damage = 0;
				if (entityList.size() > 1) {
					for (int id = 0; id < entityList.size(); ++id) {
						Entity target = (Entity) entityList.get(id);
						if (!(target.isDead)) {
							if (target instanceof EntityLiving && target.getEntityId() != entity.getEntityId()) {
								if ((target instanceof EntityTameable
										&& !((EntityTameable) target).func_152113_b().equals(player.getCommandSenderName()))
										|| ((target instanceof EntityPlayer)
												&& (((EntityPlayer) target).getCommandSenderName() == player.getCommandSenderName()))) {
									continue;
								}
								if (target.isEntityAlive()) {
									GameUtils.attackEntity(target, player);
									++damage;
								}
							}
						}
					}
					if (!player.worldObj.isRemote && damage > 0) {
						player.worldObj.playSoundAtEntity(entity, "peatized:hit", 1.0F,
								0.9F + player.worldObj.rand.nextFloat() * 0.2F);
					}
				}
			}
		if (player.worldObj.isRemote && entity instanceof EntityLivingBase) {
			if (!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setShort("blooded", (short) GameUtils.getTicksFromSeconds(10));
		}
		return super.onLeftClickEntity(stack, player, entity);
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean f) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("blooded")) {
			short value = stack.getTagCompound().getShort("blooded");
			if (value > 0)
				value--;
			if (value < 0)
				value = 0;
			stack.getTagCompound().setShort("blooded", value);
		}
	}

	@Override
	public boolean func_150897_b(Block block) {
		return block == Blocks.web;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z,
			EntityLivingBase base) {
		if (!this.isUnbreakable && (double) block.getBlockHardness(world, x, y, z) != 0.0D) {
			stack.damageItem(1, base);
		}
		return true;
	}
}
