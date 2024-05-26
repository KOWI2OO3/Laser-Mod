package KOWI2003.LaserMod.items.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
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
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

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
	
	@SuppressWarnings("deprecation")
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

		float l = 10;
		
		CompoundTag tag = stack.getTag();
		l = tag.getFloat("distance");
		
		Player player = Minecraft.getInstance().player;
		
		Vector4f end = new Vector4f(new Vector3f(.45f, .4f, l+1));
		
		float[] pos = new float[] {0, 0, 0};
		float size = 0.05f;
		
		RenderSystem.enableBlend();

		matrix.translate(.5, -0.065, 0);
		
		PoseStack localToWorld = new PoseStack();
		BakedModel model = Minecraft.getInstance().getItemRenderer().getModel(stack, player.level, player, 0);
		Vector3f translation = model.getTransforms().getTransform(transformType).translation;
		
		localToWorld.translate(-translation.x(), -translation.y(), -translation.z());
		
		localToWorld.mulPose(Vector3f.YP.rotationDegrees(-90));
		
		Matrix4f worldToLocal = new Matrix4f(localToWorld.last().pose());
		worldToLocal.invert();
		
		end.transform(worldToLocal);
		
		Vector3f direction = new Vector3f(end);
		double s1 = MathUtils.getLenghtSqr(direction);
		direction.normalize();
		
//		float s = .1f;
//		RenderUtils.renderCube(matrix, end.x()-s/2f, end.y()-s/2f, end.z()-s/2f, s, s, s, 1, 0, 0);
		
		Vector4f forward = new Vector4f(.1f, 0, 0, 1);
		double s2 = MathUtils.getLenghtSqr(new Vector3f(forward));
		forward.normalize();
		
		l = (float) Math.sqrt(s1 + s2);
		
		Quaternion rotation = MathUtils.getQuaternionRotationBetweenVectors(new Vector3f(forward), direction);
		rotation.normalize();
		
		matrix.mulPose(rotation);
		RenderUtils.renderQuad(buffer.getBuffer(LaserRenderType.LASER_RENDER), matrix, new float[] {pos[0], pos[1] - size/2f, pos[2]}, 
				new float[] {l, size, size}, new float[] {0, 0, 1, 1}, new float[] {color[0], color[1], color[2], 0.5f}, combinedLight, combinedOverlay);
		matrix.mulPose(Vector3f.XP.rotationDegrees(90));
		RenderUtils.renderQuad(buffer.getBuffer(LaserRenderType.LASER_RENDER), matrix, new float[] {pos[0], pos[1] - size/2f, pos[2]}, 
				new float[] {l, size, size}, new float[] {0, 0, 1, 1}, new float[] {color[0], color[1], color[2], 0.5f}, combinedLight, combinedOverlay);
		
		matrix.popPose();

		matrix.translate(-0.5, -0.5, -0.5);
	}
}
