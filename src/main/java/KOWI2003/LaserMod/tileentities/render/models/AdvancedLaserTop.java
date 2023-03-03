package KOWI2003.LaserMod.tileentities.render.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.utils.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec2;

public class AdvancedLaserTop<T extends Entity> extends EntityModel<T> {

	public static ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "lasercontrollerlevers"), "main");
    //fields
    public ModelPart Rotatingbase;
    public ModelPart Piston1base;
    public ModelPart Piston1baseattach;
    public ModelPart Piston1;
    public ModelPart Piston1attach;
    public ModelPart Piston2attach;
    public ModelPart Piston2baseattach;
    public ModelPart Piston2base;
    public ModelPart Piston2;
    public ModelPart Piston3attach;
    public ModelPart Piston3;
    public ModelPart Piston3base;
    public ModelPart Piston3baseattach;
    public ModelPart Piston4baseattach;
    public ModelPart Piston4attach;
    public ModelPart Piston4base;
    public ModelPart Piston4;
    public ModelPart Rotatingbase2;
    public ModelPart Laserhead;

    public AdvancedLaserTop() {
    	createBodyLayer();
	}
    
    public void init(ModelPart root) {
		this.Rotatingbase = root.getChild("Rotatingbase");
		this.Piston1base = root.getChild("Piston1base");
		this.Piston1baseattach = root.getChild("Piston1baseattach");
		this.Piston1 = root.getChild("Piston1");
		this.Piston1attach = root.getChild("Piston1attach");
		this.Piston2attach = root.getChild("Piston2attach");
		this.Piston2baseattach = root.getChild("Piston2baseattach");
		this.Piston2base = root.getChild("Piston2base");
		this.Piston2 = root.getChild("Piston2");
		this.Piston3attach = root.getChild("Piston3attach");
		this.Piston3 = root.getChild("Piston3");
		this.Piston3base = root.getChild("Piston3base");
		this.Piston3baseattach = root.getChild("Piston3baseattach");
		this.Piston4baseattach = root.getChild("Piston4baseattach");
		this.Piston4attach = root.getChild("Piston4attach");
		this.Piston4base = root.getChild("Piston4base");
		this.Piston4 = root.getChild("Piston4");
		this.Rotatingbase2 = root.getChild("Rotatingbase2");
		this.Laserhead = root.getChild("Laserhead");
	}
	
	public AdvancedLaserTop(ModelPart root) {
		init(root);
	}
    
	public void createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		
		PartDefinition Rotatingbase = partdefinition.addOrReplaceChild("Rotatingbase", 
				CubeListBuilder.create()
				.texOffs(64, 64).addBox(-2F, 0F, -2F, 4, 0.5f, 4),
				PartPose.offset(-8F, 23.35F, -8F));
		this.Rotatingbase = Rotatingbase.bake(0, 11);
        setRotation(this.Rotatingbase, 0F, 0F, 0F);
        
        PartDefinition Piston1base = partdefinition.addOrReplaceChild("Piston1base", 
				CubeListBuilder.create()
				.texOffs(64, 64).addBox(2F, -0.2499998F, -1.8F, 0.5f, 0.5f, 1.65f),
				PartPose.offset(-8F, 22.15F, -6.3F));
		this.Piston1base = Piston1base.bake(24, 13);
        setRotation(this.Piston1base, -5.890486F, 0F, 0F);
		
        PartDefinition Piston1baseattach = partdefinition.addOrReplaceChild("Piston1baseattach", 
				CubeListBuilder.create()
				.texOffs(64, 64).addBox(0F, -0.5000001F, 0F, 0.6f, 0.6f, 0.6f),
				PartPose.offset(-6F, 22.35F, -6.6F));
		this.Piston1baseattach = Piston1baseattach.bake(8, 0);
        setRotation(this.Piston1baseattach, 0F, 0F, 0F);
		
        PartDefinition Piston1 = partdefinition.addOrReplaceChild("Piston1", 
				CubeListBuilder.create()
				.texOffs(64, 64)
				.addBox(2.05F, -0.2F, -3.61F, 0.4f, 0.4f, 2.3f)//
				,PartPose.offset(-8F, 22.15F, -6.3F));
		this.Piston1 = Piston1.bake(16, 12);
        setRotation(this.Piston1, -5.890486F, 0F, 0F);
		
        PartDefinition Piston1attach = partdefinition.addOrReplaceChild("Piston1attach", 
				CubeListBuilder.create()
				.texOffs(64, 64).addBox(2F, 0F, -2F, 0.5f, 0.5f, 0.5f),
				PartPose.offset(-8F, 23.35F, -8F));
		this.Piston1attach = Piston1attach.bake(12, 4);
        setRotation(this.Piston1attach, 0F, 0F, 0F);
        
        PartDefinition Piston2attach = partdefinition.addOrReplaceChild("Piston2attach", 
				CubeListBuilder.create()
				.texOffs(64, 64).addBox(-2F, 0F, -2.5F, 0.5f, 0.5f, 0.5f),
				PartPose.offset(-8F, 23.35F, -8F));
		this.Piston2attach = Piston2attach.bake(12, 4);
        setRotation(this.Piston2attach, 0F, 0F, 0F);
        
        PartDefinition Piston2baseattach = partdefinition.addOrReplaceChild("Piston2baseattach", 
				CubeListBuilder.create()
				.texOffs(64, 64).addBox(0F, -0.5000001F, 0F, 0.6f, 0.6f, 0.6f),
				PartPose.offset(-6.6F, 22.35F, -10.6F));
		this.Piston2baseattach = Piston2baseattach.bake(8, 0);
        setRotation(this.Piston2baseattach, 0F, 0F, 0F);
        
        PartDefinition Piston2base = partdefinition.addOrReplaceChild("Piston2base", 
				CubeListBuilder.create()
				.texOffs(64, 64).addBox(-1.8F, -0.2499998F, -0.1999998F, 1.65f, 0.5f, 0.5f),
				PartPose.offset(-6.3F, 22.15F, -10.3F));
		this.Piston2base = Piston2base.bake(16, 10);
        setRotation(this.Piston2base, 0F, 0F, 5.890486F);
        
        PartDefinition Piston2 = partdefinition.addOrReplaceChild("Piston2", 
				CubeListBuilder.create()
				.texOffs(64, 64)
				.addBox(-3.65F, -0.2F, -0.1500001F, 2.3f, 0.4f, 0.4f)//
				,PartPose.offset(-6.3F, 22.15F, -10.3F));
		this.Piston2 = Piston2.bake(0, 0);
        setRotation(this.Piston2, 0F, 0F, 5.890486F);
        
        PartDefinition Piston3attach = partdefinition.addOrReplaceChild("Piston3attach", 
				CubeListBuilder.create()
				.texOffs(64, 64).addBox(1.5F, 0F, 2F, 0.5f, 0.5f, 0.5f),
				PartPose.offset(-8F, 23.35F, -8F));
		this.Piston3attach = Piston3attach.bake(12, 4);
        setRotation(this.Piston3attach, 0F, 0F, 0F);
        
        PartDefinition Piston3 = partdefinition.addOrReplaceChild("Piston3", 
				CubeListBuilder.create()
				.texOffs(64, 64)
				.addBox(1.45F, -0.2F, -0.25F, 2.3f, 0.4f, 0.4f)//
				,PartPose.offset(-9.7F, 22.15F, -5.7F));
		this.Piston3 = Piston3.bake(0, 0);
        setRotation(this.Piston3, 0F, 0F, 0.3926991F);
        
        PartDefinition Piston3base = partdefinition.addOrReplaceChild("Piston3base", 
				CubeListBuilder.create()
				.texOffs(64, 64).addBox(0.1999998F, -0.2499998F, -0.3000002F, 1.65f, 0.5f, 0.5f),
				PartPose.offset(-9.7F, 22.15F, -5.7F));
		this.Piston3base = Piston3base.bake(16, 10);
        setRotation(this.Piston3base, 0F, 0F, 0.3926991F);
        
        PartDefinition Piston3baseattach = partdefinition.addOrReplaceChild("Piston3baseattach", 
				CubeListBuilder.create()
				.texOffs(64, 64).addBox(0F, -0.5000001F, 0F, 0.6f, 0.6f, 0.6f),
				PartPose.offset(-10F, 22.35F, -6F));
		this.Piston3baseattach = Piston3baseattach.bake(8, 0);
        setRotation(this.Piston3baseattach, 0F, 0F, 0F);
        
        PartDefinition Piston4baseattach = partdefinition.addOrReplaceChild("Piston4baseattach", 
				CubeListBuilder.create()
				.texOffs(64, 64).addBox(0F, -0.5000001F, 0F, 0.6f, 0.6f, 0.6f),
				PartPose.offset(-10.6F, 22.35F, -10F));
		this.Piston4baseattach = Piston4baseattach.bake(8, 0);
        setRotation(this.Piston4baseattach, 0F, 0F, 0F);
        
        PartDefinition Piston4attach = partdefinition.addOrReplaceChild("Piston4attach", 
				CubeListBuilder.create()
				.texOffs(64, 64).addBox(-2.5F, 0F, 1.5F, 0.5f, 0.5f, 0.5f),
				PartPose.offset(-8F, 23.35F, -8F));
		this.Piston4attach = Piston4attach.bake(12, 4);
        setRotation(this.Piston4attach, 0F, 0F, 0F);
        
        PartDefinition Piston4base = partdefinition.addOrReplaceChild("Piston4base", 
				CubeListBuilder.create()
				.texOffs(64, 64).addBox(-0.1999998F, -0.2499998F, 0.09999943F, 0.5f, 0.5f, 1.65f),
				PartPose.offset(-10.3F, 22.15F, -9.7F));
		this.Piston4base = Piston4base.bake(24, 13);
        setRotation(this.Piston4base, -0.3926991F, 0F, 0F);
        
        PartDefinition Piston4 = partdefinition.addOrReplaceChild("Piston4", 
				CubeListBuilder.create()
				.texOffs(64, 64)
				.addBox(-0.1500003F, -0.2F, 1.49F, 0.4f, 0.4f, 2.3f)//
				,PartPose.offset(-10.3F, 22.15F, -9.7F));
		this.Piston4 = Piston4.bake(16, 12);
        setRotation(this.Piston4, -0.3926991F, 0F, 0F);
        
        PartDefinition Rotatingbase2 = partdefinition.addOrReplaceChild("Rotatingbase2", 
				CubeListBuilder.create()
				.texOffs(64, 64).addBox(-1.75F, 0.5F, -1.75F, 3.5f, 0.3f, 3.5f),
				PartPose.offset(-8F, 23.35F, -8F));
		this.Rotatingbase2 = Rotatingbase2.bake(0, 6);
        setRotation(this.Rotatingbase2, 0F, 0F, 0F);
        
        PartDefinition Laserhead = partdefinition.addOrReplaceChild("Laserhead", 
				CubeListBuilder.create()
				.texOffs(16, 16).addBox(-1.5F, 0.7F, -1.5F, 3, 0.25f, 3),
				PartPose.offset(-8F, 23.35F, -8F));
		this.Laserhead = Laserhead.bake(0, 2);
        setRotation(this.Laserhead, 0F, 0F, 0F);
        
