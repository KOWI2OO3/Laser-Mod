package KOWI2003.LaserMod.tileentities.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import KOWI2003.LaserMod.blocks.BlockHorizontal;
import KOWI2003.LaserMod.init.ModItems;
import KOWI2003.LaserMod.tileentities.TileEntityPrecisionAssembler;
import KOWI2003.LaserMod.utils.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.Items;

public class PrecisionAssemblerRenderer implements BlockEntityRenderer<TileEntityPrecisionAssembler> {
	private final BlockEntityRendererProvider.Context context;
	
	public PrecisionAssemblerRenderer(BlockEntityRendererProvider.Context context) {
		this.context = context;
	}

	@Override
	public void render(TileEntityPrecisionAssembler te, float partialTicks, PoseStack matrix, MultiBufferSource bufferIn,
			int combinedLightIn, int combinedOverlayIn) {
		matrix.pushPose();
		matrix.translate(0.5f, 0f, 0.5);
		RenderSystem.enableDepthTest();
		RenderUtils.rotateMatrix(matrix, te.getBlockState().getValue(BlockHorizontal.FACING));

		RenderSystem.enableDepthTest();
		matrix.pushPose();
		try {
			matrix.translate(0.375, 0.0615f, -0.4408); //left-right / up-down / forward-backward
			float height = (float)te.handler.getStackInSlot(0).getCount()/(float)te.handler.getStackInSlot(0).getItem().getMaxStackSize(te.handler.getStackInSlot(0)) * 0.12f;
			RenderUtils.renderQuad(matrix, 0, 0, 0, 0.035f, height, 0, 1.0f, 0);
			
			matrix.translate(-0.05, 0, 0); //left-right / up-down / forward-backward
			height = (float)te.handler.getStackInSlot(1).getCount()/(float)te.handler.getStackInSlot(1).getItem().getMaxStackSize(te.handler.getStackInSlot(1)) * 0.12f;
			RenderUtils.renderQuad(matrix, 0, 0, 0, 0.035f, height, 0, 1.0f, 0);
			
			matrix.translate(-0.05, 0, 0); //left-right / up-down / forward-backward
			height = (float)te.handler.getStackInSlot(2).getCount()/(float)te.handler.getStackInSlot(2).getItem().getMaxStackSize(te.handler.getStackInSlot(2)) * 0.12f;
			RenderUtils.renderQuad(matrix, 0, 0, 0, 0.035f, height, 0, 1.0f, 0);
			
			matrix.translate(-0.625, 0, 0); //left-right / up-down / forward-backward
			height = (float)te.handler.getStackInSlot(4).getCount()/(float)te.handler.getStackInSlot(4).getItem().getMaxStackSize(te.handler.getStackInSlot(4)) * 0.12f;
			RenderUtils.renderQuad(matrix, 0, 0, 0, 0.035f, height, 0, 1.0f, 0);
		}catch(Exception e) {}
		matrix.popPose();
		matrix.pushPose();
			float progress = te.getProgress();
			if(progress == 0)
				progress = 1;
			matrix.translate(0.22, 0.0615f, -0.444);
			matrix.pushPose();
			matrix.mulPose(Vector3f.ZP.rotationDegrees(180));
			String msg = Math.round(Math.abs(progress - 1) * 100) + "%";
			matrix.translate(0.45f/2f - 0.05f*(msg.length()/2f), -0.09f, -0.003);
			RenderUtils.renderString(matrix, msg, 0, 0, 0, 0.01f, 1.0f, 1.0f, 1.0f, false);
			matrix.popPose();
			RenderSystem.enableDepthTest();
			float width = Math.abs(progress - 1) * 0.45f;
			RenderUtils.renderQuad(matrix, 0 - width, 0, 0, width, 0.12f, 0, 1.0f, 0);
		matrix.popPose();
		
		//Item Render
		matrix.pushPose();
		int slot = 0;
		if(!te.handler.getStackInSlot(slot).isEmpty()) {
			float scaler = 0.15f;
			if(te.handler.getStackInSlot(slot).getItem() == Items.REDSTONE) {
				matrix.translate(-0.268f, 0.75f, 0.063f);
				matrix.mulPose(Vector3f.XP.rotationDegrees(90));
			}else if(te.handler.getStackInSlot(slot).getItem() == ModItems.LaserCrystal.get()) {
				scaler = scaler/2f;
				matrix.translate(-0.480f, 0.36f, 0.165f);
				matrix.mulPose(Vector3f.XP.rotationDegrees(90));
				matrix.mulPose(Vector3f.YP.rotationDegrees(36.1f));
			}else {
				matrix.translate(-0.268f, 0.107f, -0.255f);
			}
			RenderUtils.renderItemOnTop(matrix, te.handler.getStackInSlot(slot), 0, 0, 0, scaler, bufferIn, combinedLightIn, combinedOverlayIn);
		}
		matrix.popPose();
		matrix.pushPose();
		slot = 1;
		if(!te.handler.getStackInSlot(slot).isEmpty()) {
			float scaler = 0.15f;
			matrix.translate(0, 0, -0.27f * slot);
			if(te.handler.getStackInSlot(slot).getItem() == Items.REDSTONE) {
				matrix.translate(-0.268f, 0.75f, 0.063f);
				matrix.mulPose(Vector3f.XP.rotationDegrees(90));
			}else if(te.handler.getStackInSlot(slot).getItem() == ModItems.LaserCrystal.get()) {
				scaler = scaler/2f;
				matrix.translate(-0.480f, 0.36f, 0.165f);
				matrix.mulPose(Vector3f.XP.rotationDegrees(90));
				matrix.mulPose(Vector3f.YP.rotationDegrees(36.1f));
			}else {
				matrix.translate(-0.268f, 0.107f, -0.255f);
			}
			RenderUtils.renderItemOnTop(matrix, te.handler.getStackInSlot(slot), 0, 0, 0, scaler, bufferIn, combinedLightIn, combinedOverlayIn);
		}
		matrix.popPose();
		matrix.pushPose();
		slot = 2;
		if(!te.handler.getStackInSlot(slot).isEmpty()) {
			float scaler = 0.15f;
			matrix.translate(0, 0, -0.27f * slot);
			if(te.handler.getStackInSlot(slot).getItem() == Items.REDSTONE) {
				matrix.translate(-0.268f, 0.75f, 0.063f);
				matrix.mulPose(Vector3f.XP.rotationDegrees(90));
			}else if(te.handler.getStackInSlot(slot).getItem() == ModItems.LaserCrystal.get()) {
				scaler = scaler/2f;
				matrix.translate(-0.480f, 0.36f, 0.165f);
				matrix.mulPose(Vector3f.XP.rotationDegrees(90));
				matrix.mulPose(Vector3f.YP.rotationDegrees(36.1f));
			}else {
				matrix.translate(-0.268f, 0.107f, -0.255f);
			}
			RenderUtils.renderItemOnTop(matrix, te.handler.getStackInSlot(slot), 0, 0, 0, scaler, bufferIn, combinedLightIn, combinedOverlayIn);
		}
		RenderSystem.enableDepthTest();
		matrix.popPose();
		matrix.pushPose();
		slot = 3;
		if(!te.handler.getStackInSlot(slot).isEmpty()) {
			float scaler = 0.15f;
			matrix.translate(-0.378f, 0, -0.07f);
			if(te.handler.getStackInSlot(slot).getItem() == Items.REDSTONE) {
				matrix.translate(-0.268f, 0.75f, 0.063f);
				matrix.mulPose(Vector3f.XP.rotationDegrees(90));
			}else if(te.handler.getStackInSlot(slot).getItem() == ModItems.LaserCrystal.get()) {
				scaler = scaler/2f;
				matrix.translate(-0.480f, 0.36f, 0.165f);
				matrix.mulPose(Vector3f.XP.rotationDegrees(90));
				matrix.mulPose(Vector3f.YP.rotationDegrees(36.1f));
			}else {
				matrix.translate(-0.268f, 0.107f, -0.255f);
			}
			RenderUtils.renderItemOnTop(matrix, te.handler.getStackInSlot(slot), 0, 0, 0, scaler, bufferIn, combinedLightIn, combinedOverlayIn);
		}
		RenderSystem.enableDepthTest();
		matrix.popPose();
		matrix.pushPose();
		slot = 4;
		if(!te.handler.getStackInSlot(slot).isEmpty()) {
			float scaler = 0.2f;
			matrix.translate(-0.455f, -0.015f, -0.45f);
			if(te.handler.getStackInSlot(slot).getItem() == Items.REDSTONE) {
				matrix.translate(-0.268f, 0.75f, 0.063f);
				matrix.mulPose(Vector3f.XP.rotationDegrees(90));
			}else if(te.handler.getStackInSlot(slot).getItem() == ModItems.LaserCrystal.get()) {
				scaler = scaler/2f;
				matrix.translate(-0.480f, 0.36f, 0.165f);
				matrix.mulPose(Vector3f.XP.rotationDegrees(90));
				matrix.mulPose(Vector3f.YP.rotationDegrees(36.1f));
			}else {
				matrix.translate(-0.268f, 0.107f, -0.255f);
			}
			RenderUtils.renderItemOnTop(matrix, te.handler.getStackInSlot(slot), 0, 0, 0, scaler, bufferIn, combinedLightIn, combinedOverlayIn);
		}
		RenderSystem.enableDepthTest();
		matrix.popPose();
		matrix.popPose();
	}
	
}
