package KOWI2003.LaserMod.tileentities.projector.gui.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import KOWI2003.LaserMod.tileentities.projector.data.ProjectorWidgetData;
import KOWI2003.LaserMod.tileentities.projector.gui.RenderContext;
import KOWI2003.LaserMod.utils.RenderUtils;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ProjectorWidget {

	public float x = 0;
	public float y = 0;
	public float z = 0;
	protected float width = 20;
	protected float height = 20;
	protected float depth = 20;
	protected float rotation = 0;
	
	public float scale = 1;
	
	protected float alpha = 1.0f;
	
	private ScalingType scalingType;
	
	private boolean hasAlpha = true;
	
	ProjectorWidgetData data;
	
	public boolean renderOutline = false;
	public boolean isFlatHovering = false;
	public boolean isHovering = false;
	
	public ProjectorWidget(ProjectorWidgetData data) {
		this(data.x, data.y, data.z, data.width, data.height, data.depth);
		scale = data.scale;
		this.alpha = data.alpha;
		this.rotation = data.rotation;
		this.data = data;
	}
	
	public ProjectorWidget(float x, float y, float width, float height) {
		this(x, y, 0, width, height, 0);
	}
	
	public ProjectorWidget(float x, float y, float z, float width, float height, float depth) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.width = width;
		this.height = height;
		this.depth = depth;
	}

	public PoseStack getRenderMatrix(PoseStack matrix) {
		matrix.mulPose(Vector3f.YP.rotationDegrees(data.rotation));
		return matrix;
	}
	
	public void renderWidget(RenderContext<?> context) {
		
	}
	
	public void setScalingType(ScalingType scalingType) {
		this.scalingType = scalingType;
	}

	public void renderOutline(RenderContext<?> context) {
		renderOutline(context, new float[] {1, 1, 0});
	}
	
	public void renderOutline(RenderContext<?> context, float[] color) {
		RenderUtils.renderOutline(getRenderMatrix(context.getMatrix()), getX(), getY(), getZ(), getWidth(), getHeight(), getDepth(), color);
	}
	
	public void onClicked(float mouseX, float mouseY, int buttonId) {
		
	}
	
	public boolean isCursorOver(float mouseX, float mouseY) {
		return mouseX >= getX() && mouseX <= getX() + getWidth() && mouseY >= getY() && mouseY <= getY() + getHeight();
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public float getDepth() {
		return depth;
	}
	
	public float getRotation() {
		return rotation;
	}
	
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getZ() {
		return z;
	}
	
	public boolean isScalable() {
		return scalingType == ScalingType.Normal;
	}
	
	public boolean isRectangularScalable() {
		return scalingType == ScalingType.Rectangular;
	}
	
	public boolean hasAlpha() {
		return hasAlpha;
	}
	
	public void setHasAlpha(boolean hasAlpha) {
		this.hasAlpha = hasAlpha;
	}
	
	public float getAlpha(BlockEntity te) {
		return hasAlpha() ? this.alpha : 1.0f;
	}
	
	public void setScale(float scale) {
		this.scale = scale;
		if(isRectangularScalable()) {
			width = 1;
			height = 1;
			depth = 1;
		}
	}
	
	public void setScale(float width, float height, float depth) {
		if(isScalable()) {
			this.width = width;
			this.height = height;
			this.depth = depth;
		}
	}
	
	public float getScale() {
		return scale;
	}
	
	public static enum ScalingType {
		None, Normal, Rectangular;
	}
	
}
