package KOWI2003.LaserMod.tileentities.projector.gui.widgets;

import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.tileentities.projector.data.ProjectorItemData;
import KOWI2003.LaserMod.tileentities.projector.gui.RenderContext;
import KOWI2003.LaserMod.utils.RenderUtils;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

public class ProjectorItem extends ProjectorWidget {

	public ItemStack stack;
	public boolean renderOnTop = false;
	
	public ProjectorItem(ProjectorItemData data) {
		super(data);
		stack = data.item;
		renderOnTop = data.onSurface;
		setHasAlpha(false);
		setScalingType(ScalingType.Rectangular);
	}
	
	public ProjectorItem(float x, float y, float z, ItemStack stack) {
		super(x, y, z, 1, 1, 1);
		this.stack = stack;
		setHasAlpha(false);
		setScalingType(ScalingType.Rectangular);
	}
	
	public ProjectorItem(float x, float y, ItemStack stack) {
		super(x, y, 1, 1);
		this.stack = stack;
		setHasAlpha(false);
		setScalingType(ScalingType.Rectangular);
	}
	
	@Override
	public PoseStack getRenderMatrix(PoseStack matrix) {
		matrix = super.getRenderMatrix(matrix);
		matrix.translate(-0.5f, 0, -0.5f);
		return matrix;
	}
	
	@Override
	public void renderWidget(RenderContext<?> context) {
		if(stack.isEmpty())
			renderOutline(context, new float[] {0f, 0.5f, 0.8f});
		else {
			if(renderOnTop)
				RenderUtils.renderItemOnTop(getRenderMatrix(context.getMatrix()), stack, x, y, z, 100f * getScale() * (stack.getItem() instanceof BlockItem ? 1 : 0.5f), context.getBuffer(), context.getCombinedLight(), context.getCombinedOverlay());
			else
				RenderUtils.renderItem(getRenderMatrix(context.getMatrix()), stack, x, y, z, 100f * getScale(), context.getBuffer(), context.getCombinedLight(), context.getCombinedOverlay());
		}
	}
	
	@Override
	public float getX() {
		return super.getX() - getWidth()/2f + 0.5f;
	}
	
	@Override
	public float getY() {
		return super.getY() - 11.5f + 0.5f + 11.5f - getHeight()/2f;
	}
	
	@Override
	public float getZ() {
		return super.getZ() -getDepth()/2f + 0.5f;
	}
	
	@Override
	public float getWidth() {
		return 52 * getScale();
	}
	
	@Override
	public float getHeight() {
		return 52 * getScale();
	}
	
	@Override
	public float getDepth() {
		return 52 * getScale();
	}

}
