package KOWI2003.LaserMod.utils.client.render;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.config.Config;
import KOWI2003.LaserMod.init.ModItems;
import KOWI2003.LaserMod.tileentities.TileEntityLaser;
import KOWI2003.LaserMod.tileentities.TileEntityLaser.MODE;
import KOWI2003.LaserMod.tileentities.TileEntityMirror;
import KOWI2003.LaserMod.utils.client.rendertypes.LaserRenderType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;

/**
 * A helper class for rendering the laser
 * @author KOWI2003
 */
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Reference.MODID, value = Dist.CLIENT)
public class LaserRenderHelper {

    /**
     * A method for rendering the laser
     * @param tile the tile entity being rendered
     * @param pose the pose stack for transformations
     * @param buffer the buffer source for rendering
     * @param mode the mode of the laser 
     * @param facing the direction the laser is facing
     * @param distance the distance the laser is going
     * @param color the color of the laser
     * @param laserOffset the offset of the laser from the base
     * @param combinedOverlayIn the overlay for the laser
     * @param combinedLightIn the light for the laser
     */
    public static void renderLaser(BlockEntity tile, PoseStack pose, MultiBufferSource buffer, MODE mode, Direction facing, float distance, Vector4f color, 
            float laserOffset, int combinedOverlayIn, int combinedLightIn) {
        renderLaser(tile, pose, buffer, mode, facing, distance, color, laserOffset, null, combinedOverlayIn, combinedLightIn);
    }

    /**
     * The main render method for rendering the laser
     * @param tile the tile entity being rendered
     * @param pose the pose stack for transformations
     * @param buffer the buffer source for rendering
     * @param mode the mode of the laser 
     * @param facing the direction the laser is facing
     * @param distance the distance the laser is going
     * @param color the color of the laser
     * @param laserOffset the offset of the laser from the base
     * @param transformation the additional transformation for the laser
     * @param combinedOverlayIn the overlay for the laser
     * @param combinedLightIn the light for the laser
     */
    public static void renderLaser(BlockEntity tile, PoseStack pose, MultiBufferSource buffer, MODE mode, Direction facing, float distance, Vector4f color, 
            float laserOffset, @Nullable Consumer<PoseStack> transformation, int combinedOverlayIn, int combinedLightIn) {
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        float thickness = 0.05f;

        pose.pushPose();

        pose.translate(.5f, .5f, .5f);
					
        pose.mulPose(Vector3f.XP.rotationDegrees(facing.step().y() * 90f - 90f));
        pose.mulPose(Vector3f.ZP.rotationDegrees((Math.abs(facing.step().y())-1) * (facing.toYRot() + 180f)));

        var hasTransformation = transformation != null;

        if(transformation != null)
            transformation.accept(pose);

        switch (mode) {
            case INVISIBLE: if(!canSee()) break;
            case NORMAL:
                renderSimpleLaser(pose, buffer, distance, thickness, color, laserOffset, hasTransformation);
                break;
            case NEW_POWER:
                renderLaser(tile, pose, buffer, facing, distance, thickness, color, laserOffset, false, hasTransformation);
                break;
            case BEAM:
                renderLaserBeam(tile, pose, buffer, facing, distance, color, laserOffset, combinedOverlayIn, combinedLightIn, hasTransformation);
                break;
            case POWER:
                renderLaser(tile, pose, buffer, facing, distance, .5f, color, laserOffset, true, hasTransformation);
                break;
            default:
                break;
        }
        
        pose.popPose();
        RenderSystem.enableDepthTest();
    }

