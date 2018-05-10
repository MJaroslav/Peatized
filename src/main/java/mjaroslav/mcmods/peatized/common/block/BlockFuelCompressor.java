package mjaroslav.mcmods.peatized.common.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mjaroslav.mcmods.mjutils.lib.utils.UtilsGame;
import mjaroslav.mcmods.peatized.ModPeatized;
import mjaroslav.mcmods.peatized.common.init.PeatizedBlocks;
import mjaroslav.mcmods.peatized.common.tileentity.TileFuelCompressor;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockFuelCompressor extends Block implements ITileEntityProvider {
    private final Random rand = new Random();

    public boolean isLit;
    private static boolean onReplace;

    public BlockFuelCompressor(boolean isLit) {
        super(Material.rock);
        this.isLit = isLit;
        setHardness(3.5F);
        setStepSound(Block.soundTypeStone);
        setBlockName("peatized.compressor.fuel");
        setBlockTextureName("stone");
        if (isLit) {
            setLightLevel(0.875F);
            setCreativeTab(null);
        } else
            setCreativeTab(ModPeatized.tab);
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
        if (!onReplace)
            dropItems(world, x, y, z);
        super.breakBlock(world, x, y, z, b, m);
    }

    public static void updateCompressorBlockState(boolean hasLit, boolean isLit, World world, int x, int y, int z) {
        if (hasLit) {
            int meta = world.getBlockMetadata(x, y, z);
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            onReplace = true;
            if (isLit) {
                world.setBlock(x, y, z, PeatizedBlocks.fuelCompressorLit);
            } else {
                world.setBlock(x, y, z, PeatizedBlocks.fuelCompressor);
            }
            onReplace = false;
            world.setBlockMetadataWithNotify(x, y, z, meta, 2);
            if (tileEntity != null) {
                tileEntity.validate();
                world.setTileEntity(x, y, z, tileEntity);
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileFuelCompressor();
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
        world.setBlockMetadataWithNotify(x, y, z, UtilsGame.getMetaFromRotation(placer), 2);
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

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        if (isLit) {
            int l = world.getBlockMetadata(x, y, z);
            float f = (float) x + 0.5F;
            float f1 = (float) y + 0.8F + rand.nextFloat() * 2.0F / 16.0F;
            float f2 = (float) z + 0.5F;
            float f3 = 0.52F;
            float f4 = rand.nextFloat() * 0.3F - 0.15F;
            if (l == 5) {
                world.spawnParticle("smoke", (double) (f - f3), (double) f1, (double) (f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double) (f - f3), (double) f1, (double) (f2 + f4), 0.0D, 0.0D, 0.0D);
            } else if (l == 4) {
                world.spawnParticle("smoke", (double) (f + f3), (double) f1, (double) (f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double) (f + f3), (double) f1, (double) (f2 + f4), 0.0D, 0.0D, 0.0D);
            } else if (l == 3) {
                world.spawnParticle("smoke", (double) (f + f4), (double) f1, (double) (f2 - f3), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double) (f + f4), (double) f1, (double) (f2 - f3), 0.0D, 0.0D, 0.0D);
            } else if (l == 2) {
                world.spawnParticle("smoke", (double) (f + f4), (double) f1, (double) (f2 + f3), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double) (f + f4), (double) f1, (double) (f2 + f3), 0.0D, 0.0D, 0.0D);
            }
            world.spawnParticle("largesmoke", (double) x + 0.5, (double) y + 1.625, (double) z + 0.5, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB box, List list, Entity entity) {
        this.setBlockBounds(0.3125F, 1, 0.3125F, 0.6875F, 1.625F, 0.6875F);
        super.addCollisionBoxesToList(world, x, y, z, box, list, entity);
        this.setBlockBounds(0, 0, 0, 1, 1, 1);
        super.addCollisionBoxesToList(world, x, y, z, box, list, entity);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float clickX,
            float clickY, float clickZ) {
        Block block = world.getBlock(x, y, z);
        TileEntity entity = world.getTileEntity(x, y, z);
        if (block != null && entity != null && player != null) {
            if (!player.isSneaking()) {
                player.openGui(ModPeatized.instance, 2, world, x, y, z);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        int rSide = UtilsGame.getSideFromMeta(world.getBlockMetadata(x, y, z), side.ordinal());
        return side.equals(ForgeDirection.DOWN) || rSide == 3 || rSide == 5;
    }
}
