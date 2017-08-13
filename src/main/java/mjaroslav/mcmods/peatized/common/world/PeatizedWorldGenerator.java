package mjaroslav.mcmods.peatized.common.world;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;
import mjaroslav.mcmods.peatized.common.config.PeatizedConfig;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;

public class PeatizedWorldGenerator implements IWorldGenerator {
	public static float temperatureSwampland;
	public static float rainfallSwampland;

	public static BogDirtGeneration bogDirt = new BogDirtGeneration(5);
	public static PeathouseGenerator peathouse = new PeathouseGenerator();

	public PeatizedWorldGenerator() {
		temperatureSwampland = BiomeGenBase.swampland.temperature;
		rainfallSwampland = BiomeGenBase.swampland.rainfall;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
		switch (world.provider.dimensionId) {
		case -1:
			generateNether(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		case 0:
			generateSurface(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
			break;
		case 1:
			generateEnd(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		}
	}

	public void generateSurface(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
		if (PeatizedConfig.generateBogDirt)
			bogDirt.runGenerator(world, random, chunkX, chunkZ, 1);
		if (PeatizedConfig.peathousePercentChance >= 100 || (PeatizedConfig.peathousePercentChance != 0
				&& random.nextInt(100) < PeatizedConfig.peathousePercentChance))
			peathouse.runGenerator(world, random, chunkX, chunkZ);
	}

	public void generateNether(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
	}

	public void generateEnd(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
	}
}
