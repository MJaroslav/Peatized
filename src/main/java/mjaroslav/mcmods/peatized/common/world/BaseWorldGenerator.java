package mjaroslav.mcmods.peatized.common.world;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class BaseWorldGenerator extends WorldGenerator implements IWorldGenerator {
	public int[] allowedDims = new int[] { 0 };

	public BaseWorldGenerator() {
	}

	public BaseWorldGenerator(int... allowedDims) {
		this.allowedDims = allowedDims;
	}

	public abstract boolean canGenerate(World world, Random rand, int x, int y, int z);

	public final boolean runGenerator(World world, Random rand, int chunkX, int chunkZ) {
		int[] coords = getCoords(world, rand, chunkX, chunkZ);
		int x = coords[0];
		int y = coords[1];
		int z = coords[2];
		return this.canGenerate(world, rand, x, y, z) && this.generate(world, rand, x, y, z);
	}

	@Override
	public final void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
		for (int id : allowedDims)
			if (world.provider.dimensionId == id)
				this.runGenerator(world, random, chunkX, chunkZ);
	}

	public abstract int[] getCoords(World world, Random rand, int chunkX, int chunkZ);
}