    /**
     * Renders the basic simple laser
     */
    private static void renderSimpleLaser(PoseStack pose, MultiBufferSource bufferSource, float distance, float thickness, Vector4f color, float laserOffset, boolean hasTransformation) {
        float ll = 0.5f - thickness/2.0f;
        float lr = 0.5f + thickness/2.0f;
        
        float r = color.x();
        float g = color.y();
        float b = color.z();
        float a = color.w();

        if(!hasTransformation)
            pose.translate(-.5, -.5, 0); 

        var matrix = pose.last().pose();

        var buffer = bufferSource.getBuffer(LaserRenderType.SIMPLE_LASER_RENDER);
        RenderSystem.setShaderColor(1, 1, 1, 1);

        buffer.vertex(matrix, ll, laserOffset, 0F).color(r, g, b, a).uv(0, 0).endVertex();
        buffer.vertex(matrix, ll, distance, 0F).color(r, g, b, a).uv(0, 0).endVertex();
        buffer.vertex(matrix, lr, distance, 0F).color(r, g, b, a).uv(0, 0).endVertex();
        
        buffer.vertex(matrix, ll, laserOffset, 0F).color(r, g, b, a).uv(0, 0).endVertex();
        buffer.vertex(matrix, lr, distance, 0F).color(r, g, b, a).uv(0, 0).endVertex();
        buffer.vertex(matrix, lr, laserOffset, 0F).color(r, g, b, a).uv(0, 0).endVertex();
						
        pose.translate(.5, 0, -.5f); 
        matrix = pose.last().pose();
				        
        buffer.vertex(matrix, 0, laserOffset, ll).color(r, g, b, a).uv(0, 0).endVertex();
        buffer.vertex(matrix, 0, distance, ll).color(r, g, b, a).uv(0, 0).endVertex();
        buffer.vertex(matrix, 0, distance, lr).color(r, g, b, a).uv(0, 0).endVertex();

        buffer.vertex(matrix, 0, laserOffset, ll).color(r, g, b, a).uv(0, 0).endVertex();
        buffer.vertex(matrix, 0, distance, lr).color(r, g, b, a).uv(0, 0).endVertex();
        buffer.vertex(matrix, 0, laserOffset, lr).color(r, g, b, a).uv(0, 0).endVertex();
    }

    private static void renderLaserBeam(BlockEntity tile, PoseStack pose, MultiBufferSource bufferSource, Direction facing, float distance, Vector4f color, 
            float laserOffset, int combinedOverlayIn, int combinedLightIn, boolean hasTransformation) {
        pose.pushPose();
        
        float r = color.x();
        float g = color.y();
        float b = color.z();
        float a = 0.65f;

        var ll = 0.3f;
        var lr = 0.7f;
        
        var buffer = bufferSource.getBuffer(LaserRenderType.LASER_BEAM_RENDER.apply(distance));
        var matrix = pose.last().pose();
        
        if(!hasTransformation) {
            rotationLogic(pose, tile, facing);
            
            pose.translate(-.5, -.5, 0);
        }

        
        buffer.vertex(matrix, ll, laserOffset, 0f).color(r, g, b, a).uv(-1, -1).endVertex();
        buffer.vertex(matrix, ll, (float) distance, 0f).color(r, g, b, a).uv(1, -1).endVertex();
        buffer.vertex(matrix, lr, (float) distance, 0f).color(r, g, b, a).uv(1, 1).endVertex();
        
        buffer.vertex(matrix, ll, laserOffset, 0f).color(r, g, b, a).uv(-1, -1).endVertex();
        buffer.vertex(matrix, lr, (float) distance, 0f).color(r, g, b, a).uv(1, 1).endVertex();
        buffer.vertex(matrix, lr, laserOffset, 0f).color(r, g, b, a).uv(-1, 1).endVertex();
        
        pose.popPose();
    }
    
