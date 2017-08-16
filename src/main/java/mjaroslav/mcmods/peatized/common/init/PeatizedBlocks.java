package mjaroslav.mcmods.peatized.common.init;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import mjaroslav.mcmods.peatized.PeatizedMod;
import mjaroslav.mcmods.peatized.common.block.BlockBogDirt;
import mjaroslav.mcmods.peatized.common.block.BlockCompressor;
import mjaroslav.mcmods.peatized.common.block.BlockPeat;
import mjaroslav.mcmods.peatized.common.block.BlockPeatSlab;
import mjaroslav.mcmods.peatized.common.block.BlockPeatizedStairs;
import mjaroslav.mcmods.peatized.common.block.ItemBlockBogDirt;
import mjaroslav.mcmods.peatized.common.block.ItemPeatSlab;
import mjaroslav.mcmods.peatized.common.block.ItemPeatizedBlock;
import mjaroslav.mcmods.peatized.common.tileentity.TileCompressor;
import mjaroslav.mcmods.peatized.common.tileentity.TileFuelCompressor;
import mjaroslav.mcmods.peatized.common.tileentity.TileRFCompressor;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

public class PeatizedBlocks implements IInitBase {
	public static Block bogDirt = new BlockBogDirt(Material.ground, false).setCreativeTab(PeatizedMod.tab)
			.setBlockName("peatized.bogDirt").setHardness(0.5F).setStepSound(Block.soundTypeGravel);
	public static Block bogDirtGenerated = new BlockBogDirt(Material.ground, true).setCreativeTab(PeatizedMod.tab)
			.setBlockName("peatized.bogDirt").setHardness(0.5F).setStepSound(Block.soundTypeGravel);
	public static Block compressor = new BlockCompressor(false).setStepSound(Block.soundTypeStone)
			.setBlockName("peatized.compressor").setBlockTextureName("stone").setCreativeTab(PeatizedMod.tab);
	public static Block compressorLit = new BlockCompressor(false).setLightLevel(0.875F).setStepSound(Block.soundTypeStone)
			.setBlockName("peatized.compressor").setBlockTextureName("stone");
	public static Block peat = new BlockPeat().setBlockName("peatized.peat").setStepSound(Block.soundTypeStone)
			.setHardness(1.5F).setResistance(10.0F);
	public static Block peatSlab = new BlockPeatSlab(false).setBlockName("peatized.peatSlab")
			.setBlockTextureName(PeatizedMod.MODID + ":peatbrick").setStepSound(Block.soundTypeStone).setHardness(2.0F)
			.setResistance(10.0F);
	public static Block peatSlabDouble = new BlockPeatSlab(true).setBlockName("peatized.peatSlab")
			.setBlockTextureName(PeatizedMod.MODID + ":peatbrick").setStepSound(Block.soundTypeStone).setHardness(2.0F)
			.setResistance(10.0F);
	public static Block peatStairs = new BlockPeatizedStairs(peat, 0).setBlockName("peatized.peatStairs")
			.setStepSound(Block.soundTypeStone);

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		GameRegistry.registerBlock(bogDirt, ItemBlockBogDirt.class, "bog_dirt");
		GameRegistry.registerBlock(bogDirtGenerated, ItemBlockBogDirt.class, "bog_dirt_generated");
		Blocks.fire.setFireInfo(bogDirt, 60, 20);
		Blocks.fire.setFireInfo(bogDirtGenerated, 60, 20);
		GameRegistry.registerBlock(compressor, ItemPeatizedBlock.class, "compressor");
		GameRegistry.registerBlock(compressorLit, ItemPeatizedBlock.class, "compressor_lit");
		GameRegistry.registerTileEntity(TileCompressor.class, "tile_compressor");
		GameRegistry.registerTileEntity(TileRFCompressor.class, "tile_compressor_rf");
		GameRegistry.registerTileEntity(TileFuelCompressor.class, "tile_compressor_fuel");
		GameRegistry.registerBlock(peat, ItemPeatizedBlock.class, "peat");
		GameRegistry.registerBlock(peatSlab, ItemPeatSlab.class, "peat_slab");
		GameRegistry.registerBlock(peatSlabDouble, ItemPeatSlab.class, "peat_slab_double");
		GameRegistry.registerBlock(peatStairs, "peat_stairs");
	}

	@Override
	public void init(FMLInitializationEvent event) {
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
	}
}
