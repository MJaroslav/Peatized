package mjaroslav.mcmods.peatized.common.world;

import java.util.Random;

import mjaroslav.mcmods.peatized.common.init.PeatizedBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BogDirtGeneration extends WorldGenerator {
	private Block block;
	private int meta;
	private int numberOfBlocks;

	public BogDirtGeneration(int numberOfBlocks) {
		this.block = PeatizedBlocks.bogDirtGenerated;
		this.meta = 0;
		this.numberOfBlocks = numberOfBlocks;
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
							if (block == Blocks.grass || block == Blocks.dirt || block == this.block) {
								world.setBlock(xx, yy, zz, this.block, this.meta, 2);
							}
						}
					}
				}
			}
			return true;
		}
	}

	public void runGenerator(World world, Random rand, int chunkX, int chunkZ,
			int chancesToSpawn, int minHeight, int maxHeight) {
		if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
			throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");
		int heightDiff = maxHeight - minHeight + 1;
		for (int i = 0; i < chancesToSpawn; i++) {
			int x = chunkX * 16 + rand.nextInt(16);
			int y = minHeight + rand.nextInt(heightDiff);
			int z = chunkZ * 16 + rand.nextInt(16);
			BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
			if (biome.temperature == PeatizedWorldGenerator.temperatureSwampland
					&& biome.rainfall == PeatizedWorldGenerator.rainfallSwampland)
				generate(world, rand, x, y, z);
		}
	}
}
