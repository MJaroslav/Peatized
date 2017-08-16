package mjaroslav.mcmods.peatized.common.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mjaroslav.mcmods.peatized.PeatizedMod;
import mjaroslav.mcmods.peatized.common.init.PeatizedBlocks;
import mjaroslav.mcmods.peatized.common.tileentity.TileCompressor;
import mjaroslav.mcmods.peatized.common.tileentity.TileFuelCompressor;
import mjaroslav.mcmods.peatized.common.tileentity.TileRFCompressor;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCompressor extends Block implements ITileEntityProvider {
	private final Random rand = new Random();
	public boolean isLit;
	private static boolean onReplace;

	public BlockCompressor(boolean isLit) {
		super(Material.rock);
		this.isLit = isLit;
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		if (!this.isLit)
			for (int meta = 0; meta < 3; meta++)
				list.add(new ItemStack(item, 1, meta));
	}

	@Override
	public Item getItem(World world, int x, int y, int z) {
		return world.getBlock(x, y, z) == PeatizedBlocks.compressorLit
				? Item.getItemFromBlock(PeatizedBlocks.compressor) : super.getItem(world, x, y, z);
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	public int getDamageValue(World world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		switch (meta) {
		case 0:
			return new TileCompressor();
		case 1:
			return new TileRFCompressor();
		case 2:
			return new TileFuelCompressor();
		default:
			return null;
		}
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		switch (meta) {
		case 0:
			return 3.5F;
		case 1:
			return 4.0F;
		case 2:
			return 3.5F;
		default:
			return 1.0F;
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float clickX,
			float clickY, float clickZ) {
		Block block = world.getBlock(x, y, z);
		TileEntity entity = world.getTileEntity(x, y, z);
		if (block != null && entity != null && player != null) {
			if (!player.isSneaking()) {
				int guiId;
				switch (world.getBlockMetadata(x, y, z)) {
				case 0:
					guiId = 0;
					break;
				case 1:
					guiId = 1;
					break;
				case 2:
					guiId = 2;
					break;
				default:
					guiId = -1;
					break;
				}
				if (guiId != -1) {
					player.openGui(PeatizedMod.instance, guiId, world, x, y, z);
					return true;
				}
			}
		}
		return false;
	}

	public static void updateCompressorBlockState(boolean hasLit, boolean isLit, World world, int x, int y, int z) {
		if (hasLit) {
			int meta = world.getBlockMetadata(x, y, z);
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			onReplace = true;
			if (isLit) {
				world.setBlock(x, y, z, PeatizedBlocks.compressorLit);
			} else {
				world.setBlock(x, y, z, PeatizedBlocks.compressor);
			}
			onReplace = false;
			world.setBlockMetadataWithNotify(x, y, z, meta, 2);
			if (tileEntity != null) {
				tileEntity.validate();
				world.setTileEntity(x, y, z, tileEntity);
			}
		}
	}

	public void dropItems(World world, int x, int y, int z) {
		IInventory tile = (IInventory) world.getTileEntity(x, y, z);
		if (tile != null) {
			for (int slot = 0; slot < tile.getSizeInventory(); ++slot) {
				ItemStack itemStack = tile.getStackInSlot(slot);
				if (itemStack != null) {
					float rX = this.rand.nextFloat() * 0.8F + 0.1F;
					float rY = this.rand.nextFloat() * 0.8F + 0.1F;
					float rZ = this.rand.nextFloat() * 0.8F + 0.1F;
					EntityItem entityItem = new EntityItem(world, (double) ((float) x + rX), (double) ((float) y + rY),
							(double) ((float) z + rZ), itemStack);
					float mult = 0.05F;
					entityItem.motionX = (double) ((float) this.rand.nextGaussian() * mult);
					entityItem.motionY = (double) ((float) this.rand.nextGaussian() * mult + 0.2F);
					entityItem.motionZ = (double) ((float) this.rand.nextGaussian() * mult);
					world.spawnEntityInWorld(entityItem);
				}
			}
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int m) {
		if (!onReplace) {
			dropItems(world, x, y, z);
			super.breakBlock(world, x, y, z, b, m);
		}
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isBlockNormalCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		return side.equals(ForgeDirection.UP) || side.equals(ForgeDirection.DOWN);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if (!world.isRemote && world.getBlock(x, y, z) == PeatizedBlocks.compressorLit
				&& world.getBlockMetadata(x, y, z) == 2) {
			entity.setFire(2);
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		float f = 0.03125F;
		return AxisAlignedBB.getBoundingBox((double) x, (double) y, (double) z, (double) x + 1, (double) y + 1 - f,
				(double) z + 1);
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		if (world.getBlock(x, y, z) == PeatizedBlocks.compressorLit && world.getBlockMetadata(x, y, z) == 2) {
			double f = (float) x + 0.5F + MathHelper.getRandomDoubleInRange(rand, -0.1875, 0.1875);
			float f1 = (float) y + 1.02F;
			double f2 = (float) z + 0.5F + MathHelper.getRandomDoubleInRange(rand, -0.1875, 0.1875);
			float f4 = rand.nextFloat() * 0.6F - 0.3F;
			world.spawnParticle("smoke", f, (double) f1, f2, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", f, (double) f1, f2, 0.0D, 0.0D, 0.0D);
		}
	}
}
