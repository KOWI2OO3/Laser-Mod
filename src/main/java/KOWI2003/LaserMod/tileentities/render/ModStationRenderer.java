package KOWI2003.LaserMod.tileentities.render;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import KOWI2003.LaserMod.blocks.BlockHorizontal;
import KOWI2003.LaserMod.init.ModItems;
import KOWI2003.LaserMod.items.ItemLaserToolBase;
import KOWI2003.LaserMod.items.tools.ItemLaserToolOpend;
import KOWI2003.LaserMod.items.tools.ToolLaserAxe;
import KOWI2003.LaserMod.items.tools.ToolLaserHoe;
import KOWI2003.LaserMod.items.tools.ToolLaserPickaxe;
import KOWI2003.LaserMod.items.tools.ToolLaserShovel;
import KOWI2003.LaserMod.items.tools.ToolLaserSword;
import KOWI2003.LaserMod.tileentities.TileEntityModStation;
import KOWI2003.LaserMod.utils.LaserItemUtils;
import KOWI2003.LaserMod.utils.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;

public class ModStationRenderer implements BlockEntityRenderer<TileEntityModStation> {
	public ModStationRenderer(BlockEntityRendererProvider.Context context) {}

	@Override
	public void render(@Nonnull TileEntityModStation te, float partialTicks, @Nonnull PoseStack matrix, @Nonnull MultiBufferSource bufferIn,
			int combinedLightIn, int combinedOverlayIn) {
		RenderSystem.enableDepthTest();
		matrix.pushPose();
		matrix.translate(0.5, 0, 0.5f);
		RenderUtils.rotateMatrix(matrix, te.getBlockState().getValue(BlockHorizontal.FACING).getClockWise());
		
		if(te.handler.getStackInSlot(0).getItem() instanceof ItemLaserToolBase) {
			ItemLaserToolBase tool = (ItemLaserToolBase) te.handler.getStackInSlot(0).getItem();
			ItemStack stack = ((ItemLaserToolOpend)ModItems.LaserToolOpened.get()).getItemWithColor(LaserItemUtils.getColor(te.handler.getStackInSlot(0)));
			if(tool instanceof ToolLaserPickaxe)
				stack = ((ItemLaserToolOpend)ModItems.LaserPickaxeOpen.get()).getItemWithColor(LaserItemUtils.getColor(te.handler.getStackInSlot(0)));
			if(tool instanceof ToolLaserSword)
				stack = ((ItemLaserToolOpend)ModItems.LaserSwordOpen.get()).getItemWithColor(LaserItemUtils.getColor(te.handler.getStackInSlot(0)));
			if(tool instanceof ToolLaserAxe)
				stack = ((ItemLaserToolOpend)ModItems.LaserAxeOpen.get()).getItemWithColor(LaserItemUtils.getColor(te.handler.getStackInSlot(0)));
			if(tool instanceof ToolLaserShovel)
				stack = ((ItemLaserToolOpend)ModItems.LaserShovelOpen.get()).getItemWithColor(LaserItemUtils.getColor(te.handler.getStackInSlot(0)));
			if(tool instanceof ToolLaserHoe)
				stack = ((ItemLaserToolOpend)ModItems.LaserHoeOpen.get()).getItemWithColor(LaserItemUtils.getColor(te.handler.getStackInSlot(0)));
			
			if(stack.getItem() == ModItems.LaserToolOpened.get())
				RenderUtils.renderItemOnTop(matrix, stack, -0.17f, 0.1f, -0.1f, 0.5f, bufferIn, combinedLightIn, combinedOverlayIn);
			else {
				matrix.pushPose();
				matrix.mulPose(Vector3f.ZP.rotationDegrees(-90));
				matrix.mulPose(Vector3f.XP.rotationDegrees(-90));
				matrix.translate(-.54f, -.35f, -.4f);
				RenderUtils.renderItemOnTop(matrix, stack, -0.17f, 0.1f, -0.1f, 0.5f, bufferIn, combinedLightIn, combinedOverlayIn);
				matrix.popPose();
			}
				
		}
		else if(te.handler.getStackInSlot(0).getItem() == ModItems.LaserMultiTool.get()) {
			ItemStack stack = ((ItemLaserToolOpend)ModItems.LaserMultiToolOpen.get()).getItemWithColor(LaserItemUtils.getColor(te.handler.getStackInSlot(0)));
			matrix.mulPose(Vector3f.YP.rotationDegrees(90));
			RenderUtils.renderItemOnTop(matrix, stack, -0.5f, -.42f, -0.5f, 0.5f, .5f, bufferIn, combinedLightIn, combinedOverlayIn);
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