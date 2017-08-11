package mjaroslav.mcmods.peatized.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelCompressor extends ModelBase {
	ModelRenderer compressorBase;
	ModelRenderer compressorCover;
	ModelRenderer compressorBody;
	ModelRenderer compressorBody1;
	ModelRenderer compressorOutLeft;
	ModelRenderer compressorOutBack;
	ModelRenderer compressorOutRight;
	ModelRenderer compressorOutForward;

	public float size;

	public ModelCompressor(float size) {
		this.size = size;
		textureWidth = 64;
		textureHeight = 64;
		compressorBase = new ModelRenderer(this, 0, 43);
		compressorBase.addBox(-8F, 0F, -8F, 16, 1, 16);
		compressorBase.setRotationPoint(0F, 23F, 0F);
		compressorBase.setTextureSize(64, 64);
		compressorBase.mirror = true;
		setRotation(compressorBase, 0F, 0F, 0F);
		compressorCover = new ModelRenderer(this, 0, 0);
		compressorCover.addBox(-8F, -15F, -8F, 16, 1, 16);
		compressorCover.setRotationPoint(0F, 23F, 0F);
		compressorCover.setTextureSize(64, 64);
		compressorCover.mirror = true;
		setRotation(compressorCover, 0F, 0F, 0F);
		compressorBody = new ModelRenderer(this, 0, 26);
		compressorBody.addBox(-5F, -2F, -5F, 10, 7, 10);
		compressorBody.setRotationPoint(0F, 18F, 0F);
		compressorBody.setTextureSize(64, 64);
		compressorBody.mirror = true;
		setRotation(compressorBody, 0F, 0F, 0F);
		compressorBody1 = new ModelRenderer(this, 0, 17);
		compressorBody1.addBox(-6F, -9F, -6F, 12, 7, 12);
		compressorBody1.setRotationPoint(0F, 18F, 0F);
		compressorBody1.setTextureSize(64, 64);
		compressorBody1.mirror = true;
		setRotation(compressorBody1, 0F, 0F, 0F);
		compressorOutLeft = new ModelRenderer(this, 36, 17);
		compressorOutLeft.addBox(-8F, -4.466667F, -2.5F, 5, 5, 5);
		compressorOutLeft.setRotationPoint(0F, 18F, 0F);
		compressorOutLeft.setTextureSize(64, 64);
		compressorOutLeft.mirror = true;
		setRotation(compressorOutLeft, 0F, 0F, 0F);
		compressorOutBack = new ModelRenderer(this, 36, 17);
		compressorOutBack.addBox(-8F, -4.466667F, -2.5F, 5, 5, 5);
		compressorOutBack.setRotationPoint(0F, 18F, 0F);
		compressorOutBack.setTextureSize(64, 64);
		compressorOutBack.mirror = true;
		setRotation(compressorOutBack, 0F, 1.570796F, 0F);
		compressorOutRight = new ModelRenderer(this, 36, 17);
		compressorOutRight.addBox(-8F, -4.466667F, -2.5F, 5, 5, 5);
		compressorOutRight.setRotationPoint(0F, 18F, 0F);
		compressorOutRight.setTextureSize(64, 64);
		compressorOutRight.mirror = true;
		setRotation(compressorOutRight, 0F, 3.141593F, 0F);
		compressorOutForward = new ModelRenderer(this, 36, 17);
		compressorOutForward.addBox(-8F, -4.466667F, -2.5F, 5, 5, 5);
		compressorOutForward.setRotationPoint(0F, 18F, 0F);
		compressorOutForward.setTextureSize(64, 64);
		compressorOutForward.mirror = true;
		setRotation(compressorOutForward, 0F, -1.570796F, 0F);
	}

	public void render() {
		compressorBase.render(size);
		compressorCover.render(size);
		compressorBody.render(size);
		compressorBody1.render(size);
		compressorOutLeft.render(size);
		compressorOutBack.render(size);
		compressorOutRight.render(size);
		compressorOutForward.render(size);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
