package KOWI2003.LaserMod.tileentities.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import KOWI2003.LaserMod.blocks.BlockHorizontal;
import KOWI2003.LaserMod.init.ModItems;
import KOWI2003.LaserMod.items.ItemLaserToolBase;
import KOWI2003.LaserMod.tileentities.TileEntityInfuser;
import KOWI2003.LaserMod.utils.LaserItemUtils;
import KOWI2003.LaserMod.utils.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.Items;

public class InfuserRenderer implements BlockEntityRenderer<TileEntityInfuser> {
	private final BlockEntityRendererProvider.Context context;
	
	
	public InfuserRenderer(BlockEntityRendererProvider.Context context) {
		this.context = context;
	}

	@Override
	public void render(TileEntityInfuser te, float partialTicks, PoseStack matrix, MultiBufferSource bufferIn,
			int combinedLightIn, int combinedOverlayIn) {
		matrix.pushPose();
		matrix.translate(0.5f, 0f, 0.5);
		RenderSystem.enableDepthTest();
		RenderUtils.rotateMatrix(matrix, te.getBlockState().getValue(BlockHorizontal.FACING));

		RenderSystem.enableDepthTest();
		matrix.pushPose();
		try {
			matrix.translate(-0.302, 0.0615f, -0.4408); //left-right / up-down / forward-backward
			float height = (float)te.handler.getStackInSlot(0).getCount()/(float)te.handler.getStackInSlot(0).getItem().getMaxStackSize(te.handler.getStackInSlot(0)) * 0.12f;
			RenderUtils.renderQuad(matrix, 0, 0, 0, 0.035f, height, 0, 1.0f, 0);
			
			matrix.translate(-0.05, 0, 0); //left-right / up-down / forward-backward
			height = (float)te.handler.getStackInSlot(1).getCount()/(float)te.handler.getStackInSlot(1).getItem().getMaxStackSize(te.handler.getStackInSlot(1)) * 0.12f;
			RenderUtils.renderQuad(matrix, 0, 0, 0, 0.035f, height, 0, 1.0f, 0);
			
			matrix.translate(-0.05, 0, 0); //left-right / up-down / forward-backward
			height = (float)te.handler.getStackInSlot(2).getCount()/(float)te.handler.getStackInSlot(2).getItem().getMaxStackSize(te.handler.getStackInSlot(2)) * 0.12f;
			RenderUtils.renderQuad(matrix, 0, 0, 0, 0.035f, height, 0, 1.0f, 0);
		}catch(Exception e) {}
		matrix.popPose();
		matrix.pushPose();
			float progress = te.getProgress();
			if(progress == 0)
				progress = 1;
			matrix.translate(0.22, 0.0615f, -0.444);
			matrix.pushPose();
			matrix.mulPose(Axis.ZP.rotationDegrees(180));
			String msg = Math.round(Math.abs(progress - 1) * 100) + "%";
			matrix.translate(0.45f/2f - 0.05f*(msg.length()/2f), -0.09f, -0.003);
			RenderUtils.renderString(matrix, msg, 0, 0, -.001f, 0.01f, 1.0f, 1.0f, 1.0f, false);
			matrix.popPose();
			RenderSystem.enableDepthTest();
			float width = Math.abs(progress - 1) * 0.45f;
			RenderUtils.renderQuad(matrix, 0 - width, 0, 0, width, 0.12f, 0, 1.0f, 0);
		matrix.popPose();
		
		//Item Render
		matrix.pushPose();
		if(!te.handler.getStackInSlot(0).isEmpty()) {
			if(te.handler.getStackInSlot(0).getItem() == Items.REDSTONE) {
				matrix.translate(-0.285f, 0.75f, 0.04f);
				matrix.mulPose(Axis.XP.rotationDegrees(90));
			}else {
				matrix.translate(-0.2825f, 0.107f, -0.275f);
			}
			RenderUtils.renderItemOnTop(matrix, te.handler.getStackInSlot(0), 0, 0, 0, 0.2f, bufferIn, combinedLightIn, combinedOverlayIn);
		}
		matrix.popPose();
		matrix.pushPose();
		float scaler = 0.2f;
		if(!te.handler.getStackInSlot(1).isEmpty()) {
			matrix.translate(-0.25, -0.01f, -0.43f);
			if(te.handler.getStackInSlot(1).getItem() == Items.REDSTONE) {
				matrix.translate(-0.285f, 0.75f, 0.04f);
				matrix.mulPose(Axis.XP.rotationDegrees(90));
			}else if(te.handler.getStackInSlot(1).getItem() instanceof ItemLaserToolBase) {
				scaler = .2f;
				matrix.translate(0.22f, 0.106f, -0.505f);
				matrix.mulPose(Axis.YP.rotationDegrees(-45f));
//				scaler = 0.7f;
//				matrix.translate(-0.585f, 0.058f, 0.085f);
//				matrix.mulPose(Vector3f.XP.rotationDegrees(90));
//				matrix.mulPose(Vector3f.YP.rotationDegrees(0f));
			}else {
				matrix.translate(-0.2825f, 0.105f, -0.275f);
			}
			RenderUtils.renderItemOnTop(matrix, LaserItemUtils.setExtended(te.handler.getStackInSlot(1).copy(), false), 0, 0, 0, scaler, bufferIn, combinedLightIn, combinedOverlayIn);
		}
		matrix.popPose();
		matrix.pushPose();
		if(!te.handler.getStackInSlot(2).isEmpty()) {
			float scale = 0.2f;
			if(te.handler.getStackInSlot(2).getItem() == ModItems.LaserCrystal.get()) {
				matrix.translate(-0.79, 0.29f, -0.29f);
				scale= 0.1f;
			}else {
				if(te.handler.getStackInSlot(2).getItem() == Items.REDSTONE) {
					matrix.translate(-0.78, 0.2f, -0.28f);
//				}else if(te.handler.getStackInSlot(2).getItem() instanceof ItemLaserToolBase) {
//					scale = 0.7f;
//					matrix.translate(-1.08, 0.65f, 0.35f);
//					matrix.mulPose(Vector3f.XP.rotationDegrees(-90));
//					matrix.mulPose(Vector3f.YP.rotationDegrees(36.1f));
				}else {
					matrix.mulPose(Axis.XP.rotationDegrees(-90));
					matrix.translate(-0.78, -0.365f, -0.13f);
				}
			}
			RenderUtils.renderItemOnTop(matrix, LaserItemUtils.setExtended(te.handler.getStackInSlot(2).copy(), false), 0, 0, 0, scale, bufferIn, combinedLightIn, combinedOverlayIn);
		}
		RenderSystem.enableDepthTest();
		matrix.popPose();
		matrix.popPose();
	}

}
