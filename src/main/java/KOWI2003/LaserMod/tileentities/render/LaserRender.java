package KOWI2003.LaserMod.tileentities.render;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Axis;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.blocks.BlockLaser;
import KOWI2003.LaserMod.blocks.BlockRotatable;
import KOWI2003.LaserMod.init.ModItems;
import KOWI2003.LaserMod.tileentities.TileEntityLaser;
import KOWI2003.LaserMod.tileentities.TileEntityLaser.MODE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class LaserRender implements BlockEntityRenderer<TileEntityLaser> {
	private final BlockEntityRendererProvider.Context context;
	
	private float uMin, vMin = 0;
	
	public LaserRender(BlockEntityRendererProvider.Context context) {
		this.context = context;
	}
	
	@Override
	public boolean shouldRenderOffScreen(TileEntityLaser te) {
		return te.active && (te.getDirection() == Direction.NORTH || te.getDirection() == Direction.WEST || te.distance > 9);
	}
	
	@Override
	public void render(TileEntityLaser te, float partialTicks, PoseStack matrix, MultiBufferSource bufferIn,
		int combinedLightIn, int combinedOverlayIn) {
		if(te.active) {
			RenderSystem.enableDepthTest();
			RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
//			RenderSystem.color(1.0f, 1.0f, 1.0f);
				double distance = te.distance;
				float r = te.red;
				float g = te.green;
				float b = te.blue;
				float a = 0.4f;
				
				float thickness = 0.05f;
				
				float ll = 0.5f - thickness/2f;
		        float lr = 0.5f + thickness/2f;
		        
		        Direction facing = te.getBlockState().getValue(BlockLaser.FACING);
		        
		        if(te.mode == MODE.NORMAL || (te.mode == MODE.INVISIBLE && canBeSeen())) {
			        matrix.pushPose();
			        
					VertexConsumer buffer = bufferIn.getBuffer(LaserRenderType.LASER_RENDER);
//					VertexConsumer buffer = bufferIn.getBuffer(LaserRenderType.getDebugRenderType2());
					
					Matrix4f matrix2 = matrix.last().pose();
						
						matrix.translate(.5f, .5f, .5f);
					
						matrix.mulPose(Axis.XP.rotationDegrees(facing.step().y() * 90f - 90f));
						matrix.mulPose(Axis.ZP.rotationDegrees((Math.abs(facing.step().y())-1) * (facing.toYRot() + 180f)));
						
						matrix.translate(-.5, -.5, 0); matrix2 = matrix.last().pose();
						
						RenderSystem.setShaderColor(1, 1, 1, 1);
						
						buffer.vertex(matrix2, ll, 0F, 0F).color(r, g, b, a).uv(0, 0).endVertex();
		    			buffer.vertex(matrix2, ll, (float) distance, 0F).color(r, g, b, a).uv(0, 0).endVertex();
		    			buffer.vertex(matrix2, lr, (float) distance, 0F).color(r, g, b, a).uv(0, 0).endVertex();
		    			buffer.vertex(matrix2, lr, 0, 0F).color(r, g, b, a).uv(0, 0).endVertex();
						
				        matrix.translate(.5, 0, -0.5f); matrix2 = matrix.last().pose();
				        
		    			buffer.vertex(matrix2, 0F, 0F, ll).color(r, g, b, a).uv(0, 0).endVertex();
		    			buffer.vertex(matrix2, 0f, (float) distance, ll).color(r, g, b, a).uv(0, 0).endVertex();
		    			buffer.vertex(matrix2, 0f, (float) distance, lr).color(r, g, b, a).uv(0, 0).endVertex();
		    			buffer.vertex(matrix2, 0f, 0F, lr).color(r, g, b, a).uv(0, 0).endVertex();
		    			
			        matrix.popPose();
		        }else if(te.mode == MODE.POWER) {
		        	matrix.pushPose();
		        	 
		        		VertexConsumer buffer = bufferIn.getBuffer(LaserRenderType.getDebugRenderType());
						Matrix4f matrix2 = matrix.last().pose();
		
							matrix.translate(.5f, .5f, .5f);
						
							matrix.mulPose(Axis.XP.rotationDegrees(facing.step().y() * 90f - 90f));
							matrix.mulPose(Axis.ZP.rotationDegrees((Math.abs(facing.step().y())-1) * (facing.toYRot() + 180f)));

							matrix.translate(-.5, -1, -.5);

							distance += .5d;
							 
							buffer.vertex(matrix2, 0, 0.1F, 0.5F).color(r, g, b, a).uv(0, 0).endVertex();
							buffer.vertex(matrix2, 0, (float)distance, 0.5F).color(r,g, b, a).uv(1, 0).endVertex();
							buffer.vertex(matrix2, 1F, (float)distance, 0.5F).color(r,g, b, a).uv(1, 1).endVertex();
							buffer.vertex(matrix2, 1F, 0.1F, 0.5F).color(r,g, b, a).uv(0, 1).endVertex();
//			    			
					        matrix.translate(0, 0, 0f);

							buffer.vertex(matrix2, 0.5F, 0.1F, 0).color(r, g, b, a).uv(0, 0).endVertex();
							buffer.vertex(matrix2, 0.5F, (float)distance, 0).color(r,g, b, a).uv(1, 0).endVertex();
							buffer.vertex(matrix2, 0.5F, (float)distance, 0 + 1F).color(r,g, b, a).uv(1, 1).endVertex();
							buffer.vertex(matrix2, 0.5F, 0.1F, 0 + 1F).color(r,g, b, a).uv(0, 1).endVertex();
			    			
							buffer = bufferIn.getBuffer(LaserRenderType.LASER_RENDER_BEAM);

							//RenderSystem.setShaderTexture(0, new ResourceLocation(Reference.MODID, "textures/render/laser_power_old_beam.png"));
							
							float r2, g2, b2;
							r2 = g2 = b2 = 1.0f;
							float a2 = 1.0f;
							float s = 0.126f;
							buffer.vertex(matrix2, 0.5f - s/2f, 0.1F, 0.5F).color(r2, g2, b2, a2).uv(0, 0).endVertex();
							buffer.vertex(matrix2, 0.5f - s/2f, (float) distance, 0.5F).color(r2, g2, b2, a2).uv(1, 0).endVertex();
							buffer.vertex(matrix2, 0.5f + s/2f, (float) distance, 0.5F).color(r2, g2, b2, a2).uv(1, 1).endVertex();
							buffer.vertex(matrix2, 0.5f + s/2f, 0.1F, 0.5F).color(r2, g2, b2, a2).uv(0, 1).endVertex();
							

							buffer.vertex(matrix2, 0.5F, 0.1F, 0.5f - s/2f).uv(0, 0).color(r2, g2, b2, a2).endVertex();
							buffer.vertex(matrix2, 0.5F, (float) distance, 0.5f - s/2f).uv(1, 0).color(r2, g2, b2, a2).endVertex();
							buffer.vertex(matrix2, 0.5F, (float) distance, 0.5f + s/2f).uv(1, 1).color(r2, g2, b2, a2).endVertex();
							buffer.vertex(matrix2, 0.5F, 0.1F, 0.5f + s/2f).uv(0, 1).color(r2, g2, b2, a2).endVertex();
							
				        matrix.popPose();
		        	
		        }
		        else if(te.mode == MODE.BEAM) {
		        	 matrix.pushPose();
				        
		        	 ll = 0.3f;
		        	 lr = 0.7f;
		        	 
		        	 	VertexConsumer buffer = bufferIn.getBuffer(LaserRenderType.LASER_RENDER_POWER_NEW);
						Matrix4f matrix2 = matrix.last().pose();
						
						matrix.translate(.5f, .5f, .5f);
						
						matrix.mulPose(Axis.XP.rotationDegrees(facing.step().y() * 90f - 90f));
						matrix.mulPose(Axis.ZP.rotationDegrees((Math.abs(facing.step().y())-1) * (facing.toYRot() + 180f)));
						
						rotationLogic(matrix, te);
						
						matrix.translate(-.5, -.5, 0);
						
						uMin -= 0.01f;
						if((uMin + 0.01f) == 1) {
							uMin = 0;
						}
						
						a += 0.25;
						
						buffer.vertex(matrix2, ll, 0f, 0f).color(r, g, b, a).uv(uMin, 0).endVertex();
						buffer.vertex(matrix2, ll, (float) distance, 0f).color(r, g, b, a).uv(uMin + 10f, 0).endVertex();
						buffer.vertex(matrix2, lr, (float) distance, 0f).color(r, g, b, a).uv(uMin + 10f, 1).endVertex();
						buffer.vertex(matrix2, lr, 0f, 0f).color(r, g, b, a).uv(uMin, 1).endVertex();
						
					matrix.popPose();
		        }else if(te.mode == MODE.NEW_POWER) { 
		        	 matrix.pushPose();
		        	 
		        	 	VertexConsumer buffer = bufferIn.getBuffer(LaserRenderType.LASER_RENDER_BEAM);
						Matrix4f matrix2 = matrix.last().pose();
							
						thickness *= 2f;
						
						ll = 0.5f - thickness/2f;
				        lr = 0.5f + thickness/2f;
						
						float centerThickness = (thickness/2f)/5f;
					
						matrix.translate(.5f, .5f, .5f);
					
						matrix.mulPose(Axis.XP.rotationDegrees(facing.step().y() * 90f - 90f));
						matrix.mulPose(Axis.ZP.rotationDegrees((Math.abs(facing.step().y())-1) * (facing.toYRot() + 180f)));
						
						rotationLogic(matrix, te);
						
						matrix.translate(-.5, -.5, 0);
						
		    			buffer.vertex(matrix2, (lr+ll)/2f-centerThickness, 0F, 0F).color(1f, 1f, 1f, 1f).uv(0, 0.48f).endVertex();
		    			buffer.vertex(matrix2, (lr+ll)/2f-centerThickness, (float) distance, 0F).color(1f, 1f, 1f, 1f).uv(1, 0.48f).endVertex();
		    			buffer.vertex(matrix2, (lr+ll)/2f+centerThickness, (float) distance, 0F).color(1f, 1f, 1f, 1f).uv(1, 0.52f).endVertex();
		    			buffer.vertex(matrix2, (lr+ll)/2f+centerThickness, 0, 0F).color(1f, 1f, 1f, 1f).uv(0, 0.52f).endVertex();
		    			
		    			buffer = bufferIn.getBuffer(LaserRenderType.LASER_RENDER);

						buffer.vertex(matrix2, ll, 0F, 0F).color(r,g, b, 0f).uv(0, 0).endVertex();
		    			buffer.vertex(matrix2, ll, (float) distance, 0F).color(r, g, b, 0f).uv(0, 0).endVertex();
		    			buffer.vertex(matrix2, (lr+ll)/2f-centerThickness, (float) distance, 0F).color(r, g, b, 1f).uv(0, 0).endVertex();
		    			buffer.vertex(matrix2, (lr+ll)/2f-centerThickness, 0, 0F).color(r, g, b, 1f).uv(0, 0).endVertex();
		    			
		    			buffer.vertex(matrix2, (lr+ll)/2f+centerThickness, 0F, 0F).color(r, g, b, 1f).uv(0, 0).endVertex();
		    			buffer.vertex(matrix2, (lr+ll)/2f+centerThickness, (float) distance, 0F).color(r, g, b, 1f).uv(0, 0).endVertex();
		    			buffer.vertex(matrix2, lr, (float) distance, 0F).color(r, g, b, 0f).uv(0, 0).endVertex();
		    			buffer.vertex(matrix2, lr, 0, 0F).color(r, g, b, 0f).uv(0, 0).endVertex();

			        matrix.popPose();
		        }
		        RenderSystem.enableDepthTest();
		}
		RenderSystem.enableDepthTest();
		RenderSystem.depthMask(true);
	}
	
	public boolean canBeSeen() {
		boolean condition = false;
		for(ItemStack stack : Minecraft.getInstance().player.getArmorSlots()) {
			if(stack.getItem() == ModItems.IR_Glasses.get()) {
				condition = true;
				break;
			}
		}
		return condition && Minecraft.getInstance().options.getCameraType().isFirstPerson();
	}
	
	public void rotationLogic(PoseStack matrix, TileEntityLaser te) {
		Direction facing = te.getBlockState().getValue(BlockRotatable.FACING);
		Vec3 playerPos = Minecraft.getInstance().player.position()
				.add(new Vec3(0, Minecraft.getInstance().player.getEyeHeight(), 0));
		
		Vector3f pos = new Vector3f(te.getBlockPos().getX() + 0.5f, te.getBlockPos().getY() + 0.5f, te.getBlockPos().getZ() + 0.5f);
		Vec3 toPlayer = playerPos.subtract(pos.x(), pos.y(), pos.z());
		Vector3f right = new Vector3f();
		Vector3f normal = new Vector3f();
		
		if(facing.getAxis().isHorizontal()) {
			right = new Vector3f(-facing.getStepZ(), facing.getStepY(), facing.getStepX());
			normal = new Vector3f(right);
			normal.cross(facing.step());
			normal = new Vector3f(Math.abs(normal.x()), Math.abs(normal.y()), Math.abs(normal.z()));
			toPlayer = toPlayer.multiply(new Vec3(Math.abs(right.x() + normal.x()), Math.abs(right.y() + normal.y()), Math.abs(right.z() + normal.z())));
		}else {
			right = new Vector3f(-facing.getStepY(), facing.getStepX(), facing.getStepZ());
			normal = new Vector3f(right);
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
		
		matrix.mulPose(Axis.YP.rotationDegrees(angle));
	}
	
public static class LaserRenderType extends RenderType {
	
	public LaserRenderType(String p_173178_, VertexFormat p_173179_, Mode p_173180_, int p_173181_, boolean p_173182_,
			boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
		super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
	}

	public static final RenderType LASER_RENDER = create("laser_render",
			DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 256, 
			true, false,
			RenderType.CompositeState.builder()
			.setShaderState(RENDERTYPE_LEASH_SHADER)
			.setShaderState(RENDERTYPE_LIGHTNING_SHADER)
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
	
	public static RenderType getDebugRenderType2() {
		return create("debug2",
				DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 256, true, false,
				RenderType.CompositeState.builder()
				.setShaderState(RENDERTYPE_LEASH_SHADER)
				.setShaderState(RENDERTYPE_LIGHTNING_SHADER)
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
	}
	
	public static RenderType getDebugRenderType() {
		return create("debug", 
				DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256, 
				false, true,
				RenderType.CompositeState.builder()
				.setShaderState(RENDERTYPE_ENERGY_SWIRL_SHADER)
				.setWriteMaskState(COLOR_WRITE)
				.setTextureState(TexStatePOWER)
				.setCullState(NO_CULL)
				.setTransparencyState(TRANSLUCENT_TRANSPARENCY)
				.setLightmapState(LIGHTMAP)
				.createCompositeState(false));
	}
	
	private static final TextureStateShard TexStatePOWER = new TextureStateShard(new ResourceLocation(Reference.MODID, "textures/render/laser_power_old_double.png"), false, false);
	private static final TextureStateShard TexStatePOWER_NEW = new TextureStateShard(new ResourceLocation(Reference.MODID, "textures/render/laser_power.png"), false, false);
	private static final TextureStateShard TexStateBEAM = new TextureStateShard(new ResourceLocation(Reference.MODID, "textures/render/laser_power_old_beam.png"), false, true);
	
	public static final RenderType LASER_RENDER_POWER_NEW = create("laser_power_new",
			DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256, 
			 false, true,
				RenderType.CompositeState.builder()
				.setShaderState(RENDERTYPE_ENERGY_SWIRL_SHADER)
				.setWriteMaskState(COLOR_WRITE)
				.setTextureState(TexStatePOWER_NEW)
				.setCullState(NO_CULL)
				.setTransparencyState(TRANSLUCENT_TRANSPARENCY)
				.setLightmapState(LIGHTMAP)
				.createCompositeState(false));
	
	public static final RenderType LASER_RENDER_POWER = create("laser_power",
			DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256, 
			 false, true,
			 RenderType.CompositeState.builder()
			 .setShaderState(RENDERTYPE_ENERGY_SWIRL_SHADER)
			 .setWriteMaskState(COLOR_WRITE)
			 .setTextureState(TexStatePOWER)
			 .setCullState(NO_CULL)
			 .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
			 .setShaderState(RENDERTYPE_SOLID_SHADER)
			 .createCompositeState(false));
	
	public static final RenderType LASER_RENDER_BEAM = create("laser_beam",
			DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 256, 
			false, true,
			RenderType.CompositeState.builder()
			.setShaderState(RENDERTYPE_LIGHTNING_SHADER)
			.setWriteMaskState(COLOR_WRITE)
			.setTextureState(TexStateBEAM)
			.setCullState(NO_CULL)
			.setTransparencyState(TRANSLUCENT_TRANSPARENCY)
			.setLightmapState(LIGHTMAP)
			.createCompositeState(false));

	private static RenderType create(String id, VertexFormat positionTex, int iMode, int size,
			boolean p_173220_, boolean p_173221_, CompositeState state) {
		return create(id, positionTex, Mode.QUADS, size, p_173220_, p_173221_, state);
	}
	   
}

}
