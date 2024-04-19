package KOWI2003.LaserMod.items.render.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import KOWI2003.LaserMod.LaserProperties;
import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.utils.LaserItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

// Made with Blockbench 4.2.5
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


public class LaserArmorModel<T extends LivingEntity> extends HumanoidModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	static final ModelPart bakedHumanoidModel = HumanoidModel.createMesh(CubeDeformation.NONE, 0).getRoot().bake(16, 16);
	static ModelPart bakedModel;

	public boolean HelmetVisible = true;
	private ModelPart HelmetBand;
	private ModelPart HelmetGem;
	public ModelPart HelmetActive;
	public ModelPart HelmetActiveAirtight;
	
	
	public boolean ChestVisible = true;
	private ModelPart ChestBase;
	private ModelPart ChestRightArm;
	private ModelPart ChestLeftArm;
	private ModelPart ChestGem;
	public ModelPart ChestActive;
	public ModelPart ChestActiveRightArm;
	public ModelPart ChestActiveLeftArm;
	

	public boolean LeggingsVisible = true;
	public boolean LeggingsActive = false;
	private ModelPart LeggingsBase;
	private ModelPart LeggingsGem;
	private ModelPart LeggingsActiveLeft;
	private ModelPart LeggingsActiveRight;
	private ModelPart LeggingsActiveTop;

	public boolean BootsVisible = true;
	public boolean BootsActive = false;
	private ModelPart GemLeftBoot;
	private ModelPart GemRightBoot;
	private ModelPart LeftBoot;
	private ModelPart RightBoot;
	private ModelPart BootsActiveLeft;
	private ModelPart BootsActiveRight;
	
	
	ItemStack stack;

	public void init(ModelPart root) {
		this.HelmetBand = root.getChild("HelmetBand");
		this.HelmetGem = root.getChild("HelmetGem");
		this.HelmetActive = root.getChild("HelmetActive");
		this.HelmetActiveAirtight = root.getChild("HelmetActiveAirtight");
		
		this.ChestBase = root.getChild("ChestBase");
		this.ChestRightArm = root.getChild("ChestArmRight");
		this.ChestLeftArm = root.getChild("ChestArmLeft");
		this.ChestGem = root.getChild("ChestGem");
		this.ChestActive = root.getChild("ChestplateActive");
		this.ChestActiveRightArm = root.getChild("ChestplateActiveRightArm");
		this.ChestActiveLeftArm = root.getChild("ChestplateActiveLeftArm");
		
		this.LeggingsBase = root.getChild("LeggingsBase");
		this.LeggingsGem = root.getChild("LeggingsGem");
		this.LeggingsActiveLeft = root.getChild("LeggingsActiveLeft");
		this.LeggingsActiveRight = root.getChild("LeggingsActiveRight");
		this.LeggingsActiveTop = root.getChild("LeggingsActiveTop");

		this.GemLeftBoot = root.getChild("GemLeftBoot");
		this.GemRightBoot = root.getChild("GemRightBoot");
		this.LeftBoot = root.getChild("LeftBoot");
		this.RightBoot = root.getChild("RightBoot");
		this.BootsActiveLeft = root.getChild("BootsActiveLeft");
		this.BootsActiveRight = root.getChild("BootsActiveRight");
	}
	
	public LaserArmorModel(ModelPart root) {
		super(bakedHumanoidModel);
		init(root);
	}
	
	public LaserArmorModel(ItemStack stack) {
		super(bakedHumanoidModel);
		//init(getBakedModel());
		init(createBodyLayer().bakeRoot());
		this.stack = stack;
		
	}
	
	static ModelPart getBakedModel() {
		if(bakedModel == null)
			bakedModel = createBodyLayer().bakeRoot();
		return bakedModel;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition = createHelmetLayer(partdefinition);
		partdefinition = createChestplateLayer(partdefinition);
		partdefinition = createLeggingsLayer(partdefinition);
		partdefinition = createBootsLayer(partdefinition);
		
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@SuppressWarnings("unused")
	private static PartDefinition createHelmetLayer(PartDefinition partdefinition) {
		boolean realSizeArmor = true;
		
		PartDefinition HelmetBand = partdefinition.addOrReplaceChild("HelmetBand", CubeListBuilder.create().texOffs(0, 0)
				.addBox(-4.25F, -7.0F, -4.24F, 8.5F, 1.0F, 0.7F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-4.25F, -7.0F, -3.99F, 0.7F, 1.0F, 8.5F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(3.55F, -7.0F, -3.99F, 0.7F, 1.0F, 8.5F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-4.25F, -7.0F, 3.86F, 8.5F, 1.0F, 0.7F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition Rotation = HelmetBand.addOrReplaceChild("Rotation", CubeListBuilder.create().texOffs(0, 0).addBox(4.05F, -7.02F, -0.5F, 0.39F, 1.02F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition Cube_r1 = Rotation.addOrReplaceChild("Cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(7.581F, 0.5F, 1.6457F, 0.4F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.94F, -7.5F, 0.09F, 0.0F, 0.3927F, 0.0F));

		PartDefinition Cube_r2 = Rotation.addOrReplaceChild("Cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(6.431F, 0.5F, -2.4057F, 0.36F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.75F, -7.5F, 0.14F, 0.0F, -0.3927F, 0.0F));

		PartDefinition HelmetGem = partdefinition.addOrReplaceChild("HelmetGem", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition Cube_r3 = HelmetGem.addOrReplaceChild("Cube_r3", CubeListBuilder.create().texOffs(40, 4).addBox(4.05F, -7.0F, -0.5F, 0.4F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition HelmetActive = partdefinition.addOrReplaceChild("HelmetActive", CubeListBuilder.create().texOffs(0, 0).addBox(-4F, -8F, -4F, 8.0F, 8.0F, 8.0F, new CubeDeformation(realSizeArmor ? 0.9f : 0.2f)), PartPose.offset(0.0F, 30.5F, 0.0F));
		
		PartDefinition HelmetActiveAirtight = partdefinition.addOrReplaceChild("HelmetActiveAirtight", CubeListBuilder.create().texOffs(0, 0).addBox(-4F, -8F, -4F, 8.0F, 8.0F, 8.0F, new CubeDeformation(realSizeArmor ? 0.7f : 0.2f)), PartPose.offset(0.0F, 30.5F, 0.0F));
		
		return partdefinition;
	}

	@SuppressWarnings("unused")
	private static PartDefinition createChestplateLayer(PartDefinition partdefinition) {
		PartDefinition ChestBase = partdefinition.addOrReplaceChild("ChestBase", CubeListBuilder.create(), PartPose.offset(0.0F, 22.525F, 0.225F));

		PartDefinition Fillin = ChestBase.addOrReplaceChild("Fillin", CubeListBuilder.create()
		.texOffs(0, 0).addBox(-0.75F, -0.025F, -2.75F, 1.0F, 5.05F, 0.575F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-0.75F, 0, 1.55F, 1.0F, 4.05F, 0.575F, new CubeDeformation(0.0F))
		.texOffs(40, 40).addBox(-1.5F, 0, -2.5F, 2.5F, 4.275F, 0.3F, new CubeDeformation(0.0F))
		.texOffs(40, 40).addBox(2.25F, .025f, 1.75F, 1.1F, 2.6F, 0.3F, new CubeDeformation(0.0F))
		.texOffs(40, 40).addBox(-3.85F, .025f, 1.75F, 1.1F, 2.475F, 0.3F, new CubeDeformation(0.0F))
		.texOffs(40, 40).addBox(1.0F, .025f, 1.75F, 1.25F, 3.0F, 0.3F, new CubeDeformation(0.0F))
		.texOffs(40, 40).addBox(-2.75F, .025f, 1.75F, 1.25F, 3.0F, 0.3F, new CubeDeformation(0.0F))
		.texOffs(40, 40).addBox(-1.5F, .025f, 1.75F, 2.5F, 3.275F, 0.3F, new CubeDeformation(0.0F))
		.texOffs(40, 40).addBox(-2.75F, 0, -2.5F, 1.25F, 4.0F, 0.3F, new CubeDeformation(0.0F))
		.texOffs(40, 40).addBox(1.0F, 0, -2.5F, 1.25F, 4.0F, 0.3F, new CubeDeformation(0.0F))
		.texOffs(40, 40).addBox(-3.85F, 0, -2.5F, 1.1F, 3.475F, 0.3F, new CubeDeformation(0.0F))
		.texOffs(40, 40).addBox(2.25F, 0, -2.5F, 1.1F, 3.6F, 0.3F, new CubeDeformation(0.0F)), PartPose.offset(0.25F, 0.0F, 0.0F));

		PartDefinition Outline = ChestBase.addOrReplaceChild("Outline", CubeListBuilder.create()
				.texOffs(0, 0).addBox(3.275F, 21.225F, -2.675F, 0.725F, 3.775F, 0.5F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.0F, 21.235f, -2.675F, 0.725F, 3.75F, 0.5F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -21.25F, 0.0F));

		PartDefinition cube_r4 = Outline.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(4.15F, -2.525F, -2.7F, 0.75F, 3.725F, 0.525F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.2F, -1.725F+22.525F, 0.0F, 0.0F, 0.0F, 1.9199F));

		PartDefinition cube_r5 = Outline.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 0).addBox(4.425F, -2.375F, -2.7F, 0.75F, 3.725F, 0.525F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.575F+22.525F, 0.0F, 0.0F, 0.0F, 1.2217F));

		PartDefinition Outline2 = Outline.addOrReplaceChild("Outline2", CubeListBuilder.create()
				.texOffs(0, 0).addBox(3.275F, -1.275F+22.525F, 1.6F, 0.725F, 2.775F, 0.5F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.0F, -1.265F+22.525F, 1.6F, 0.725F, 2.75F, 0.5F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r6 = Outline2.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 0).addBox(4.15F, -2.525F, -2.7F, 0.75F, 3.725F, 0.525F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.2F, -2.7F+22.525F, 4.275F, 0.0F, 0.0F, 1.9199F));

		PartDefinition cube_r7 = Outline2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 0).addBox(4.425F, -2.375F, -2.7F, 0.75F, 3.725F, 0.525F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.55F+22.525F, 4.275F, 0.0F, 0.0F, 1.2217F));

		PartDefinition Support = Outline.addOrReplaceChild("Support", CubeListBuilder.create()
				.texOffs(0, 0).addBox(3.275F, -1.775F+22.525F, -2.675F, 0.725F, 0.6F, 4.775F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.0F, -1.775F+22.525F, -2.675F, 0.725F, 0.6F, 4.775F, new CubeDeformation(0.0F))
		.texOffs(40, 40).addBox(-3.35F, -1.5F+22.525F, -2.5F, 0.85F, 0.225F, 4.55F, new CubeDeformation(0.0F))
		.texOffs(40, 40).addBox(2.4F, -1.5F+22.525F, -2.5F, 0.85F, 0.225F, 4.55F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		
		PartDefinition Details = ChestBase.addOrReplaceChild("Details", CubeListBuilder.create()
				.texOffs(0, 0).addBox(-0.75F, -20.975F+22.525F, -3.0F, 1.0F, 1.0F, 0.575F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-3.25F, -20.225F+22.525F, -2.675F, 1.0F, 0.25F, 0.25F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-3.25F, -21.725F+22.525F, -2.675F, 1.0F, 0.25F, 0.25F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-3.2F, -21.475F+22.525F, -2.625F, 0.125F, 1.25F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-2.45F, -21.475F+22.525F, -2.625F, 0.125F, 1.25F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(9, 53).addBox(-1.5F, -19.975F+22.525F, -2.625F, 0.75F, 0.25F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(9, 53).addBox(0.015F, -19.47F+22.525F, -2.625F, 0.75F, 0.25F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(9, 53).addBox(-1.325F, -21.3F+22.525F, -2.625F, 0.575F, 0.25F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(9, 53).addBox(0.175F, -21.3F+22.525F, -2.625F, 1.225F, 0.25F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(9, 53).addBox(1.675F, -20.3F+22.525F, -2.625F, 0.825F, 0.25F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(9, 53).addBox(-2.575F, -22.175F+22.525F, -2.625F, 1.5F, 0.25F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(9, 53).addBox(1.825F, -22.425F+22.525F, -2.625F, 1.175F, 0.25F, 0.2F, new CubeDeformation(0.0F))
		
		.texOffs(9, 53).addBox(-3.5F, -22.45F+22.525F, -2.625F, 0.75F, 0.25F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(9, 53).addBox(-1.875F, -20.725F+22.525F, -2.625F, 0.375F, 0.25F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(9, 53).addBox(-1.75F, -20.475F+22.525F, -2.625F, 0.25F, 0.75F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(9, 53).addBox(-2.775F, -19.975F+22.525F, -2.625F, 0.25F, 0.725F, 0.2F, new CubeDeformation(0.0F))
		
		.texOffs(9, 53).addBox(2.25F, -20.05F+22.525F, -2.625F, 0.25F, 1.1F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(9, 53).addBox(-1.325F, -21.925F+22.525F, -2.625F, 0.25F, 0.625F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(9, 53).addBox(1.15F, -21.05F+22.525F, -2.625F, 0.25F, 0.525F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(9, 53).addBox(1.575F, -22.425F+22.525F, -2.625F, 0.25F, 1.9F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(9, 53).addBox(-2.575F, -21.925F+22.525F, -2.625F, 0.25F, 0.25F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(9, 53).addBox(-3.0F, -22.2F+22.525F, -2.625F, 0.25F, 0.475F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(52, 49).addBox(-0.65F, -20.875F+22.525F, -3.1F, 0.8F, 0.8F, 0.575F, new CubeDeformation(0.0F))
		.texOffs(21, 25).addBox(1.1F, -20.525F+22.525F, -2.8F, 0.8F, 0.8F, 0.275F, new CubeDeformation(0.0F))
		.texOffs(52, 49).addBox(-3.15F, -21.5F+22.525F, -2.55F, 0.8F, 1.425F, 0.025F, new CubeDeformation(0.0F)), PartPose.offset(0.25F, 0.0F, 0.0F));

		PartDefinition cube_r8 = Details.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(9, 53).addBox(-2.025F, -21.625F, -2.6F, 0.775F, 0.25F, 0.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.9F+22.525F, -0.25F, 0.0F, 0.1309F, 0.0F));

		PartDefinition cube_r9 = Details.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(9, 53).addBox(-17.225F, -11.075F, -2.375F, 0.925F, 0.25F, 0.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.9F+22.525F, -0.25F, 0.0F, 0.0F, 0.8727F));

		PartDefinition cube_r10 = Details.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(9, 53).addBox(13.625F, -15.375F, -2.375F, 0.475F, 0.25F, 0.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.05F, 1.125F+22.525F, -0.25F, 0.0F, 0.0F, -0.6981F));

		PartDefinition cube_r11 = Details.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 0).addBox(-0.75F, -21.525F, 1.5F, 1.0F, 1.525F, 0.45F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.475F+22.525F, -0.725F, 0.1745F, 0.0F, 0.0F));

		PartDefinition cube_r12 = Details.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 0).addBox(-0.75F, -20.775F, -7.75F, 1.0F, 1.475F, 0.525F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.675F+22.525F, 1.3F, -0.1745F, 0.0F, 0.0F));

		PartDefinition ChestArmRight = partdefinition.addOrReplaceChild("ChestArmRight", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition Outline3 = ChestArmRight.addOrReplaceChild("Outline3", CubeListBuilder.create()
				.texOffs(0, 0).addBox(-0.65F+5F, 3.81F+22.24F, 0.875F, 3.9F, 0.4F, 0.5F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-1.215F+5F, 3.81F+22.24F, 2.25F, 0.54F, 0.4F, 1.8F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-0.165F+5F, 3.735F+22.24F, 2.7F, 3.415F, 0.475F, 0.8F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-0.65F+5F, 3.81F+22.24F, 4.925F, 3.9F, 0.4F, 0.5F, new CubeDeformation(0.0F)), PartPose.offset(-7.275F, -28.225F, -3.15F));

		PartDefinition cube_r13 = Outline3.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 0).addBox(-4.775F, 3.835F, -2.675F, 1.485F, 0.375F, 0.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5F, 22.24F, 0.0F, 0.0F, 1.9635F, 0.0F));

		PartDefinition cube_r14 = Outline3.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 0).addBox(-4.76F, 3.835F, -2.675F, 1.5F, 0.375F, 0.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.075F+5F, 22.24F, -1.125F, 0.0F, 1.1781F, 0.0F));

		PartDefinition FIll = ChestArmRight.addOrReplaceChild("Fill", CubeListBuilder.create().texOffs(40, 40).addBox(-8.0F+5F, -24.29F+22.24F, -1.775F, 3.975F, 0.3F, 3.55F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Detail = ChestArmRight.addOrReplaceChild("Detail", CubeListBuilder.create()
				.texOffs(9, 53).addBox(-5.075F+5F, -24.35F+22.24F, -1.4F, 1.05F, 0.15F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(9, 53).addBox(-6.275F+5F, -24.35F+22.24F, -1.4F, 0.2F, 0.15F, 0.975F, new CubeDeformation(0.0F))
		.texOffs(9, 53).addBox(-6.075F+5F, -24.35F+22.24F, -1.4F, 0.8F, 0.15F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(9, 53).addBox(-5.075F+5F, -24.35F+22.24F, 1.125F, 1.05F, 0.15F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(9, 53).addBox(-6.075F+5F, -24.35F+22.24F, 1.125F, 0.8F, 0.15F, 0.2F, new CubeDeformation(0.0F))
		.texOffs(9, 53).addBox(-5.275F+5F, -24.35F+22.24F, -1.4F, 0.2F, 0.15F, 0.975F, new CubeDeformation(0.0F))
		.texOffs(9, 53).addBox(-5.275F+5F, -24.35F+22.24F, 0.35F, 0.2F, 0.15F, 0.975F, new CubeDeformation(0.0F))
		.texOffs(9, 53).addBox(-6.275F+5F, -24.35F+22.24F, 0.35F, 0.2F, 0.15F, 0.975F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition ChestArmLeft = partdefinition.addOrReplaceChild("ChestArmLeft", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition Outline5 = ChestArmLeft.addOrReplaceChild("Outline5", CubeListBuilder.create()
				.texOffs(0, 0).addBox(3.28F-5F, 20.31F+22.24F, 13.175F, 3.9F, 0.4F, 0.5F, new CubeDeformation(0.0F))
			.texOffs(0, 0).addBox(7.205F-5F, 20.31F+22.24F, 10.5F, 0.54F, 0.4F, 1.8F, new CubeDeformation(0.0F))
			.texOffs(0, 0).addBox(3.28F-5F, 20.235F+22.24F, 11.05F, 3.415F, 0.475F, 0.8F, new CubeDeformation(0.0F))
			.texOffs(0, 0).addBox(3.28F-5F, 20.31F+22.24F, 9.125F, 3.9F, 0.4F, 0.5F, new CubeDeformation(0.0F)), PartPose.offset(0.75F, -44.725F, -11.4F));

		PartDefinition cube_r15 = Outline5.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(0, 0).addBox(-3.906F, 16.3175F, 5.0454F, 0.5F, 0.375F, 1.485F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.39F-5F, 4.0175F+22.24F, 3.15F, 0.0F, 0.3927F, 0.0F));

		PartDefinition cube_r16 = Outline5.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(0, 0).addBox(2.4088F, 16.3175F, 8.7001F, 0.5F, 0.375F, 1.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.39F-5F, 4.0175F+22.24F, 3.15F, 0.0F, -0.3927F, 0.0F));

		PartDefinition FIll3 = ChestArmLeft.addOrReplaceChild("FIll3", CubeListBuilder.create().texOffs(40, 40).addBox(-3.995F-5F, -7.79F+22.24F, 6.475F, 3.975F, 0.3F, 3.55F, new CubeDeformation(0.0F)), PartPose.offset(8.025F, -16.5F, -8.25F));

		PartDefinition Detail3 = ChestArmLeft.addOrReplaceChild("Detail3", CubeListBuilder.create()
				.texOffs(9, 53).addBox(-3.995F-5F, -7.85F+22.24F, 9.45F, 1.05F, 0.15F, 0.2F, new CubeDeformation(0.0F))
			.texOffs(9, 53).addBox(-1.945F-5F, -7.85F+22.24F, 8.675F, 0.2F, 0.15F, 0.975F, new CubeDeformation(0.0F))
			.texOffs(9, 53).addBox(-2.745F-5F, -7.85F+22.24F, 9.45F, 0.8F, 0.15F, 0.2F, new CubeDeformation(0.0F))
			.texOffs(9, 53).addBox(-3.995F-5F, -7.85F+22.24F, 6.925F, 1.05F, 0.15F, 0.2F, new CubeDeformation(0.0F))
			.texOffs(9, 53).addBox(-2.745F-5F, -7.85F+22.24F, 6.925F, 0.8F, 0.15F, 0.2F, new CubeDeformation(0.0F))
			.texOffs(9, 53).addBox(-2.945F-5F, -7.85F+22.24F, 8.675F, 0.2F, 0.15F, 0.975F, new CubeDeformation(0.0F))
			.texOffs(9, 53).addBox(-2.945F-5F, -7.85F+22.24F, 6.925F, 0.2F, 0.15F, 0.975F, new CubeDeformation(0.0F))
			.texOffs(9, 53).addBox(-1.945F-5F, -7.85F+22.24F, 6.925F, 0.2F, 0.15F, 0.975F, new CubeDeformation(0.0F)), PartPose.offset(8.025F, -16.5F, -8.25F));

		PartDefinition ChestGem = partdefinition.addOrReplaceChild("ChestGem", CubeListBuilder.create()
				.texOffs(48, 19).addBox(-0.325F, -21.55F+ 23.275F, -3.15F, 0.65F, 0.65F, 0.525F, new CubeDeformation(0.0F))
		.texOffs(48, 19).addBox(-2.82F, -22.25F+ 23.275F, -2.6f, 0.7F, 1.35F, 0.025F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 23.275F, 0.0F));

		PartDefinition ChestplateActive = partdefinition.addOrReplaceChild("ChestplateActive", CubeListBuilder.create()
				.texOffs(16, 16).addBox(-4, 0, -2, 8, 12, 4, new CubeDeformation(0.9f)), PartPose.offset(0.0F, 0.0F, 0.0F));
		
		PartDefinition ChestplateActiveRightArm = partdefinition.addOrReplaceChild("ChestplateActiveRightArm", CubeListBuilder.create()
				.texOffs(40, 16).addBox(4-7F, -2, -2, 4, 12, 4, new CubeDeformation(0.9f)), PartPose.offset(0.0F, 0.0F, 0.0F));
		
		PartDefinition ChestplateActiveLeftArm = partdefinition.addOrReplaceChild("ChestplateActiveLeftArm", CubeListBuilder.create()
				.texOffs(40, 16).addBox(4-5F, -2, -2, 4, 12, 4, new CubeDeformation(0.9f)), PartPose.offset(0.0F, 0.0F, 0.0F));
		
		return partdefinition;
	}

	@SuppressWarnings("unused")
	private static PartDefinition createLeggingsLayer(PartDefinition partdefinition) {
		PartDefinition LeggingsBase = partdefinition.addOrReplaceChild("LeggingsBase", CubeListBuilder.create()
				.texOffs(0, 0).addBox(-4.25F, -12.475F+24.0F, -2.25F, 8.5F, 0.5F, 4.5F, new CubeDeformation(0.0F))
		.texOffs(49, 50).addBox(-0.4F, -12.525F+24.0F, -2.4F, 0.8F, 0.6F, 0.45F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition RightLeg = LeggingsBase.addOrReplaceChild("RightLeg", CubeListBuilder.create()
				.texOffs(49, 53).addBox(-4.275F, -12.275F+24.0F, -0.25F, 0.275F, 0.55F, 0.25F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.325F, -11.725F+24.0F, -0.75F, 0.325F, 1.75F, 1.25F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition RL_Details = RightLeg.addOrReplaceChild("RL_Details", CubeListBuilder.create()
				.texOffs(44, 44).addBox(-4.375F, -11.425F+24.0F, -0.55F, 0.325F, 0.85F, 0.85F, new CubeDeformation(0.0F))
		.texOffs(8, 44).addBox(-4.375F, -10.325F+24.0F, -0.6F, 0.325F, 0.15F, 0.15F, new CubeDeformation(0.0F))
		.texOffs(8, 44).addBox(-4.375F, -10.325F+24.0F, -0.35F, 0.325F, 0.15F, 0.15F, new CubeDeformation(0.0F))
		.texOffs(8, 44).addBox(-4.375F, -10.325F+24.0F, -0.1F, 0.325F, 0.15F, 0.15F, new CubeDeformation(0.0F))
		.texOffs(8, 44).addBox(-4.375F, -10.325F+24.0F, 0.15F, 0.325F, 0.15F, 0.15F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition LeftLeg = LeggingsBase.addOrReplaceChild("LeftLeg", CubeListBuilder.create()
				.texOffs(0, 0).addBox(-4.325F, -11.725F+24.0F, -0.75F, 0.325F, 1.75F, 1.25F, new CubeDeformation(0.0F))
		.texOffs(49, 53).addBox(-4.275F, -12.275F+24.0F, -0.25F, 0.275F, 0.55F, 0.25F, new CubeDeformation(0.0F)), PartPose.offset(8.3F, 0.0F, 0.0F));

		PartDefinition LL_Details = LeftLeg.addOrReplaceChild("LL_Details", CubeListBuilder.create()
				.texOffs(44, 44).addBox(-4.375F, -11.425F+24.0F, -0.55F, 0.325F, 0.85F, 0.85F, new CubeDeformation(0.0F))
		.texOffs(8, 44).addBox(-4.375F, -10.325F+24.0F, -0.6F, 0.325F, 0.15F, 0.15F, new CubeDeformation(0.0F))
		.texOffs(8, 44).addBox(-4.375F, -10.325F+24.0F, -0.35F, 0.325F, 0.15F, 0.15F, new CubeDeformation(0.0F))
		.texOffs(8, 44).addBox(-4.375F, -10.325F+24.0F, -0.1F, 0.325F, 0.15F, 0.15F, new CubeDeformation(0.0F))
		.texOffs(8, 44).addBox(-4.375F, -10.325F+24.0F, 0.15F, 0.325F, 0.15F, 0.15F, new CubeDeformation(0.0F)), PartPose.offset(0.125F, 0.0F, 0.0F));

		PartDefinition LeggingsGem = partdefinition.addOrReplaceChild("LeggingsGem", CubeListBuilder.create()
				.texOffs(48, 19).addBox(4.075F, -11.35F+24.0F, -0.475F, 0.35F, 0.7F, 0.7F, new CubeDeformation(0.0F))
		.texOffs(48, 19).addBox(-4.4F, -11.35F+24.0F, -0.475F, 0.35F, 0.7F, 0.7F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
		
		PartDefinition LeggingsActiveRight = partdefinition.addOrReplaceChild("LeggingsActiveRight", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.7F))
				, PartPose.offset(-1.9F, -12.0F, 0.0F));
		
		PartDefinition LeggingsActiveLeft = partdefinition.addOrReplaceChild("LeggingsActiveLeft", CubeListBuilder.create()
				.texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.7F)), PartPose.offset(1.9F, -12.0F, 0.0F));
		
		PartDefinition LeggingsActiveTop = partdefinition.addOrReplaceChild("LeggingsActiveTop", CubeListBuilder.create()
				.texOffs(16, 16).addBox(-4, 0, -2, 8, 12, 4, new CubeDeformation(0.7f)), PartPose.offset(0.0F, 0.0F, 0.0F));
		
		return partdefinition;
	}
	

	@SuppressWarnings("unused")
	private static PartDefinition createBootsLayer(PartDefinition partdefinition) {
		float yOffset = -11.8f;
		
		PartDefinition GemLeftBoot = partdefinition.addOrReplaceChild("GemLeftBoot", CubeListBuilder.create()
				.texOffs(43, 20).addBox(-2.7F + 4.1f, 0.2f + yOffset, 1.15F, 4.975F, 0.775F, 0.875F, new CubeDeformation(0.0F)), PartPose.offset(-0.25F, 24.0F, 0.0F));

		PartDefinition GemRightBoot = partdefinition.addOrReplaceChild("GemRightBoot", CubeListBuilder.create()
				.texOffs(43, 20).addBox(-2.7F - 3.7f, 0.2f + yOffset, 1.15F, 4.975F, 0.775F, 0.875F, new CubeDeformation(0.0F)), PartPose.offset(-3.75F, 24.0F, 0.0F));

		float xOffset = 1.8f;
		
		PartDefinition LeftBoot = partdefinition.addOrReplaceChild("LeftBoot", CubeListBuilder.create()
		.texOffs(6, 52).addBox(-0.125F + xOffset, -0.25f + yOffset, 0.25F, 4.325F, 2.25F, 2.25F, new CubeDeformation(0.0F))
		.texOffs(6, 52).addBox(-0.125F + xOffset, 2F + yOffset, 1.75F, 4.325F, 0.5F, 0.75F, new CubeDeformation(0.0F))
		.texOffs(0, 10).addBox(-0.15F + xOffset, -0.325F + yOffset, 0.5F, 4.45F, 1.825F, 2.075F, new CubeDeformation(0.0F))
		.texOffs(0, 10).addBox(-0.15F + xOffset, 1.5F + yOffset, 2.0F, 4.45F, 0.75F, 0.575F, new CubeDeformation(0.0F))
		.texOffs(41, 53).addBox(-0.25F + xOffset, -0.025F + yOffset, 0.9F, 4.675F, 1.225F, 1.3F, new CubeDeformation(0.0F))
		.texOffs(9, 55).addBox(-0.35F + xOffset, 0.125F + yOffset, 1.025F, 4.875F, 0.925F, 1.025F, new CubeDeformation(0.0F))
		.texOffs(6, 52).addBox(-0.125F + xOffset, -0.25F + yOffset, -2.5F, 4.325F, 1.75F, 2.75F, new CubeDeformation(0.0F))
		.texOffs(0, 1).addBox(-0.15F + xOffset, -0.325F + yOffset, -2.575F, 4.45F, 1.325F, 3.075F, new CubeDeformation(0.0F)), PartPose.offset(0, 24.0F, 0.0F));
		
		PartDefinition BootsActiveLeft = partdefinition.addOrReplaceChild("BootsActiveLeft", CubeListBuilder.create()
				.texOffs(24, 23).addBox(-0.1F + xOffset, -0.0F + yOffset, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.9F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		xOffset = -2f;
		
		PartDefinition RightBoot = partdefinition.addOrReplaceChild("RightBoot", CubeListBuilder.create()
		.texOffs(6, 52).addBox(-4.125F + xOffset, -0.25F + yOffset, 0.25F, 4.325F, 2.25F, 2.25F, new CubeDeformation(0.0F))
		.texOffs(6, 52).addBox(-4.125F + xOffset, 2F + yOffset, 1.75F, 4.325F, 0.5F, 0.75F, new CubeDeformation(0.0F))
		.texOffs(0, 10).addBox(-4.15F + xOffset, -0.325F + yOffset, 0.5F, 4.45F, 1.825F, 2.075F, new CubeDeformation(0.0F))
		.texOffs(0, 10).addBox(-4.15F + xOffset, 1.5F + yOffset, 2.0F, 4.45F, 0.75F, 0.575F, new CubeDeformation(0.0F))
		.texOffs(41, 53).addBox(-4.25F + xOffset, -0.025F + yOffset, 0.9F, 4.675F, 1.225F, 1.3F, new CubeDeformation(0.0F))
		.texOffs(9, 55).addBox(-4.35F + xOffset, 0.125F + yOffset, 1.025F, 4.875F, 0.925F, 1.025F, new CubeDeformation(0.0F))
		.texOffs(6, 52).addBox(-4.125f + xOffset, -0.25F + yOffset, -2.5F, 4.325F, 1.75F, 2.75F, new CubeDeformation(0.0F))
		.texOffs(0, 1).addBox(-4.15F + xOffset, -0.325F + yOffset, -2.575F, 4.45F, 1.325F, 3.075F, new CubeDeformation(0.0F)), PartPose.offset(0.25F, 24.0F, 0.0F));
		
		PartDefinition BootsActiveRight = partdefinition.addOrReplaceChild("BootsActiveRight", CubeListBuilder.create()
				.texOffs(24, 23).addBox(-3.9F + xOffset, -0.0F + yOffset, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.9F)), PartPose.offset(0.0F, 24.0F, 0.0F));
		return partdefinition;
	}
	
	@Override
	public void renderToBuffer(PoseStack matrix, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

		float[] color = LaserItemUtils.getColor(stack);
		float cred = color.length > 0 ? color[0] : 1.0f;
		float cgreen = color.length > 1 ? color[1] : 1.0f;
		float cblue = color.length > 2 ? color[2] : 1.0f;
		float calpha = color.length > 3 ? color[3] : 1.0f;
		
		if(HelmetVisible)
			renderHelmet(matrix, packedLight, packedOverlay, red, green, blue, alpha, cred, cgreen, cblue, calpha);
		if(ChestVisible)
			renderChestplate(matrix, packedLight, packedOverlay, red, green, blue, alpha, cred, cgreen, cblue, calpha);
		if(LeggingsVisible)
			renderLeggings(matrix, packedLight, packedOverlay, red, green, blue, alpha, cred, cgreen, cblue, calpha);
		if(BootsVisible)
			renderBoots(matrix, packedLight, packedOverlay, red, green, blue, alpha, cred, cgreen, cblue, calpha);
	}
	
	private void renderHelmet(PoseStack matrix, int packedLight, int packedOverlay, float red, float green, float blue, float alpha, float cred, float cgreen, float cblue, float calpha) {
		var texture = new ResourceLocation(Reference.MODID, "textures/models/armor/laser_armor.png");
		RenderType renderType = RenderType.entityTranslucent(texture);
		VertexConsumer buffer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(renderType);
		HelmetBand.loadPose(head.storePose());
		HelmetGem.loadPose(head.storePose());
		HelmetActive.loadPose(head.storePose());
		
		HelmetBand.render(matrix, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		HelmetGem.render(matrix, buffer, packedLight, packedOverlay, cred, cgreen, cblue, calpha);
		
		texture = new ResourceLocation(Reference.MODID, "textures/models/armor/laser_armor_overlay.png");
		renderType = RenderType.entityTranslucent(texture);
		buffer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(renderType);
		
		HelmetActive.render(matrix, buffer, packedLight, packedOverlay, cred, cgreen, cblue, .6f);

		HelmetActiveAirtight.visible = HelmetActive.visible;
		
		if(hasAirtightSeal()) {
			HelmetActiveAirtight.loadPose(head.storePose());
			texture = new ResourceLocation(Reference.MODID, "textures/models/armor/laser_armor_overlay_airtight.png");
			renderType = RenderType.entityTranslucent(texture);
			buffer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(renderType);
			HelmetActiveAirtight.render(matrix, buffer, packedLight, packedOverlay, cred, cgreen, cblue, .4f);
		}
	}
	
	private void renderChestplate(PoseStack matrix, int packedLight, int packedOverlay, float red, float green, float blue, float alpha, float cred, float cgreen, float cblue, float calpha) {
		var texture = new ResourceLocation(Reference.MODID, "textures/models/armor/laser_armor.png");
		RenderType renderType = RenderType.entityTranslucent(texture);
		VertexConsumer buffer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(renderType);
		ChestBase.loadPose(body.storePose());
		ChestGem.loadPose(body.storePose());
		ChestRightArm.loadPose(rightArm.storePose());
		ChestLeftArm.loadPose(leftArm.storePose());
		
		ChestBase.render(matrix, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		ChestRightArm.render(matrix, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		ChestLeftArm.render(matrix, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		ChestGem.render(matrix, buffer, packedLight, packedOverlay, cred, cgreen, cblue, calpha);
		
		texture = new ResourceLocation(Reference.MODID, "textures/models/armor/laser_armor_overlay.png");
		renderType = RenderType.entityTranslucent(texture);
		buffer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(renderType);
		
		ChestActive.loadPose(body.storePose());
		ChestActive.render(matrix, buffer, packedLight, packedOverlay, cred, cgreen, cblue, .6f);
		if(ChestActive.visible) {
			ChestActiveLeftArm.loadPose(leftArm.storePose());
			ChestActiveRightArm.loadPose(rightArm.storePose());
			ChestActiveLeftArm.render(matrix, buffer, packedLight, packedOverlay, cred, cgreen, cblue, .6f);
			ChestActiveRightArm.render(matrix, buffer, packedLight, packedOverlay, cred, cgreen, cblue, .6f);
		}

	}
	
	private void renderLeggings(PoseStack matrix, int packedLight, int packedOverlay, float red, float green, float blue, float alpha, float cred, float cgreen, float cblue, float calpha)
	{
		var texture = new ResourceLocation(Reference.MODID, "textures/models/armor/laser_armor.png");
		RenderType renderType = RenderType.entityTranslucent(texture);
		VertexConsumer buffer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(renderType);
		
		LeggingsBase.loadPose(body.storePose());
		LeggingsGem.loadPose(body.storePose());
		
		LeggingsBase.render(matrix, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		LeggingsGem.render(matrix, buffer, packedLight, packedOverlay, cred, cgreen, cblue, calpha);
		
		texture = new ResourceLocation(Reference.MODID, "textures/models/armor/laser_armor_overlay_2.png");
		renderType = RenderType.entityTranslucent(texture);
		buffer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(renderType);
		
		if(LeggingsActive) {
			LeggingsActiveLeft.loadPose(leftLeg.storePose());
			LeggingsActiveRight.loadPose(rightLeg.storePose());
			LeggingsActiveTop.loadPose(body.storePose());
			LeggingsActiveLeft.render(matrix, buffer, packedLight, packedOverlay, cred, cgreen, cblue, .6f);
			LeggingsActiveRight.render(matrix, buffer, packedLight, packedOverlay, cred, cgreen, cblue, .6f);
			LeggingsActiveTop.render(matrix, buffer, packedLight, packedOverlay, cred, cgreen, cblue, .6f);
		}
	}
	
	private void renderBoots(PoseStack matrix, int packedLight, int packedOverlay, float red, float green, float blue, float alpha, float cred, float cgreen, float cblue, float calpha)
	{
		var texture = new ResourceLocation(Reference.MODID, "textures/models/armor/laser_armor.png");
		RenderType renderType = RenderType.entityTranslucent(texture);
		VertexConsumer buffer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(renderType);
		
		LeftBoot.loadPose(leftLeg.storePose());
		GemLeftBoot.loadPose(leftLeg.storePose());
		RightBoot.loadPose(rightLeg.storePose());
		GemRightBoot.loadPose(rightLeg.storePose());

		LeftBoot.zRot = 3.14f;
		GemLeftBoot.zRot = 3.14f;
		RightBoot.zRot = 3.14f;
		GemRightBoot.zRot = 3.14f;
		
		LeftBoot.render(matrix, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		GemLeftBoot.render(matrix, buffer, packedLight, packedOverlay, cred, cgreen, cblue, calpha);
		RightBoot.render(matrix, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		GemRightBoot.render(matrix, buffer, packedLight, packedOverlay, cred, cgreen, cblue, calpha);
		
		texture = new ResourceLocation(Reference.MODID, "textures/models/armor/laser_armor_overlay_2.png");
		renderType = RenderType.entityTranslucent(texture);
		buffer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(renderType);
		
		if(BootsActive) {
			BootsActiveLeft.loadPose(leftLeg.storePose());
			BootsActiveRight.loadPose(rightLeg.storePose());
			BootsActiveLeft.zRot = 3.14f;
			BootsActiveRight.zRot = 3.14f;
			BootsActiveLeft.render(matrix, buffer, packedLight, packedOverlay, cred, cgreen, cblue, .6f);
			BootsActiveRight.render(matrix, buffer, packedLight, packedOverlay, cred, cgreen, cblue, .6f);
		}
	}
	
	boolean hasAirtightSeal() {
		if(stack.getEquipmentSlot() != EquipmentSlot.HEAD)
			return false;
		LaserProperties properties = LaserItemUtils.getProperties(stack);
		return properties.hasUpgarde("airtight_seal");
	}
}