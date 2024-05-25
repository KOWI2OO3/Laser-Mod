package KOWI2003.LaserMod.tileentities.render;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector4f;

import KOWI2003.LaserMod.blocks.BlockLaser;
import KOWI2003.LaserMod.tileentities.TileEntityLaser;
import KOWI2003.LaserMod.utils.client.render.LaserRenderHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;

public class LaserRender implements BlockEntityRenderer<TileEntityLaser> {
	
	public LaserRender(BlockEntityRendererProvider.Context context) {}
	
	@Override
	public boolean shouldRenderOffScreen(@Nonnull TileEntityLaser te) {
		return te.active && (te.getDirection() == Direction.NORTH || te.getDirection() == Direction.WEST || te.distance > 9);
	}
	
	@Override
	public void render(@Nonnull TileEntityLaser tile, float partialTicks, @Nonnull PoseStack matrix, @Nonnull MultiBufferSource bufferIn,
		int combinedLightIn, int combinedOverlayIn) {
		var color = new Vector4f(tile.red, tile.green, tile.blue, 0.4f);
		Direction facing = tile.getBlockState().getValue(BlockLaser.FACING);
		
		if(tile.active)
			LaserRenderHelper.renderLaser(tile, matrix, bufferIn, tile.mode, facing, (float)tile.distance, color, .1f, combinedOverlayIn, combinedLightIn);
	}

}
