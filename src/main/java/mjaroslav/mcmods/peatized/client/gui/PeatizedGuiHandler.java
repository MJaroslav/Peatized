package mjaroslav.mcmods.peatized.client.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import mjaroslav.mcmods.peatized.client.gui.inventory.GuiCompressor;
import mjaroslav.mcmods.peatized.common.inventory.ContainerCompressor;
import mjaroslav.mcmods.peatized.common.tileentity.TileCompressor;
import mjaroslav.mcmods.peatized.common.tileentity.TileFuelCompressor;
import mjaroslav.mcmods.peatized.common.tileentity.TileRFCompressor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PeatizedGuiHandler implements IGuiHandler {
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (ID == 0 && tile instanceof TileCompressor) {
			return new ContainerCompressor(player.inventory, (TileCompressor) tile);
		}
		if (ID == 1 && tile instanceof TileRFCompressor) {
			return new ContainerCompressor(player.inventory, (TileRFCompressor) tile);
		}
		if (ID == 2 && tile instanceof TileFuelCompressor) {
			return new ContainerCompressor(player.inventory, (TileFuelCompressor) tile);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (ID == 0 && tile instanceof TileCompressor) {
			return new GuiCompressor(player.inventory, (TileCompressor) tile);
		}
		if (ID == 1 && tile instanceof TileRFCompressor) {
			return new GuiCompressor(player.inventory, (TileRFCompressor) tile);
		}
		if (ID == 2 && tile instanceof TileFuelCompressor) {
			return new GuiCompressor(player.inventory, (TileFuelCompressor) tile);
		}
		return null;
	}
}
