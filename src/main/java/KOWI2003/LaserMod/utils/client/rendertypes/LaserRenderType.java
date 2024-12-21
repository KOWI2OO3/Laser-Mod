package KOWI2003.LaserMod.utils.client.rendertypes;

import java.io.IOException;
import java.util.function.Function;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;

import KOWI2003.LaserMod.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
public class LaserRenderType extends RenderType {

    // Initializing shader
    static ShaderInstance beamShader;
    static ShaderInstance laserShader;
    static ShaderInstance laserPowerShader;
    static ShaderInstance hitMarkerShader;

    static {
        reloadShaders();
    }

    /**
     * Loads a shader from the resource location supplied. If the shader fails to load, it will default to the position color shader
     * @param location the location of the shader
     * @param vertexFormat the vertex format of the shader
     * @return the shader instance or the default shader if the shader fails to load
     */
    private static final ShaderInstance loadShader(ResourceLocation location, VertexFormat vertexFormat) {
        try {
            return new ShaderInstance(Minecraft.getInstance().getResourceManager(), location, vertexFormat);
        } catch (IOException e) {
            System.err.println("Failed to load laser '" + location + "' shader");
        }
        return GameRenderer.getPositionColorShader() == null ? RenderSystem.getShader() : GameRenderer.getPositionColorShader();
    }

    public static final RenderType SIMPLE_LASER_RENDER = create("simple_laser_render", 
        DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.TRIANGLES, 256, 
        true, false, 
        CompositeState.builder()
        .setShaderState(POSITION_COLOR_SHADER)
        .setWriteMaskState(COLOR_DEPTH_WRITE)
        .setTextureState(NO_TEXTURE)
        .setCullState(NO_CULL)
        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
        .setLayeringState(VIEW_OFFSET_Z_LAYERING)
        .setLightmapState(NO_LIGHTMAP)
        .setDepthTestState(LEQUAL_DEPTH_TEST)
        .setOutputState(TRANSLUCENT_TARGET)
        .createCompositeState(true));

    public static final RenderType LASER_RENDER = create("laser_render", 
        DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.TRIANGLES, 256, 
        true, false, 
        CompositeState.builder()
        .setShaderState(new ShaderStateShard(() -> laserShader))
        .setWriteMaskState(COLOR_DEPTH_WRITE)
        .setTextureState(NO_TEXTURE)
        .setCullState(NO_CULL)
        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
        .setLayeringState(VIEW_OFFSET_Z_LAYERING)
        .setLightmapState(NO_LIGHTMAP)
        .setDepthTestState(LEQUAL_DEPTH_TEST)
        .setOutputState(TRANSLUCENT_TARGET)
        .createCompositeState(true));

        public static final RenderType LASER_POWER_RENDER = create("laser_power_render", 
        DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.TRIANGLES, 256, 
        true, false, 
        CompositeState.builder()
        .setShaderState(new ShaderStateShard(() -> laserPowerShader))
        .setWriteMaskState(COLOR_DEPTH_WRITE)
        .setTextureState(NO_TEXTURE)
        .setCullState(NO_CULL)
        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
        .setLayeringState(VIEW_OFFSET_Z_LAYERING)
        .setLightmapState(NO_LIGHTMAP)
        .setDepthTestState(LEQUAL_DEPTH_TEST)
        .setOutputState(TRANSLUCENT_TARGET)
        .createCompositeState(true));

    public static final RenderType HIT_MARKER_RENDER = create("hit_marker_render", 
        DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.TRIANGLES, 256, 
        true, false, 
        CompositeState.builder()
        .setShaderState(new ShaderStateShard(() -> hitMarkerShader))
        .setWriteMaskState(COLOR_DEPTH_WRITE)
        .setTextureState(NO_TEXTURE)
        .setCullState(NO_CULL)
        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
        .setLayeringState(VIEW_OFFSET_Z_LAYERING)
        .setLightmapState(NO_LIGHTMAP)
        .setDepthTestState(LEQUAL_DEPTH_TEST)
        .setOutputState(MAIN_TARGET)
        .createCompositeState(true));

    public static final Function<Float, RenderType> LASER_BEAM_RENDER = (length) -> create("laser_beam_render", 
        DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.TRIANGLES, 256, 
        true, false, 
        CompositeState.builder()
        .setShaderState(new ShaderStateShard(() -> {
            beamShader.safeGetUniform("laserLength").set(length);
            return beamShader;
        }))
        .setWriteMaskState(COLOR_DEPTH_WRITE)
        .setTextureState(NO_TEXTURE)
        .setCullState(NO_CULL)
        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
        .setLayeringState(VIEW_OFFSET_Z_LAYERING)
        .setLightmapState(NO_LIGHTMAP)
        .setDepthTestState(LEQUAL_DEPTH_TEST)
        .setOutputState(TRANSLUCENT_TARGET)
        .createCompositeState(true));

	public static final RenderType LASER_PROJECTOR_RENDER = create("laser_projector_render",
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

    public LaserRenderType(String p_173178_, VertexFormat p_173179_, Mode p_173180_, int p_173181_, boolean p_173182_,
            boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
        super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
    }

    private static void reloadShaders() {
        if(beamShader != null)
            beamShader.close();
        beamShader = loadShader(new ResourceLocation(Reference.MODID, "laser_beam"), DefaultVertexFormat.POSITION_COLOR);
        if(hitMarkerShader != null)
            hitMarkerShader.close();
        hitMarkerShader = loadShader(new ResourceLocation(Reference.MODID, "hit_marker"), DefaultVertexFormat.POSITION_COLOR);

        if(laserShader != null)
            laserShader.close();
        laserShader = loadShader(new ResourceLocation(Reference.MODID, "laser"), DefaultVertexFormat.POSITION_COLOR);

        if(laserPowerShader != null)
            laserPowerShader.close();
        laserPowerShader = loadShader(new ResourceLocation(Reference.MODID, "laser_power"), DefaultVertexFormat.POSITION_COLOR);
    }

    @OnlyIn(Dist.CLIENT)
    @Mod.EventBusSubscriber(modid = Reference.MODID, value = Dist.CLIENT)
    public static class LaserRenderTypeEvent {
        @SubscribeEvent
        public void onReloadResources(TextureStitchEvent.Post event) {
            // A bit of an hacky way to reload the shaders on loading resource pack
            LaserRenderType.reloadShaders();
        }
    }
}
