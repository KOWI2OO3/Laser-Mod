package KOWI2003.LaserMod.tileentities.projector.gui;

import java.util.ArrayList;
import java.util.List;

import KOWI2003.LaserMod.tileentities.projector.data.ProjectorItemData;
import KOWI2003.LaserMod.tileentities.projector.data.ProjectorPlayerData;
import KOWI2003.LaserMod.tileentities.projector.data.ProjectorShapeData;
import KOWI2003.LaserMod.tileentities.projector.data.ProjectorTextBoxData;
import KOWI2003.LaserMod.tileentities.projector.data.ProjectorTextData;
import KOWI2003.LaserMod.tileentities.projector.data.ProjectorWidgetData;
import KOWI2003.LaserMod.tileentities.projector.gui.widgets.ProjectorItem;
import KOWI2003.LaserMod.tileentities.projector.gui.widgets.ProjectorPlayer;
import KOWI2003.LaserMod.tileentities.projector.gui.widgets.ProjectorShape;
import KOWI2003.LaserMod.tileentities.projector.gui.widgets.ProjectorText;
import KOWI2003.LaserMod.tileentities.projector.gui.widgets.ProjectorTextBox;
import KOWI2003.LaserMod.tileentities.projector.gui.widgets.ProjectorWidget;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ProjectorGui<T extends BlockEntity> {

	protected List<ProjectorWidget> widgets = new ArrayList<ProjectorWidget>();
	
	T tileentity;
	
//	Vector3f localUp;
//	Vector3f localRight;
	
	public ProjectorGui(T te) {
		this.tileentity = te;
		init();
	}
	
	public void init() {
		widgets.clear();
	}
	
	public final void render(RenderContext<T> context) {
		renderBg(context);
		renderWidgets(context);
		renderFg(context);
	}
	
	private final void renderWidgets(RenderContext<T> context) {
		for (ProjectorWidget widget : widgets) {
			context.getMatrix().pushPose();
			widget.renderWidget(context);	
			context.getMatrix().popPose();
		}
	}
	
	protected void renderBg(RenderContext<T> context) {}
	
	protected void renderFg(RenderContext<T> context) {}
	
	public void onClicked(float mouseX, float mouseY, int buttonId) {
		for (ProjectorWidget widget : widgets) 
			widget.onClicked(mouseX, mouseY, buttonId);
	}
	
	public void onCursorOver(float mouseX, float mouseY) {
		
	}
	
	public T getTileentity() {
		return tileentity;
	}
	
	public List<ProjectorWidget> getWidgets() {
		return widgets;
	}
	
	public static ProjectorWidget getWidget(ProjectorWidgetData data) {
		switch (data.type) {
			case Text:
				return new ProjectorText((ProjectorTextData)data);
			case TextBox:
				return new ProjectorTextBox((ProjectorTextBoxData)data);
			case Item:
				return new ProjectorItem((ProjectorItemData)data);
			case Player:
				return new ProjectorPlayer((ProjectorPlayerData)data);
			case Shape:
				return new ProjectorShape((ProjectorShapeData)data);
			default:
				return null;
		}
	}
	
}
