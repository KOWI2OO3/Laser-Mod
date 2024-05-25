package KOWI2003.LaserMod.tileentities.render;

import java.util.function.Consumer;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;

import KOWI2003.LaserMod.blocks.BlockLaser;
import KOWI2003.LaserMod.blocks.BlockRotatable;
import KOWI2003.LaserMod.init.ModItems;
import KOWI2003.LaserMod.tileentities.TileEntityAdvancedLaser;
import KOWI2003.LaserMod.tileentities.TileEntityLaser;
import KOWI2003.LaserMod.tileentities.render.models.AdvancedLaserTop;
import KOWI2003.LaserMod.utils.MathUtils;
import KOWI2003.LaserMod.utils.client.render.LaserRenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class RenderAdvancedLaser implements BlockEntityRenderer<TileEntityAdvancedLaser> {

	public RenderAdvancedLaser(BlockEntityRendererProvider.Context context) {}	
	
	@Override
	public boolean shouldRenderOffScreen(@Nonnull TileEntityAdvancedLaser te) {
		return te.active;
	}

	@Override
	public void render(@Nonnull TileEntityAdvancedLaser tile, float partialTicks, @Nonnull PoseStack matrix, @Nonnull MultiBufferSource bufferIn,
			int combinedLightIn, int combinedOverlayIn) {
		
		var color = new Vector4f(tile.red, tile.green, tile.blue, 0.4f);
		var facing = tile.getBlockState().getValue(BlockLaser.FACING);

		var rotation = tile.getRotation();
		var translation = new Vector3f(0, tile.height, 0);
		var origin = new Vector3f(.5f + translation.x()/16f, 0.3f + translation.y()/16f, 0 + translation.z()/16f);

		var dir = MathUtils.rotateVector(new Vector3f(0, 1, 0), origin, new Vector3f(rotation.x, rotation.y, 0));
		var distance = tile.distance - 0.5f *(dir.x() * tile.height/16f + dir.y() * tile.height/16f) - 0.05f;

		{
			float r = tile.red;
			float g = tile.green;
			float b = tile.blue;

			matrix.pushPose();
			matrix.translate(.5f, .5f, .5f);
			
			matrix.mulPose(Vector3f.XP.rotationDegrees(tile.getBlockState().getValue(BlockLaser.FACING).step().y() * 90f - 90f));
			matrix.mulPose(Vector3f.ZP.rotationDegrees((Math.abs(tile.getBlockState().getValue(BlockLaser.FACING).step().y())-1) * (tile.getBlockState().getValue(BlockLaser.FACING).toYRot() + 180f)));
			
			matrix.translate(-.5, -.5, 0);
			matrix.translate(1, -1.15, .5);
			
			AdvancedLaserTop<?> model = new AdvancedLaserTop<Entity>();
			model.setTranslationForPlatform(translation.x(), translation.y(), translation.z());
			model.setRotationPlatform(rotation.x, rotation.y);
			
			model.renderToBuffer(matrix, null, combinedLightIn, combinedOverlayIn, r, g, b, 1.0f);
			matrix.popPose();
		}

		Consumer<PoseStack> transformation = pose -> {
			switch (tile.mode) {
				case NORMAL, INVISIBLE:
						pose.translate(-.5, -.5, 0);
						pose.translate(origin.x(), origin.y(), origin.z());
						pose.mulPose(Vector3f.ZP.rotation(rotation.y));
						pose.mulPose(Vector3f.XP.rotation(rotation.x));
						pose.translate(-.5f, 0.05f, 0);
					break;
				case POWER, BEAM, NEW_POWER:
					pose.translate(-.5, -.5, 0);
							
					pose.translate(origin.x(), origin.y(), origin.z());
					pose.mulPose(Vector3f.ZP.rotation(rotation.y));
					pose.mulPose(Vector3f.XP.rotation(rotation.x));
					pose.translate(-.5f, 0.05f, 0);
					pose.translate(.5f, .5f, 0);
					rotationLogic(pose, tile);
					pose.translate(-.5, -.5, 0);
					break;
				default:
					break;
			}

		};

		if(tile.active)
			LaserRenderHelper.renderLaser(tile, matrix, bufferIn, tile.mode, facing, (float)distance, color, 0, transformation, combinedOverlayIn, combinedLightIn);
	}
	
	// Minecraft should not be used with a try for resource at it closed the game hence the suppressing of warnings
	@SuppressWarnings({ "resource", "null" })
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
	
	// Minecraft should not be used with a try for resource at it closed the game hence the suppressing of warnings
	@SuppressWarnings({ "resource", "null" })
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
