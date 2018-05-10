package mjaroslav.mcmods.peatized.client.gui.inventory;

import java.util.*;

import org.lwjgl.opengl.GL11;

import mjaroslav.mcmods.peatized.common.inventory.ContainerCompressor;
import mjaroslav.mcmods.peatized.common.item.crafting.CompressorRecipes;
import mjaroslav.mcmods.peatized.common.tileentity.*;
import mjaroslav.mcmods.peatized.lib.ModInfo;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

public class GuiCompressor extends GuiContainer {
    private static final ResourceLocation compressorGuiTextures = new ResourceLocation(
            ModInfo.MODID + ":textures/gui/container/compressor.png");
    private ICompressor tile;

    public GuiCompressor(InventoryPlayer player, TileBaseCompressor tile) {
        super(new ContainerCompressor(player, tile));
        this.tile = tile;
    }

    public GuiCompressor(InventoryPlayer player, TileRFCompressor tile) {
        super(new ContainerCompressor(player, tile));
        this.tile = tile;
    }

    public GuiCompressor(InventoryPlayer player, TileFuelCompressor tile) {
        super(new ContainerCompressor(player, tile));
        this.tile = tile;
    }

    public void drawHoveringText(int x, int y, int xPos, int yPost, int width, int height, FontRenderer font,
            String... text) {
        if (text == null || text.length == 0)
            return;
        int xZero = (this.width - this.xSize) / 2;
        int yZero = (this.height - this.ySize) / 2;
        if (x > xZero + xPos && x < xZero + width + xPos && y > yZero + yPost && y < yZero + height + yPost) {
            ArrayList<String> strings = new ArrayList<String>();
            Iterator iterator = Arrays.asList(text).iterator();
            while (iterator.hasNext()) {
                String string = (String) iterator.next();
                String[] lines = string.split("&n");
                strings.addAll(Arrays.asList(lines));
            }
            int labeLenght = 0;
            iterator = strings.iterator();
            while (iterator.hasNext()) {
                String string = (String) iterator.next();
                int lenght = font.getStringWidth(string);
                if (lenght > 120)
                    if (lenght > labeLenght) {
                        labeLenght = lenght;
                    }
            }
            int xText = x - xZero;
            int yText = y - yZero;
            if (x + labeLenght + 12 > this.width)
                xText -= labeLenght + 12 + 12;
            this.drawHoveringText(strings, xText, yText, font);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2,
                4210752);
        String s = this.tile.getCompressor().currentActivations + "/" + this.tile.getCompressor().activations;
        fontRendererObj.drawString(s, 69, 42, 4210752);
        s = ((IInventory) tile).hasCustomInventoryName() ? ((IInventory) tile).getInventoryName()
                : I18n.format(((IInventory) tile).getInventoryName(), new Object[0]);
        this.fontRendererObj.drawString(s, 6, 6, 4210752);
        if (tile instanceof TileBaseCompressor)
            fgBase((TileBaseCompressor) tile, x, y);
        if (tile instanceof TileFuelCompressor)
            fgFuel((TileFuelCompressor) tile, x, y);
        if (tile instanceof TileRFCompressor)
            fgRF((TileRFCompressor) tile, x, y);
    }

    protected void fgBase(TileBaseCompressor tile, int x, int y) {
        if (tile.getCompressor().canWork() && tile.hasPlate)
            this.drawHoveringText(x, y, 162, 72, 7, 7, this.fontRendererObj,
                    new String[] { StatCollector.translateToLocal("container.compressor.ready") });
        this.drawHoveringText(x, y, 162, 62, 7, 7, this.fontRendererObj,
                new String[] { StatCollector.translateToLocal("container.compressor.info") });
        if (!tile.hasPlate)
            this.drawHoveringText(x, y, 162, 52, 7, 7, this.fontRendererObj,
                    new String[] { StatCollector.translateToLocal("container.compressor.noPlate") });
        if (tile.getCompressor().inventory[0] == null
                || CompressorRecipes.compressing().getCompressingResult(tile.getCompressor().inventory[0]) == null
                || CompressorRecipes.compressing().getStackSizeForCompressing(
                        tile.getCompressor().inventory[0]) > tile.getCompressor().inventory[0].stackSize
                || CompressorRecipes.compressing().isAutomatic(tile.getCompressor().inventory[0]))
            this.drawHoveringText(x, y, 162, 42, 7, 7, this.fontRendererObj,
                    new String[] { StatCollector.translateToLocal("container.compressor.noRecipe") });
        if (tile.getCompressor().inventoryFull())
            this.drawHoveringText(x, y, 162, 32, 7, 7, this.fontRendererObj,
                    new String[] { StatCollector.translateToLocal("container.compressor.inventoryFull") });
    }

