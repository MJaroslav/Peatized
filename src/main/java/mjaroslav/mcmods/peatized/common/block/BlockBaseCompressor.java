package mjaroslav.mcmods.peatized.common.block;

import java.util.Random;

import mjaroslav.mcmods.mjutils.lib.utils.UtilsGame;
import mjaroslav.mcmods.peatized.ModPeatized;
import mjaroslav.mcmods.peatized.common.tileentity.TileBaseCompressor;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockBaseCompressor extends Block implements ITileEntityProvider {
    private final Random rand = new Random();

    public BlockBaseCompressor() {
        super(Material.rock);
        setHardness(3.5F);
        setStepSound(Block.soundTypeStone);
        setBlockName("peatized.compressor.base");
        setBlockTextureName("stone");
        setCreativeTab(ModPeatized.tab);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileBaseCompressor();
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

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float clickX,
            float clickY, float clickZ) {
        Block block = world.getBlock(x, y, z);
        TileEntity entity = world.getTileEntity(x, y, z);
        if (block != null && entity != null && player != null) {
            if (!player.isSneaking()) {
                player.openGui(ModPeatized.instance, 0, world, x, y, z);
                return true;
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
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        return side.equals(ForgeDirection.UP) || side.equals(ForgeDirection.DOWN)
                || UtilsGame.getSideFromMeta(world.getBlockMetadata(x, y, z), side.ordinal()) == 3;
    }
}
