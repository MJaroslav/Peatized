package mjaroslav.mcmods.peatized.common.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mjaroslav.mcmods.peatized.PeatizedMod;
import mjaroslav.mcmods.peatized.common.config.PeatizedConfig;
import mjaroslav.mcmods.peatized.common.init.PeatizedItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.TempCategory;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockBogDirt extends Block {
	public static IIcon[] icons = new IIcon[6];

	private boolean generated;

	public BlockBogDirt(Material material, boolean isGenerated) {
		super(material);
		this.generated = isGenerated;
		setHarvestLevel("shovel", 0);
		if (!isGenerated)
			setTickRandomly(true);
	}

	public boolean isGenerated() {
		return generated;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register) {
		for (int meta = 0; meta < 6; meta++)
			icons[meta] = register.registerIcon(PeatizedMod.MODID + ":bog_dirt_stage_" + meta);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		if (isGenerated() || !isDry(world.getBlockMetadata(x, y, z))) {
			float f = 0.125F;
			if (world.getBlock(x, y + 1, z).getMaterial() == Material.water)
				f *= 3;
			return AxisAlignedBB.getBoundingBox((double) x, (double) y, (double) z, (double) (x + 1),
					(double) ((float) (y + 1) - f), (double) (z + 1));
		}
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}

	public static int getWaterCount(World world, int x, int y, int z) {
		int water = 0;
		for (int yy = y - 2; yy < y + 3; yy++)
			for (int xx = x - 2; xx < x + 3; xx++)
				for (int zz = z - 2; zz < z + 3; zz++)
					if (world.getBlock(xx, yy, zz).getMaterial().equals(Material.water))
						water++;
		return water;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if (isGenerated() || !isDry(world.getBlockMetadata(x, y, z))) {
			if (world.getBlock(x, y + 1, z).getMaterial() == Material.water) {
				entity.motionY *= 0.2D;
				entity.motionX *= 0.2D;
				entity.motionZ *= 0.2D;
			} else {
				entity.motionX *= 0.4D;
				entity.motionZ *= 0.4D;
			}
		}
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> drop = new ArrayList<ItemStack>();
		drop.add(new ItemStack(Blocks.dirt, 1));
		if (isGenerated() || !isDry(metadata)) {
			if (isGenerated() || isMature(metadata) || world.rand.nextInt(4) == 0)
				drop.add(new ItemStack(PeatizedItems.resource, 1 + world.rand.nextInt(2), 0));
		}
		return drop;
	}

	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int meta) {
		return true;
	}

	public String getGrowth(int meta) {
		if (isGenerated())
			return StatCollector.translateToLocal("tooltip.bogDirt.humidity.error");
		if (isMature(meta))
			return StatCollector.translateToLocal("tooltip.bogDirt.humidity.wet");
		if (meta == 0)
			return StatCollector.translateToLocal("tooltip.bogDirt.humidity.dry");
		int percent = Math.round((meta + 1) * 100 / 6);
		return StatCollector.translateToLocal("tooltip.bogDirt.humidity.percent").replace("{percent}",
				String.valueOf(percent));
	}

	@Override
	public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction,
			IPlantable plantable) {
		Block plant = plantable.getPlant(world, x, y + 1, z);
		EnumPlantType plantType = plantable.getPlantType(world, x, y + 1, z);
		int meta = world.getBlockMetadata(x, y, z);
		if (plantType.equals(EnumPlantType.Plains)
				|| (plantType.equals(EnumPlantType.Crop) && (isGenerated() || !isDry(meta))))
			return true;
		if (plantType.equals(EnumPlantType.Beach)) {
			boolean hasWater = (world.getBlock(x - 1, y, z).getMaterial() == Material.water
					|| world.getBlock(x + 1, y, z).getMaterial() == Material.water
					|| world.getBlock(x, y, z - 1).getMaterial() == Material.water
					|| world.getBlock(x, y, z + 1).getMaterial() == Material.water);
			return hasWater;
		}
		return super.canSustainPlant(world, x, y, z, direction, plantable);
	}

	@Override
	public int getDamageValue(World world, int x, int y, int z) {
		if (isGenerated())
			return 0;
		int meta = world.getBlockMetadata(x, y, z);
		return meta > 5 ? 0 : meta;
	}

	public boolean isDry(int meta) {
		return (meta < 3 || meta > 5) && !isGenerated();
	}

	public boolean isMature(int meta) {
		return meta == 5 && !isGenerated();
	}

	public boolean isBad(int meta) {
		return meta > 5;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int item, int meta) {
		if (meta < 6 && !isGenerated())
			return icons[meta];
		return icons[5];
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xx, float yy,
			float zz) {
		BlockBogDirt block = null;
		if (world.getBlock(x, y, z) instanceof BlockBogDirt)
			block = (BlockBogDirt) world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		ItemStack heldStack = null;
		if (player != null)
			heldStack = player.getHeldItem();
		Item heldItem = null;
		if (heldStack != null && heldStack.getItem() != null)
			heldItem = heldStack.getItem();
		if (player != null && heldItem == Items.stick) {
			if (!block.isGenerated()) {
				if (block.isBad(meta) && world.isRemote)
					player.addChatMessage(new ChatComponentText(
							EnumChatFormatting.RED + StatCollector.translateToLocal("tooltip.error.meta")));
				int waterCount = block.getWaterCount(world, x, y, z);
				int waterOneStep = PeatizedConfig.waterSteps > 0
						? Math.round(PeatizedConfig.reqWaterMax / PeatizedConfig.waterSteps)
						: PeatizedConfig.reqWaterMax;
				int waterSteps = Math.abs(waterCount / waterOneStep);
				if (waterSteps > PeatizedConfig.waterSteps)
					waterSteps = PeatizedConfig.waterSteps;
				int waterStepsMax = Math.abs(PeatizedConfig.reqWaterMax / waterOneStep);
				int waterBonus = 0;
				for (int i = 0; i < waterSteps; i++)
					waterBonus += PeatizedConfig.waterStepSummand;
				waterBonus *= -1;
				int waterMaxBonus = PeatizedConfig.waterSteps * PeatizedConfig.waterStepSummand * -1;
				int waterStepBonus = PeatizedConfig.waterStepSummand * -1;
				String waterCountString = (waterCount >= PeatizedConfig.reqWaterMax ? EnumChatFormatting.GREEN
						: EnumChatFormatting.YELLOW) + String.valueOf(waterCount) + EnumChatFormatting.RESET;
				String waterReqMaxString = EnumChatFormatting.YELLOW + String.valueOf(PeatizedConfig.reqWaterMax)
						+ EnumChatFormatting.RESET;
				String waterMaxString = EnumChatFormatting.YELLOW + String.valueOf(124) + EnumChatFormatting.RESET;
				String waterBonusString = (waterBonus > 0 ? EnumChatFormatting.GREEN
						: (waterBonus < 0 ? EnumChatFormatting.RED : EnumChatFormatting.YELLOW))
						+ String.valueOf(waterBonus) + EnumChatFormatting.RESET;
				String waterMaxBonusString = EnumChatFormatting.YELLOW + String.valueOf(waterMaxBonus)
						+ EnumChatFormatting.RESET;
				String waterStepsString = (waterSteps >= waterStepsMax ? EnumChatFormatting.GREEN
						: EnumChatFormatting.YELLOW) + String.valueOf(waterSteps) + EnumChatFormatting.RESET;
				String waterStepsMaxString = EnumChatFormatting.YELLOW + String.valueOf(waterStepsMax)
						+ EnumChatFormatting.RESET;
				String waterOneStepString = EnumChatFormatting.YELLOW + String.valueOf(waterOneStep)
						+ EnumChatFormatting.RESET;
				String waterStepBonusString = (waterStepBonus > 0 ? EnumChatFormatting.GREEN
						: (waterStepBonus < 0 ? EnumChatFormatting.RED : EnumChatFormatting.YELLOW))
						+ String.valueOf(waterStepBonus) + EnumChatFormatting.RESET;
				String water = StatCollector.translateToLocal("tooltip.bogDirt.water.0")
						.replace("{waterCount}", waterCountString).replace("{waterReqMax}", waterReqMaxString)
						.replace("{waterMax}", waterMaxString).replace("{waterBonus}", waterBonusString)
						.replace("{waterMaxBonus}", String.valueOf(waterMaxBonusString));
				String water1 = StatCollector.translateToLocal("tooltip.bogDirt.water.1")
						.replace("{waterReqMax}", String.valueOf(waterReqMaxString))
						.replace("{waterSteps}", waterStepsString).replace("{waterStepsMax}", waterStepsMaxString);
				String water2 = StatCollector.translateToLocal("tooltip.bogDirt.water.2")
						.replace("{waterOneStep}", waterOneStepString)
						.replace("{waterStepBonus}", waterStepBonusString);
				if (world.isRemote) {
					player.addChatMessage(new ChatComponentText(water));
					player.addChatMessage(new ChatComponentText(water1));
					player.addChatMessage(new ChatComponentText(water2));
				}

				int rainBonus = world.canLightningStrikeAt(x, y + 1, z) ? PeatizedConfig.rainSummand * -1 : 0;
				String rainBonusString = (rainBonus > 0 ? EnumChatFormatting.GREEN
						: (rainBonus < 0 ? EnumChatFormatting.RED : EnumChatFormatting.YELLOW))
						+ String.valueOf(rainBonus) + EnumChatFormatting.RESET;
				if (world.isRemote)
					player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("tooltip.bogDirt.rain")
							.replace("{rainBonus}", rainBonusString)));

				int biomeBonus = ((world.getBiomeGenForCoords(x, z).isHighHumidity() ? 1 : 0)
						* PeatizedConfig.biomeSummand
						+ (world.getBiomeGenForCoords(x, z).getTempCategory().equals(TempCategory.WARM) ? -1 : 0)
								* PeatizedConfig.biomeSummand)
						* -1;
				String biomeBonusString = (biomeBonus > 0 ? EnumChatFormatting.GREEN
						: (biomeBonus < 0 ? EnumChatFormatting.RED : EnumChatFormatting.YELLOW))
						+ String.valueOf(biomeBonus) + EnumChatFormatting.RESET;
				if (world.isRemote)
					player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("tooltip.bogDirt.biome")
							.replace("{biomeBonus}", biomeBonusString)));

				int totalBonus = waterBonus + rainBonus + biomeBonus;
				String totalBonusString = (totalBonus > 0 ? EnumChatFormatting.GREEN
						: (totalBonus < 0 ? EnumChatFormatting.RED : EnumChatFormatting.YELLOW))
						+ String.valueOf(totalBonus) + EnumChatFormatting.RESET;
				if (world.isRemote)
					player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("tooltip.bogDirt.total")
							.replace("{totalBonus}", totalBonusString)));
				String humidity = StatCollector.translateToLocal("tooltip.bogDirt.humidity");
				String value = block.getGrowth(meta);
				if (block.isBad(meta)) {
					value = EnumChatFormatting.RED + value + EnumChatFormatting.RESET;
				} else if (block.isMature(meta))
					value = EnumChatFormatting.GREEN + value + EnumChatFormatting.RESET;
				else
					value = EnumChatFormatting.YELLOW + value + EnumChatFormatting.RESET;
				if (world.isRemote)
					player.addChatMessage(new ChatComponentText(humidity.replace("{value}", value)));
				return true;
			}
		}
		return false;
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		if (isGenerated())
			list.add(new ItemStack(item, 1, 0));
		else
			for (int meta = 0; meta < 6; meta++)
				list.add(new ItemStack(item, 1, meta));
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		if (!isGenerated()) {
			int meta = world.getBlockMetadata(x, y, z);
			int waterCount = getWaterCount(world, x, y, z);
			if (waterCount > 0) {
				if (!isMature(meta)) {
					int chance = createChance(world, x, y, z, random, waterCount);
					if (chance < 1 || random.nextInt(chance) == 0)
						world.setBlockMetadataWithNotify(x, y, z, meta + 1, 2);
				}
			}
		}
	}

	public static int createChance(World world, int x, int y, int z, Random random, int waterCount) {
		int chance = PeatizedConfig.baseChance;
		if (world.getBiomeGenForCoords(x, z).isHighHumidity())
			chance += PeatizedConfig.biomeSummand;
		if (world.getBiomeGenForCoords(x, z).getTempCategory().equals(TempCategory.WARM))
			chance -= PeatizedConfig.biomeSummand;
		int waterStepCount = PeatizedConfig.waterSteps > 0
				? Math.round(PeatizedConfig.reqWaterMax / PeatizedConfig.waterSteps) : PeatizedConfig.reqWaterMax;
		int steps = Math.abs(waterCount / waterStepCount);
		if (steps > PeatizedConfig.waterSteps)
			steps = PeatizedConfig.waterSteps;
		for (int i = 0; i < steps; i++)
			chance += PeatizedConfig.waterStepSummand;
		if (world.canLightningStrikeAt(x, y + 1, z))
			chance += PeatizedConfig.rainSummand;
		return chance;
	}
}