    protected void fgFuel(TileFuelCompressor tile, int x, int y) {
        if (this.tile.getCompressor().canWork() && tile.hasFuel())
            this.drawHoveringText(x, y, 162, 72, 7, 7, this.fontRendererObj,
                    new String[] { StatCollector.translateToLocal("container.compressor.ready") });
        this.drawHoveringText(x, y, 162, 62, 7, 7, this.fontRendererObj,
                new String[] { StatCollector.translateToLocal("container.compressor.info.fuel") });
        if (!tile.hasFuel())
            this.drawHoveringText(x, y, 162, 52, 7, 7, this.fontRendererObj,
                    new String[] { StatCollector.translateToLocal("container.compressor.noFuel") });
        if (this.tile.getCompressor().inventory[0] == null
                || CompressorRecipes.compressing().getCompressingResult(this.tile.getCompressor().inventory[0]) == null
                || CompressorRecipes.compressing().getStackSizeForCompressing(
                        this.tile.getCompressor().inventory[0]) > this.tile.getCompressor().inventory[0].stackSize)
            this.drawHoveringText(x, y, 162, 42, 7, 7, this.fontRendererObj,
                    new String[] { StatCollector.translateToLocal("container.compressor.noRecipe") });
        if (this.tile.getCompressor().inventoryFull())
            this.drawHoveringText(x, y, 162, 32, 7, 7, this.fontRendererObj,
                    new String[] { StatCollector.translateToLocal("container.compressor.inventoryFull") });
    }

    protected void fgRF(TileRFCompressor tile, int x, int y) {
        if (this.tile.getCompressor().canWork() && tile.hasEnergy())
            this.drawHoveringText(x, y, 162, 72, 7, 7, this.fontRendererObj,
                    new String[] { StatCollector.translateToLocal("container.compressor.ready") });
        this.drawHoveringText(x, y, 162, 62, 7, 7, this.fontRendererObj,
                new String[] { StatCollector.translateToLocal("container.compressor.info.rf") });
        if (!tile.hasEnergy())
            this.drawHoveringText(x, y, 162, 52, 7, 7, this.fontRendererObj,
                    new String[] { StatCollector.translateToLocal("container.compressor.noEnergy") });
        if (this.tile.getCompressor().inventory[0] == null
                || CompressorRecipes.compressing().getCompressingResult(this.tile.getCompressor().inventory[0]) == null
                || CompressorRecipes.compressing().getStackSizeForCompressing(
                        this.tile.getCompressor().inventory[0]) > this.tile.getCompressor().inventory[0].stackSize)
            this.drawHoveringText(x, y, 162, 42, 7, 7, this.fontRendererObj,
                    new String[] { StatCollector.translateToLocal("container.compressor.noRecipe") });
        if (this.tile.getCompressor().inventoryFull())
            this.drawHoveringText(x, y, 162, 32, 7, 7, this.fontRendererObj,
                    new String[] { StatCollector.translateToLocal("container.compressor.inventoryFull") });
        this.drawHoveringText(x, y, 9, 16, 14, 42, this.fontRendererObj, tile.getEnergyStored(ForgeDirection.UNKNOWN)
                + "/" + tile.getMaxEnergyStored(ForgeDirection.UNKNOWN) + " RF");
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float size, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(compressorGuiTextures);
        int xZero = (this.width - this.xSize) / 2;
        int yZero = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(xZero, yZero, 0, 0, this.xSize, this.ySize);
        this.drawTexturedModalRect(xZero + 162, yZero + 62, 183, 48, 7, 7);
        if (!this.tile.getCompressor().inventoryFull())
            this.drawTexturedModalRect(xZero + 162, yZero + 32, 197, 48, 7, 7);
        else
            this.drawTexturedModalRect(xZero + 162, yZero + 32, 190, 48, 7, 7);
        if (tile instanceof TileBaseCompressor)
            bgBase((TileBaseCompressor) tile, size, x, y, xZero, yZero);
        if (tile instanceof TileFuelCompressor)
            bgFuel((TileFuelCompressor) tile, size, x, y, xZero, yZero);
        if (tile instanceof TileRFCompressor)
            bgRF((TileRFCompressor) tile, size, x, y, xZero, yZero);
    }

