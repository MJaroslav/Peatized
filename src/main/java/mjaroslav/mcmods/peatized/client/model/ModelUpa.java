package mjaroslav.mcmods.peatized.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Upa - MJaroslav Created using Tabula 4.1.1
 */
public class ModelUpa extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer frontRightLeg;
    public ModelRenderer frontLeftLeg;
    public ModelRenderer backRightLeg;
    public ModelRenderer backLeftLeg;
    public ModelRenderer leftEar;
    public ModelRenderer rightEar;
    public ModelRenderer tail;

    public float size;

    public ModelUpa(float size) {
        this.size = size;
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.leftEar = new ModelRenderer(this, 50, 0);
        this.leftEar.setRotationPoint(-6.0F, -6.0F, -2.5F);
        this.leftEar.addBox(-5.0F, -5.0F, -0.5F, 6, 6, 1, 0.0F);
        this.setRotateAngle(leftEar, 0.0F, 0.0F, 0.3490658503988659F);
        this.tail = new ModelRenderer(this, 32, 24);
        this.tail.setRotationPoint(0.0F, 0.0F, 6.0F);
        this.tail.addBox(-1.0F, 0.0F, 0.0F, 2, 2, 4, 0.0F);
        this.setRotateAngle(tail, 0.321833770397792F, 0.0F, 0.0F);
        this.rightEar = new ModelRenderer(this, 36, 0);
        this.rightEar.setRotationPoint(6.0F, -6.0F, -2.5F);
        this.rightEar.addBox(-1.0F, -5.0F, -0.5F, 6, 6, 1, 0.0F);
        this.setRotateAngle(rightEar, 0.0F, 0.0F, -0.3490658503988659F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body.addBox(-6.0F, -6.0F, -6.0F, 12, 12, 12, 0.0F);
        this.backLeftLeg = new ModelRenderer(this, 16, 24);
        this.backLeftLeg.setRotationPoint(-4.0F, 6.0F, 6.0F);
        this.backLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
        this.backRightLeg = new ModelRenderer(this, 24, 24);
        this.backRightLeg.setRotationPoint(4.0F, 6.0F, 6.0F);
        this.backRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
        this.frontRightLeg = new ModelRenderer(this, 0, 24);
        this.frontRightLeg.setRotationPoint(-4.0F, 6.0F, -6.0F);
        this.frontRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
        this.frontLeftLeg = new ModelRenderer(this, 8, 24);
        this.frontLeftLeg.setRotationPoint(4.0F, 6.0F, -6.0F);
        this.frontLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
        this.body.addChild(this.leftEar);
        this.body.addChild(this.tail);
        this.body.addChild(this.rightEar);
        this.body.addChild(this.backLeftLeg);
        this.body.addChild(this.backRightLeg);
        this.body.addChild(this.frontRightLeg);
        this.body.addChild(this.frontLeftLeg);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.body.render(f5);
    }

    public void render() {
        this.body.render(size);
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
