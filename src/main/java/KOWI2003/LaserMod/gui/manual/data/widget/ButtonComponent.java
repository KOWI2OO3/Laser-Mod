package KOWI2003.LaserMod.gui.manual.data.widget;

import KOWI2003.LaserMod.gui.manual.ManualWidgetTypes;
import KOWI2003.LaserMod.gui.manual.data.WidgetBase;
import net.minecraft.resources.ResourceLocation;

public class ButtonComponent extends WidgetBase {

	public ResourceLocation Texture;
	public IButtonAction action;
	
	public ButtonComponent(String name, int x, int y, int width, int height, String tooltip, float[] tooltipColor,
			float[] backgroundColor, ResourceLocation texture, IButtonAction action) {
		super(name, x, y, width, height, tooltip, tooltipColor, backgroundColor);
		type = ManualWidgetTypes.Button;
		Texture = texture;
		this.action = action;
	}

	public ButtonComponent(String name, int x, int y, int width, int height, ResourceLocation texture,
			IButtonAction action) {
		super(name, x, y, width, height);
		type = ManualWidgetTypes.Button;
		Texture = texture;
		this.action = action;
	}

	public ButtonComponent(String name, int x, int y, int width, int height, float[] backgroundColor,
			ResourceLocation texture, IButtonAction action) {
		super(name, x, y, width, height, backgroundColor);
		type = ManualWidgetTypes.Button;
		Texture = texture;
		this.action = action;
	}

	public ButtonComponent(String name, int x, int y, int width, int height, String tooltip, float[] tooltipColor,
			ResourceLocation texture, IButtonAction action) {
		super(name, x, y, width, height, tooltip, tooltipColor);
		type = ManualWidgetTypes.Button;
		Texture = texture;
		this.action = action;
	}
	
	public ButtonComponent(String name, int x, int y, int width, int height, String tooltip, float[] tooltipColor,
			float[] backgroundColor, IButtonAction action) {
		this(name, x, y, width, height, tooltip, tooltipColor, backgroundColor, new ResourceLocation("textures/gui/widgets.png"), action);
		type = ManualWidgetTypes.Button;
	}

	public ButtonComponent(String name, int x, int y, int width, int height, IButtonAction action) {
		this(name, x, y, width, height, new ResourceLocation("textures/gui/widgets.png"), action);
		type = ManualWidgetTypes.Button;
	}

	public ButtonComponent(String name, int x, int y, int width, int height, float[] backgroundColor, IButtonAction action) {
		this(name, x, y, width, height, backgroundColor, new ResourceLocation("textures/gui/widgets.png"), action);
		type = ManualWidgetTypes.Button;
	}

	public ButtonComponent(String name, int x, int y, int width, int height, String tooltip, float[] tooltipColor, IButtonAction action) {
		this(name, x, y, width, height, tooltip, tooltipColor, new ResourceLocation("textures/gui/widgets.png"), action);
		type = ManualWidgetTypes.Button;
	}
	
	
}
