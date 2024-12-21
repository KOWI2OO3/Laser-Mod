package KOWI2003.LaserMod.tileentities.projector.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;

import KOWI2003.LaserMod.tileentities.projector.data.ProjectorShapeData;
import KOWI2003.LaserMod.tileentities.projector.data.ProjectorShapeData.ShapeType;
import KOWI2003.LaserMod.tileentities.projector.gui.RenderContext;
import KOWI2003.LaserMod.utils.RenderUtils;
import KOWI2003.LaserMod.utils.Utils;

public class ProjectorShape extends ProjectorWidget {

	public float[] color = new float[] {1, 0, 0};
	public ShapeType type;
	
	public ProjectorShape(ProjectorShapeData data) {
		super(data);
		color = data.color;
		type = data.type;
		setHasAlpha(true);
		setScalingType(ScalingType.Normal);
	}
	
	public ProjectorShape(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	public ProjectorShape(float x, float y, float z, float width, float height, float depth) {
		super(x, y, z, width, height, depth);
	}
	
	@Override
	public PoseStack getRenderMatrix(PoseStack matrix) {
		return super.getRenderMatrix(matrix);
	}
	
	@Override
	public void renderWidget(RenderContext<?> context) {
		context.getMatrix().pushPose();
		PoseStack matrix = getRenderMatrix(context.getMatrix());
		color = Utils.parseColor(color);
		color = new float[] {color[0], color[1], color[2], getAlpha(context.getTileentity())};
		RenderSystem.disableTexture();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableBlend();
		RenderSystem.enableCull();
		
		switch(type) {
			case Cube:
//				RenderUtils.renderCube(matrix, getX(), getY(), getZ(), getWidth(), getHeight(), getDepth(), color[0], color[1], color[2], (double)getAlpha(context.getTileentity()));
				RenderUtils.renderCube(context.getBuffer().getBuffer(RenderUtils.triangularEntityTranslucent(RenderUtils.getEmptyTexture(), Mode.QUADS)),
						matrix, new float[] {getX(), getY(), getZ()}, new float[] {getWidth(), getHeight(), getDepth()},
						new float[] {0, 0, 1, 1}, color, context.getCombinedLight(), context.getCombinedOverlay());
				break;
//			case Sphere:
////				RenderUtils.renderSphere
//				RenderUtils.renderUvSphere(matrix, 10, 10, color);
//				break;
			case Pyramide:
				RenderUtils.renderPyramide(RenderUtils.getEmptyTexture(), context.getBuffer(), matrix, new float[] {getX(), getY(), getZ()}, new float[] {getWidth(), getHeight(), getDepth()},
						new float[] {0, 0, 1, 1}, color, context.getCombinedLight(), context.getCombinedOverlay());
				
//				RenderUtils.renderPyramide(matrix, getX(), getY(), getZ(), getWidth(), getHeight(), getDepth(), color[0], color[1], color[2], (double)getAlpha(context.getTileentity()));
//				RenderUtils.bindTexture(new ResourceLocation("textures/block/stone.png"));
//				RenderUtils.renderPyramide(matrix, getX(), getY(), getZ(), getWidth(), getHeight(), getDepth(), 0, 0, 1, 1);
				break;
			default:
				break;
		}
		
		RenderSystem.disableCull();
		RenderSystem.enableTexture();
		context.getMatrix().popPose();
	}

	@Override
	public float getX() {
		return super.getX() - getWidth()/2f;
	}
	
	@Override
	public float getY() {
		return super.getY() - getHeight()/2f;
	}
	
	@Override
	public float getZ() {
		return super.getZ() - getDepth()/2f;
	}
	
	@Override
	public float getWidth() {
		return width * getScale();
	}
	
	@Override
	public float getHeight() {
		return height * getScale();
	}
	
	@Override
	public float getDepth() {
		return depth * getScale();
	}
	
	@Override
	public float getScale() {
		return 40;
	}

}
