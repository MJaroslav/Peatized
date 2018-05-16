package mjaroslav.mcmods.peatized.common.block;

import java.util.ArrayList;
import java.util.Random;

import mjaroslav.mcmods.peatized.ModPeatized;
import mjaroslav.mcmods.peatized.common.init.PeatizedBlocks;
import mjaroslav.mcmods.peatized.common.tileentity.TileUpa;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class BlockUpa extends Block implements ITileEntityProvider {
    public BlockUpa() {
        super(Material.cloth);
        setBlockTextureName("wool_colored_white");
        setBlockName("peatized.upa");
        setStepSound(Block.soundTypeCloth);
        setCreativeTab(ModPeatized.tab);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isNormalCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return PeatizedBlocks.renderBlockUpaID;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileUpa();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float clickX,
            float clickY, float clickZ) {
        Block block = world.getBlock(x, y, z);
        TileEntity entity = world.getTileEntity(x, y, z);
        if (block != null && entity != null && player != null) {
            if (!player.isSneaking()) {
                player.openGui(ModPeatized.instance, 3, world, x, y, z);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
        int l = MathHelper.floor_double((double) (placer.rotationYaw * 16.0F / 360.0F) + 0.5D) & 15;
        world.setBlockMetadataWithNotify(x, y, z, l, 2);
        TileUpa tile = (TileUpa) world.getTileEntity(x, y, z);
        tile.fromStack(stack);
    }

    private final Random rand = new Random();

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileUpa tile = (TileUpa) world.getTileEntity(x, y, z);
        if (tile != null) {
            ItemStack itemStack = tile.toStack();
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
        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
        TileUpa tile = (TileUpa) world.getTileEntity(x, y, z);
        if (tile != null) {
            NBTTagCompound nbt = new NBTTagCompound();
            tile.writeToNBT(nbt);
            ItemStack stack = new ItemStack(PeatizedBlocks.upa, 1);
            nbt.removeTag("id");
            nbt.removeTag("x");
            nbt.removeTag("y");
            nbt.removeTag("z");
            stack.setTagCompound(nbt);
            return stack;
        } else
            return super.getPickBlock(target, world, x, y, z, player);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> res = new ArrayList<ItemStack>();
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileUpa)
            res.add(((TileUpa) tile).toStack());
        return res;
    }
}
