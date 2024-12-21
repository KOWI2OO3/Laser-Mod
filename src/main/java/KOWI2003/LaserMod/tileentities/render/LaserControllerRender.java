package KOWI2003.LaserMod.tileentities.render;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.blocks.BlockHorizontal;
import KOWI2003.LaserMod.tileentities.TileEntityLaser;
import KOWI2003.LaserMod.tileentities.TileEntityLaser.MODE;
import KOWI2003.LaserMod.tileentities.TileEntityLaserController;
import KOWI2003.LaserMod.tileentities.TileEntityLaserProjector;
import KOWI2003.LaserMod.tileentities.render.models.LaserControllerLevers;
import KOWI2003.LaserMod.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;

public class LaserControllerRender implements BlockEntityRenderer<TileEntityLaserController> {
	public LaserControllerRender(BlockEntityRendererProvider.Context context) {}

	@Override
	public void render(@Nonnull TileEntityLaserController te, float partialTicks, @Nonnull PoseStack matrix, @Nonnull MultiBufferSource bufferIn,
			int combinedLightIn, int combinedOverlayIn) {
		ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/render/levers_and_switches.png");
		
		LaserControllerLevers<?> levers = new LaserControllerLevers<Entity>(LaserControllerLevers.createBodyLayer().bakeRoot());
		Minecraft.getInstance().getTextureManager().bindForSetup(TEXTURE);
		RenderUtils.rotateMatrixForBlock(matrix, te.getBlockState().getValue(BlockHorizontal.FACING));
		float rot = 1.6f;
		if(te.isConnected()) {
			BlockEntity tile = te.getControlTileEntity();
			boolean isActive = false;
			if(tile instanceof TileEntityLaser) {
				isActive = ((TileEntityLaser)tile).active;
				TileEntityLaser laser = ((TileEntityLaser)tile);
				levers.setValues(laser.red, laser.green, laser.blue, laser.mode.ordinal(), MODE.values().length-1);
			}
			if(tile instanceof TileEntityLaserProjector)
				isActive = ((TileEntityLaserProjector)tile).isActive;

			rot = isActive ? 0 : rot;
		}else levers.setValues(0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
		levers.setRotationAngle(levers.MainLever, rot, 0, 0);
		levers.renderToBuffer(matrix, bufferIn.getBuffer(RenderType.entityTranslucent(TEXTURE)), combinedLightIn, combinedOverlayIn, 1.0f, 1.0f, 1.0f, 1.0f);	

		RenderSystem.enableDepthTest();
		
		//Screens
		matrix.pushPose();
		{	
			matrix.mulPose(Vector3f.ZN.rotationDegrees(180f));
			matrix.translate(-0.695, -0.72, 0);
			
			String mode = "NULL";

			if(te.isConnected()) {
				BlockEntity tile = te.getControlTileEntity();
				if(tile instanceof TileEntityLaser) 
					mode = ((TileEntityLaser)tile).mode.getFormalName();
			}
			
			RenderUtils.renderString(matrix, "MODE: " + mode, 0, 0, 0, 0.0033f);
		}
		matrix.popPose();
		
		if(te.isConnected()) {
			BlockEntity tile = te.getControlTileEntity();
			if(tile instanceof TileEntityLaser) {
				matrix.pushPose();
				
				float scale = 0.195f;
				RenderSystem.enableDepthTest();
				RenderUtils.renderQuad(matrix, 0.211f, 0.32f, 0.01f, scale, scale, ((TileEntityLaser)tile).red, ((TileEntityLaser)tile).green, ((TileEntityLaser)tile).blue);
				
				matrix.popPose();
			}
		}
	}
}