//        return LayerDefinition.create(meshdefinition, 16, 16);
	}

    public void setRotation(ModelPart model, float x, float y, float z)
    {
        model.xRot = x;
        model.yRot = y;
        model.zRot = z;
    }

	public void setTranslationForPlatform(float x, float y, float z) {
        Rotatingbase.setPos(-8F + x, 23.35F + y, -8F + z);
        Rotatingbase2.setPos(-8F + x, 23.35F + y, -8F + z);
        Laserhead.setPos(-8F + x, 23.35F + y, -8F + z);
        Piston1attach.setPos(-8F + x, 23.35F + y, -8F + z);
        Piston2attach.setPos(-8F + x, 23.35F + y, -8F + z);
        Piston3attach.setPos(-8F + x, 23.35F + y, -8F + z);
        Piston4attach.setPos(-8F + x, 23.35F + y, -8F + z);
	}
	
	public void setRotationPlatform(float x, float y) {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		
		setRotation(Rotatingbase, x, 0, y);
		setRotation(Rotatingbase2, x, 0, y);
		setRotation(Laserhead, x, 0, y);
		setRotation(Piston1attach, x, 0, y);
		
		setRotation(Piston2attach, x, 0, y);
		setRotation(Piston3attach, x, 0, y);
		setRotation(Piston4attach, x, 0, y);
		
		//PISTON 1
		
		float angle = getPistonAngle(Piston1attach, Piston1baseattach, new Vector3f(0, 0, -1), "ZY");
		double lenght = getPistonLenght(Piston1attach, Piston1baseattach, Piston1, "ZY");

		PartDefinition Piston1 = partdefinition.addOrReplaceChild("Piston1", 
				CubeListBuilder.create()
				.texOffs(64, 64)
				.addBox(2.05F, -0.2F, -4.61F + 2.3f*2f - (float)lenght, 0.4f, 0.4f, (float)lenght /*2.3f*/)//
				,PartPose.offset(-8F, 22.15F, -6.3F));
		this.Piston1 = Piston1.bake(16, 12);
		setRotation(this.Piston1, angle, 0, 0);
		setRotation(Piston1base, angle, 0, 0);
		
		//PISTON 2
		angle = getPistonAngle(Piston2attach, Piston2baseattach, new Vector3f(-1, 0, 0), "XY");
		lenght = getPistonLenght(Piston2attach, Piston2baseattach, Piston2, "XY");
		
		PartDefinition Piston2 = partdefinition.addOrReplaceChild("Piston2", 
				CubeListBuilder.create()
				.texOffs(64, 64)
				.addBox(-4.65F + 2.3f*2f - (float)lenght, -0.2F, -0.1500001F, (float)lenght, 0.4f, 0.4f)//
				,PartPose.offset(-6.3F, 22.15F, -10.3F));
		this.Piston2 = Piston2.bake(16, 10);
		setRotation(this.Piston2, 0, 0, -angle);
		setRotation(Piston2base, 0, 0, -angle);
		
		//PISTON 3
		angle = getPistonAngle(Piston3attach, Piston3baseattach, new Vector3f(1, 0, 0), "XY");
		lenght = getPistonLenght(Piston3attach, Piston3baseattach, Piston3, "XY");
		
        PartDefinition Piston3 = partdefinition.addOrReplaceChild("Piston3", 
				CubeListBuilder.create()
				.texOffs(64, 64)
				.addBox(0.0F, -0.2F, -0.25F, (float)lenght, 0.4f, 0.4f)//
				,PartPose.offset(-9.7F, 22.15F, -5.7F));
		this.Piston3 = Piston3.bake(0, 0);
		setRotation(this.Piston3, 0, 0, angle);
		setRotation(Piston3base, 0, 0, angle);
        
		//PISTON 4
		angle = getPistonAngle(Piston4attach, Piston4baseattach, new Vector3f(0, 0, 1), "ZY");
		lenght = getPistonLenght(Piston4attach, Piston4baseattach, Piston4, "ZY");
        
        PartDefinition Piston4 = partdefinition.addOrReplaceChild("Piston4", 
				CubeListBuilder.create()
				.texOffs(64, 64)
				.addBox(-0.1500003F, -0.2F, 0.0F, 0.4f, 0.4f, (float)lenght)
				,PartPose.offset(-10.3F, 22.15F, -9.7F));
		this.Piston4 = Piston4.bake(16, 12);
		setRotation(this.Piston4, -angle, 0, 0);
		setRotation(Piston4base, -angle, 0, 0);
	}
	
	public float getPistonAngle(ModelPart attach, ModelPart baseAttach, Vector3f direction, String interperter) {
		Vector3f pos = MathUtils.getPosWithRotation(attach);
		Vector3f pos2 =MathUtils.getPosWithRotation(baseAttach);
		
		//PISTON 1
		Vector3f dir = new Vector3f(pos.x() - pos2.x(), 
				pos.y() - pos2.y(), 
				pos.z() - pos2.z());
		Vec2 vec = MathUtils.getVec2f(dir, interperter);
		Vec2 vec2 = MathUtils.getVec2f(direction, interperter);
		vec = MathUtils.normalize(vec);
		return (float) MathUtils.getAngle(vec, vec2);
	}
	
	public double getPistonLenght(ModelPart attach, ModelPart baseAttach, ModelPart piston, String interperter) {
		Vector3f pos = MathUtils.getPosWithRotation(attach);
		Vector3f pos2 =MathUtils.getPosWithRotation(baseAttach);
		Vector3f dir = new Vector3f(pos.x() - pos2.x(), 
				pos.y() - pos2.y(), 
				pos.z() - pos2.z());
		return MathUtils.getLenght(MathUtils.getVec2f(dir, interperter));
	}
	
	public void renderToBuffer(PoseStack matrix, VertexConsumer bufferr, int combinedLight, int combinedLightOverlay,
			float red, float green, float blue, float alpha) {

		ResourceLocation GRAY = new ResourceLocation("minecraft", "textures/block/gray_concrete.png");
		ResourceLocation LIGHT_GRAY = new ResourceLocation("minecraft", "textures/block/light_gray_concrete.png");
		ResourceLocation REDSTONE = new ResourceLocation(Reference.MODID, "textures/blocks/redstone_block_bw.png");
		
		BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
		Rotatingbase.render(matrix, buffer.getBuffer(RenderType.entityTranslucent(GRAY)), combinedLight, combinedLightOverlay, 1.0f, 1.0f, 1.0f, 1.0f);
	    Piston1base.render(matrix, buffer.getBuffer(RenderType.entityTranslucent(GRAY)), combinedLight, combinedLightOverlay, 1.0f, 1.0f, 1.0f, 1.0f);
	    Piston1baseattach.render(matrix, buffer.getBuffer(RenderType.entityTranslucent(GRAY)), combinedLight, combinedLightOverlay, 1.0f, 1.0f, 1.0f, 1.0f);
	    Piston1.render(matrix, buffer.getBuffer(RenderType.entityTranslucent(LIGHT_GRAY)), combinedLight, combinedLightOverlay, 1.0f, 1.0f, 1.0f, 1.0f);
	    Piston1attach.render(matrix, buffer.getBuffer(RenderType.entityTranslucent(LIGHT_GRAY)), combinedLight, combinedLightOverlay, 1.0f, 1.0f, 1.0f, 1.0f);
	    Piston2attach.render(matrix, buffer.getBuffer(RenderType.entityTranslucent(LIGHT_GRAY)), combinedLight, combinedLightOverlay, 1.0f, 1.0f, 1.0f, 1.0f);
	    Piston2baseattach.render(matrix, buffer.getBuffer(RenderType.entityTranslucent(GRAY)), combinedLight, combinedLightOverlay, 1.0f, 1.0f, 1.0f, 1.0f);
	    Piston2base.render(matrix, buffer.getBuffer(RenderType.entityTranslucent(GRAY)), combinedLight, combinedLightOverlay, 1.0f, 1.0f, 1.0f, 1.0f);
	    Piston2.render(matrix, buffer.getBuffer(RenderType.entityTranslucent(LIGHT_GRAY)), combinedLight, combinedLightOverlay, 1.0f, 1.0f, 1.0f, 1.0f);
	    Piston3attach.render(matrix, buffer.getBuffer(RenderType.entityTranslucent(LIGHT_GRAY)), combinedLight, combinedLightOverlay, 1.0f, 1.0f, 1.0f, 1.0f);
	    Piston3.render(matrix, buffer.getBuffer(RenderType.entityTranslucent(LIGHT_GRAY)), combinedLight, combinedLightOverlay, 1.0f, 1.0f, 1.0f, 1.0f);
	    Piston3base.render(matrix, buffer.getBuffer(RenderType.entityTranslucent(GRAY)), combinedLight, combinedLightOverlay, 1.0f, 1.0f, 1.0f, 1.0f);
	    Piston3baseattach.render(matrix, buffer.getBuffer(RenderType.entityTranslucent(GRAY)), combinedLight, combinedLightOverlay, 1.0f, 1.0f, 1.0f, 1.0f);
	    Piston4baseattach.render(matrix, buffer.getBuffer(RenderType.entityTranslucent(GRAY)), combinedLight, combinedLightOverlay, 1.0f, 1.0f, 1.0f, 1.0f);
	    Piston4attach.render(matrix, buffer.getBuffer(RenderType.entityTranslucent(LIGHT_GRAY)), combinedLight, combinedLightOverlay, 1.0f, 1.0f, 1.0f, 1.0f);
	    Piston4base.render(matrix, buffer.getBuffer(RenderType.entityTranslucent(GRAY)), combinedLight, combinedLightOverlay, 1.0f, 1.0f, 1.0f, 1.0f);
	    Piston4.render(matrix, buffer.getBuffer(RenderType.entityTranslucent(LIGHT_GRAY)), combinedLight, combinedLightOverlay, 1.0f, 1.0f, 1.0f, 1.0f);
	    Rotatingbase2.render(matrix, buffer.getBuffer(RenderType.entityTranslucent(GRAY)), combinedLight, combinedLightOverlay, 1.0f, 1.0f, 1.0f, 1.0f);
	    Laserhead.render(matrix, buffer.getBuffer(RenderType.entityTranslucent(REDSTONE)), combinedLight, combinedLightOverlay, red, green, blue, alpha);
	}

	@Override
	public void setupAnim(T p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_,
			float p_102623_) {
	}
 
}