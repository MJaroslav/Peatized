package mjaroslav.mcmods.peatized.client.gui;

import org.lwjgl.input.Keyboard;

import mjaroslav.mcmods.peatized.common.init.NetworkHandler;
import mjaroslav.mcmods.peatized.common.network.PacketUpa;
import mjaroslav.mcmods.peatized.common.tileentity.TileUpa;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.entity.player.EntityPlayer;

public class GuiUpa extends GuiScreen {
    private final EntityPlayer editingPlayer;

    private GuiTextField colorMainTextField;
    private GuiTextField colorSecondaryTextField;
    private GuiTextField colorEarsTextField;
    private GuiTextField colorEyesTextField;
    private GuiTextField colorNoseTextField;
    private GuiTextField colorFaceTextField;
    private GuiTextField colorCheeksTextField;
    private GuiTextField colorLineTextField;

    private final TileUpa upa;
    private GuiButton doneBtn;
    private GuiButton cancelBtn;

    public GuiUpa(EntityPlayer par1EntityPlayer, TileUpa tile) {
        this.editingPlayer = par1EntityPlayer;
        upa = tile;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        this.buttonList.add(this.doneBtn = new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, "Done"));
        this.buttonList
                .add(this.cancelBtn = new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, "Cancel"));
        newPos();
        colorMainTextField = new GuiTextField(this.fontRendererObj, bX, bY, 200, 20);
        colorMainTextField.setMaxStringLength(32767);
        colorMainTextField.setText(upa.getMainColorString());
        newPos();
        colorSecondaryTextField = new GuiTextField(this.fontRendererObj, bX, bY, 200, 20);
        colorSecondaryTextField.setMaxStringLength(32767);
        colorSecondaryTextField.setText(upa.getSecondaryColorString());
        newPos();
        colorEarsTextField = new GuiTextField(this.fontRendererObj, bX, bY, 200, 20);
        colorEarsTextField.setMaxStringLength(32767);
        colorEarsTextField.setText(upa.getEarsColorString());
        newPos();
        colorEyesTextField = new GuiTextField(this.fontRendererObj, bX, bY, 200, 20);
        colorEyesTextField.setMaxStringLength(32767);
        colorEyesTextField.setText(upa.getEyesColorString());
        newPos();
        colorNoseTextField = new GuiTextField(this.fontRendererObj, bX, bY, 200, 20);
        colorNoseTextField.setMaxStringLength(32767);
        colorNoseTextField.setText(upa.getNoseColorString());
        newPos();
        colorFaceTextField = new GuiTextField(this.fontRendererObj, bX, bY, 200, 20);
        colorFaceTextField.setMaxStringLength(32767);
        colorFaceTextField.setText(upa.getFaceColorString());
        newPos();
        colorCheeksTextField = new GuiTextField(this.fontRendererObj, bX, bY, 200, 20);
        colorCheeksTextField.setMaxStringLength(32767);
        colorCheeksTextField.setText(upa.getCheeksColorString());
        newPos();
        colorLineTextField = new GuiTextField(this.fontRendererObj, bX, bY, 200, 20);
        colorLineTextField.setMaxStringLength(32767);
        colorLineTextField.setText(upa.getLineColorString());
        this.doneBtn.enabled = this.colorMainTextField.getText().trim().length() > 0
                && this.colorSecondaryTextField.getText().trim().length() > 0
                && this.colorLineTextField.getText().trim().length() > 0
                && this.colorEarsTextField.getText().trim().length() > 0
                && this.colorEyesTextField.getText().trim().length() > 0
                && this.colorCheeksTextField.getText().trim().length() > 0
                && this.colorFaceTextField.getText().trim().length() > 0
                && this.colorNoseTextField.getText().trim().length() > 0;
    }

    private int counter = 0;
    private int bX;
    private int bY;

    private void newPos() {
        if (counter > 7)
            counter = 0;
        bX = this.width / 2 - 220;
        bY = 20 + counter * 40;
        if (counter > 3) {
            bX = this.width / 2 + 20;
            bY = 20 + (counter - 4) * 40;
        }
        counter++;
    }

    @Override
    protected void actionPerformed(GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id == 1) {
                this.mc.displayGuiScreen((GuiScreen) null);
            }
            if (par1GuiButton.id == 0) {
                int main = tryParse(this.colorMainTextField.getText().replaceFirst("^#", ""), upa.getMainColor());
                int secondary = tryParse(this.colorSecondaryTextField.getText().replaceFirst("^#", ""),
                        upa.getSecondaryColor());
                int cheeks = tryParse(this.colorCheeksTextField.getText().replaceFirst("^#", ""), upa.getCheeksColor());
                int ears = tryParse(this.colorEarsTextField.getText().replaceFirst("^#", ""), upa.getEarsColor());
                int eyes = tryParse(this.colorEyesTextField.getText().replaceFirst("^#", ""), upa.getEyesColor());
                int face = tryParse(this.colorFaceTextField.getText().replaceFirst("^#", ""), upa.getFaceColor());
                int line = tryParse(this.colorLineTextField.getText().replaceFirst("^#", ""), upa.getLineColor());
                int nose = tryParse(this.colorNoseTextField.getText().replaceFirst("^#", ""), upa.getNoseColor());
                NetworkHandler.sendToServer(new PacketUpa(upa.xCoord, upa.yCoord, upa.zCoord, main, secondary, line,
                        cheeks, eyes, nose, ears, face));
                Minecraft.getMinecraft().renderGlobal.markBlockForRenderUpdate(upa.xCoord, upa.yCoord, upa.zCoord);
                this.mc.displayGuiScreen((GuiScreen) null);
            }
        }
    }

    public static Integer tryParse(String text, int defaultValue) {
        if (text.length() == 6)
            try {
                return new Integer(Integer.parseInt(text, 16));
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        return defaultValue;
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        this.colorMainTextField.textboxKeyTyped(par1, par2);
        colorCheeksTextField.textboxKeyTyped(par1, par2);
        colorEarsTextField.textboxKeyTyped(par1, par2);
        colorEyesTextField.textboxKeyTyped(par1, par2);
        colorFaceTextField.textboxKeyTyped(par1, par2);
        colorLineTextField.textboxKeyTyped(par1, par2);
        colorNoseTextField.textboxKeyTyped(par1, par2);
        colorSecondaryTextField.textboxKeyTyped(par1, par2);
        this.doneBtn.enabled = this.colorMainTextField.getText().trim().length() > 0
                && this.colorSecondaryTextField.getText().trim().length() > 0
                && this.colorLineTextField.getText().trim().length() > 0
                && this.colorEarsTextField.getText().trim().length() > 0
                && this.colorEyesTextField.getText().trim().length() > 0
                && this.colorCheeksTextField.getText().trim().length() > 0
                && this.colorFaceTextField.getText().trim().length() > 0
                && this.colorNoseTextField.getText().trim().length() > 0;

        if (par2 != 28 && par1 != 13) {
            if (par2 == 1) {
                this.actionPerformed(this.cancelBtn);
            }
        } else {
            this.actionPerformed(this.doneBtn);
        }
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
        this.colorMainTextField.mouseClicked(par1, par2, par3);
        colorCheeksTextField.mouseClicked(par1, par2, par3);
        colorEarsTextField.mouseClicked(par1, par2, par3);
        colorEyesTextField.mouseClicked(par1, par2, par3);
        colorFaceTextField.mouseClicked(par1, par2, par3);
        colorLineTextField.mouseClicked(par1, par2, par3);
        colorNoseTextField.mouseClicked(par1, par2, par3);
        colorSecondaryTextField.mouseClicked(par1, par2, par3);
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.colorMainTextField.drawTextBox();
        colorCheeksTextField.drawTextBox();
        colorEarsTextField.drawTextBox();
        colorEyesTextField.drawTextBox();
        colorFaceTextField.drawTextBox();
        colorLineTextField.drawTextBox();
        colorNoseTextField.drawTextBox();
        colorSecondaryTextField.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
