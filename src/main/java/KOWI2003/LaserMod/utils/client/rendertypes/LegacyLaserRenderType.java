package KOWI2003.LaserMod.utils.client.rendertypes;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;

import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LegacyLaserRenderType extends RenderType {
	
	public LegacyLaserRenderType(String p_173178_, VertexFormat p_173179_, Mode p_173180_, int p_173181_, boolean p_173182_,
			boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
		super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
	}

	public static final RenderType LASER_RENDER = create("legacy_laser_render",
			DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 256, 
			true, false,
			RenderType.CompositeState.builder()
			.setShaderState(POSITION_COLOR_SHADER)
			.setWriteMaskState(COLOR_DEPTH_WRITE)
			.setTextureState(NO_TEXTURE)
			.setCullState(NO_CULL)
			.setTransparencyState(TRANSLUCENT_TRANSPARENCY)
			.setLayeringState(VIEW_OFFSET_Z_LAYERING)
			.setOutputState(TRANSLUCENT_TARGET)
			.setLightmapState(NO_LIGHTMAP)
			.setDepthTestState(LEQUAL_DEPTH_TEST)
			.createCompositeState(true));
	
	public static final RenderType LASER_PROJECTOR_RENDER = create("laser_render",
			DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 256, 
			true, false,
			RenderType.CompositeState.builder()
			.setShaderState(RENDERTYPE_LEASH_SHADER)
			.setShaderState(RENDERTYPE_LIGHTNING_SHADER)
			.setShaderState(POSITION_COLOR_SHADER)
			.setWriteMaskState(COLOR_WRITE)
			.setTextureState(NO_TEXTURE)
			.setCullState(NO_CULL)
			.setTransparencyState(TRANSLUCENT_TRANSPARENCY)
			.setLayeringState(VIEW_OFFSET_Z_LAYERING)
			.setOutputState(TRANSLUCENT_TARGET)
			.setLightmapState(NO_LIGHTMAP)
			.setDepthTestState(LEQUAL_DEPTH_TEST)
			.createCompositeState(true));
}