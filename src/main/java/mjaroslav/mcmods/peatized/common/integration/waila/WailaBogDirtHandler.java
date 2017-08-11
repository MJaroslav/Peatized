package mjaroslav.mcmods.peatized.common.integration.waila;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mjaroslav.mcmods.peatized.common.block.BlockBogDirt;
import mjaroslav.mcmods.peatized.common.config.PeatizedConfig;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.TempCategory;

public class WailaBogDirtHandler implements IWailaDataProvider {

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return accessor.getStack();
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return currenttip;
	}

	// TODO REWRITE THIS HERESY
	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		BlockBogDirt block = (BlockBogDirt) accessor.getBlock();
		if (!block.isGenerated()) {
			int meta = accessor.getMetadata();
			if (block.isBad(meta))
				currenttip.add(EnumChatFormatting.RED + StatCollector.translateToLocal("tooltip.error.meta"));
			int waterCount = block.getWaterCount(accessor.getWorld(), accessor.getPosition().blockX,
					accessor.getPosition().blockY, accessor.getPosition().blockZ);
			int waterOneStep = PeatizedConfig.waterSteps > 0
					? Math.round(PeatizedConfig.reqWaterMax / PeatizedConfig.waterSteps) : PeatizedConfig.reqWaterMax;
			int waterSteps = Math.abs(waterCount / waterOneStep);
			if (waterSteps > PeatizedConfig.waterSteps)
				waterSteps = PeatizedConfig.waterSteps;
			int waterStepsMax = Math.abs(PeatizedConfig.reqWaterMax / waterOneStep);
			int waterBonus = 0;
			for (int i = 0; i < waterSteps; i++)
				waterBonus += PeatizedConfig.waterStepSummand;
			waterBonus *= -1;
			int waterMaxBonus = PeatizedConfig.waterSteps * PeatizedConfig.waterStepSummand * -1;
			int waterStepBonus = PeatizedConfig.waterStepSummand * -1;
			String waterCountString = (waterCount >= PeatizedConfig.reqWaterMax ? EnumChatFormatting.GREEN
					: EnumChatFormatting.YELLOW) + String.valueOf(waterCount) + EnumChatFormatting.RESET;
			String waterReqMaxString = EnumChatFormatting.YELLOW + String.valueOf(PeatizedConfig.reqWaterMax)
					+ EnumChatFormatting.RESET;
			String waterMaxString = EnumChatFormatting.YELLOW + String.valueOf(124) + EnumChatFormatting.RESET;
			String waterBonusString = (waterBonus > 0 ? EnumChatFormatting.GREEN
					: (waterBonus < 0 ? EnumChatFormatting.RED : EnumChatFormatting.YELLOW))
					+ String.valueOf(waterBonus) + EnumChatFormatting.RESET;
			String waterMaxBonusString = EnumChatFormatting.YELLOW + String.valueOf(waterMaxBonus)
					+ EnumChatFormatting.RESET;
			String waterStepsString = (waterSteps >= waterStepsMax ? EnumChatFormatting.GREEN
					: EnumChatFormatting.YELLOW) + String.valueOf(waterSteps) + EnumChatFormatting.RESET;
			String waterStepsMaxString = EnumChatFormatting.YELLOW + String.valueOf(waterStepsMax)
					+ EnumChatFormatting.RESET;
			String waterOneStepString = EnumChatFormatting.YELLOW + String.valueOf(waterOneStep)
					+ EnumChatFormatting.RESET;
			String waterStepBonusString = (waterStepBonus > 0 ? EnumChatFormatting.GREEN
					: (waterStepBonus < 0 ? EnumChatFormatting.RED : EnumChatFormatting.YELLOW))
					+ String.valueOf(waterStepBonus) + EnumChatFormatting.RESET;
			String water = StatCollector.translateToLocal("tooltip.bogDirt.water.0")
					.replace("{waterCount}", waterCountString).replace("{waterReqMax}", waterReqMaxString)
					.replace("{waterMax}", waterMaxString).replace("{waterBonus}", waterBonusString)
					.replace("{waterMaxBonus}", String.valueOf(waterMaxBonusString));
			String water1 = StatCollector.translateToLocal("tooltip.bogDirt.water.1")
					.replace("{waterReqMax}", String.valueOf(waterReqMaxString))
					.replace("{waterSteps}", waterStepsString).replace("{waterStepsMax}", waterStepsMaxString);
			String water2 = StatCollector.translateToLocal("tooltip.bogDirt.water.2")
					.replace("{waterOneStep}", waterOneStepString).replace("{waterStepBonus}", waterStepBonusString);
			currenttip.add(water);
			currenttip.add(water1);
			currenttip.add(water2);

			int rainBonus = accessor.getWorld().canLightningStrikeAt(accessor.getPosition().blockX,
					accessor.getPosition().blockY + 1, accessor.getPosition().blockZ) ? PeatizedConfig.rainSummand * -1
							: 0;
			String rainBonusString = (rainBonus > 0 ? EnumChatFormatting.GREEN
					: (rainBonus < 0 ? EnumChatFormatting.RED : EnumChatFormatting.YELLOW)) + String.valueOf(rainBonus)
					+ EnumChatFormatting.RESET;
			currenttip.add(
					StatCollector.translateToLocal("tooltip.bogDirt.rain").replace("{rainBonus}", rainBonusString));

			int biomeBonus = ((accessor.getWorld()
					.getBiomeGenForCoords(accessor.getPosition().blockX, accessor.getPosition().blockZ).isHighHumidity()
							? 1 : 0)
					* PeatizedConfig.biomeSummand
					+ (accessor.getWorld()
							.getBiomeGenForCoords(accessor.getPosition().blockX, accessor.getPosition().blockZ)
							.getTempCategory().equals(TempCategory.WARM) ? -1 : 0) * PeatizedConfig.biomeSummand)
					* -1;
			String biomeBonusString = (biomeBonus > 0 ? EnumChatFormatting.GREEN
					: (biomeBonus < 0 ? EnumChatFormatting.RED : EnumChatFormatting.YELLOW))
					+ String.valueOf(biomeBonus) + EnumChatFormatting.RESET;
			currenttip.add(
					StatCollector.translateToLocal("tooltip.bogDirt.biome").replace("{biomeBonus}", biomeBonusString));

			int totalBonus = waterBonus + rainBonus + biomeBonus;
			String totalBonusString = (totalBonus > 0 ? EnumChatFormatting.GREEN
					: (totalBonus < 0 ? EnumChatFormatting.RED : EnumChatFormatting.YELLOW))
					+ String.valueOf(totalBonus) + EnumChatFormatting.RESET;
			currenttip.add(
					StatCollector.translateToLocal("tooltip.bogDirt.total").replace("{totalBonus}", totalBonusString));
			String humidity = StatCollector.translateToLocal("tooltip.bogDirt.humidity");
			String value = block.getGrowth(meta);
			if (block.isBad(meta)) {
				value = EnumChatFormatting.RED + value + EnumChatFormatting.RESET;
			} else if (block.isMature(meta))
				value = EnumChatFormatting.GREEN + value + EnumChatFormatting.RESET;
			else
				value = EnumChatFormatting.YELLOW + value + EnumChatFormatting.RESET;
			currenttip.add(humidity.replace("{value}", value));
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x,
			int y, int z) {
		return tag;
	}

}
