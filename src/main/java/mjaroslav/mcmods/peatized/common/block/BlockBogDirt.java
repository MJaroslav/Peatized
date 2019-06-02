package mjaroslav.mcmods.peatized.common.block;

import mjaroslav.mcmods.peatized.common.item.ItemRegistry;
import mjaroslav.mcmods.peatized.common.lib.BlockNames;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockBogDirt extends BlockBase {
    public BlockBogDirt() {
        super(Material.GROUND, BlockNames.BOG_DIRT, SoundType.GROUND, 0, 0.5f);
        setHarvestLevel("shovel", 0);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ItemRegistry.PEAT_BALL;
    }

    @Override
    public int quantityDropped(Random random) {
        return 1 + random.nextInt(2);
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
        return quantityDropped(random) + random.nextInt(fortune + 1);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        super.getDrops(drops, world, pos, state, fortune);
        drops.add(new ItemStack(Blocks.DIRT, 1));
    }

    @Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {
        return true;
    }

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return 60;
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return 100;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D);
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        entityIn.motionX *= 0.4D;
        entityIn.motionZ *= 0.4D;
    }
}
