package KOWI2003.LaserMod.gui.widgets.properties;

import java.lang.reflect.Field;

import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.tileentities.projector.data.ProjectorWidgetData;
import KOWI2003.LaserMod.utils.RenderUtils;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;

public class DataProperty<T> extends AbstractWidget {

	protected T value;
	String name;
	private boolean hasChanged = false;
	
	boolean isHoveredOver = false;
	
	public DataProperty(int x, int y, int width, int height, String displayName, T value) {
		super(x, y, width, height, MutableComponent.create(new LiteralContents(Utils.SpaceOnUpperCase(displayName))));
		this.value = value;
		this.name = displayName;
	}

	public void setValue(Object value) {
		if(this.value.getClass() == value.getClass()) {
			this.value = (T)value;
		}
	}
	
	public T getValue() {
		return value;
	}
	
	public String getDisplayName() {
		return getMessage().getString();
	}
	
	public void sync(ProjectorWidgetData data) {
		try {
			Field field = data.getClass().getField(getFieldName());
			field.set(data, value);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		hasChanged = false;
	}
	
	public boolean hasChanged() {
		return hasChanged;
	}
	
	public void setHasChanged() {
		hasChanged = true;
	}
	
	public void setHoveredOver(boolean isHoveredOver) {
		this.isHoveredOver = isHoveredOver;
	}
	
	public boolean isHoveredOver() {
		return isHoveredOver;
	}
	
	@Override
	public void render(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
		RenderUtils.Gui.drawStringWithinBox(matrix, getDisplayName() + ": ", x + 2, y, 35f, 10, 0xffffff);
	}
	
	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
		return super.isMouseOver(mouseX, mouseY);
	}
	
	public final String getFieldName() {
		return name;
	}
	
	@Override
	public void updateNarration(NarrationElementOutput p_259858_) {}
}
