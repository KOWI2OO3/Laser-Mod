package KOWI2003.LaserMod.utils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.function.BiFunction;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.NativeType;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.blocks.BlockRotatable;
import KOWI2003.LaserMod.utils.client.rendertypes.LegacyLaserRenderType;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class RenderUtils {

	public static class Gui {
		public static void drawQuad(PoseStack matrix, float x, float y, float width, float height, float uvX, float uvY, float uvWidth, float uvHeight) {
			BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();//new BufferBuilder(256);//Tessellator.getInstance().getBuilder();
			Matrix4f matrix2 = matrix.last().pose();
			bufferbuilder.begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
			bufferbuilder.vertex(matrix2, x		   , y + height, 0f).uv(uvX			 , uvY - uvHeight).endVertex();
			bufferbuilder.vertex(matrix2, x + width, y + height, 0f).uv(uvX + uvWidth, uvY - uvHeight).endVertex();
			bufferbuilder.vertex(matrix2, x + width, y		   , 0f).uv(uvX + uvWidth, uvY 			 ).endVertex();
			bufferbuilder.vertex(matrix2, x		   , y		   , 0f).uv(uvX			 , uvY 			 ).endVertex();
			Tesselator.getInstance().end();
		}
		
		public static void drawQuad(PoseStack matrix, float x, float y, float width, float height, float uvX, float uvY) {
			drawQuad(matrix, x, y, width, height, uvX, uvY, width, height);
		}
		
		public static void drawQuad(PoseStack matrix, float x, float y, float width, float height, float red, float green, float blue) {
			drawQuad(matrix, new float[] {x, y}, new float[] {width, height}, new float[] {red, green, blue, 1f}, new float[] {0, 0, 0, 0});
		}
		
		public static void drawQuadColor(PoseStack matrix, float x, float y, float width, float height, float red, float green, float blue) {
			drawQuadColor(matrix, new float[] {x, y}, new float[] {width, height}, new float[] {red, green, blue, 1f});
		}
		
		public static void drawQuadColor(PoseStack matrix, float[] position, float[]size2D, float[] color) {
			float x = position.length > 0 ? position[0] : 0f;
			float y = position.length > 1 ? position[1] : 0f;

			float width = size2D.length > 0 ? size2D[0] : 1f;
			float height = size2D.length > 1 ? size2D[1] : 1f;

			float red = color.length > 0 ? color[0] : 1f;
			float green = color.length > 1 ? color[1] : 1f;
			float blue = color.length > 2 ? color[2] : 1f;
			float alpha = color.length > 3 ? color[3] : 1f;
			
			RenderSystem.setShader(GameRenderer::getPositionColorShader);
			RenderSystem.setShaderColor(red, green, blue, alpha);
			BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
			Matrix4f matrix2 = matrix.last().pose();
			bufferbuilder.begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
			bufferbuilder.vertex(matrix2, x		   , y + height, 0f).color(red, green, blue, alpha).endVertex();
			bufferbuilder.vertex(matrix2, x + width, y + height, 0f).color(red, green, blue, alpha).endVertex();
			bufferbuilder.vertex(matrix2, x + width, y		   , 0f).color(red, green, blue, alpha).endVertex();
			bufferbuilder.vertex(matrix2, x		   , y		   , 0f).color(red, green, blue, alpha).endVertex();
			Tesselator.getInstance().end();
		}
		
		public static void drawOutline(PoseStack matrix, float x, float y, float width, float height, float red, float green, float blue) {
			drawOutline(matrix, new float[] {x, y}, new float[] {width, height}, new float[] {red, green, blue, 1f});
		}
		
		public static void drawOutline(PoseStack matrix, float[] position, float[]size2D, float[] color) {
			float x = position.length > 0 ? position[0] : 0f;
			float y = position.length > 1 ? position[1] : 0f;

			float width = size2D.length > 0 ? size2D[0] : 1f;
			float height = size2D.length > 1 ? size2D[1] : 1f;

			float red = color.length > 0 ? color[0] : 1f;
			float green = color.length > 1 ? color[1] : 1f;
			float blue = color.length > 2 ? color[2] : 1f;
			float alpha = color.length > 3 ? color[3] : 1f;
			
			RenderSystem.setShader(GameRenderer::getPositionColorShader);
			RenderSystem.setShaderColor(red, green, blue, alpha);
			BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
			Matrix4f matrix2 = matrix.last().pose();
			bufferbuilder.begin(Mode.DEBUG_LINE_STRIP, DefaultVertexFormat.POSITION_COLOR);
			bufferbuilder.vertex(matrix2, x		   , y + height, 0f).color(red, green, blue, alpha).endVertex();
			bufferbuilder.vertex(matrix2, x + width, y + height, 0f).color(red, green, blue, alpha).endVertex();
			bufferbuilder.vertex(matrix2, x + width, y		   , 0f).color(red, green, blue, alpha).endVertex();
			bufferbuilder.vertex(matrix2, x		   , y		   , 0f).color(red, green, blue, alpha).endVertex();
			bufferbuilder.vertex(matrix2, x		   , y + height, 0f).color(red, green, blue, alpha).endVertex();
			Tesselator.getInstance().end();
		}
		
		public static void drawQuad(PoseStack matrix, float[] position, float[]size2D, float[] color, float[] uv) {
			float x = position.length > 0 ? position[0] : 0f;
			float y = position.length > 1 ? position[1] : 0f;

			float width = size2D.length > 0 ? size2D[0] : 1f;
			float height = size2D.length > 1 ? size2D[1] : 1f;

			float red = color.length > 0 ? color[0] : 1f;
			float green = color.length > 1 ? color[1] : 1f;
			float blue = color.length > 2 ? color[2] : 1f;
			float alpha = color.length > 3 ? color[3] : 1f;
			
			float uvX = uv.length > 0 ? uv[0] : 0f;
			float uvY = uv.length > 1 ? uv[1] : 0f;
			float uvWidth = uv.length > 2 ? uv[2] : width;
			float uvHeight = uv.length > 3 ? uv[3] : height;
			
			RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
			RenderSystem.setShaderColor(red, green, blue, alpha);
			BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
			Matrix4f matrix2 = matrix.last().pose();
			bufferbuilder.begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
			bufferbuilder.vertex(matrix2, x		   , y + height, 0f).uv(uvX			 , uvY - uvHeight).color(red, green, blue, alpha).endVertex();
			bufferbuilder.vertex(matrix2, x + width, y + height, 0f).uv(uvX + uvWidth, uvY - uvHeight).color(red, green, blue, alpha).endVertex();
			bufferbuilder.vertex(matrix2, x + width, y		   , 0f).uv(uvX + uvWidth, uvY 			 ).color(red, green, blue, alpha).endVertex();
			bufferbuilder.vertex(matrix2, x		   , y		   , 0f).uv(uvX			 , uvY 			 ).color(red, green, blue, alpha).endVertex();
			Tesselator.getInstance().end();
		}
		
		public static void drawQuad(PoseStack matrix, float x, float y, float width, float height, float red, float green, float blue, double alpha) {
			RenderSystem.disableTexture();
			RenderSystem.setShaderColor(red, green, blue, (float)alpha);
			drawQuad(matrix, x, y, width, height, 0, 0);
			RenderSystem.enableTexture();
		}
		
		public static void drawFontString(PoseStack matrix, String msg, float x, float y, float red, float green, float blue) {
			Minecraft.getInstance().font.draw(matrix, msg, x, y, Utils.getHexIntFromRGB(red, green, blue));
		}
		
		public static void drawFontString(PoseStack matrix, String msg, float x, float y, float red, float green, float blue, float scale) {
			matrix.pushPose();
			matrix.translate(x, y, 0);
			matrix.scale(scale, scale, scale);
			Minecraft.getInstance().font.draw(matrix, msg, 0, 0, Utils.getHexIntFromRGB(red, green, blue));
			matrix.popPose();
		}
		
		@Deprecated
		public static void drawCircle(PoseStack matrix, double radius, int segments) {
			radius *= 0.5f;
			BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
			Matrix4f matrix2 = matrix.last().pose();
//			bufferbuilder.begin(GL11.GL_POLYGON, DefaultVertexFormat.POSITION_TEX);
			bufferbuilder.begin(Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_TEX);
	        for (int i = 0; i < segments; i++) {
	        	bufferbuilder.vertex(matrix2, (float)(Math.sin((i / (double) segments) * Math.PI * 2) * radius), (float)(Math.cos((i / (double) segments) * Math.PI * 2) * radius), 0f).endVertex();
	        }
			Tesselator.getInstance().end();
	    }
		
		public static void renderItem(ItemStack stack, float x, float y, float scale) {
			renderItem(new PoseStack(), stack, x, y, 0, scale, ItemTransforms.TransformType.GUI);
		}

		@SuppressWarnings("deprecation")
		public static void renderItem(PoseStack matrix, ItemStack stack, float x, float y, float z, float scale, TransformType transform) {
			RenderSystem.enableDepthTest();
			BakedModel model = Minecraft.getInstance().getItemRenderer().getModel(stack, (Level)null, (LivingEntity)null, 0);
			Minecraft.getInstance().getTextureManager().getTexture(TextureAtlas.LOCATION_BLOCKS).setFilter(false, false);
			RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
			RenderSystem.enableBlend();
			RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			PoseStack posestack = RenderSystem.getModelViewStack();
			posestack.pushPose();
			posestack.translate((double)x, (double)y, (double)(z + 100.0F + Minecraft.getInstance().getItemRenderer().blitOffset));
			posestack.translate(8.0D, 8.0D, 0.0D);
			posestack.scale(1.0F, -1.0F, 1.0F);
			posestack.scale(16.0F, 16.0F, 16.0F);
			posestack.scale(scale, scale, scale);
			posestack.mulPoseMatrix(matrix.last().pose());
			RenderSystem.applyModelViewMatrix();
			MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
			boolean flag = !model.usesBlockLight();
			if (flag) 
				Lighting.setupForFlatItems();

			Minecraft.getInstance().getItemRenderer().render(stack, transform, false, new PoseStack(), multibuffersource$buffersource, 15728880, OverlayTexture.NO_OVERLAY, model);
			multibuffersource$buffersource.endBatch();
			RenderSystem.enableDepthTest();
			if (flag)
				Lighting.setupFor3DItems();
			
			posestack.popPose();
	 	    RenderSystem.applyModelViewMatrix();
		}
		
		public static void drawCenteredStringWithinLine(PoseStack pose, String msg, float x, float y, float lineWith, int color) {
			Font font = Minecraft.getInstance().font;
			int textWidth = font.width(msg);
			float factor = Math.min(1, lineWith/textWidth);
			pose.translate(lineWith/2f - (factor * textWidth/2f), +(1-factor), 0);
			pose.scale(factor, factor, factor);
			font.draw(pose, msg, x, y, color);
		}
		
		public static void drawCenteredStringWithinBox(PoseStack pose, String msg, float x, float y, float boxWith, float boxHeight, int color) {
			Font font = Minecraft.getInstance().font;
			int textWidth = font.width(msg);
			float factor = Math.min(1, Math.min(boxWith/textWidth, boxHeight/font.lineHeight));
			pose.translate(boxWith/2f - (factor * textWidth/2f), boxHeight/2f - (factor * font.lineHeight/2f), 0);
			pose.scale(factor, factor, factor);
			font.draw(pose, msg, x, y, color);
		}
		
		public static void drawStringWithinLine(PoseStack pose, String msg, float x, float y, float lineWith, int color) {
			pose.pushPose();
			float factor = Math.min(1, lineWith/ Minecraft.getInstance().font.width(msg));
			pose.translate(x, y - factor*Minecraft.getInstance().font.lineHeight/2, 0);
			pose.scale(factor, factor, factor);
			Minecraft.getInstance().font.draw(pose, msg, 0, 0, color);
			pose.popPose();
		}
		
		public static void drawStringWithinBox(PoseStack pose, String msg, float x, float y, float boxWith, float boxHeight, int color) {
			pose.pushPose();
			Font font = Minecraft.getInstance().font;
			int textWidth = font.width(msg);
			float factor = Math.min(1, Math.min(boxWith/textWidth, boxHeight/font.lineHeight));
			pose.translate(x, y + boxHeight - factor*Minecraft.getInstance().font.lineHeight/2, 0);
			pose.scale(factor, factor, factor);
			Minecraft.getInstance().font.draw(pose, msg, 0, 0, color);
			pose.popPose();
		}
	}
	
	private static ShaderInstance LIGHTEND_POSITION_COLOR_SHADER;
	
	public static ShaderInstance getLightendPositionColorShader() {
		if(LIGHTEND_POSITION_COLOR_SHADER == null)
			try {
				LIGHTEND_POSITION_COLOR_SHADER =  new ShaderInstance(Minecraft.getInstance().getResourceManager(), 
						new ResourceLocation(Reference.MODID, "lightend_position_color"), DefaultVertexFormat.NEW_ENTITY);
			} catch (IOException e) {
				System.err.println("Error Loading Shader 'lasermod:lightend_position_color' falling back on vanilla shaders!");
				return GameRenderer.getRendertypeEntityTranslucentShader();
			}
		return LIGHTEND_POSITION_COLOR_SHADER;
	}
	
	public static RenderType triangularEntityTranslucent(ResourceLocation texture, Mode mode) {
		return TriangularEntityTranslucentType.TRIANGULAR_ENTITY_TRANSLUCENT.apply(texture, mode);
	}
	
	public static ResourceLocation getEmptyTexture() {
		return new ResourceLocation("textures/misc/white.png");
	}
	
	public static void bindTexture(ResourceLocation textureLocation) {
	    RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, textureLocation);
	}
	
	public static void setColor(float[] color) {
		RenderSystem.setShaderColor(color.length > 0 ? color[0] : 1f,
     		   color.length > 1 ? color[1] : 1f, 
     		   color.length > 2 ? color[2] : 1f, 
     		   color.length > 3 ? color[3] : 1f);
	}
	
	public static void setColor(float red, float green, float blue, float alpha) {
		RenderSystem.setShaderColor(red, green, blue, alpha);
	}
	
	@Deprecated
	public static void renderCircle(PoseStack matrix, double radius, int segments, float u, float v, float uWidth, float vHeight) {
		radius *= 0.5f;
		BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
		Matrix4f matrix2 = matrix.last().pose();
//		bufferbuilder.begin(GL11.GL_POLYGON, DefaultVertexFormat.POSITION_TEX);
		bufferbuilder.begin(Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_TEX);
        for (int i = 0; i < segments; i++) {
        	bufferbuilder.vertex(matrix2, (float)(Math.sin((i / (double) segments) * Math.PI * 2) * radius), (float)(Math.cos((i / (double) segments) * Math.PI * 2) * radius), 0f)
        	.uv((float)(u + 0.5f + (Math.sin((i / (double) segments) * Math.PI * 2)/2f+1f) * uWidth), (float)(v + 0.5f + (Math.cos((i / (double) segments) * Math.PI * 2)/2f + 1f) * vHeight)).endVertex();
        }
        Tesselator.getInstance().end();
    }
	
	public static void renderCircle(PoseStack matrix, double radius, int segments, float[] color) {
		RenderSystem.disableTexture();
		radius *= 0.5f;
		BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
		Matrix4f matrix2 = matrix.last().pose();
//		bufferbuilder.begin(GL11.GL_POLYGON, DefaultVertexFormat.POSITION_COLOR);
		bufferbuilder.begin(Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
        for (int i = 0; i < segments; i++) {
        	bufferbuilder.vertex(matrix2, (float)(Math.sin((i / (double) segments) * Math.PI * 2) * radius), (float)(Math.cos((i / (double) segments) * Math.PI * 2) * radius), 0f)
        	.color(color.length > 0 ? color[0] : 1f,
        		   color.length > 1 ? color[1] : 1f, 
        		   color.length > 2 ? color[2] : 1f, 
        		   color.length > 3 ? color[3] : 1f).endVertex();
        }
        Tesselator.getInstance().end();
		RenderSystem.enableTexture();
    }
	
	public static void renderCircle(PoseStack matrix, double radius, int segments, float red, float green, float blue) {
		RenderSystem.disableTexture();
		renderCircle(matrix, radius, segments, new float[] {red, green, blue});
		RenderSystem.enableTexture();
    }
	
	public static void renderCircle(PoseStack matrix, double radius, int segments) {
		radius *= 0.5f;
		BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
		Matrix4f matrix2 = matrix.last().pose();
//		bufferbuilder.begin(GL11.GL_POLYGON, DefaultVertexFormat.POSITION);
		bufferbuilder.begin(Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION);
        for (int i = 0; i < segments; i++) {
        	bufferbuilder.vertex(matrix2, (float)(Math.sin((i / (double) segments) * Math.PI * 2) * radius), (float)(Math.cos((i / (double) segments) * Math.PI * 2) * radius), 0f).endVertex();
        }
        Tesselator.getInstance().end();
    }
	
	public static void renderCube(PoseStack matrix, float x, float y, float z, float width, float height, float depth, float uvX, float uvY, float uvWidth, float uvHeight) {
		BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
		Matrix4f matrix2 = matrix.last().pose();
		bufferbuilder.begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferbuilder.vertex(matrix2, x		   , y + height, z).uv(uvX			, uvY + uvHeight).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y + height, z).uv(uvX + uvWidth, uvY + uvHeight).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y		   , z).uv(uvX + uvWidth, uvY			).endVertex();
		bufferbuilder.vertex(matrix2, x		   , y		   , z).uv(uvX			, uvY			).endVertex();
		

		bufferbuilder.vertex(matrix2, x + width, y + height, z + depth).uv(uvX + uvWidth, uvY + uvHeight).endVertex();
		bufferbuilder.vertex(matrix2, x		   , y + height, z + depth).uv(uvX			, uvY + uvHeight).endVertex();
		bufferbuilder.vertex(matrix2, x		   , y		   , z + depth).uv(uvX			, uvY			).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y		   , z + depth).uv(uvX + uvWidth, uvY			).endVertex();

		bufferbuilder.vertex(matrix2, x + width, y 	   	   , z + depth).uv(uvX + uvWidth, uvY + uvHeight).endVertex();
		bufferbuilder.vertex(matrix2, x		   , y 		   , z + depth).uv(uvX			, uvY + uvHeight).endVertex();
		bufferbuilder.vertex(matrix2, x		   , y		   , z).uv(uvX			, uvY			).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y		   , z).uv(uvX + uvWidth, uvY			).endVertex();
		

		bufferbuilder.vertex(matrix2, x		   , y + height, z + depth).uv(uvX			, uvY + uvHeight).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y + height, z + depth).uv(uvX + uvWidth, uvY + uvHeight).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y + height, z).uv(uvX + uvWidth, uvY			).endVertex();
		bufferbuilder.vertex(matrix2, x		   , y + height, z).uv(uvX			, uvY			).endVertex();
		

		bufferbuilder.vertex(matrix2, x		   , y + height, z + depth).uv(uvX + uvWidth, uvY + uvHeight).endVertex();
		bufferbuilder.vertex(matrix2, x		   , y + height, z).uv(uvX			, uvY + uvHeight).endVertex();
		bufferbuilder.vertex(matrix2, x		   , y		   , z).uv(uvX			, uvY			).endVertex();
		bufferbuilder.vertex(matrix2, x 	   , y		   , z + depth).uv(uvX + uvWidth, uvY			).endVertex();
		

		bufferbuilder.vertex(matrix2, x + width, y + height, z).uv(uvX			, uvY + uvHeight).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y + height, z + depth).uv(uvX + uvWidth, uvY + uvHeight).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y		   , z + depth).uv(uvX + uvWidth, uvY			).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y		   , z).uv(uvX			, uvY			).endVertex();
		Tesselator.getInstance().end();
	}
	
	public static void renderCube(VertexConsumer consumer, PoseStack matrix, float[] position, float[] size, float[] uv, float[] color, int packedLight, int packedOverlay) {
		float x = position != null && position.length > 0 ? position[0] : 0;
		float y = position != null && position.length > 1 ? position[1] : 0;
		float z = position != null && position.length > 2 ? position[2] : 0;
		
		float width = size != null && size.length > 0 ? size[0] : 1;
		float height = size != null && size.length > 1 ? size[1] : 1;
		float depth = size != null && size.length > 2 ? size[2] : 1;
		
		float uvX = uv != null && uv.length > 0 ? uv[0] : 0;
		float uvY = uv != null && uv.length > 1 ? uv[1] : 0;
		float uvWidth = uv != null && uv.length > 2 ? uv[2] : 1;
		float uvHeight = uv != null && uv.length > 3 ? uv[3] : 1;
		
		float red = color != null && color.length > 0 ? color[0] : 1f;
		float green = color != null && color.length > 1 ? color[1] : 1f;
		float blue = color != null && color.length > 2 ? color[2] : 1f;
		float alpha = color != null && color.length > 3 ? color[3] : 1f;

		RenderUtils.setColor(1, 1, 1, 1);
		
		RenderSystem.enableTexture();
		Matrix4f matrix2 = matrix.last().pose();
		Matrix3f normal = matrix.last().normal();
		
		consumer.vertex(matrix2, x		   , y + height, z).color(red, green, blue, alpha).uv(uvX			, uvY + uvHeight).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, 0, -1).endVertex();
		consumer.vertex(matrix2, x + width, y + height, z).color(red, green, blue, alpha).uv(uvX + uvWidth, uvY + uvHeight).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, 0, -1).endVertex();
		consumer.vertex(matrix2, x + width, y		   , z).color(red, green, blue, alpha).uv(uvX + uvWidth, uvY			).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, 0, -1).endVertex();
		consumer.vertex(matrix2, x		   , y		   , z).color(red, green, blue, alpha).uv(uvX			, uvY			).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, 0, -1).endVertex();
		
		consumer.vertex(matrix2, x + width, y + height, z + depth).color(red, green, blue, alpha).uv(uvX + uvWidth, uvY + uvHeight).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, 0, 1).endVertex();
		consumer.vertex(matrix2, x		   , y + height, z + depth).color(red, green, blue, alpha).uv(uvX			, uvY + uvHeight).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, 0, 1).endVertex();
		consumer.vertex(matrix2, x		   , y		   , z + depth).color(red, green, blue, alpha).uv(uvX			, uvY			).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, 0, 1).endVertex();
		consumer.vertex(matrix2, x + width, y		   , z + depth).color(red, green, blue, alpha).uv(uvX + uvWidth, uvY			).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, 0, 1).endVertex();

		consumer.vertex(matrix2, x + width, y 	   	   , z + depth).color(red, green, blue, alpha).uv(uvX + uvWidth, uvY + uvHeight).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, -1, 0).endVertex();
		consumer.vertex(matrix2, x		   , y 		   , z + depth).color(red, green, blue, alpha).uv(uvX			, uvY + uvHeight).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, -1, 0).endVertex();
		consumer.vertex(matrix2, x		   , y		   , z).color(red, green, blue, alpha).uv(uvX			, uvY			).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, -1, 0).endVertex();
		consumer.vertex(matrix2, x + width, y		   , z).color(red, green, blue, alpha).uv(uvX + uvWidth, uvY			).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, -1, 0).endVertex();
		

		consumer.vertex(matrix2, x		   , y + height, z + depth).color(red, green, blue, alpha).uv(uvX			, uvY + uvHeight).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, 1, 0).endVertex();
		consumer.vertex(matrix2, x + width, y + height, z + depth).color(red, green, blue, alpha).uv(uvX + uvWidth, uvY + uvHeight).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, 1, 0).endVertex();
		consumer.vertex(matrix2, x + width, y + height, z).color(red, green, blue, alpha).uv(uvX + uvWidth, uvY			).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, 1, 0).endVertex();
		consumer.vertex(matrix2, x		   , y + height, z).color(red, green, blue, alpha).uv(uvX			, uvY			).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, 1, 0).endVertex();
		
		consumer.vertex(matrix2, x		   , y + height, z + depth).color(red, green, blue, alpha).uv(uvX + uvWidth, uvY + uvHeight).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, -1, 0, 0).endVertex();
		consumer.vertex(matrix2, x		   , y + height, z).color(red, green, blue, alpha).uv(uvX			, uvY + uvHeight).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, -1, 0, 0).endVertex();
		consumer.vertex(matrix2, x		   , y		   , z).color(red, green, blue, alpha).uv(uvX			, uvY			).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, -1, 0, 0).endVertex();
		consumer.vertex(matrix2, x 	   , y		   , z + depth).color(red, green, blue, alpha).uv(uvX + uvWidth, uvY			).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, -1, 0, 0).endVertex();
		
		consumer.vertex(matrix2, x + width, y + height, z).color(red, green, blue, alpha).uv(uvX			, uvY + uvHeight).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 1, 0, 0).endVertex();
		consumer.vertex(matrix2, x + width, y + height, z + depth).color(red, green, blue, alpha).uv(uvX + uvWidth, uvY + uvHeight).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 1, 0, 0).endVertex();
		consumer.vertex(matrix2, x + width, y		   , z + depth).color(red, green, blue, alpha).uv(uvX + uvWidth, uvY			).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 1, 0, 0).endVertex();
		consumer.vertex(matrix2, x + width, y		   , z).color(red, green, blue, alpha).uv(uvX			, uvY			).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 1, 0, 0).endVertex();
	}
	
	public static void renderQuad(PoseStack matrix, float x, float y, float z, float width, float height, float uvX, float uvY, float uvWidth, float uvHeight) {
		BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
		Matrix4f matrix2 = matrix.last().pose();
		bufferbuilder.begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferbuilder.vertex(matrix2, x		   , y + height, z).uv(uvX			, uvY + uvHeight).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y + height, z).uv(uvX + uvWidth, uvY + uvHeight).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y		   , z).uv(uvX + uvWidth, uvY			).endVertex();
		bufferbuilder.vertex(matrix2, x		   , y		   , z).uv(uvX			, uvY			).endVertex();
		Tesselator.getInstance().end();
	}
	
	public static void renderQuad(PoseStack matrix, float x, float y, float z, float width, float height, float uvX, float uvY) {
		renderQuad(matrix, x, y, z, width, height, uvX, uvY, width, height);
	}
	
	public static void renderQuad(PoseStack matrix, float x, float y, float z, float width, float height, float[] color) {
		renderQuad(matrix, x, y, z, width, height, 
				color.length > 0 ? color[0] : 1f,
				color.length > 1 ? color[1] : 1f, 
	     	    color.length > 2 ? color[2] : 1f, 
	     	    color.length > 3 ? color[3] : 1f);
	}

	public static void renderCube(PoseStack matrix, float x, float y, float z, float width, float height, float depth, float uvX, float uvY) {
		renderCube(matrix, x, y, z, width, height, depth, uvX, uvY, width, height);
	}
	
	public static void renderQuad(PoseStack matrix, float x, float y, float z, float width, float height, float red, float green, float blue) {
		renderQuad(matrix, x, y, z, width, height, red, green, blue, 1d);
	}
	
	public static void renderQuad(PoseStack matrix, float x, float y, float z, float width, float height, float red, float green, float blue, double alpha) {
		RenderSystem.disableTexture();
		renderQuadColor(matrix, new float[] {x, y, z}, new float[] {width, height}, new float[] {red, green, blue, (float)alpha});
		RenderSystem.enableTexture();
	}
	
	public static void renderQuad(VertexConsumer consumer, PoseStack matrix, float[] position, float[] size, float[] uv, float[] color, int packedLight, int packedOverlay) {
		float x = position.length > 0 ? position[0] : 0f;
		float y = position.length > 1 ? position[1] : 0f;
		float z = position.length > 2 ? position[2] : 0f;

		float width = size.length > 0 ? size[0] : 1f;
		float height = size.length > 1 ? size[1] : 1f;

		float red = color.length > 0 ? color[0] : 1f;
		float green = color.length > 1 ? color[1] : 1f;
		float blue = color.length > 2 ? color[2] : 1f;
		float alpha = color.length > 3 ? color[3] : 1f;
		
		float uvX = uv.length > 0 ? uv[0] : 0f;
		float uvY = uv.length > 1 ? uv[1] : 0f;
		float uvWidth = uv.length > 2 ? uv[2] : 1;
		float uvHeight = uv.length > 3 ? uv[3] : 1;
		
		RenderUtils.setColor(1, 1, 1, 1);
		
		RenderSystem.enableTexture();
		Matrix4f matrix2 = matrix.last().pose();
		Matrix3f normal = matrix.last().normal();
		
		consumer.vertex(matrix2, x		   , y + height, z).color(red, green, blue, alpha).uv(uvX			, uvY + uvHeight).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, 0, -1).endVertex();
		consumer.vertex(matrix2, x + width, y + height, z).color(red, green, blue, alpha).uv(uvX + uvWidth, uvY + uvHeight).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, 0, -1).endVertex();
		consumer.vertex(matrix2, x + width, y		   , z).color(red, green, blue, alpha).uv(uvX + uvWidth, uvY			).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, 0, -1).endVertex();
		consumer.vertex(matrix2, x		   , y		   , z).color(red, green, blue, alpha).uv(uvX			, uvY			).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, 0, -1).endVertex();
		
		RenderSystem.setShaderColor(1, 1, 1, 1);
	}
	
	public static void renderQuad(PoseStack matrix, float[] position, float[] size2D, float[] color, float[] uv) {
		float x = position.length > 0 ? position[0] : 0f;
		float y = position.length > 1 ? position[1] : 0f;
		float z = position.length > 2 ? position[2] : 0f;

		float width = size2D.length > 0 ? size2D[0] : 1f;
		float height = size2D.length > 1 ? size2D[1] : 1f;

		float red = color.length > 0 ? color[0] : 1f;
		float green = color.length > 1 ? color[1] : 1f;
		float blue = color.length > 2 ? color[2] : 1f;
		float alpha = color.length > 3 ? color[3] : 1f;
		
		float uvX = uv.length > 0 ? uv[0] : 0f;
		float uvY = uv.length > 1 ? uv[1] : 0f;
		float uvWidth = uv.length > 2 ? uv[2] : width;
		float uvHeight = uv.length > 3 ? uv[3] : height;
		
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(red, green, blue, alpha);
		
		BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
		Matrix4f matrix2 = matrix.last().pose();
		bufferbuilder.begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferbuilder.vertex(matrix2, x		   , y + height, z).uv(uvX			, uvY + uvHeight).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y + height, z).uv(uvX + uvWidth, uvY + uvHeight).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y		   , z).uv(uvX + uvWidth, uvY			).endVertex();
		bufferbuilder.vertex(matrix2, x		   , y		   , z).uv(uvX			, uvY			).endVertex();
		Tesselator.getInstance().end();
		RenderSystem.setShaderColor(1, 1, 1, 1);
	}
	
	public static void renderQuadColor(PoseStack matrix, float[] position, float[] size2D, float[] color) {
		float x = position.length > 0 ? position[0] : 0f;
		float y = position.length > 1 ? position[1] : 0f;
		float z = position.length > 2 ? position[2] : 0f;

		float width = size2D.length > 0 ? size2D[0] : 1f;
		float height = size2D.length > 1 ? size2D[1] : 1f;

		float red = color.length > 0 ? color[0] : 1f;
		float green = color.length > 1 ? color[1] : 1f;
		float blue = color.length > 2 ? color[2] : 1f;
		float alpha = color.length > 3 ? color[3] : 1f;
		
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		RenderSystem.setShaderColor(red, green, blue, alpha);
		
		BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
		Matrix4f matrix2 = matrix.last().pose();
		bufferbuilder.begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
		bufferbuilder.vertex(matrix2, x		   , y + height, z).color(red, green, blue, alpha).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y + height, z).color(red, green, blue, alpha).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y		   , z).color(red, green, blue, alpha).endVertex();
		bufferbuilder.vertex(matrix2, x		   , y		   , z).color(red, green, blue, alpha).endVertex();
		Tesselator.getInstance().end();
		RenderSystem.setShaderColor(1, 1, 1, 1);
	}
	
	public static void renderCube(PoseStack matrix, float x, float y, float z, float width, float height, float depth, float red, float green, float blue) {
		renderCube(matrix, x, y, z, width, height, depth, red, green, blue, 1d);
	}
	
	public static void renderCube(PoseStack matrix, float x, float y, float z, float width, float height, float depth, float red, float green, float blue, double alpha) {
		RenderSystem.disableTexture();
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		RenderSystem.setShaderColor(red, green, blue, (float)alpha);
		BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
		Matrix4f matrix2 = matrix.last().pose();
		bufferbuilder.begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
		bufferbuilder.vertex(matrix2, x		   , y + height, z).color(red, green, blue, (float) alpha).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y + height, z).color(red, green, blue, (float) alpha).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y		   , z).color(red, green, blue, (float) alpha).endVertex();
		bufferbuilder.vertex(matrix2, x		   , y		   , z).color(red, green, blue, (float) alpha).endVertex();
		

		bufferbuilder.vertex(matrix2, x + width, y + height, z + depth).color(red, green, blue, (float) alpha).endVertex();
		bufferbuilder.vertex(matrix2, x		   , y + height, z + depth).color(red, green, blue, (float) alpha).endVertex();
		bufferbuilder.vertex(matrix2, x		   , y		   , z + depth).color(red, green, blue, (float) alpha).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y		   , z + depth).color(red, green, blue, (float) alpha).endVertex();

		bufferbuilder.vertex(matrix2, x + width, y 	   	   , z + depth).color(red, green, blue, (float) alpha).endVertex();
		bufferbuilder.vertex(matrix2, x		   , y 		   , z + depth).color(red, green, blue, (float) alpha).endVertex();
		bufferbuilder.vertex(matrix2, x		   , y		   , z).color(red, green, blue, (float) alpha).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y		   , z).color(red, green, blue, (float) alpha).endVertex();
		

		bufferbuilder.vertex(matrix2, x		   , y + height, z + depth).color(red, green, blue, (float) alpha).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y + height, z + depth).color(red, green, blue, (float) alpha).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y + height, z).color(red, green, blue, (float) alpha).endVertex();
		bufferbuilder.vertex(matrix2, x		   , y + height, z).color(red, green, blue, (float) alpha).endVertex();
		

		bufferbuilder.vertex(matrix2, x		   , y + height, z + depth).color(red, green, blue, (float) alpha).endVertex();
		bufferbuilder.vertex(matrix2, x		   , y + height, z).color(red, green, blue, (float) alpha).endVertex();
		bufferbuilder.vertex(matrix2, x		   , y		   , z).color(red, green, blue, (float) alpha).endVertex();
		bufferbuilder.vertex(matrix2, x 	   , y		   , z + depth).color(red, green, blue, (float) alpha).endVertex();
		

		bufferbuilder.vertex(matrix2, x + width, y + height, z).color(red, green, blue, (float) alpha).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y + height, z + depth).color(red, green, blue, (float) alpha).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y		   , z + depth).color(red, green, blue, (float) alpha).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y		   , z).color(red, green, blue, (float) alpha).endVertex();
		Tesselator.getInstance().end();
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.enableTexture();
	}
	
	public static void renderPyramide(PoseStack matrix, float x, float y, float z, float width, float height, float depth, float red, float green, float blue, double alpha) {
		RenderSystem.disableTexture();
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		RenderSystem.setShaderColor(red, green, blue, (float)alpha);
		BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
		Matrix4f matrix2 = matrix.last().pose();
		bufferbuilder.begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
		//Base
		bufferbuilder.vertex(matrix2, x	+ width, y, z+ depth).color(red, green, blue, (float) alpha).endVertex();
		bufferbuilder.vertex(matrix2, x, y, z + depth).color(red, green, blue, (float) alpha).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y, z).color(red, green, blue, (float) alpha).endVertex();

		bufferbuilder.vertex(matrix2, x, y , z+  depth).color(red, green, blue, (float) alpha).endVertex();
		bufferbuilder.vertex(matrix2, x, y, z).color(red, green, blue, (float) alpha).endVertex();
		bufferbuilder.vertex(matrix2, x	+ width, y, z).color(red, green, blue, (float) alpha).endVertex();
		
		//Sides
		bufferbuilder.vertex(matrix2, x, y, z).color(red, green, blue, (float) alpha).endVertex();
		bufferbuilder.vertex(matrix2, x + width/2f, y + height, z + depth/2f).color(red, green, blue, (float) alpha).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y, z).color(red, green, blue, (float) alpha).endVertex();
		
		bufferbuilder.vertex(matrix2, x, y, z + depth).color(red, green, blue, (float) alpha).endVertex();
		bufferbuilder.vertex(matrix2, x + width/2f, y + height, z + depth/2f).color(red, green, blue, (float) alpha).endVertex();
		bufferbuilder.vertex(matrix2, x, y, z).color(red, green, blue, (float) alpha).endVertex();
		
		bufferbuilder.vertex(matrix2, x + width, y, z).color(red, green, blue, (float) alpha).endVertex();
		bufferbuilder.vertex(matrix2, x + width/2f, y + height, z + depth/2f).color(red, green, blue, (float) alpha).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y, z + depth).color(red, green, blue, (float) alpha).endVertex();
		

		bufferbuilder.vertex(matrix2, x + width, y, z + depth).color(red, green, blue, (float) alpha).endVertex();
		bufferbuilder.vertex(matrix2, x + width/2f, y + height, z + depth/2f).color(red, green, blue, (float) alpha).endVertex();
		bufferbuilder.vertex(matrix2, x, y, z + depth).color(red, green, blue, (float) alpha).endVertex();
		
		Tesselator.getInstance().end();
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.enableTexture();
	}
	
	public static void renderPyramide(PoseStack matrix, float x, float y, float z, float width, float height, float depth, float uvX, float uvY, float uvWidth, float uvHeight) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
		Matrix4f matrix2 = matrix.last().pose();
		bufferbuilder.begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_TEX);
		//Base
		bufferbuilder.vertex(matrix2, x	+ width, y, z + depth).uv(uvX + uvWidth, uvY + uvHeight).endVertex();
		bufferbuilder.vertex(matrix2, x, y, z + depth).uv(uvX, uvY + uvHeight).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y, z).uv(uvX + uvWidth, uvY).endVertex();

		bufferbuilder.vertex(matrix2, x, y, z + depth).uv(uvX + uvWidth, uvY + uvHeight).endVertex();
		bufferbuilder.vertex(matrix2, x, y, z).uv(uvX, uvY).endVertex();
		bufferbuilder.vertex(matrix2, x	+ width, y, z).uv(uvX + uvWidth, uvY).endVertex();
		
		//Sides
		bufferbuilder.vertex(matrix2, x, y, z).uv(uvX, uvY).endVertex();
		bufferbuilder.vertex(matrix2, x + width/2f, y + height, z + depth/2f).uv(uvX + uvWidth/2f, uvY + uvHeight).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y, z).uv(uvX + uvWidth, uvY).endVertex();
		
		bufferbuilder.vertex(matrix2, x, y, z + depth).uv(uvX + uvWidth, uvY).endVertex();
		bufferbuilder.vertex(matrix2, x + width/2f, y + height, z + depth/2f).uv(uvX + uvWidth/2f, uvY + uvHeight).endVertex();
		bufferbuilder.vertex(matrix2, x, y, z).uv(uvX, uvY).endVertex();
		
		bufferbuilder.vertex(matrix2, x + width, y, z).uv(uvX, uvY).endVertex();
		bufferbuilder.vertex(matrix2, x + width/2f, y + height, z + depth/2f).uv(uvX + uvWidth/2f, uvY + uvHeight).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y, z + depth).uv(uvX + uvWidth, uvY).endVertex();
		
		bufferbuilder.vertex(matrix2, x + width, y, z + depth).uv(uvX + uvWidth, uvY).endVertex();
		bufferbuilder.vertex(matrix2, x + width/2f, y + height, z + depth/2f).uv(uvX + uvWidth/2f, uvY + uvHeight).endVertex();
		bufferbuilder.vertex(matrix2, x, y, z + depth).uv(uvX, uvY).endVertex();
		
		Tesselator.getInstance().end();
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
	}
	
	public static void renderPyramide(ResourceLocation texture, MultiBufferSource buffer, PoseStack matrix, float position[], float[] size, float[] uv, float[] color, int packedLight, int packedOverlay) {
		float x = position != null && position.length > 0 ? position[0] : 0;
		float y = position != null && position.length > 1 ? position[1] : 0;
		float z = position != null && position.length > 2 ? position[2] : 0;
		
		float width = size != null && size.length > 0 ? size[0] : 1;
		float height = size != null && size.length > 1 ? size[1] : 1;
		float depth = size != null && size.length > 2 ? size[2] : 1;
		
		float uvX = uv != null && uv.length > 0 ? uv[0] : 0;
		float uvY = uv != null && uv.length > 1 ? uv[1] : 0;
		float uvWidth = uv != null && uv.length > 2 ? uv[2] : 1;
		float uvHeight = uv != null && uv.length > 3 ? uv[3] : 1;
		
		float red = color != null && color.length > 0 ? color[0] : 1f;
		float green = color != null && color.length > 1 ? color[1] : 1f;
		float blue = color != null && color.length > 2 ? color[2] : 1f;
		float alpha = color != null && color.length > 3 ? color[3] : 1f;

//		RenderUtils.setColor(red, green, blue, alpha);
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.enableTexture();
//		BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
		Matrix4f matrix2 = matrix.last().pose();
		Matrix3f normal = matrix.last().normal();
		VertexConsumer consumer = buffer.getBuffer(triangularEntityTranslucent(texture, Mode.TRIANGLES));
//		bufferbuilder.begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
		float normalOffset = 0.707106f;
		
		//Base
		consumer.vertex(matrix2, x	+ width, y, z+ depth).color(red, green, blue, (float) alpha).uv(uvX + uvWidth, uvY + uvHeight).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, -1, 0).endVertex();
		consumer.vertex(matrix2, x, y, z + depth).color(red, green, blue, (float) alpha).uv(uvX, uvY + uvHeight).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, -1, 0).endVertex();
		consumer.vertex(matrix2, x + width, y, z).color(red, green, blue, (float) alpha).uv(uvX + uvWidth, uvY).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, -1, 0).endVertex();

		consumer.vertex(matrix2, x, y , z+  depth).color(red, green, blue, (float) alpha).uv(uvX, uvY + uvHeight).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, -1, 0).endVertex();
		consumer.vertex(matrix2, x, y, z).color(red, green, blue, (float) alpha).uv(uvX, uvY).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, -1, 0).endVertex();
		consumer.vertex(matrix2, x	+ width, y, z).color(red, green, blue, (float) alpha).uv(uvX + uvWidth, uvY).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, -1, 0).endVertex();
		
		//Sides
		consumer.vertex(matrix2, x, y, z).color(red, green, blue, (float) alpha).uv(uvX, uvY).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, normalOffset, -normalOffset).endVertex();
		consumer.vertex(matrix2, x + width/2f, y + height, z + depth/2f).color(red, green, blue, (float) alpha).uv(uvX + uvWidth/2f, uvY + uvHeight).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, normalOffset, -normalOffset).endVertex();
		consumer.vertex(matrix2, x + width, y, z).color(red, green, blue, (float) alpha).uv(uvX + uvWidth, uvY).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, normalOffset, -normalOffset).endVertex();
		
		consumer.vertex(matrix2, x, y, z + depth).color(red, green, blue, (float) alpha).uv(uvX + uvWidth, uvY).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, -normalOffset, normalOffset, 0).endVertex();
		consumer.vertex(matrix2, x + width/2f, y + height, z + depth/2f).color(red, green, blue, (float) alpha).uv(uvX + uvWidth/2f, uvY + uvHeight).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, -normalOffset, normalOffset, 0).endVertex();
		consumer.vertex(matrix2, x, y, z).color(red, green, blue, (float) alpha).uv(uvX, uvY).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, -normalOffset, normalOffset, 0).endVertex();
		
		consumer.vertex(matrix2, x + width, y, z).color(red, green, blue, (float) alpha).uv(uvX, uvY).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, normalOffset, normalOffset, 0).endVertex();
		consumer.vertex(matrix2, x + width/2f, y + height, z + depth/2f).color(red, green, blue, (float) alpha).uv(uvX + uvWidth/2f, uvY + uvHeight).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, normalOffset, normalOffset, 0).endVertex();
		consumer.vertex(matrix2, x + width, y, z + depth).color(red, green, blue, (float) alpha).uv(uvX + uvWidth, uvY).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, normalOffset, normalOffset, 0).endVertex();
		

		consumer.vertex(matrix2, x + width, y, z + depth).color(red, green, blue, (float) alpha).uv(uvX + uvWidth, uvY).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, normalOffset, normalOffset).endVertex();
		consumer.vertex(matrix2, x + width/2f, y + height, z + depth/2f).color(red, green, blue, (float) alpha).uv(uvX + uvWidth/2f, uvY + uvHeight).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, normalOffset, normalOffset).endVertex();
		consumer.vertex(matrix2, x, y, z + depth).color(red, green, blue, (float) alpha).uv(uvX, uvY).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, normalOffset, normalOffset).endVertex();
		
