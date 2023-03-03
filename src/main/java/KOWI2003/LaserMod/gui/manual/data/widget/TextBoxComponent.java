package KOWI2003.LaserMod.gui.manual.data.widget;

import KOWI2003.LaserMod.gui.manual.ManualWidgetTypes;
import KOWI2003.LaserMod.gui.manual.data.WidgetBase;

public class TextBoxComponent extends WidgetBase {

	public String Text;
	public float[] TextColor;
	
	public boolean centred = false;
	
	public TextBoxComponent(String name, int x, int y, int width, int height, String tooltip, float[] tooltipColor,
			float[] backgroundColor, String text, float[] textColor) {
		super(name, x, y, width, height, tooltip, tooltipColor, backgroundColor);
		Text = text;
		TextColor = textColor;
		type = ManualWidgetTypes.TextBox;
	}

	public TextBoxComponent(String name, int x, int y, int width, int height, String text, float[] textColor) {
		super(name, x, y, width, height);
		Text = text;
		TextColor = textColor;
		type = ManualWidgetTypes.TextBox;
	}

	public TextBoxComponent(String name, int x, int y, int width, int height, float[] backgroundColor, String text,
			float[] textColor) {
		super(name, x, y, width, height, backgroundColor);
		Text = text;
		TextColor = textColor;
		type = ManualWidgetTypes.TextBox;
	}

	public TextBoxComponent(String name, int x, int y, int width, int height, String tooltip, float[] tooltipColor,
			String text, float[] textColor) {
		super(name, x, y, width, height, tooltip, tooltipColor);
		Text = text;
		TextColor = textColor;
		type = ManualWidgetTypes.TextBox;
	}
	
	//
	public TextBoxComponent(String name, int x, int y, int width, int height, String tooltip, float[] tooltipColor,
			float[] backgroundColor, String text, float[] textColor, boolean Centred) {
		super(name, x, y, width, height, tooltip, tooltipColor, backgroundColor);
		Text = text;
		TextColor = textColor;
		type = ManualWidgetTypes.TextBox;
		centred = Centred;
	}

	public TextBoxComponent(String name, int x, int y, int width, int height, String text, float[] textColor, boolean Centred) {
		super(name, x, y, width, height);
		Text = text;
		TextColor = textColor;
		type = ManualWidgetTypes.TextBox;
		centred = Centred;
	}

	public TextBoxComponent(String name, int x, int y, int width, int height, float[] backgroundColor, String text,
			float[] textColor, boolean Centred) {
		super(name, x, y, width, height, backgroundColor);
		Text = text;
		TextColor = textColor;
		type = ManualWidgetTypes.TextBox;
		centred = Centred;
	}

	public TextBoxComponent(String name, int x, int y, int width, int height, String tooltip, float[] tooltipColor,
			String text, float[] textColor, boolean Centred) {
		super(name, x, y, width, height, tooltip, tooltipColor);
		Text = text;
		TextColor = textColor;
		type = ManualWidgetTypes.TextBox;
		centred = Centred;
	}
}
