package KOWI2003.LaserMod.tileentities.render.models;

import java.util.ArrayList;
import java.util.HashMap;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

// Made with Blockbench 4.2.4
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


public class LaserControllerLevers<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "lasercontrollerlevers"), "main");
	public ModelPart MainLever;
	public ModelPart ModeSlider;
	public ModelPart Blue;
	public ModelPart Green;
	public ModelPart Red;

	public LaserControllerLevers() {
		this.MainLever = new ModelPart(new ArrayList<ModelPart.Cube>(), new HashMap<String, ModelPart>());
		this.ModeSlider = new ModelPart(new ArrayList<ModelPart.Cube>(), new HashMap<String, ModelPart>());
		this.Blue = new ModelPart(new ArrayList<ModelPart.Cube>(), new HashMap<String, ModelPart>());
		this.Green = new ModelPart(new ArrayList<ModelPart.Cube>(), new HashMap<String, ModelPart>());
		this.Red = new ModelPart(new ArrayList<ModelPart.Cube>(), new HashMap<String, ModelPart>());
	}
	
	public void init(ModelPart root) {
		this.MainLever = root.getChild("MainLever");
		this.ModeSlider = root.getChild("ModeSlider");
		this.Blue = root.getChild("Blue");
		this.Green = root.getChild("Green");
		this.Red = root.getChild("Red");
	}
	
	public LaserControllerLevers(ModelPart root) {
		init(root);
	}

	@SuppressWarnings("unused")
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition MainLever = partdefinition.addOrReplaceChild("MainLever", CubeListBuilder.create(), PartPose.offset(7.65F, 10.4F, 0.5F));

		PartDefinition top_r1 = MainLever.addOrReplaceChild("top_r1", CubeListBuilder.create().texOffs(10, 3).addBox(2.6F, -9.6F, -4.0F, 2.0F, 0.0F, 0.0F, new CubeDeformation(0.3F))
		.texOffs(0, 0).addBox(2.7F, -9.3F, -4.0F, 0.0F, 2.0F, 0.0F, new CubeDeformation(0.25F))
		.texOffs(0, 0).addBox(4.4F, -9.3F, -4.0F, 0.0F, 2.0F, 0.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-7.0F, 2.425F, 8.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition ModeSlider = partdefinition.addOrReplaceChild("ModeSlider", CubeListBuilder.create().texOffs(11, 1).addBox(-4.6F, -3.7F, -8.3F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.3F))
		.texOffs(0, 1).addBox(-4.6F, -3.7F, -8.1F, 0.0F, 0.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offset(16.95F, 15.1F, 7.9F));

		PartDefinition Blue = partdefinition.addOrReplaceChild("Blue", CubeListBuilder.create().texOffs(12, 1).addBox(-4.7F, 2.7F, -8.3F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.3F))
		.texOffs(0, 1).addBox(-4.7F, 2.7F, -8.1F, 0.0F, 0.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offset(17.1F, 2.35F, 7.9F));

		PartDefinition Green = partdefinition.addOrReplaceChild("Green", CubeListBuilder.create().texOffs(11, 4).addBox(-4.7F, 1.4F, -8.3F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.3F))
		.texOffs(0, 1).addBox(-4.7F, 1.4F, -8.1F, 0.0F, 0.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offset(17.1F, 4.9F, 7.9F));

		PartDefinition Red = partdefinition.addOrReplaceChild("Red", CubeListBuilder.create().texOffs(11, 3).addBox(-4.7F, 0.1F, -8.3F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.3F))
		.texOffs(0, 1).addBox(-4.7F, 0.1F, -8.1F, 0.0F, 0.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offset(17.1F, 7.55F, 7.9F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}
	
	public void setValues(float red, float green, float blue, float mode, float modeMax) {
		ModeSlider.setPos(16.95F, 15.1F - (1.7f * (mode/modeMax)) , 7.9F);
		Red.setPos(17.1F - (3.1f * red), 7.55F, 7.9F);
		Green.setPos(17.1F - (3.1f * green), 4.9F, 7.9F);
		Blue.setPos(17.1F - (3.1f * blue), 2.35F, 7.9F);
	}
	
	public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		MainLever.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		ModeSlider.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Blue.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Green.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Red.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}