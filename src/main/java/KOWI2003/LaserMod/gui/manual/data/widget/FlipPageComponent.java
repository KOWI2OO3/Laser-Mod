package KOWI2003.LaserMod.gui.manual.data.widget;

import KOWI2003.LaserMod.gui.manual.ManualWidgetTypes;
import KOWI2003.LaserMod.gui.manual.data.WidgetBase;

public class FlipPageComponent extends WidgetBase {

	public String GuiId;
	public ButtonIconType buttonType;

	public FlipPageComponent(String name, int x, int y, int width, int height, String tooltip, float[] tooltipColor,
			float[] backgroundColor, String guiId, ButtonIconType buttonType) {
		super(name, x, y, width, height, tooltip, tooltipColor, backgroundColor);
		GuiId = guiId;
		this.type = ManualWidgetTypes.PageFlip;
		this.buttonType = buttonType;
	}

	public FlipPageComponent(String name, int x, int y, int width, int height, String guiId, ButtonIconType buttonType) {
		super(name, x, y, width, height);
		GuiId = guiId;
		this.type = ManualWidgetTypes.PageFlip;
		this.buttonType = buttonType;
	}

	public FlipPageComponent(String name, int x, int y, int width, int height, float[] backgroundColor, String guiId, ButtonIconType buttonType) {
		super(name, x, y, width, height, backgroundColor);
		GuiId = guiId;
		this.type = ManualWidgetTypes.PageFlip;
		this.buttonType = buttonType;
	}

	public FlipPageComponent(String name, int x, int y, int width, int height, String tooltip, float[] tooltipColor,
			String guiId, ButtonIconType buttonType) {
		super(name, x, y, width, height, tooltip, tooltipColor);
		GuiId = guiId;
		this.type = ManualWidgetTypes.PageFlip;
		this.buttonType = buttonType;
	}
	
	public static enum ButtonIconType {
		NextArrow, PrevArrow, CompleteReturn
	}
}
