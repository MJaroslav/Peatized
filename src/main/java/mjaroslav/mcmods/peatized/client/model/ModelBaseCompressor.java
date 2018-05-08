package mjaroslav.mcmods.peatized.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * BaseCompressor - MJaroslav Created using Tabula 4.1.1
 */
public class ModelBaseCompressor extends ModelBase {
    public ModelRenderer base;
    public ModelRenderer base1;
    public ModelRenderer base2;
    public ModelRenderer pistonBox;
    public ModelRenderer base3;
    public ModelRenderer base4;
    public ModelRenderer base5;
    public ModelRenderer base6;
    public ModelRenderer pistonStick;
    public ModelRenderer pistonHead;

    private float size;

    public ModelBaseCompressor(float size) {
        this.size = size;
        this.textureWidth = 64;
        this.textureHeight = 76;
        this.pistonStick = new ModelRenderer(this, 0, 68);
        this.pistonStick.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.pistonStick.addBox(-0.5F, -4.0F, -0.5F, 1, 4, 1, 0.0F);
        this.base6 = new ModelRenderer(this, 4, 18);
        this.base6.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.base6.addBox(-7.0F, -4.0F, -7.0F, 1, 12, 1, 0.0F);
        this.pistonHead = new ModelRenderer(this, 0, 67);
        this.pistonHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.pistonHead.addBox(-3.5F, 0.0F, -3.5F, 7, 1, 7, 0.0F);
        this.base5 = new ModelRenderer(this, 0, 18);
        this.base5.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.base5.addBox(6.0F, -4.0F, -7.0F, 1, 12, 1, 0.0F);
        this.base3 = new ModelRenderer(this, 0, 51);
        this.base3.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.base3.addBox(-6.0F, -4.0F, 4.0F, 12, 12, 4, 0.0F);
        this.pistonBox = new ModelRenderer(this, 32, 51);
        this.pistonBox.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.pistonBox.addBox(-3.5F, -4.0F, -3.5F, 7, 4, 7, 0.0F);
        this.base2 = new ModelRenderer(this, 32, 62);
        this.base2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.base2.addBox(-4.0F, 4.0F, -4.0F, 8, 3, 8, 0.0F);
        this.base1 = new ModelRenderer(this, 0, 17);
        this.base1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.base1.addBox(-8.0F, -8.0F, -8.0F, 16, 1, 16, 0.0F);
        this.base = new ModelRenderer(this, 0, 0);
        this.base.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.base.addBox(-8.0F, 7.0F, -8.0F, 16, 1, 16, 0.0F);
        this.base4 = new ModelRenderer(this, 0, 34);
        this.base4.setRotationPoint(0.0F, -1.0F, -7.0F);
        this.base4.addBox(-7.0F, -6.0F, 0.0F, 14, 2, 15, 0.0F);
        this.base.addChild(this.pistonStick);
        this.base.addChild(this.base6);
        this.pistonStick.addChild(this.pistonHead);
        this.base.addChild(this.base5);
        this.base.addChild(this.base3);
        this.base.addChild(this.pistonBox);
        this.base.addChild(this.base2);
        this.base.addChild(this.base1);
        this.base.addChild(this.base4);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.base.render(f5);
    }

    public void render() {
        this.base.render(size);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
