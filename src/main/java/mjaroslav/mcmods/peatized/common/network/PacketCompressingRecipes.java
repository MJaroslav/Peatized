package mjaroslav.mcmods.peatized.common.network;

import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import mjaroslav.mcmods.peatized.common.item.crafting.CompressorRecipes;
import mjaroslav.mcmods.peatized.common.item.crafting.CompressorRecipes.CompressorRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class PacketCompressingRecipes extends AbstractPacket<PacketCompressingRecipes> {
	private Map<ItemStack, CompressorRecipe> recipeList = new HashMap();

	public PacketCompressingRecipes() {
		this.recipeList = CompressorRecipes.compressing().recipeList;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		short size = buf.readShort();
		this.recipeList = new HashMap();
		for (int id = 0; id < size; ++id) {
			ItemStack key = ByteBufUtils.readItemStack(buf);
			ItemStack result = ByteBufUtils.readItemStack(buf);
			float experience = buf.readFloat();
			int jumps = buf.readInt();
			boolean electrical = buf.readBoolean();
			this.recipeList.put(key, new CompressorRecipe(result, experience, jumps, electrical));
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		if ((this.recipeList != null) && (this.recipeList.size() > 0)) {
			buf.writeShort(this.recipeList.size());
			for (Map.Entry<ItemStack, CompressorRecipe> entry : this.recipeList.entrySet()) {
				if (entry != null) {
					ByteBufUtils.writeItemStack(buf, entry.getKey());
					CompressorRecipe recipe = entry.getValue();
					ByteBufUtils.writeItemStack(buf, recipe.result);
					buf.writeFloat(recipe.experience);
					buf.writeInt(recipe.jumps);
					buf.writeBoolean(recipe.autimatic);
				}
			}
		} else {
			buf.writeShort(0);
		}
	}

	@Override
	public void handleClientSide(final PacketCompressingRecipes message, EntityPlayer player) {
		CompressorRecipes.compressing().recipeListCache = CompressorRecipes.compressing().recipeList;
		CompressorRecipes.compressing().recipeList = message.recipeList;
	}
	
	@Override
	public void handleServerSide(final PacketCompressingRecipes message, EntityPlayer player) {
	}
	
}
