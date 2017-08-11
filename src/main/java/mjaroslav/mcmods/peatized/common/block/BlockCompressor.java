package mjaroslav.mcmods.peatized.common.block;

import java.util.List;
import java.util.Random;

import mjaroslav.mcmods.peatized.PeatizedMod;
import mjaroslav.mcmods.peatized.common.PeatizedCommonProxy;
import mjaroslav.mcmods.peatized.common.tileentity.TileCompressor;
import mjaroslav.mcmods.peatized.common.tileentity.TileRFCompressor;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCompressor extends Block implements ITileEntityProvider {
	private final Random rand = new Random();

	public BlockCompressor() {
		super(Material.rock);
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int meta = 0; meta < 2; meta++)
			list.add(new ItemStack(item, 1, meta));
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
				int guiId = -1;
				switch (world.getBlockMetadata(x, y, z)) {
				case 0:
					guiId = 0;
					break;
				case 1:
					guiId = 1;
					break;
				default:
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
		dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, b, m);
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
		return PeatizedCommonProxy.riCompressor;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		return side.equals(ForgeDirection.UP) || side.equals(ForgeDirection.DOWN);
	}
}
