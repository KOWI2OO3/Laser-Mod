package KOWI2003.LaserMod.tileentities.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import KOWI2003.LaserMod.blocks.BlockHorizontal;
import KOWI2003.LaserMod.init.ModItems;
import KOWI2003.LaserMod.items.ItemLaserToolBase;
import KOWI2003.LaserMod.items.tools.ItemLaserToolOpend;
import KOWI2003.LaserMod.tileentities.TileEntityModStation;
import KOWI2003.LaserMod.utils.LaserItemUtils;
import KOWI2003.LaserMod.utils.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;

public class ModStationRenderer implements BlockEntityRenderer<TileEntityModStation> {
	private final BlockEntityRendererProvider.Context context;
	
	public ModStationRenderer(BlockEntityRendererProvider.Context context) {
		this.context = context;
	}

	@Override
	public void render(TileEntityModStation te, float partialTicks, PoseStack matrix, MultiBufferSource bufferIn,
			int combinedLightIn, int combinedOverlayIn) {
		RenderSystem.enableDepthTest();
		matrix.pushPose();
		matrix.translate(0.5, 0, 0.5f);
		RenderUtils.rotateMatrix(matrix, te.getBlockState().getValue(BlockHorizontal.FACING).getClockWise());
		
		if(te.handler.getStackInSlot(0).getItem() instanceof ItemLaserToolBase) {
			ItemStack stack = ((ItemLaserToolOpend)ModItems.LaserToolOpened.get()).getItemWithColor(LaserItemUtils.getColor(te.handler.getStackInSlot(0)));
			RenderUtils.renderItemOnTop(matrix, stack, -0.17f, 0.1f, -0.1f, 0.5f, bufferIn, combinedLightIn, combinedOverlayIn);
		}
		else if(te.handler.getStackInSlot(0).getItem() == ModItems.LaserHelmet.get()) {
			ItemStack stack = ((ItemLaserToolOpend)ModItems.LaserHelmetOpened.get()).getItemWithColor(LaserItemUtils.getColor(te.handler.getStackInSlot(0)));
			matrix.mulPose(Vector3f.YP.rotationDegrees(90));
			RenderUtils.renderItem(matrix, stack, -0.5f, -.34f, -0.5f, 0.5f, .5f, bufferIn, combinedLightIn, combinedOverlayIn);
		}
		else if(te.handler.getStackInSlot(0).getItem() == ModItems.LaserChestplate.get()) {
			ItemStack stack = ((ItemLaserToolOpend)ModItems.LaserChestplateOpened.get()).getItemWithColor(LaserItemUtils.getColor(te.handler.getStackInSlot(0)));
			matrix.mulPose(Vector3f.YP.rotationDegrees(90));
			RenderUtils.renderItem(matrix, stack, -0.5f, -.22f, -0.55f, 0.5f, 1.1f, bufferIn, combinedLightIn, combinedOverlayIn);
		}
		else if(te.handler.getStackInSlot(0).getItem() == ModItems.LaserLeggings.get()) {
			ItemStack stack = ((ItemLaserToolOpend)ModItems.LaserLegginsOpened.get()).getItemWithColor(LaserItemUtils.getColor(te.handler.getStackInSlot(0)));
			matrix.mulPose(Vector3f.YP.rotationDegrees(90));
			RenderUtils.renderItem(matrix, stack, -0.5f, -.28f, -0.5f, 0.5f, .7f, bufferIn, combinedLightIn, combinedOverlayIn);
		}
		else if(te.handler.getStackInSlot(0).getItem() == ModItems.LaserBoots.get()) {
			ItemStack stack = ((ItemLaserToolOpend)ModItems.LaserBootsOpened.get()).getItemWithColor(LaserItemUtils.getColor(te.handler.getStackInSlot(0)));
			matrix.mulPose(Vector3f.YP.rotationDegrees(90));
			RenderUtils.renderItem(matrix, stack, -0.5f, -.24f, -0.5f, 0.5f, 1f, bufferIn, combinedLightIn, combinedOverlayIn);
		}
		
		matrix.popPose();
	}
}