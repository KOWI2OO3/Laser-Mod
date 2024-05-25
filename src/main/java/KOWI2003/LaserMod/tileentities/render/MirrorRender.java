package KOWI2003.LaserMod.tileentities.render;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector4f;

import KOWI2003.LaserMod.tileentities.TileEntityLaser;
import KOWI2003.LaserMod.tileentities.TileEntityMirror;
import KOWI2003.LaserMod.utils.client.render.LaserRenderHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;

public class MirrorRender implements BlockEntityRenderer<TileEntityMirror> {
	
	public MirrorRender(BlockEntityRendererProvider.Context context) {}
	
	@Override
	public boolean shouldRenderOffScreen(@Nonnull TileEntityMirror te) {
		return te.isActive() && (te.getDirection() == Direction.NORTH || te.getDirection() == Direction.WEST || te.distance > 10);
	}

	@Override
	public void render(@Nonnull TileEntityMirror mirror, float partialTicks, @Nonnull PoseStack matrix, @Nonnull MultiBufferSource bufferIn,
			int combinedLightIn, int combinedOverlayIn) {
		if(!mirror.hasLaser())
			return;

		TileEntityLaser laser = mirror.getLaser();
		var color = new Vector4f(laser.red, laser.green, laser.blue, 0.4f);
		Direction facing = mirror.getDirection();
		
		if(laser.active)
			LaserRenderHelper.renderLaser(mirror, matrix, bufferIn, laser.mode, facing, (float)mirror.distance, color, .5f, combinedOverlayIn, combinedLightIn);
	}
}
