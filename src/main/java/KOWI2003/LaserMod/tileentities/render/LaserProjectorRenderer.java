package KOWI2003.LaserMod.tileentities.render;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;

import KOWI2003.LaserMod.blocks.BlockHorizontal;
import KOWI2003.LaserMod.tileentities.TileEntityLaserProjector;
import KOWI2003.LaserMod.tileentities.projector.gui.RenderContext;
import KOWI2003.LaserMod.tileentities.projector.gui.guis.ProjectorCustomGui;
import KOWI2003.LaserMod.utils.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class LaserProjectorRenderer implements BlockEntityRenderer<TileEntityLaserProjector> {
	public LaserProjectorRenderer(BlockEntityRendererProvider.Context context) {}
	
	@Override
	public void render(@Nonnull TileEntityLaserProjector te, float partialTicks, @Nonnull  PoseStack matrix, @Nonnull  MultiBufferSource bufferIn,
			int combinedLightIn, int combinedOverlayIn) {
		if(te.isActive) {
			RenderContext<TileEntityLaserProjector> context = new RenderContext<TileEntityLaserProjector>(te, partialTicks, matrix, bufferIn, combinedLightIn, combinedOverlayIn);
			
			RenderUtils.renderLighting(matrix, new float[] {1.0f, 0.0f, 0.0f}, 0.3f, 0.4f, 0.3f);
			
			matrix.pushPose();
			renderCustom(context);
			matrix.popPose();
		}
	}
	
	public void handleRotation(PoseStack matrix, TileEntityLaserProjector te) {
		RenderUtils.rotateMatrixForBlock(matrix, te.getBlockState().getValue(BlockHorizontal.FACING));
	}
	
	public void renderCustom(RenderContext<TileEntityLaserProjector> context) {
		handleRotation(context.getMatrix(), context.getTileentity());
		float scale = 0.01f;
		context.getMatrix().translate(0.5f, 1.1f + 0.11f*2.2f, 0.5f);
		context.getMatrix().scale(scale, scale, scale);
		
		new ProjectorCustomGui(context.getTileentity()).render(context);
		RenderSystem.enableCull();
	}
	
public static class ProjectorRenderType extends RenderType {

	public ProjectorRenderType(String p_173178_, VertexFormat p_173179_, Mode p_173180_, int p_173181_,
			boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
		super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
	}

	public static final RenderType PROJECTION_RENDER = create("projection_render",
			DefaultVertexFormat.POSITION_COLOR_TEX, Mode.QUADS, 256, false, true,
			 RenderType.CompositeState.builder()
			 .setWriteMaskState(COLOR_WRITE)
			 .setCullState(NO_CULL)
			 .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
			 .setTextureState(NO_TEXTURE)
			 .setShaderState(RENDERTYPE_ENTITY_SMOOTH_CUTOUT_SHADER)
			 .createCompositeState(false));
	}
	
}
