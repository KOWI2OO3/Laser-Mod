package KOWI2003.LaserMod.tileentities.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;

import KOWI2003.LaserMod.blocks.BlockLaser;
import KOWI2003.LaserMod.blocks.BlockRotatable;
import KOWI2003.LaserMod.init.ModItems;
import KOWI2003.LaserMod.tileentities.TileEntityAdvancedLaser;
import KOWI2003.LaserMod.tileentities.TileEntityLaser;
import KOWI2003.LaserMod.tileentities.TileEntityLaser.MODE;
import KOWI2003.LaserMod.tileentities.render.LaserRender.LaserRenderType;
import KOWI2003.LaserMod.tileentities.render.models.AdvancedLaserTop;
import KOWI2003.LaserMod.utils.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class RenderAdvancedLaser implements BlockEntityRenderer<TileEntityAdvancedLaser> {
	private final BlockEntityRendererProvider.Context context;
	
	private float uMin, vMin = 0;
	
	public RenderAdvancedLaser(BlockEntityRendererProvider.Context context) {
		this.context = context;
	}	
	
	@Override
	public boolean shouldRenderOffScreen(TileEntityAdvancedLaser te) {
		return te.active;
	}

	@Override
	public void render(TileEntityAdvancedLaser te, float partialTicks, PoseStack matrix, MultiBufferSource bufferIn,
			int combinedLightIn, int combinedOverlayIn) {
		
		matrix.pushPose();
//		GL11.glPushMatrix();
		Vec2 rotation = te.getRotation();
		Vector3f translation = new Vector3f(0, te.height, 0);
		{
			matrix.pushPose();
			
			matrix.translate(.5f, .5f, .5f);
			
			matrix.mulPose(Vector3f.XP.rotationDegrees(te.getBlockState().getValue(BlockLaser.FACING).step().y() * 90f - 90f));
			matrix.mulPose(Vector3f.ZP.rotationDegrees((Math.abs(te.getBlockState().getValue(BlockLaser.FACING).step().y())-1) * (te.getBlockState().getValue(BlockLaser.FACING).toYRot() + 180f)));
			
			matrix.translate(-.5, -.5, 0);
			matrix.translate(1, -1.15, .5);
			
			AdvancedLaserTop<?> model = new AdvancedLaserTop<Entity>();
			model.setTranslationForPlatform(translation.x(), translation.y(), translation.z());
			model.setRotationPlatform(rotation.x, rotation.y);
			float r = te.red;
			float g = te.green;
			float b = te.blue;
			
			model.renderToBuffer(matrix, null/*bufferIn.getBuffer(RenderType.entityCutout(new Resourece))*/, combinedLightIn, combinedOverlayIn, r, g, b, 1.0f);
			matrix.popPose();
		}
		
		if(te.active) {
			RenderSystem.enableDepthTest();
			RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
				double distance = te.distance;
				float r = te.red;
				float g = te.green;
				float b = te.blue;
				float a = 0.4f;
				
				float thickness = 0.05f;
				
				float ll = 0.5f - thickness/2f;
		        float lr = 0.5f + thickness/2f;
		        
		        Direction facing = te.getBlockState().getValue(BlockLaser.FACING);

		        Vector3f origin = new Vector3f(.5f + translation.x()/16f, 0.3f + translation.y()/16f, 0 + translation.z()/16f);
		        
				Vector3f dir = MathUtils.rotateVector(new Vector3f(0, 1, 0), origin, new Vector3f(rotation.x, rotation.y, 0));
				
				distance = distance - 0.5f *(dir.x() * te.height/16f + dir.y() * te.height/16f);

				distance -= 0.05f;
				
		        if(te.mode == MODE.NORMAL || (te.mode == MODE.INVISIBLE && canBeSeen())) {
			        matrix.pushPose();
			        
					VertexConsumer buffer = bufferIn.getBuffer(LaserRenderType.LASER_RENDER);
					Matrix4f matrix2 = matrix.last().pose();
					
						matrix.translate(.5f, .5f, .5f);
					
						matrix.mulPose(Vector3f.XP.rotationDegrees(facing.step().y() * 90f - 90f));
						matrix.mulPose(Vector3f.ZP.rotationDegrees((Math.abs(facing.step().y())-1) * (facing.toYRot() + 180f)));
						
						matrix.translate(-.5, -.5, 0);
						
						matrix.translate(origin.x(), origin.y(), origin.z());
						matrix.mulPose(Vector3f.ZP.rotation(rotation.y));
						matrix.mulPose(Vector3f.XP.rotation(rotation.x));
						matrix.translate(-.5f, 0.05f, 0);
						
						buffer.vertex(matrix2, ll, 0F, 0F).color(r,g, b, a).uv(0, 0).endVertex();
		    			buffer.vertex(matrix2, ll, (float) distance, 0F).color(r, g, b, a).uv(0, 0).endVertex();
		    			buffer.vertex(matrix2, lr, (float) distance, 0F).color(r, g, b, a).uv(0, 0).endVertex();
		    			buffer.vertex(matrix2, lr, 0, 0F).color(r, g, b, a).uv(0, 0).endVertex();
		    			
				        buffer = bufferIn.getBuffer(LaserRenderType.LASER_RENDER);
		    			
				        matrix.translate(.5, 0, -0.5f);
				        
		    			buffer.vertex(matrix2, 0F, 0F, ll).color(r, g, b, a).uv(0, 0).endVertex();
		    			buffer.vertex(matrix2, 0f, (float) distance, ll).color(r, g, b, a).uv(0, 0).endVertex();
		    			buffer.vertex(matrix2, 0f, (float) distance, lr).color(r, g, b, a).uv(0, 0).endVertex();
		    			buffer.vertex(matrix2, 0f, 0F, lr).color(r, g, b, a).uv(0, 0).endVertex();
		    			
			        matrix.popPose();
		        }else if(te.mode == MODE.POWER) {
		        	matrix.pushPose();
		        	 
						VertexConsumer buffer = bufferIn.getBuffer(LaserRenderType.LASER_RENDER_POWER);
						Matrix4f matrix2 = matrix.last().pose();
		
							matrix.translate(.5f, .5f, .5f);
						
							matrix.mulPose(Vector3f.XP.rotationDegrees(facing.step().y() * 90f - 90f));
							matrix.mulPose(Vector3f.ZP.rotationDegrees((Math.abs(facing.step().y())-1) * (facing.toYRot() + 180f)));
							
							matrix.translate(-.5f, -.5f, -.5f);
							
							matrix.translate(origin.x(), origin.y(), origin.z());
							matrix.mulPose(Vector3f.ZP.rotation(rotation.y));
							matrix.mulPose(Vector3f.XP.rotation(rotation.x));
							matrix.translate(-.5f, 0.05f, 0);
							
							distance += .5d;
							
							buffer.vertex(matrix2, 0, 0.1F, 0.5F).color(r,g, b, a).uv(0, 0).endVertex();
							buffer.vertex(matrix2, 0, (float)distance, 0.5F).color(r,g, b, a).uv(1, 0).endVertex();
							buffer.vertex(matrix2, 1F, (float)distance, 0.5F).color(r,g, b, a).uv(1, 1).endVertex();
							buffer.vertex(matrix2, 1F, 0.1F, 0.5F).color(r,g, b, a).uv(0, 1).endVertex();
			    			
					        buffer = bufferIn.getBuffer(LaserRenderType.LASER_RENDER_POWER);
			    			
					        matrix.translate(0, 0, 0f);

							buffer.vertex(matrix2, 0.5F, 0.1F, 0).color(r,g, b, a).uv(0, 0).endVertex();
							buffer.vertex(matrix2, 0.5F, (float)distance, 0).color(r,g, b, a).uv(1, 0).endVertex();
							buffer.vertex(matrix2, 0.5F, (float)distance, 0 + 1F).color(r,g, b, a).uv(1, 1).endVertex();
							buffer.vertex(matrix2, 0.5F, 0.1F, 0 + 1F).color(r,g, b, a).uv(0, 1).endVertex();
			    			
							buffer = bufferIn.getBuffer(LaserRenderType.LASER_RENDER_BEAM);

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
						
						matrix.mulPose(Vector3f.XP.rotationDegrees(facing.step().y() * 90f - 90f));
						matrix.mulPose(Vector3f.ZP.rotationDegrees((Math.abs(facing.step().y())-1) * (facing.toYRot() + 180f)));
						
						matrix.translate(-.5, -.5, 0);
						
						matrix.translate(origin.x(), origin.y(), origin.z());
						matrix.mulPose(Vector3f.ZP.rotation(rotation.y));
						matrix.mulPose(Vector3f.XP.rotation(rotation.x));
						matrix.translate(-.5f, 0.05f, 0);
						matrix.translate(.5f, .5f, 0);
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
					
						matrix.mulPose(Vector3f.XP.rotationDegrees(facing.step().y() * 90f - 90f));
						matrix.mulPose(Vector3f.ZP.rotationDegrees((Math.abs(facing.step().y())-1) * (facing.toYRot() + 180f)));
						
						matrix.translate(-.5, -.5, 0);
						
						matrix.translate(origin.x(), origin.y(), origin.z());
						matrix.mulPose(Vector3f.ZP.rotation(rotation.y));
						matrix.mulPose(Vector3f.XP.rotation(rotation.x));
						matrix.translate(-.5f, 0.05f, 0);
						matrix.translate(.5f, .5f, 0);
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
		matrix.popPose();
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
		
		PoseStack matrixS = new PoseStack();
		matrixS.translate(pos.x(), pos.y(), pos.z());
		
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
}
