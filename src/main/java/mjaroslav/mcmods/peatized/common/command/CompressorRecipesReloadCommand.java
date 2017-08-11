package mjaroslav.mcmods.peatized.common.command;

import java.util.List;

import mjaroslav.mcmods.peatized.PeatizedMod;
import mjaroslav.mcmods.peatized.common.item.crafting.CompressorRecipes;
import mjaroslav.mcmods.peatized.common.network.NetworkHandler;
import mjaroslav.mcmods.peatized.common.network.PacketCompressingRecipes;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;

public class CompressorRecipesReloadCommand implements ICommand {

	@Override
	public int compareTo(Object o) {
		return 2;
	}

	@Override
	public String getCommandName() {
		return "compressorreload";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return StatCollector.translateToLocal("peatized.commands.compressorreload.help");
	}

	@Override
	public List getCommandAliases() {
		return null;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] options) {
		CompressorRecipes.compressing().readFromConfig();
		NetworkHandler.INSTANCE.sendToAll(new PacketCompressingRecipes());
		PeatizedMod.log.info("Compressing recipes reloaded!");
		sender.addChatMessage(
				new ChatComponentText(StatCollector.translateToLocal("peatized.commands.compressorreload.done")));
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] options) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] usernames, int index) {
		return false;
	}

}