//		Tesselator.getInstance().end();
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.enableTexture();
	}
	
	public static void renderOutline(PoseStack matrix, AABB aabb, float[] color) {
		renderOutline(matrix, (float)aabb.minX, (float)aabb.minY, (float)aabb.minZ, (float)(aabb.maxX-aabb.minX), (float)(aabb.maxY-aabb.minY), (float)(aabb.maxZ-aabb.minZ), color);
	}
	
	public static void renderOutline(PoseStack matrix, float x, float y, float z, float width, float height, float depth, float[] color) {
		color = Utils.parseColor(color);
		
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		RenderSystem.setShaderColor(color[0], color[1], color[2], color[3]);
		
		BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
		Matrix4f matrix2 = matrix.last().pose();
		bufferbuilder.begin(Mode.DEBUG_LINE_STRIP, DefaultVertexFormat.POSITION_COLOR);
		
		bufferbuilder.vertex(matrix2, x		   , y + height, z).color(color[0], color[1], color[2], color[3]).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y + height, z).color(color[0], color[1], color[2], color[3]).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y		   , z).color(color[0], color[1], color[2], color[3]).endVertex();
		bufferbuilder.vertex(matrix2, x		   , y		   , z).color(color[0], color[1], color[2], color[3]).endVertex();
		
		Tesselator.getInstance().end();
		bufferbuilder.begin(Mode.DEBUG_LINE_STRIP, DefaultVertexFormat.POSITION_COLOR);

		bufferbuilder.vertex(matrix2, x + width, y + height, z + depth).color(color[0], color[1], color[2], color[3]).endVertex();
		bufferbuilder.vertex(matrix2, x		   , y + height, z + depth).color(color[0], color[1], color[2], color[3]).endVertex();
		bufferbuilder.vertex(matrix2, x		   , y		   , z + depth).color(color[0], color[1], color[2], color[3]).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y		   , z + depth).color(color[0], color[1], color[2], color[3]).endVertex();

		Tesselator.getInstance().end();
		bufferbuilder.begin(Mode.DEBUG_LINE_STRIP, DefaultVertexFormat.POSITION_COLOR);
		
		bufferbuilder.vertex(matrix2, x + width, y 	   	   , z + depth).color(color[0], color[1], color[2], color[3]).endVertex();
		bufferbuilder.vertex(matrix2, x		   , y 		   , z + depth).color(color[0], color[1], color[2], color[3]).endVertex();
		bufferbuilder.vertex(matrix2, x		   , y		   , z).color(color[0], color[1], color[2], color[3]).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y		   , z).color(color[0], color[1], color[2], color[3]).endVertex();

		Tesselator.getInstance().end();
		bufferbuilder.begin(Mode.DEBUG_LINE_STRIP, DefaultVertexFormat.POSITION_COLOR);

		bufferbuilder.vertex(matrix2, x		   , y + height, z + depth).color(color[0], color[1], color[2], color[3]).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y + height, z + depth).color(color[0], color[1], color[2], color[3]).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y + height, z).color(color[0], color[1], color[2], color[3]).endVertex();
		bufferbuilder.vertex(matrix2, x		   , y + height, z).color(color[0], color[1], color[2], color[3]).endVertex();
		
		Tesselator.getInstance().end();
		bufferbuilder.begin(Mode.DEBUG_LINE_STRIP, DefaultVertexFormat.POSITION_COLOR);

		bufferbuilder.vertex(matrix2, x		   , y + height, z + depth).color(color[0], color[1], color[2], color[3]).endVertex();
		bufferbuilder.vertex(matrix2, x		   , y + height, z).color(color[0], color[1], color[2], color[3]).endVertex();
		bufferbuilder.vertex(matrix2, x		   , y		   , z).color(color[0], color[1], color[2], color[3]).endVertex();
		bufferbuilder.vertex(matrix2, x 	   , y		   , z + depth).color(color[0], color[1], color[2], color[3]).endVertex();
		
		Tesselator.getInstance().end();
		bufferbuilder.begin(Mode.DEBUG_LINE_STRIP, DefaultVertexFormat.POSITION_COLOR);

		bufferbuilder.vertex(matrix2, x + width, y + height, z).color(color[0], color[1], color[2], color[3]).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y + height, z + depth).color(color[0], color[1], color[2], color[3]).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y		   , z + depth).color(color[0], color[1], color[2], color[3]).endVertex();
		bufferbuilder.vertex(matrix2, x + width, y		   , z).color(color[0], color[1], color[2], color[3]).endVertex();
		Tesselator.getInstance().end();
		RenderSystem.setShaderColor(1, 1, 1, 1);
	}
	
	public static void renderUvSphere(PoseStack matrix, int slices, int stacks, float[] color) {
		color = Utils.parseColor(color);
		
//		RenderSystem.setShader(GameRenderer::getPositionColorShader);
//		RenderSystem.setShaderColor(color[0], color[1], color[2], color[3]);
//		
//		BufferBuilder buffer = Tesselator.getInstance().getBuilder();
//		Matrix4f matrix2 = matrix.last().pose();
//		buffer.begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
		
//		//add top vertex
//		addVertex(matrix2, buffer, new Vector3f(0, 1, 0), color);
//		
//		//generate vertices per stack / slice
//		for (int i = 0; i < stacks; i++) {
//			double phi = Math.PI * (double)(i + 1) / (double)stacks;
//			for (int j = 0; j < slices; j++) {
//				double theta = 2.0 * Math.PI * (double)j / (double)slices;
//				double x = Math.sin(phi) * Math.cos(theta);
//				double y = Math.cos(phi);
//				double z = Math.sin(phi) * Math.sin(theta);
//				addVertex(matrix2, buffer, new Vector3f((float)x, (float)y, (float)z), color);
//			}
//		}
//		
//		//add bottom vertex
//		Vector3f v1 = new Vector3f(0, -1, 0);
//		addVertex(matrix2, buffer, v1, color);
//		
//		//add top / bottom triangles
//		for (int i = 0; i < slices; i++) {
//			float i0 = i + 1f;
//			float i1 = (i + 1) % slices + 1;
//			
//		}
		
//		Tesselator.getInstance().end();
//		RenderSystem.setShaderColor(1, 1, 1, 1);
	}
	
	public static void renderItemOnTop(PoseStack matrix, ItemStack stack, float x, float y, float z, float height, float scale,
			MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		matrix.pushPose();
		RenderSystem.enableDepthTest();
		
		{
			if(stack.getItem() instanceof BlockItem) {
				matrix.translate(x, y, z);
	          	matrix.translate(0.5, height, 0.5);
	          	matrix.translate(-0.5, -0.5, -0.5);
	          	matrix.translate(0.5, 0.685, 0.5);
			}else {
				matrix.translate(x, y, z);

				matrix.translate(0.5, height + 0.11, 0.5);
				matrix.translate(-0.5, -0.5, -0.5);
				matrix.mulPose(Vector3f.XP.rotationDegrees(90));
				matrix.translate(0.5, 0.5, -0.5 - 0.03125);
			}  	
			
			matrix.scale(scale, scale, scale);
			RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
			Minecraft.getInstance().getItemRenderer().renderStatic(stack, TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrix, bufferIn, 0);
		}
		matrix.popPose();
	}
	
	public static void renderItemOnTop(PoseStack matrix, ItemStack stack, float x, float y, float z, float scale,
			MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		renderItemOnTop(matrix, stack, x, y, z, 0, scale, bufferIn, combinedLightIn, combinedOverlayIn);
	}
	
	public static void renderItem(PoseStack matrix, ItemStack stack, float x, float y, float z, float scale,
			MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		renderItem(matrix, stack, x, y, z, 0, scale, bufferIn, combinedLightIn, combinedOverlayIn);
	}
	
	public static void renderItem(PoseStack matrix, ItemStack stack, float x, float y, float z, float height, float scale,
			MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		matrix.pushPose();
		RenderSystem.enableDepthTest();
		
		{
			if(stack.getItem() instanceof BlockItem) {
				matrix.translate(x, y, z);
	          	matrix.translate(0.5, height, 0.5);
	          	matrix.translate(-0.5, -0.5, -0.5);
	          	matrix.translate(0.5, 0.685, 0.5);
			}else {

				matrix.translate(x, y, z);
	          	matrix.translate(0.5, height, 0.5);
	          	matrix.translate(-0.5, -0.5, -0.5);
	          	matrix.translate(0.5, 0.685, 0.5);
	          	matrix.scale(0.5f, 0.5f, 0.5f);
			}  	
			
			matrix.scale(scale, scale, scale);
			RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
			Minecraft.getInstance().getItemRenderer().renderStatic(stack, TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrix, bufferIn, 0);
		}
		matrix.popPose();
	}
	
	public static void rotateMatrix(PoseStack matrix, Direction facing) {
		matrix.mulPose(Vector3f.YP.rotationDegrees((Math.abs(facing.step().y())-1) * (facing.toYRot() + 180f)));
	}
	
	public static void rotateMatrixForBlock(PoseStack matrix, Direction facing) {
		matrix.translate(0.5f, 0.5f, 0.5f);
		matrix.mulPose(Vector3f.YP.rotationDegrees((Math.abs(facing.step().y())-1) * (facing.toYRot() + 180f)));
		matrix.translate(-0.5f, -0.5f, -0.5f);
	}
	
	public static void rotateMatrix(PoseStack matrix, float angle) {
		matrix.mulPose(Vector3f.YP.rotationDegrees(angle));
	}
	
	public static void rotateMatrixForBlock(PoseStack matrix, float angle) {
		matrix.translate(0.5f, 0.5f, 0.5f);
		matrix.mulPose(Vector3f.YP.rotationDegrees(angle));
		matrix.translate(-0.5f, -0.5f, -0.5f);
	}
	
	public static void rotationLogic(PoseStack matrix, BlockEntity te) {
		Direction facing = Direction.NORTH;
		if(te.getBlockState().hasProperty(BlockRotatable.FACING))
			facing = te.getBlockState().getValue(BlockRotatable.FACING);
		Vec3 playerPos = Minecraft.getInstance().player.position()
				.add(new Vec3(0, Minecraft.getInstance().player.getEyeHeight(), 0));
		
		Vector3f pos = new Vector3f(te.getBlockPos().getX() + 0.5f, te.getBlockPos().getY() + 0.5f, te.getBlockPos().getZ() + 0.5f);
		Vec3 toPlayer = playerPos.subtract(pos.x(), pos.y(), pos.z());
		Vector3f right = new Vector3f();
		Vector3f normal = new Vector3f();
		
		if(facing.getAxis().isHorizontal()) {
			right = new Vector3f(-facing.getStepZ(), facing.getStepY(), facing.getStepX());
			normal = right.copy();
			normal.cross(facing.step());
			normal = new Vector3f(Math.abs(normal.x()), Math.abs(normal.y()), Math.abs(normal.z()));
			toPlayer = toPlayer.multiply(new Vec3(Math.abs(right.x() + normal.x()), Math.abs(right.y() + normal.y()), Math.abs(right.z() + normal.z())));
		}else {
			right = new Vector3f(-facing.getStepY(), facing.getStepX(), facing.getStepZ());
			normal = right.copy();
			normal.cross(facing.step());
			toPlayer = toPlayer.multiply(new Vec3(Math.abs(right.x() + normal.x()), Math.abs(right.y() + normal.y()), Math.abs(right.z() + normal.z())));
		}
		
		double rad = Math.acos(((toPlayer.x()*normal.x() + toPlayer.y()*normal.y() + toPlayer.z()*normal.z())/
				(Math.sqrt(toPlayer.x * toPlayer.x + toPlayer.y * toPlayer.y + toPlayer.z * toPlayer.z) * Math.sqrt(normal.x() * normal.x() + normal.y() * normal.y() + normal.z() * normal.z()))));
		
		float angle = (float) Math.toDegrees(rad);
		
		if(facing.getAxis().isHorizontal()) {
			if(facing == Direction.NORTH || facing == Direction.EAST) {
				if(toPlayer.z + toPlayer.x < 0)
					angle = -angle;
			}else
			if(toPlayer.z + toPlayer.x > 0)
				angle = -angle;
		}else {
			if(facing == Direction.UP) {
				if(toPlayer.x > 0)
				angle = -angle;
			}else {
				if(toPlayer.x < 0)
					angle = -angle;
			}
		}
		
		matrix.mulPose(Vector3f.YP.rotationDegrees(angle));
	}
	
	public static void renderString(PoseStack matrix, String msg, float x, float y, float z, float scale, 
			float[] RGBA, float shadowTint) {
		matrix.pushPose();
		matrix.translate(x, y, z);
		matrix.scale(scale, scale, scale);
		Minecraft.getInstance().font.draw(matrix, msg, 0, 0, Utils.getHexIntFromRGBA(RGBA));
		Minecraft.getInstance().font.drawShadow(matrix, msg, 0, 0, Utils.getHexIntFromRGB(shadowTint, shadowTint, shadowTint));
		matrix.popPose();
	}
	
	public static void renderString(PoseStack matrix, String msg, float x, float y, float z, float scale, 
			float[] RGBA, boolean drawShadow) {
		matrix.pushPose();
		matrix.translate(x, y, z);
		matrix.scale(scale, scale, scale);
		RenderSystem.setShaderColor(1, 1, 1, 1f);
		Minecraft.getInstance().font.draw(matrix, msg, 0, 0, Utils.getHexIntFromRGBA(RGBA));
		if(drawShadow)
			Minecraft.getInstance().font.drawShadow(matrix, msg, 0, 0, Utils.getHexIntFromRGB(0.3f, 0.3f, 0.3f));
//		GL11.glDisable(GL11.GL_ALPHA_TEST);
		matrix.popPose();
	}
	
	public static void renderEntity(PoseStack matrix, Entity entity, float x, float y, float z, float scale, 
			float[] RGBA, MultiBufferSource bufferIn) {
		Minecraft.getInstance().getEntityRenderDispatcher().render(entity, 0, 0, 0, 0, 0.128f, matrix, 
				bufferIn, Utils.getHexIntFromRGBA(RGBA));
	}
	
	public static void applyPlayerTexture(String username) {
		applyPlayerTexture(Utils.getProfile(username));
	}
	
	public static ResourceLocation getPlayerTexture(String username) {
		return getPlayerTexture(Utils.getProfile(username));
	}
	
	public static ResourceLocation getPlayerTexture(GameProfile profile) {
		ResourceLocation resourcelocation = DefaultPlayerSkin.getDefaultSkin();
		if(profile != null) {
            Map<Type, MinecraftProfileTexture> map = Minecraft.getInstance().getSkinManager().getInsecureSkinInformation(profile);

            if (map.containsKey(Type.SKIN))
            {
                resourcelocation = Minecraft.getInstance().getSkinManager().registerTexture(map.get(Type.SKIN), Type.SKIN);
            }
		}
		return resourcelocation;
	}
	
	public static void applyPlayerTexture(GameProfile profile) {
		ResourceLocation resourcelocation = DefaultPlayerSkin.getDefaultSkin();
		if(profile != null) {
            Map<Type, MinecraftProfileTexture> map = Minecraft.getInstance().getSkinManager().getInsecureSkinInformation(profile);

            if (map.containsKey(Type.SKIN))
            {
                resourcelocation = Minecraft.getInstance().getSkinManager().registerTexture(map.get(Type.SKIN), Type.SKIN);
            }
		}
        Minecraft.getInstance().getTextureManager().bindForSetup(resourcelocation);
	}
	
	public static Context getEntityRenderContext() {
		return new EntityRendererProvider.Context(Minecraft.getInstance().getEntityRenderDispatcher(), Minecraft.getInstance().getItemRenderer(), 
				Minecraft.getInstance().getBlockRenderer(), Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer(), Minecraft.getInstance().getResourceManager(), Minecraft.getInstance().getEntityModels(), Minecraft.getInstance().font);
	}
	
	public static void renderPlayerGameProfile(PoseStack matrix, String username, float x, float y, float z, float scale, 
			float[] RGBA, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn, boolean isChild) {
		renderPlayerGameProfile(matrix, Utils.getProfile(username), x, y, z, scale, RGBA, bufferIn, combinedLightIn, combinedOverlayIn, isChild);
	}
	
	public static void renderPlayerGameProfile(PoseStack matrix, GameProfile profile, float x, float y, float z, float scale, 
			float[] RGBA, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn , boolean isChild) {
		try {
			Context cont = getEntityRenderContext();
			PlayerModel<Player> model = new PlayerModel<Player>(cont.bakeLayer(ModelLayers.PLAYER), isChild);
			model.young = isChild;
			
			matrix.translate(x, y, z);
			matrix.mulPose(Vector3f.ZP.rotationDegrees(180));
			matrix.scale(scale, scale, scale);
			RenderSystem.enableTexture();
			matrix.pushPose();
			VertexConsumer buffer = bufferIn.getBuffer(RenderType.entityTranslucentCull(getPlayerTexture(profile)));
			model.renderToBuffer(matrix, buffer, combinedLightIn, combinedOverlayIn, 
					RGBA.length > 0 ? RGBA[0] : 1.0f, RGBA.length > 1 ? RGBA[1] : 1.0f, RGBA.length > 2 ? RGBA[2] : 1.0f, RGBA.length > 3 ? RGBA[3] : 1.0f);
			matrix.popPose();
		}catch(Exception ex) { ex.printStackTrace(); }
	}
	
	public static void renderPlayer(PoseStack matrix, Player player, float x, float y, float z, float scale, 
			float[] RGBA, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn, boolean isChild) {
		if(player == null)
			return;
		
		Minecraft.getInstance().getEntityRenderDispatcher().setRenderShadow(false);
		Minecraft.getInstance().getEntityRenderDispatcher().render(player, x, y, z, 0.0f, 0f, matrix, bufferIn, 
				combinedLightIn);
		Minecraft.getInstance().getEntityRenderDispatcher().setRenderShadow(true);
	}
	
	public static void renderString(PoseStack matrix, String msg, float x, float y, float z, float scale, boolean drawShadow) {
		renderString(matrix, msg, x, y, z, scale, new float[] {}, drawShadow);
	}
	
	public static void renderString(PoseStack matrix, String msg, float x, float y, float z, float scale) {
		renderString(matrix, msg, x, y, z, scale, false);
	}
	
	public static void renderString(PoseStack matrix, String msg, float x, float y, float z, float scale,
			float red, float green, float blue, boolean drawShadow) {
		renderString(matrix, msg, x, y, z, scale, new float[] {red, green, blue}, drawShadow);
	}
	
	public static void renderString(PoseStack matrix, String msg, float x, float y, float z, float scale, float shadowTint) {
		renderString(matrix, msg, x, y, z, scale, new float[] {}, shadowTint);
	}
	
	public static void renderString(PoseStack matrix, String msg, float x, float y, float z, 
			float red, float green, float blue, float shadowTint) {
		renderString(matrix, msg, x, y, z, 1.0f, new float[] {red, green, blue}, shadowTint);
	}
	
	public static void renderString(PoseStack matrix, String msg, float x, float y, float z, 
			float[] RGBA, float shadowTint) {
		renderString(matrix, msg, x, y, z, 1.0f, RGBA, shadowTint);
	}
	
	public static void renderString(PoseStack matrix, String msg, float x, float y, float z) {
		renderString(matrix, msg, x, y, z, 1.0f);
	}
	
	public static void renderString(PoseStack matrix, String msg, float x, float y, float z,
			float red, float green, float blue, boolean drawShadow) {
		renderString(matrix, msg, x, y, z, 1.0f, new float[] {red, green, blue}, drawShadow);
	}
	
	public static void renderString(PoseStack matrix, String msg, float x, float y, float z,
			float[] RGBA, boolean drawShadow) {
		renderString(matrix, msg, x, y, z, 1.0f, RGBA, drawShadow);
	}
	
	public static void renderString(PoseStack matrix, String msg, float x, float y, float z,
			float red, float green, float blue) {
		renderString(matrix, msg, x, y, z, red, green, blue, false);
	}
	
	public static void renderString(PoseStack matrix, String msg, float x, float y, float z,
			float[] RGBA) {
		RGBA = Utils.parseColor(RGBA);
		renderString(matrix, msg, x, y, z, RGBA, false);
	}
	
	public static void renderCenteredStringWithinLine(PoseStack pose, String msg, float x, float y, float z, float lineWith, int color) {
		Font font = Minecraft.getInstance().font;
		int textWidth = font.width(msg);
		float factor = Math.min(1, lineWith/textWidth);
		pose.translate(lineWith/2f - (factor * textWidth/2f), 0, 0);
		pose.translate(x, y, z);
		pose.scale(factor, factor, factor);
		font.draw(pose, msg, 0, 0, color);
	}
	
	public static void renderCenteredStringWithinBox(PoseStack pose, String msg, float x, float y, float z, float boxWith, float boxHeight, int color) {
		Font font = Minecraft.getInstance().font;
		int textWidth = font.width(msg);
		float factor = Math.min(1, Math.min(boxWith/textWidth, boxHeight/font.lineHeight));
		pose.translate(boxWith/2f - (factor * textWidth/2f), boxHeight/2f - (factor * font.lineHeight/2f), 0);
		pose.translate(x, y, z);
		pose.scale(factor, factor, factor);
		font.draw(pose, msg, 0, 0, color);
	}
	
	public static void renderStringWithinLine(PoseStack pose, String msg, float x, float y, float z, float lineWith, int color) {
		pose.pushPose();
		float factor = Math.min(1, lineWith/ Minecraft.getInstance().font.width(msg));
		pose.translate(x, y + Minecraft.getInstance().font.lineHeight/2, 0);
		pose.translate(x, y, z);
		pose.scale(factor, factor, factor);
		Minecraft.getInstance().font.draw(pose, msg, 0, 0, color);
		pose.popPose();
	}
	
	public static void renderLighting(PoseStack matrix, float[] RGB, float peel, float height, float bottomOffset) {
		RGB = Utils.parseColor(RGB);
		for (int i = 0; i < 4; i++) {
			matrix.pushPose();
			matrix.translate(0.5, 0, 0.5);
			RenderUtils.rotateMatrix(matrix, 90f * i);
			matrix.translate(-0.5, 0, -0.5);
			
			RenderSystem.disableTexture();
			RenderSystem.enableDepthTest();
			RenderSystem.disableCull();
			RenderSystem.enableBlend();
			RenderSystem.depthMask(true);
			
			RenderSystem.setShader(GameRenderer::getRendertypeLightningShader);
			RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
			
			matrix.translate(0, 0.5, 0.7);
			VertexConsumer buffer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(LegacyLaserRenderType.LASER_PROJECTOR_RENDER);
			Matrix4f matrix2 = matrix.last().pose();
			
			buffer.vertex(matrix2, 0 - (peel-0.3f), height, peel).color(RGB[0], RGB[1], RGB[2], 0f).endVertex();
			buffer.vertex(matrix2, 1 + (peel-0.3f), height, peel).color(RGB[0], RGB[1], RGB[2], 0f).endVertex();
			buffer.vertex(matrix2, 1 - bottomOffset, 0, 0f)		 .color(RGB[0], RGB[1], RGB[2], 1f).endVertex();
			buffer.vertex(matrix2, bottomOffset, 0, 0f)			 .color(RGB[0], RGB[1], RGB[2], 1f).endVertex();
			
			RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

			RenderSystem.enableTexture();
			RenderSystem.enableCull();
			RenderSystem.disableBlend();
			
			matrix.popPose();
		}
	}
	
	public static void renderLine(PoseStack matrix, float[] position, Vector3f direction, float[] color) {
		RenderSystem.disableTexture();
		matrix.pushPose();
		color = Utils.parseColor(color);
		float x = position.length > 0 ? position[0] : 0f;
		float y = position.length > 1 ? position[1] : 0f;
		float z = position.length > 2 ? position[2] : 0f;
		
		matrix.translate(x, y, z);
		
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		RenderSystem.setShaderColor(color[0], color[1], color[2], 1);

		Matrix4f matrix2 = matrix.last().pose();
		
		BufferBuilder builder = Tesselator.getInstance().getBuilder();
		builder.begin(Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);

		builder.vertex(matrix2, 0, 0, 0).color(color[0], color[1], color[2], color[3]).endVertex();
		builder.vertex(matrix2, direction.x(), direction.y(), direction.z()).color(color[0], color[1], color[2], color[3]).endVertex();

		Tesselator.getInstance().end();
		RenderSystem.enableTexture();
		matrix.popPose();
	}
	
	public static void renderGrid(PoseStack matrix, float stepSize, float x, float y, float z, float width, float height, float depth, float[] color) {
		renderGrid(matrix, stepSize, new float[] {x, y, z}, new float[] {width, height, depth}, color);
	}
	
	public static void renderGrid(PoseStack matrix, float stepSize, float[] position, float[] size, float[] color) {
		RenderSystem.disableTexture();
		color = Utils.parseColor(color);
		matrix.pushPose();
		float x = position.length > 0 ? position[0] : 0f;
		float y = position.length > 1 ? position[1] : 0f;
		float z = position.length > 2 ? position[2] : 0f;

		float width = size.length > 0 ? size[0] : 1f;
		float height = size.length > 1 ? size[1] : 1f;
//		float depth = size.length > 2 ? size[2] : 1f;

		float red = color.length > 0 ? color[0] : 1f;
		float green = color.length > 1 ? color[1] : 1f;
		float blue = color.length > 2 ? color[2] : 1f;
		float alpha = color.length > 3 ? color[3] : 1f;
		
		matrix.translate(x, y, z);
		
		Matrix4f matrix2 = matrix.last().pose();
		int Xcount = (int) Math.ceil(height / stepSize);
		int Ycount = (int) Math.ceil(width / stepSize);
//		int Zcount = (int) Math.ceil(depth / stepSize);
		
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		RenderSystem.setShaderColor(color[0], color[1], color[2], 1);
		
		BufferBuilder builder = Tesselator.getInstance().getBuilder();
		builder.begin(Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);
		
		for (int i = 0; i < Xcount; i++) {
			builder.vertex(matrix2, 0, i * stepSize, 0).color(red, green, blue, alpha).endVertex();
			builder.vertex(matrix2, width, i * stepSize, 0).color(red, green, blue, alpha).endVertex();
		}
		
		for (int i = 0; i < Ycount; i++) {
			builder.vertex(matrix2, i * stepSize, 0, 0).color(red, green, blue, alpha).endVertex();
			builder.vertex(matrix2, i * stepSize, height, 0).color(red, green, blue, alpha).endVertex();
		}
		
		Tesselator.getInstance().end();
		matrix.popPose();
		RenderSystem.enableTexture();
	}
	
	public static BakedModel createBakedModel(ResourceLocation rl) {
		Minecraft mc = Minecraft.getInstance();
		return mc.getModelManager().getModel(rl);
	}
	   
	public static void renderStatic(ItemStack stack, ItemTransforms.TransformType transform, int combinedLight, int combinedOverlay, PoseStack pose, MultiBufferSource buffer, BakedModel model) {
        Minecraft.getInstance().getItemRenderer().render(stack, transform, false, pose, buffer, combinedLight, combinedOverlay, model);
	}
	
	static class TriangularEntityTranslucentType extends RenderType {

		public static final BiFunction<ResourceLocation, VertexFormat.Mode, RenderType> TRIANGULAR_ENTITY_TRANSLUCENT = Util.memoize((p_173227_, p_173228_) -> {
		      RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder().setShaderState(new RenderStateShard
		    		  .ShaderStateShard(GameRenderer::getRendertypeEntityTranslucentCullShader))
		    		  .setTextureState(new RenderStateShard.TextureStateShard(p_173227_, false, false))
		    		  .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
		    		  .setCullState(NO_CULL)
		    		  .setLightmapState(LIGHTMAP)
		    		  .setOverlayState(OVERLAY)
		    		  .createCompositeState(true);
		      return create("triangular_entity_translucent", DefaultVertexFormat.NEW_ENTITY, p_173228_, 256, true, true, rendertype$compositestate);
		   });
		
		public TriangularEntityTranslucentType(String p_173178_, VertexFormat p_173179_, Mode p_173180_, int p_173181_,
				boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
			super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
		}
		
	}
	
