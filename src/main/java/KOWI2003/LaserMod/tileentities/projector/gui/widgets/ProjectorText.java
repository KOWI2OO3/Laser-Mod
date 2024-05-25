package KOWI2003.LaserMod.tileentities.projector.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import KOWI2003.LaserMod.tileentities.projector.data.ProjectorTextData;
import KOWI2003.LaserMod.tileentities.projector.gui.RenderContext;
import KOWI2003.LaserMod.utils.RenderUtils;
import KOWI2003.LaserMod.utils.TileEntityUtils;
import net.minecraft.client.Minecraft;

public class ProjectorText extends ProjectorWidget {

	String text;
	boolean isCentered = true;
	
	@SuppressWarnings("resource")
	public ProjectorText(ProjectorTextData data) {
		super(data);
		text = TileEntityUtils.StringCommands(Minecraft.getInstance().player, data.text);
		isCentered = data.Centered;
		setScalingType(ScalingType.Rectangular);
	}
	
	public ProjectorText(float x, float y, float z, String text, boolean isCentered) {
		super(x, y, z, 1, 1, 1);
		this.text = text;
		this.isCentered = isCentered;
		setScalingType(ScalingType.Rectangular);
	}
	
	public ProjectorText(float x, float y, String text, boolean isCentered) {
		super(x, y, 1, 1);
		this.text = text;
		this.isCentered = isCentered;
		setScalingType(ScalingType.Rectangular);
	}
	
	@Override
	@SuppressWarnings("resource")
	public PoseStack getRenderMatrix(PoseStack matrix) {
		matrix = super.getRenderMatrix(matrix);
		matrix.translate(x, y, z);
		matrix.mulPose(Vector3f.ZP.rotationDegrees(180f));
		
		if(isCentered)
			matrix.translate(-getScale()*(Minecraft.getInstance().font.width(text)/2f), -Minecraft.getInstance().font.lineHeight * getScale()/2f, 0);
		return matrix;
	}
	
	@Override
	public void renderWidget(RenderContext<?> context) {
		context.getMatrix().pushPose();
		
		RenderSystem.disableCull();
		RenderUtils.renderString(getRenderMatrix(context.getMatrix()), text, 0, 0, 0, getScale(), new float[] {1.0f, 1.0f, 1.0f, getAlpha(context.getTileentity())}, false);
		RenderSystem.enableCull();
		
		context.getMatrix().popPose();
	}

	@Override
	public float getScale() {
		return scale * 2.5f;
	}
	
	@Override
	public float getX() {
		return -1;
	}
	
	@Override
	public float getY() {
		return -1;
	}
	
	@Override
	public float getZ() {
		return super.getZ() - getDepth()/2f;
	}
	
	@Override
	@SuppressWarnings("resource")
	public float getWidth() {
		return Minecraft.getInstance().font.width(text) * getScale();
	}
	
	@Override
	@SuppressWarnings("resource")
	public float getHeight() {
		return Minecraft.getInstance().font.lineHeight * getScale();
	}
	
	@Override
	public float getDepth() {
		return super.getDepth();
	}
	
	
}
