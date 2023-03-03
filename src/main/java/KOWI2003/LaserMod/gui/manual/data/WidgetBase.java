package KOWI2003.LaserMod.gui.manual.data;

import KOWI2003.LaserMod.gui.manual.ManualWidgetTypes;

public class WidgetBase {

	public String Name;
	public int X;
	public int Y;
	public int Width;
	public int Height;
	
	public String Tooltip;
	public float[] TooltipColor;
	
	public float[] backgroundColor;

	public ManualWidgetTypes type;
	
	public WidgetBase(String name, int x, int y, int width, int height, String tooltip, float[] tooltipColor,
			float[] backgroundColor) {
		Name = name;
		X = x;
		Y = y;
		Width = width;
		Height = height;
		Tooltip = tooltip;
		TooltipColor = tooltipColor;
		this.backgroundColor = backgroundColor;
	}

	public WidgetBase(String name, int x, int y, int width, int height) {
		super();
		Name = name;
		X = x;
		Y = y;
		Width = width;
		Height = height;
	}

	public WidgetBase(String name, int x, int y, int width, int height, float[] backgroundColor) {
		super();
		Name = name;
		X = x;
		Y = y;
		Width = width;
		Height = height;
		this.backgroundColor = backgroundColor;
	}

	public WidgetBase(String name, int x, int y, int width, int height, String tooltip, float[] tooltipColor) {
		super();
		Name = name;
		X = x;
		Y = y;
		Width = width;
		Height = height;
		Tooltip = tooltip;
		TooltipColor = tooltipColor;
	}
	
}