    public void bgBase(TileBaseCompressor tile, float size, int x, int y, int xZero, int yZero) {
        if (this.tile.getCompressor().inventory[0] == null
                || CompressorRecipes.compressing().getCompressingResult(this.tile.getCompressor().inventory[0]) == null
                || CompressorRecipes.compressing().getStackSizeForCompressing(
                        this.tile.getCompressor().inventory[0]) > this.tile.getCompressor().inventory[0].stackSize
                || CompressorRecipes.compressing().isAutomatic(tile.getCompressor().inventory[0]))
            this.drawTexturedModalRect(xZero + 162, yZero + 42, 190, 48, 7, 7);
        else
            this.drawTexturedModalRect(xZero + 162, yZero + 42, 197, 48, 7, 7);
        if (this.tile.getCompressor().isWorking()) {
            int i1 = tile.getScaleForProgressTime(48);
            this.drawTexturedModalRect(xZero + 109, yZero + 19, 176, 0, 48, i1);
        }
        if (this.tile.getCompressor().canWork() && tile.hasPlate)
            this.drawTexturedModalRect(xZero + 162, yZero + 72, 211, 48, 7, 7);
        else
            this.drawTexturedModalRect(xZero + 162, yZero + 72, 218, 48, 7, 7);
        if (tile.hasPlate)
            this.drawTexturedModalRect(xZero + 162, yZero + 52, 197, 48, 7, 7);
        else
            this.drawTexturedModalRect(xZero + 162, yZero + 52, 176, 48, 7, 7);
    }

    public void bgFuel(TileFuelCompressor tile, float size, int x, int y, int xZero, int yZero) {
        if (this.tile.getCompressor().inventory[0] == null
                || CompressorRecipes.compressing().getCompressingResult(this.tile.getCompressor().inventory[0]) == null
                || CompressorRecipes.compressing().getStackSizeForCompressing(
                        this.tile.getCompressor().inventory[0]) > this.tile.getCompressor().inventory[0].stackSize)
            this.drawTexturedModalRect(xZero + 162, yZero + 42, 190, 48, 7, 7);
        else
            this.drawTexturedModalRect(xZero + 162, yZero + 42, 197, 48, 7, 7);
        this.drawTexturedModalRect(xZero, yZero, 176, 135, 32, 80);
        int i1 = tile.getScaleForStoredFuel(14);
        this.drawTexturedModalRect(xZero + 9, yZero + 21 + 14 - i1, 240, 70 + 14 - i1, 14, i1);
        if (this.tile.getCompressor().canWork() && tile.hasFuel())
            this.drawTexturedModalRect(xZero + 162, yZero + 72, 211, 48, 7, 7);
        else
            this.drawTexturedModalRect(xZero + 162, yZero + 72, 218, 48, 7, 7);
        if (this.tile.getCompressor().isWorking()) {
            int i2 = tile.getScaleForProgressTime(48);
            this.drawTexturedModalRect(xZero + 109, yZero + 19, 176, 0, 48, i2);
        }
        if (tile.hasFuel())
            this.drawTexturedModalRect(xZero + 162, yZero + 52, 197, 48, 7, 7);
        else
            this.drawTexturedModalRect(xZero + 162, yZero + 52, 176, 48, 7, 7);
    }

    public void bgRF(TileRFCompressor tile, float size, int x, int y, int xZero, int yZero) {
        if (this.tile.getCompressor().inventory[0] == null
                || CompressorRecipes.compressing().getCompressingResult(this.tile.getCompressor().inventory[0]) == null
                || CompressorRecipes.compressing().getStackSizeForCompressing(
                        this.tile.getCompressor().inventory[0]) > this.tile.getCompressor().inventory[0].stackSize)
            this.drawTexturedModalRect(xZero + 162, yZero + 42, 190, 48, 7, 7);
        else
            this.drawTexturedModalRect(xZero + 162, yZero + 42, 197, 48, 7, 7);
        this.drawTexturedModalRect(xZero, yZero, 176, 55, 32, 80);
        this.drawTexturedModalRect(xZero + 9, yZero + 20, 224, 0, 14, 42);
        int i1 = tile.getScaleForStoredEnergy(42);
        this.drawTexturedModalRect(xZero + 9, yZero + 20 + 42 - i1, 238, 0 + 42 - i1, 14, i1);
        if (this.tile.getCompressor().canWork() && tile.hasEnergy())
            this.drawTexturedModalRect(xZero + 162, yZero + 72, 211, 48, 7, 7);
        else
            this.drawTexturedModalRect(xZero + 162, yZero + 72, 218, 48, 7, 7);
        if (this.tile.getCompressor().isWorking()) {
            int i2 = tile.getScaleForProgressTime(48);
            this.drawTexturedModalRect(xZero + 109, yZero + 19, 176, 0, 48, i2);
        }
        if (tile.hasEnergy())
            this.drawTexturedModalRect(xZero + 162, yZero + 52, 197, 48, 7, 7);
        else
            this.drawTexturedModalRect(xZero + 162, yZero + 52, 176, 48, 7, 7);
    }
}