/*
 * 	===========================================================================================
 * 										Advanced OpenGL
 * 	===========================================================================================
 */
	
	public static float[] screenCoordToUV(float u, float v) {
		Window window = Minecraft.getInstance().getWindow();
		Screen scr = Minecraft.getInstance().screen;
		float f = (float)window.getWidth()/(float)scr.width;
		return new float[] {(float)u*f/(float)window.getWidth(), (float)v*f/(float)window.getHeight()};
	}
	
	public static int createFrameBuffer() {
		int frameBuffer = GL30.glGenFramebuffers();
		return frameBuffer;
	}
	
	public static void bindFrameBuffer(int fbo) {
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fbo);
	}
	
	/**
	 * @return any of:</br> <b>
	 * GL_FRAMEBUFFER_COMPLETE</br>
	 * GL_FRAMEBUFFER_UNDEFINED</br>
	 * GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT</br>
	 * GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT</br>
	 * GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER</br>
	 * GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER</br>
	 * GL_FRAMEBUFFER_UNSUPPORTED</br>
	 * GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE</br>
	 * GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS</br></b>
	 */
	public static int checkFrameBufferStatus() {
		return GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER);
	}
	
	public static void resetToDefaultFrameBuffer() {
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);   
	}
	
	public static void resetToDefaultFrameBuffer(int fbo) {
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0); 
		GL30.glDeleteFramebuffers(fbo); 
	}
	
	/**
	 * 
	 * @param target the framebuffer type we're targeting (draw, read or both).
	 * @param attachment the type of attachment we're going to attach. Right now we're attaching a color attachment. 
	 * @param textarget the type of the texture you want to attach.
	 * @param texture the actual texture to attach.
	 * @param level he mipmap level.
	 */
	public static void attachFrameBufferTexture2D(int target, int attachment, int textarget, int texture, int level) {
		GL30.glFramebufferTexture2D(target, attachment, textarget, textarget, level);
	}
	
	public static void attachTextureToFrameBuffer(int texture) {
	    glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texture, 0);
	}
	
	public static int attachNewTextureToCurrentFrameBuffer(int width, int height) {
		int texture = createTextureAttachment(width, height);
		attachFrameBufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL30.GL_TEXTURE_2D, texture, 0);
		return texture;
	}
	
	public static void attachDepthTexToFrameBuffer(int depthBuffer) {
		attachFrameBufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_DEPTH_COMPONENT, depthBuffer, 0);
	}
	
	public static void attachStencilTexToFrameBuffer(int stencil) {
		attachFrameBufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_STENCIL_ATTACHMENT, GL30.GL_STENCIL_INDEX, stencil, 0);
	}
	
	public static void setCurrentTextureAsDepthAndStencil(int width, int height) {
		GL30.glTexImage2D(GL30.GL_TEXTURE_2D, 0, GL30.GL_DEPTH24_STENCIL8, width, height, 0, GL30.GL_DEPTH_STENCIL, GL30.GL_UNSIGNED_INT_24_8, (ByteBuffer)null);
	}
	
	public static void attachNewDepthAndStencilAttachment(int width, int height) {
		int texture = createTextureAttachment(width, height);
		setCurrentTextureAsDepthAndStencil(width, height);
		attachDepthAndStencilAttachment(texture);
	}
	
	public static void attachDepthAndStencilAttachment(int texture) {
		attachFrameBufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_STENCIL_ATTACHMENT, GL30.GL_TEXTURE_2D, texture, 0);
	}
	
	public static int createTextureAttachment() {
		return createTextureAttachment(800, 600);
	}
	
	public static int createTextureAttachment(@NativeType("GLsizei") int width, @NativeType("GLsizei") int height) {
		int texture = glGenTextures();
		glBindTexture(GL30.GL_TEXTURE_2D, texture);
		glBindTexture(GL_TEXTURE_2D, texture);
	    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, (ByteBuffer)null);
	    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		return texture;
	}
	
	public static int createRenderBufferObject() {
		return GL30.glGenRenderbuffers();
	}
	
	public static void bindRenderbuffer(int rbo) {
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, rbo);
	}
	
	public static int createDepthAndStencilRenderbuffer(int width, int height) {
		int rbo = createRenderBufferObject();
		bindRenderbuffer(rbo);
		setStoredRboToDepthAndStencil(width, height);
		return rbo;
	}
	
	public static int attachNewDepthAndStencilRenderBuffer(int width, int height) {
		int rbo = createDepthAndStencilRenderbuffer(width, height);
		attachRenderBuffer(rbo);
		return rbo;
	}
	
	public static void attachRenderBuffer(int rbo) {
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_STENCIL_ATTACHMENT, GL30.GL_RENDERBUFFER, rbo);
	}
	
	public static void setStoredRboToDepthAndStencil(int width, int height) {
		GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL30.GL_DEPTH24_STENCIL8, width, height);
	}
	
	public static void bindTexture(int texture) {
		GL30.glBindTexture(GL30.GL_TEXTURE_2D, texture);
	}
	
	public static void bindDefaultTexture() {
		bindTexture(0);
	}
	
	public static void bindDefaultFrameBuffer() {
		bindFrameBuffer(1);
	}
	
	public static int[] setupRenderToTexture() {
		Window win = Minecraft.getInstance().getWindow();
		return setupRenderToTexture(win.getWidth(), win.getHeight());
	}
	
	/**
	 * 
	 * @param width
	 * @param height
	 * @return a array of ints -> int[3] {fbo, texture, rbo}
	 */
	public static int[] setupRenderToTexture(int width, int height) {
		int fbo = createFrameBuffer();
		bindFrameBuffer(fbo);
		
		int texture = createTextureAttachment(width, height);
		attachTextureToFrameBuffer(texture);
		
		int rbo = createDepthAndStencilRenderbuffer(width, height);
		attachRenderBuffer(rbo);
		
		if(checkFrameBufferStatus() != GL30.GL_FRAMEBUFFER_COMPLETE)
			System.err.println("ERROR::FRAMEBUFFER:: Framebuffer is not complete!");
		bindRenderbuffer(0);
		return new int[] {fbo, texture, rbo};
	}
	
	public static void stopRenderToTexture() {
		bindDefaultFrameBuffer();
		bindDefaultTexture();
	}
	
	public static void destoryFBO(int[] fbo) {
		if(fbo.length > 0)
			GL30.glDeleteFramebuffers(fbo[0]);
		if(fbo.length > 1)
			GL30.glDeleteTextures(fbo[1]);
		if(fbo.length > 2)
			GL30.glDeleteRenderbuffers(fbo[2]);
	}
	
	public static void destoryFBO(int fbo) {
		GL30.glDeleteFramebuffers(fbo);
	}
	
	// https://www.curseforge.com/minecraft/mc-mods/stencil-shenanigans/files/2931876
	public static void setupStencil() {		
		RenderSystem.clearStencil(0xFF);
		
		EnableStencils();
		setStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);
		clearStencilBuffer();
		setStencilFunc(GL11.GL_ALWAYS, 1, 0xFF); // all fragments should pass the stencil test
		setStencilMask(0xFF);
		//Draw the Things that need to be replaced!
	}
	
	// https://www.curseforge.com/minecraft/mc-mods/stencil-shenanigans/files/2931876
		public static void setupStencil(boolean clearStencil) {	
			if(clearStencil)
				RenderSystem.clearStencil(0xFF);
			
			EnableStencils();
			setStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);
			clearStencilBuffer();
			setStencilFunc(GL11.GL_ALWAYS, 1, 0xFF); // all fragments should pass the stencil test
			setStencilMask(0xFF);
			//Draw the Things that need to be replaced!
		}
	
	// https://www.curseforge.com/minecraft/mc-mods/stencil-shenanigans/files/2931876
		public static void setupStencilInverse() {		
			RenderSystem.clearStencil(0xFF);
			
			EnableStencils();
			setStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);
			clearStencilBuffer();
			setStencilFunc(GL11.GL_ALWAYS, 0, 0xFF); // all fragments should pass the stencil test
			setStencilMask(0xFF);
			//Draw the Things that need to be replaced!
		}
	
	//TODO render outside (with stencilFunc GL_NOTEQUAL)
	public static void setupRenderInside() {
		setStencilFunc(GL11.GL_EQUAL, 1, 0xFF);
		setStencilMask(0x00); // disable writing to the stencil buffer (because we only want to read the stencil buffer from this point, not write to it anymore)
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		//Render whatever is inside of the stencil/window! (portal??)
	}
	
	public static void setupRenderOutside() {
		setStencilFunc(GL11.GL_NOTEQUAL, 1, 0xFF);
		setStencilMask(0x00); // disable writing to the stencil buffer (because we only want to read the stencil buffer from this point, not write to it anymore)
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		//Render whatever is inside of the stencil/window! (portal??)
	}
	
	public static void disableStencil() {
		setStencilMask(0xFF);
		setStencilFunc(GL11.GL_ALWAYS, 0, 0xFF);  
		resetStencilFunctions();
		disableStencils();
		RenderSystem.enableDepthTest();
		RenderSystem.depthMask(true);
//		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	
	public static void disableStencil(boolean clearStencil) {
		setStencilMask(0xFF);
		setStencilFunc(GL11.GL_ALWAYS, 0, 0xFF);  
		resetStencilFunctions();
		disableStencils(clearStencil);
		RenderSystem.enableDepthTest();
		RenderSystem.depthMask(true);
//		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	
	/**
	 * Stencil Info: https://learnopengl.com/Advanced-OpenGL/Stencil-testing
	 */
	public static void EnableStencils() {
		GL20.glEnable(GL20.GL_STENCIL_TEST);
		if(!Minecraft.getInstance().getMainRenderTarget().isStencilEnabled()) {
			Minecraft.getInstance().getMainRenderTarget().enableStencil();
		}
		clearStencilBuffer();
	}
	
	/**
	 * Stencil Info: https://learnopengl.com/Advanced-OpenGL/Stencil-testing
	 */
	private static void EnableStencils(boolean clearStencil) {
		GL20.glEnable(GL20.GL_STENCIL_TEST);
		if(!Minecraft.getInstance().getMainRenderTarget().isStencilEnabled()) {
			Minecraft.getInstance().getMainRenderTarget().enableStencil();
		}
		if(clearStencil)
			clearStencilBuffer();
	}
	
	
	public static void clearStencilBuffer() {
		RenderSystem.clear(GL11.GL_STENCIL_BUFFER_BIT, false);
//		GL20.glClear(GL20.GL_STENCIL_BUFFER_BIT);
	}
	
	private static void disableStencils() {
		clearStencilBuffer();
		GL20.glDisable(GL20.GL_STENCIL_TEST);
	}
	
	private static void disableStencils(boolean clearStencil) {
		if(clearStencil)
			clearStencilBuffer();
		GL20.glDisable(GL20.GL_STENCIL_TEST);
	}
	
	/**
	 * 0xFF -> each bit is written to the stencil buffer as is,</br>
	 * 0x00 -> each bit ends up as 0 in the stencil buffer (disabling writes)
	 * @param value
	 */
	public static void setStencilMask(int value) {
		RenderSystem.stencilMask(value);
//		GL20.glStencilMask(value);
	}
	
	/**
	 * @param func	-> GL_NEVER, GL_LESS, GL_LEQUAL, GL_GREATER, GL_GEQUAL, GL_EQUAL, GL_NOTEQUAL, GL_ALWAYS
	 * @param ref	-> the value to compare to (if 1 and the func is GL_EQUAL only parts of the buffer that contain exact 1 will pass)
	 * @param mask	-> the mask to compare (also the applied stencil buffer?) (0xFF)
	 */
	public static void setStencilFunc(int func, int ref, int mask) {
		RenderSystem.stencilFunc(func, ref, mask);
//		GL20.glStencilFunc(func, ref, mask);
	}
	
	/**
	 *	'SetStencilOperation'</br></br>
	 *  contains three options of which we can specify for each option what action to take:</br><i>
	 *  -sfail: action to take if the stencil test fails.</br>
	 *  -dpfail: action to take if the stencil test passes, but the depth test fails.</br>
	 *  -dppass: action to take if both the stencil and the depth test pass.</i>
	 *  </br></br>
	 *  Then for each of the options you can take any of the following actions:</br>
	 * 
	 * <b>GL_KEEP</b>		The currently stored stencil value is kept.</br>
	 * <b>GL_ZERO</b>		The stencil value is set to 0.</br>
	 * <b>GL_REPLACE</b>	The stencil value is replaced with the reference value set with glStencilFunc.</br>
	 * <b>GL_INCR</b>		The stencil value is increased by 1 if it is lower than the maximum value.</br>
	 * <b>GL_INCR_WRAP</b>	Same as GL_INCR, but wraps it back to 0 as soon as the maximum value is exceeded.</br>
	 * <b>GL_DECR</b>	The stencil value is decreased by 1 if it is higher than the minimum value.</br>
	 * <b>GL_DECR_WRAP</b>	Same as GL_DECR, but wraps it to the maximum value if it ends up lower than 0.</br>
	 * <b>GL_INVERT</b>	Bitwise inverts the current stencil buffer value.</br>
	 * @param sfail
	 * @param dpfail
	 * @param dppass
	 */
	public static void setStencilOp(int sfail, int dpfail, int dppass) {
		RenderSystem.stencilOp(sfail, dpfail, dppass);
//		GL20.glStencilOp(sfail, dpfail, dppass);
	}
	
	public static void resetStencilFunctions() {
		setStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
	}
}
