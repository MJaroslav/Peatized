package mjaroslav.mcmods.peatized.common.block;

import mjaroslav.mcmods.peatized.common.init.PeatizedBlocks;
import mjaroslav.mcmods.peatized.common.tileentity.TileUpa;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockUpa extends Block implements ITileEntityProvider {
    public BlockUpa() {
        super(Material.cloth);
        setBlockTextureName("wool_colored_white");
        setBlockName("peatized.upa");
        setStepSound(Block.soundTypeCloth);
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
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
        int l = MathHelper.floor_double((double) (placer.rotationYaw * 16.0F / 360.0F) + 0.5D) & 15;
        world.setBlockMetadataWithNotify(x, y, z, l, 2);
    }
}
