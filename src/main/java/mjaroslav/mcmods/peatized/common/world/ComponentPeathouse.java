package mjaroslav.mcmods.peatized.common.world;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.eventhandler.Event.Result;
import mjaroslav.mcmods.peatized.common.config.PeatizedConfig;
import mjaroslav.mcmods.peatized.common.init.PeatizedBlocks;
import mjaroslav.mcmods.peatized.common.init.PeatizedWorld;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.BiomeEvent;

public class ComponentPeathouse extends StructureVillagePieces.Village {
	public ComponentPeathouse() {
	}

	public ComponentPeathouse(StructureVillagePieces.Start par1ComponentVillageStartPiece, int type, Random par3Random,
			StructureBoundingBox par4StructureBoundingBox, int cbm) {
		super(par1ComponentVillageStartPiece, type);
		this.coordBaseMode = cbm;
		this.boundingBox = par4StructureBoundingBox;
	}

	private int groundLevel = -1;
	private boolean field_143014_b;
	private StructureVillagePieces.Start startPiece;

	public static Object buildComponent(StructureVillagePieces.Start startPiece, List pieces, Random random, int par3,
			int par4, int par5, int par6, int par7) {
		StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 10, 9,
				7, par6);
		return new ComponentPeathouse(startPiece, par7, random, var8, par6);
	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
		if (groundLevel < 0) {
			groundLevel = this.getAverageGroundLevel(world, box);
			if (groundLevel < 0)
				return true;
			boundingBox.offset(0, groundLevel - boundingBox.maxY + 9 - 1, 0);
		}
		this.fillWithBlocks(world, box, 0, 0, 0, 9, 8, 6, Blocks.air, Blocks.air, false);
		int meta = getMetadataWithOffset(Blocks.oak_stairs, 3);
		if (this.getBlockAtCurrentPosition(world, 6, 0, 0, box).getMaterial() == Material.air
				&& this.getBlockAtCurrentPosition(world, 6, -1, 0, box).getMaterial() != Material.air)
			this.placeBlockAtCurrentPosition(world, PeatizedBlocks.peatStairs, meta, 6, 0, 0, box);
		this.fillWithBlocks(world, box, 2, 0, 1, 7, 0, 1, PeatizedBlocks.peat, PeatizedBlocks.peat, false);
		this.fillWithBlocks(world, box, 1, 0, 2, 2, 0, 4, Blocks.brick_block, Blocks.brick_block, false);
		this.fillWithBlocks(world, box, 0, 0, 3, 0, 1, 3, Blocks.brick_block, Blocks.brick_block, false);
		this.fillWithBlocks(world, box, 3, 0, 2, 8, 0, 4, PeatizedBlocks.peat, PeatizedBlocks.peat, false);
		this.fillWithBlocks(world, box, 2, 0, 5, 7, 0, 5, PeatizedBlocks.peat, PeatizedBlocks.peat, false);
		this.fillWithBlocks(world, box, 2, 1, 5, 7, 1, 5, Blocks.planks, Blocks.planks, false);
		this.fillWithBlocks(world, box, 2, 3, 5, 7, 3, 5, Blocks.planks, Blocks.planks, false);
		this.fillWithBlocks(world, box, 8, 1, 2, 8, 1, 4, Blocks.planks, Blocks.planks, false);
		this.fillWithBlocks(world, box, 8, 3, 2, 8, 3, 4, Blocks.planks, Blocks.planks, false);
		this.fillWithBlocks(world, box, 2, 1, 1, 5, 1, 1, Blocks.planks, Blocks.planks, false);
		this.placeBlockAtCurrentPosition(world, Blocks.planks, 0, 7, 1, 1, box);
		this.fillWithBlocks(world, box, 2, 3, 1, 7, 3, 1, Blocks.planks, Blocks.planks, false);
		this.fillWithBlocks(world, box, 1, 0, 1, 1, 3, 1, Blocks.log, Blocks.log, false);
		this.fillWithBlocks(world, box, 1, 0, 5, 1, 3, 5, Blocks.log, Blocks.log, false);
		this.fillWithBlocks(world, box, 8, 0, 1, 8, 3, 1, Blocks.log, Blocks.log, false);
		this.fillWithBlocks(world, box, 8, 0, 5, 8, 3, 5, Blocks.log, Blocks.log, false);
		meta = getMetadataWithOffset(Blocks.oak_stairs, 0);
		this.placeBlockAtCurrentPosition(world, Blocks.brick_stairs, meta, 0, 0, 2, box);
		this.placeBlockAtCurrentPosition(world, Blocks.brick_stairs, meta, 0, 0, 4, box);
		this.placeBlockAtCurrentPosition(world, Blocks.brick_stairs, meta, 0, 2, 3, box);
		meta = getMetadataWithOffset(Blocks.oak_stairs, 3);
		this.fillWithMetadataBlocks(world, box, 0, 3, 0, 9, 3, 0, PeatizedBlocks.peatStairs, meta,
				PeatizedBlocks.peatStairs, meta, false);
		this.fillWithMetadataBlocks(world, box, 0, 4, 1, 9, 4, 1, PeatizedBlocks.peatStairs, meta,
				PeatizedBlocks.peatStairs, meta, false);
		this.fillWithMetadataBlocks(world, box, 0, 5, 2, 9, 5, 2, PeatizedBlocks.peatStairs, meta,
				PeatizedBlocks.peatStairs, meta, false);
		meta = getMetadataWithOffset(Blocks.oak_stairs, 2);
		this.fillWithMetadataBlocks(world, box, 0, 3, 6, 9, 3, 6, PeatizedBlocks.peatStairs, meta,
				PeatizedBlocks.peatStairs, meta, false);
		this.fillWithMetadataBlocks(world, box, 0, 4, 5, 9, 4, 5, PeatizedBlocks.peatStairs, meta,
				PeatizedBlocks.peatStairs, meta, false);
		this.fillWithMetadataBlocks(world, box, 0, 5, 4, 9, 5, 4, PeatizedBlocks.peatStairs, meta,
				PeatizedBlocks.peatStairs, meta, false);
		this.fillWithBlocks(world, box, 1, 1, 2, 1, 4, 2, PeatizedBlocks.peat, PeatizedBlocks.peat, false);
		this.fillWithBlocks(world, box, 1, 1, 4, 1, 4, 4, PeatizedBlocks.peat, PeatizedBlocks.peat, false);
		this.fillWithBlocks(world, box, 1, 2, 3, 1, 6, 3, Blocks.brick_block, Blocks.brick_block, false);
		this.placeBlockAtCurrentPosition(world, Blocks.cobblestone_wall, 0, 1, 7, 3, box);
		this.placeBlockAtCurrentPosition(world, Blocks.stone_slab, 4, 1, 8, 3, box);
		this.placeBlockAtCurrentPosition(world, PeatizedBlocks.peat, 1, 2, 2, 1, box);
		this.placeBlockAtCurrentPosition(world, PeatizedBlocks.peat, 1, 2, 2, 5, box);
		this.placeBlockAtCurrentPosition(world, PeatizedBlocks.peat, 1, 5, 2, 1, box);
		this.placeBlockAtCurrentPosition(world, PeatizedBlocks.peat, 1, 5, 2, 5, box);
		this.placeBlockAtCurrentPosition(world, PeatizedBlocks.peat, 1, 4, 2, 5, box);
		this.placeBlockAtCurrentPosition(world, PeatizedBlocks.peat, 1, 7, 2, 1, box);
		this.placeBlockAtCurrentPosition(world, PeatizedBlocks.peat, 1, 7, 2, 5, box);
		this.placeBlockAtCurrentPosition(world, PeatizedBlocks.peat, 1, 8, 2, 2, box);
		this.placeBlockAtCurrentPosition(world, PeatizedBlocks.peat, 1, 8, 2, 4, box);
		this.placeBlockAtCurrentPosition(world, Blocks.stained_glass_pane, 7, 8, 2, 3, box);
		this.placeBlockAtCurrentPosition(world, Blocks.stained_glass_pane, 7, 6, 2, 5, box);
		this.placeBlockAtCurrentPosition(world, Blocks.stained_glass_pane, 7, 4, 2, 1, box);
		this.placeBlockAtCurrentPosition(world, Blocks.stained_glass_pane, 7, 3, 2, 1, box);
		this.placeBlockAtCurrentPosition(world, Blocks.stained_glass_pane, 7, 3, 2, 5, box);
		meta = getMetadataWithOffset(Blocks.oak_stairs, 3);
		this.placeBlockAtCurrentPosition(world, Blocks.brick_stairs, meta, 2, 1, 2, box);
		meta = getMetadataWithOffset(Blocks.oak_stairs, 2);
		this.placeBlockAtCurrentPosition(world, Blocks.brick_stairs, meta, 2, 1, 4, box);
		this.placeBlockAtCurrentPosition(world, Blocks.stone_slab, 4, 2, 2, 3, box);
		this.placeBlockAtCurrentPosition(world, Blocks.furnace, 5, 1, 1, 3, box);
		this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 4, 1, 2, box);
		this.placeBlockAtCurrentPosition(world, Blocks.wooden_pressure_plate, 0, 4, 2, 2, box);
		this.generateStructureChestContents(world, box, rand, 6, 1, 4,
				PeatizedWorld.peathouseChestHook.getItems(rand), PeatizedWorld.peathouseChestHook.getCount(rand));
		this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 5, 2, 0, box);
		this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 5, 2, 2, box);
		this.placeDoorAtCurrentPosition(world, box, rand, 6, 1, 1, getMetadataWithOffset(Blocks.wooden_door, 1));
		meta = getMetadataWithOffset(Blocks.log, 4);
		this.fillWithMetadataBlocks(world, box, 2, 4, 3, 7, 4, 3, Blocks.log, meta, Blocks.log, meta, false);
		meta = getMetadataWithOffset(Blocks.log, 8);
		this.placeBlockAtCurrentPosition(world, Blocks.log, meta, 3, 4, 2, box);
		this.placeBlockAtCurrentPosition(world, Blocks.log, meta, 3, 4, 4, box);
		this.placeBlockAtCurrentPosition(world, Blocks.log, meta, 6, 4, 2, box);
		this.placeBlockAtCurrentPosition(world, Blocks.log, meta, 6, 4, 4, box);
		this.placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 8, 2, 4, 4, box);
		this.placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 8, 4, 4, 4, box);
		this.placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 8, 5, 4, 4, box);
		this.placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 8, 7, 4, 4, box);
		this.placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 8, 2, 4, 2, box);
		this.placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 8, 4, 4, 2, box);
		this.placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 8, 5, 4, 2, box);
		this.placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 8, 7, 4, 2, box);
		this.fillWithBlocks(world, box, 2, 5, 3, 9, 5, 3, PeatizedBlocks.peat, PeatizedBlocks.peat, false);
		this.fillWithBlocks(world, box, 2, 6, 3, 9, 6, 3, PeatizedBlocks.peatSlab, PeatizedBlocks.peatSlab, false);
		this.placeBlockAtCurrentPosition(world, PeatizedBlocks.peat, 0, 0, 5, 3, box);
		this.placeBlockAtCurrentPosition(world, PeatizedBlocks.peatSlab, 0, 0, 6, 3, box);
		this.fillWithBlocks(world, box, 8, 4, 2, 8, 4, 4, PeatizedBlocks.peat, PeatizedBlocks.peat, false);
		meta = getMetadataWithOffset(Blocks.oak_stairs, 6);
		this.placeBlockAtCurrentPosition(world, PeatizedBlocks.peatStairs, meta, 0, 3, 1, box);
		this.placeBlockAtCurrentPosition(world, PeatizedBlocks.peatStairs, meta, 9, 3, 1, box);
		this.placeBlockAtCurrentPosition(world, PeatizedBlocks.peatStairs, meta, 0, 4, 2, box);
		this.placeBlockAtCurrentPosition(world, PeatizedBlocks.peatStairs, meta, 9, 4, 2, box);
		meta = getMetadataWithOffset(Blocks.oak_stairs, 7);
		this.placeBlockAtCurrentPosition(world, PeatizedBlocks.peatStairs, meta, 0, 3, 5, box);
		this.placeBlockAtCurrentPosition(world, PeatizedBlocks.peatStairs, meta, 9, 3, 5, box);
		this.placeBlockAtCurrentPosition(world, PeatizedBlocks.peatStairs, meta, 0, 4, 4, box);
		this.placeBlockAtCurrentPosition(world, PeatizedBlocks.peatStairs, meta, 9, 4, 4, box);
		this.spawnVillagers(world, box, 5, 1, 3, 1);
		return true;
	}

	@Override
	protected int getMetadataWithOffset(Block block, int type) {
		if ((block == Blocks.log || block == Blocks.log2) && type != 0) {
			if (this.coordBaseMode == 1 || this.coordBaseMode == 3) {
				if (type < 8)
					return type + 4;
				else
					return type - 4;
			}
			return type;
		} else if (block == Blocks.stone_stairs || block == Blocks.oak_stairs || block == Blocks.nether_brick_stairs
				|| block == Blocks.stone_brick_stairs || block == Blocks.sandstone_stairs) {
			if (this.coordBaseMode == 0) {
				if (type == 7) {
					return 6;
				}
				if (type == 6) {
					return 7;
				}
			} else if (this.coordBaseMode == 1) {
				if (type == 4) {
					return 6;
				}
				if (type == 5) {
					return 7;
				}
				if (type == 6) {
					return 4;
				}
				if (type == 7) {
					return 5;
				}
			} else if (this.coordBaseMode == 3) {
				if (type == 4) {
					return 6;
				}
				if (type == 5) {
					return 7;
				}
				if (type == 6) {
					return 5;
				}
				if (type == 7) {
					return 4;
				}
			}
			return super.getMetadataWithOffset(block, type);
		} else
			return super.getMetadataWithOffset(block, type);
	}

	@Override
	protected Block func_151558_b(Block block, int type) {
		if (block == Blocks.wooden_slab) {
			BiomeEvent.GetVillageBlockID event = new BiomeEvent.GetVillageBlockID(
					startPiece == null ? null : startPiece.biome, block, type);
			MinecraftForge.TERRAIN_GEN_BUS.post(event);
			if (event.getResult() == Result.DENY)
				return event.replacement;
			if (this.field_143014_b)
				return Blocks.stone_slab;
			return block;
		} else
			return super.func_151558_b(block, type);
	}

	@Override
	protected int func_151557_c(Block block, int type) {
		if (block == Blocks.wooden_slab) {
			BiomeEvent.GetVillageBlockMeta event = new BiomeEvent.GetVillageBlockMeta(
					startPiece == null ? null : startPiece.biome, block, type);
			MinecraftForge.TERRAIN_GEN_BUS.post(event);
			if (event.getResult() == Result.DENY)
				return event.replacement;
			if (this.field_143014_b)
				return (type == 0 || type % 2 == 1) ? 1 : 9;
			return type;
		} else
			return super.func_151557_c(block, type);
	}

	@Override
	protected int getVillagerType(int type) {
		return PeatizedConfig.villagerId;
	}
}
