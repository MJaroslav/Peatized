package mjaroslav.mcmods.peatized.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * FuelCompressor - MJaroslav Created using Tabula 4.1.1
 */
public class ModelFuelCompressor extends ModelBase {
    public ModelRenderer base;
    public ModelRenderer base2;
    public ModelRenderer base3;
    public ModelRenderer base4;
    public ModelRenderer pistonStick;
    public ModelRenderer base5;
    public ModelRenderer base6;
    public ModelRenderer base7;
    public ModelRenderer base8;
    public ModelRenderer pistonHead;

    private float size;

    public ModelFuelCompressor(float size) {
        this.size = size;
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.base5 = new ModelRenderer(this, 0, 17);
        this.base5.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.base5.addBox(-8.0F, -8.0F, -8.0F, 16, 7, 16, 0.0F);
        this.base2 = new ModelRenderer(this, 0, 40);
        this.base2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.base2.addBox(2.0F, -1.0F, -8.0F, 6, 8, 16, 0.0F);
        this.base6 = new ModelRenderer(this, 6, 43);
        this.base6.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.base6.addBox(-8.0F, -1.0F, -8.0F, 1, 8, 1, 0.0F);
        this.base = new ModelRenderer(this, 0, 0);
        this.base.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.base.addBox(-8.0F, 7.0F, -8.0F, 16, 1, 16, 0.0F);
        this.base3 = new ModelRenderer(this, 44, 54);
        this.base3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.base3.addBox(-6.0F, 5.0F, -6.0F, 8, 2, 8, 0.0F);
        this.pistonHead = new ModelRenderer(this, 28, 64);
        this.pistonHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.pistonHead.addBox(-3.5F, 0.0F, -3.5F, 7, 1, 7, 0.0F);
        this.pistonStick = new ModelRenderer(this, 26, 65);
        this.pistonStick.setRotationPoint(-2.0F, 0.0F, -2.0F);
        this.pistonStick.addBox(-0.5F, -4.0F, -0.5F, 1, 4, 1, 0.0F);
        this.base4 = new ModelRenderer(this, 28, 40);
        this.base4.setRotationPoint(-1.0F, -2.0F, 0.0F);
        this.base4.addBox(-7.0F, 1.0F, 2.0F, 10, 8, 6, 0.0F);
        this.setRotateAngle(base4, 0.0F, 0.011868238913561441F, 0.0F);
        this.base8 = new ModelRenderer(this, 48, 17);
        this.base8.setRotationPoint(-3.0F, -18.0F, -3.0F);
        this.base8.addBox(0.0F, 0.0F, 0.0F, 6, 10, 6, 0.0F);
        this.base7 = new ModelRenderer(this, 0, 64);
        this.base7.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.base7.addBox(-5.5F, -1.0F, -5.5F, 7, 1, 7, 0.0F);
        this.base.addChild(this.base5);
        this.base.addChild(this.base2);
        this.base.addChild(this.base6);
        this.base.addChild(this.base3);
        this.pistonStick.addChild(this.pistonHead);
        this.base.addChild(this.pistonStick);
        this.base.addChild(this.base4);
        this.base.addChild(this.base8);
        this.base.addChild(this.base7);
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