    @SuppressWarnings("null")
    private static void renderLaser(BlockEntity tile, PoseStack pose, MultiBufferSource bufferSource, Direction facing, float distance, float thickness, Vector4f color, 
            float laserOffset, boolean isPower, boolean hasTransformation) {
        thickness *= 2f;
        
        float r = color.x();
        float g = color.y();
        float b = color.z();
        
        var useHitMarker = isPower ? Config.GetInstance().laserSettings.hitmarkerPowerLaser : Config.GetInstance().laserSettings.hitmarkerFocusedLaser;
        var endPosition = tile.getBlockPos().relative(facing, (int)distance);
        if(useHitMarker && !tile.getLevel().getBlockState(endPosition).isAir() && !(tile.getLevel().getBlockEntity(endPosition) instanceof TileEntityMirror) && !hasTransformation) {
            var size = (thickness/2.0f); 

            // Rendering Hit Marker
            pose.pushPose();
                pose.translate(-.5, -.5, -.5);
                var buffer = bufferSource.getBuffer(LaserRenderType.HIT_MARKER_RENDER);
                var matrix = pose.last().pose();

                buffer.vertex(matrix, .5f - size, distance, .5f - size).color(r, g, b, 1f).uv(0, 0).endVertex();
                buffer.vertex(matrix, .5f + size, distance, .5f - size).color(r, g, b, 1f).uv(1, 0).endVertex();
                buffer.vertex(matrix, .5f + size, distance, .5f + size).color(r, g, b, 1f).uv(1, 1).endVertex();
                
                buffer.vertex(matrix, .5f - size, distance, .5f - size).color(r, g, b, 1f).uv(0, 0).endVertex();
                buffer.vertex(matrix, .5f + size, distance, .5f + size).color(r, g, b, 1f).uv(1, 1).endVertex();
                buffer.vertex(matrix, .5f - size, distance, .5f + size).color(r, g, b, 1f).uv(0, 1).endVertex();

                if(tile instanceof TileEntityLaser) {
                    buffer.vertex(matrix, .5f - size, laserOffset, .5f - size).color(r, g, b, 1f).uv(0, 0).endVertex();
                    buffer.vertex(matrix, .5f + size, laserOffset, .5f - size).color(r, g, b, 1f).uv(1, 0).endVertex();
                    buffer.vertex(matrix, .5f + size, laserOffset, .5f + size).color(r, g, b, 1f).uv(1, 1).endVertex();
                    
                    buffer.vertex(matrix, .5f - size, laserOffset, .5f - size).color(r, g, b, 1f).uv(0, 0).endVertex();
                    buffer.vertex(matrix, .5f + size, laserOffset, .5f + size).color(r, g, b, 1f).uv(1, 1).endVertex();
                    buffer.vertex(matrix, .5f - size, laserOffset, .5f + size).color(r, g, b, 1f).uv(0, 1).endVertex();
                }
            pose.popPose();
        }

        // Rendering the laser
        pose.pushPose();

        if(!hasTransformation) {
            rotationLogic(pose, tile, facing);
                            
            pose.translate(-.5, -.5, 0);
        }

        var buffer = bufferSource.getBuffer(isPower ? LaserRenderType.LASER_POWER_RENDER : LaserRenderType.LASER_RENDER);
        var matrix = pose.last().pose();

        var size = (thickness/2.0f); // .1f;

        buffer.vertex(matrix, .5f - size, laserOffset, 0F).color(r, g, b, 1.0f).uv(0f, 0f).endVertex();
        buffer.vertex(matrix, .5f - size, (float) distance, 0F).color(r, g, b, 1.0f).uv(0f, 1f).endVertex();
        buffer.vertex(matrix, .5f + size, (float) distance, 0F).color(r, g, b, 1.0f).uv(1f, 1f).endVertex();
        
        buffer.vertex(matrix, .5f - size, laserOffset, 0F).color(r, g, b, 1.0f).uv(0f, 0f).endVertex();
        buffer.vertex(matrix, .5f + size, (float) distance, 0F).color(r, g, b, 1.0f).uv(1f, 1f).endVertex();
        buffer.vertex(matrix, .5f + size, laserOffset, 0F).color(r, g, b, 1.0f).uv(1f, 0f).endVertex();

        pose.popPose();
    }

    /**
     * Handles the rotation logic for rotating the laser such that the plane keeps directed at the player
     */
    @SuppressWarnings("null")
    private static void rotationLogic(PoseStack pose, BlockEntity tile, Direction facing) {
        var mc = Minecraft.getInstance();
        if(mc.player == null) return;

        Vec3 playerPos = mc.player.position()
                .add(new Vec3(0, mc.player.getEyeHeight(), 0));
        
        Vector3f pos = new Vector3f(tile.getBlockPos().getX() + 0.5f, tile.getBlockPos().getY() + 0.5f, tile.getBlockPos().getZ() + 0.5f);
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
            
        pose.mulPose(Vector3f.YP.rotationDegrees(angle));
	}

    /**
     * checks to see whether the laser can be observed by the player
     * @return whether the player can observe the laser
     */
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings({"resource", "null"})
    private static boolean canSee() {
		boolean condition = false;
        if(Minecraft.getInstance().player == null) return false;
		for(ItemStack stack : Minecraft.getInstance().player.getArmorSlots()) {
			if(stack.getItem() == ModItems.IR_Glasses.get()) {
				condition = true;
				break;
			}
		}
		return condition && Minecraft.getInstance().options.getCameraType().isFirstPerson();
	}
}