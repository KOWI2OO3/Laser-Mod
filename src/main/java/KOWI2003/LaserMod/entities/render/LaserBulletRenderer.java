package KOWI2003.LaserMod.entities.render;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import KOWI2003.LaserMod.entities.EntityLaserBullet;
import KOWI2003.LaserMod.utils.RenderUtils;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class LaserBulletRenderer extends EntityRenderer<EntityLaserBullet> {

	public LaserBulletRenderer(Context context) {
		super(context);
	}
	
	@Override
	public boolean shouldRender(@Nonnull EntityLaserBullet p_114491_, @Nonnull Frustum p_114492_, double p_114493_, double p_114494_,
			double p_114495_) {
		return true;
	}

	@Override
	public void render(@Nonnull EntityLaserBullet entity, float p_114486_, float p_114487_, @Nonnull PoseStack pose,
			@Nonnull MultiBufferSource buffer, int combinedLight) {
		
		pose.pushPose();
		
		pose.mulPose(Vector3f.YP.rotationDegrees(entity.getYRot()));
		pose.mulPose(Vector3f.XP.rotationDegrees(-entity.getXRot()));
		if(entity.tickCount /20f > 9.5f) {
			float size = 1f - (((entity.tickCount / 20f - 9.5f) % 0.5f) * 2f);
			size = Math.max(0, size);
			if(size <= 0) {
				pose.popPose();
				return;
			}
			pose.scale(size, size, size);
		}
		
		float[] rgb = Utils.parseColor(entity.getColor());
		rgb[3] = 0.4f;
		RenderUtils.renderCube(buffer.getBuffer(RenderType.entityTranslucent(RenderUtils.getEmptyTexture())), pose, new float[] {-.05f, -.05f, -.05f}, new float[] {.1f, .1f, .3f},
				new float[] {0, 0, 1, 1}, rgb, 0xffffff, OverlayTexture.NO_OVERLAY);
		
		pose.popPose();
	}
	
	@Override
	public ResourceLocation getTextureLocation(@Nonnull EntityLaserBullet entity) {
		return new ResourceLocation("textures/entity/boat/oak.png");
	}
}
