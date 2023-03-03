package KOWI2003.LaserMod.items.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;

import KOWI2003.LaserMod.init.ModItems;
import KOWI2003.LaserMod.tileentities.render.LaserRender.LaserRenderType;
import KOWI2003.LaserMod.utils.LaserItemUtils;
import KOWI2003.LaserMod.utils.MathUtils;
import KOWI2003.LaserMod.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class RenderMultiTool extends BlockEntityWithoutLevelRenderer {

	BlockEntityRenderDispatcher dispatcher;
	
	private static RenderMultiTool instance;
	
	public static RenderMultiTool get() {
		if(instance == null)
			instance = new RenderMultiTool(null, null);
		return instance;
	}
	
	public RenderMultiTool(BlockEntityRenderDispatcher p_172550_, EntityModelSet p_172551_) {
		super(p_172550_, p_172551_);
		this.dispatcher = p_172550_;
	}
	
	@Override
	public void renderByItem(ItemStack stack, TransformType transformType, PoseStack matrix,
			MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
		
		matrix.translate(0.5, 0.5, 0.5);
		Minecraft.getInstance().getItemRenderer().renderStatic(new ItemStack(ModItems.LaserMultiToolmodel.get()), transformType, combinedLight, combinedOverlay, matrix, buffer, 0);

		if(!LaserItemUtils.isExtended(stack) || 
				transformType == TransformType.GROUND || transformType == TransformType.HEAD || transformType == TransformType.GUI)
			return;
		
		float[] color = LaserItemUtils.getColor(stack);
		
		matrix.pushPose();
		
		Vec3 start = Minecraft.getInstance().player.position().add(new Vec3(0, Minecraft.getInstance().player.getEyeHeight(), 0));
		Vec3 dir = Minecraft.getInstance().player.getForward();
		//TODO remember to render the actual laser just like the normal laser mode on the laser block :D
		
		float l = 10;
		
		CompoundTag tag = stack.getTag();
		l = tag.getFloat("distance");
		
		float[] pos = new float[] {0, 0, 0};
		float size = 0.05f;
		
		RenderSystem.enableBlend();

		matrix.translate(.5, -0.065, 0);
		
//		if(transformType == TransformType.FIRST_PERSON_RIGHT_HAND) {
//			matrix.mulPose(Vector3f.YP.rotationDegrees(3.25f));
//			matrix.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
//		}else if(transformType == TransformType.FIRST_PERSON_LEFT_HAND) {
//			matrix.mulPose(Vector3f.YP.rotationDegrees(-3.25f));
//			matrix.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
//		}
		
		Vector4f forward = new Vector4f(0, 0, 1, 1);
		forward.transform(matrix.last().pose());
		
		Quaternion rotation = MathUtils.getQuaternionRotationBetweenVectors(new Vector3f(forward.x(), forward.y(), forward.z()), new Vector3f((float)dir.x, (float)dir.y, (float)dir.z));
		
		RenderUtils.renderQuad(buffer.getBuffer(LaserRenderType.LASER_RENDER), matrix, new float[] {pos[0], pos[1] - size/2f, pos[2]}, 
				new float[] {l, size, size}, new float[] {0, 0, 1, 1}, new float[] {color[0], color[1], color[2], 0.5f}, combinedLight, combinedOverlay);
		matrix.mulPose(Vector3f.XP.rotationDegrees(90));
		RenderUtils.renderQuad(buffer.getBuffer(LaserRenderType.LASER_RENDER), matrix, new float[] {pos[0], pos[1] - size/2f, pos[2]}, 
				new float[] {l, size, size}, new float[] {0, 0, 1, 1}, new float[] {color[0], color[1], color[2], 0.5f}, combinedLight, combinedOverlay);
		
		matrix.popPose();

		matrix.translate(-0.5, -0.5, -0.5);
	}
}
