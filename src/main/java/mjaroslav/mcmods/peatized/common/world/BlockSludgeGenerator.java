package mjaroslav.mcmods.peatized.common.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;

public class BlockSludgeGenerator extends BaseWorldGenerator {
	public Block block;
	public int meta;
	public int numberOfBlocks;
	public Block[] validSpawnBlocks;

	public BlockSludgeGenerator(Block block, int meta, int numberOfBlocks, Block... validSpawnBlocks) {
		this.block = block;
		this.meta = meta;
		this.numberOfBlocks = numberOfBlocks;
		this.validSpawnBlocks = validSpawnBlocks;
		if (this.validSpawnBlocks == null || this.validSpawnBlocks.length <= 0)
			this.validSpawnBlocks = new Block[] { Blocks.grass, Blocks.dirt, Blocks.sand };
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
		if (world.getBlock(x, y, z).getMaterial() != Material.water && !world.canBlockSeeTheSky(x, y, z)) {
			return false;
		} else {
			int l = rand.nextInt(this.numberOfBlocks - 2) + 2;
			byte h = 1;
			for (int xx = x - l; xx <= x + l; ++xx) {
				for (int zz = z - l; zz <= z + l; ++zz) {
					int xxx = xx - x;
					int zzz = zz - z;
					if (xxx * xxx + zzz * zzz <= l * l) {
						for (int yy = y - h; yy <= y + h; ++yy) {
							Block block = world.getBlock(xx, yy, zz);
							for (Block validBlock : this.validSpawnBlocks)
								if (block == validBlock || block == this.block) {
									world.setBlock(xx, yy, zz, this.block, this.meta, 2);
									break;
								}
						}
					}
				}
			}
			return true;
		}
	}

	@Override
	public boolean canGenerate(World world, Random rand, int x, int y, int z) {
		return BiomeDictionary.isBiomeOfType(world.getBiomeGenForCoords(x, z), BiomeDictionary.Type.SWAMP);
	}

	@Override
	public int[] getCoords(World world, Random rand, int chunkX, int chunkZ) {
		int x = chunkX * 16 + rand.nextInt(16) + 8;
		int z = chunkZ * 16 + rand.nextInt(16) + 8;
		int y = world.getTopSolidOrLiquidBlock(x, z);
		return new int[] { x, y, z };
	}
}
