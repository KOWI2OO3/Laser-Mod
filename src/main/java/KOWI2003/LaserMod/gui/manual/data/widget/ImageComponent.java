package KOWI2003.LaserMod.gui.manual.data.widget;

import KOWI2003.LaserMod.gui.manual.ManualWidgetTypes;
import KOWI2003.LaserMod.gui.manual.data.WidgetBase;
import net.minecraft.resources.ResourceLocation;

public class ImageComponent extends WidgetBase {

	public ResourceLocation ImageLocation;
	public float borderSize = 0;

	public ImageComponent(String name, int x, int y, int width, int height, String tooltip, float[] tooltipColor,
			float[] backgroundColor, ResourceLocation ImageLocation) {
		super(name, x, y, width, height, tooltip, tooltipColor, backgroundColor);
		this.ImageLocation = ImageLocation;
		type = ManualWidgetTypes.Image;
	}

	public ImageComponent(String name, int x, int y, int width, int height, ResourceLocation ImageLocation) {
		super(name, x, y, width, height);
		this.ImageLocation = ImageLocation;
		type = ManualWidgetTypes.Image;
	}

	public ImageComponent(String name, int x, int y, int width, int height, float[] backgroundColor, ResourceLocation ImageLocation) {
		super(name, x, y, width, height, backgroundColor);
		this.ImageLocation = ImageLocation;
		type = ManualWidgetTypes.Image;
	}

	public ImageComponent(String name, int x, int y, int width, int height, String tooltip, float[] tooltipColor, ResourceLocation ImageLocation) {
		super(name, x, y, width, height, tooltip, tooltipColor);
		this.ImageLocation = ImageLocation;
		type = ManualWidgetTypes.Image;
	}
	
	public ImageComponent(String name, int x, int y, int width, int height, String tooltip, float[] tooltipColor,
			float[] backgroundColor, ResourceLocation ImageLocation, int borderSize) {
		super(name, x, y, width, height, tooltip, tooltipColor, backgroundColor);
		this.ImageLocation = ImageLocation;
		this.borderSize = borderSize;
		type = ManualWidgetTypes.Image;
	}

	public ImageComponent(String name, int x, int y, int width, int height, ResourceLocation ImageLocation, int borderSize) {
		super(name, x, y, width, height);
		this.ImageLocation = ImageLocation;
		this.borderSize = borderSize;
		type = ManualWidgetTypes.Image;
	}

	public ImageComponent(String name, int x, int y, int width, int height, float[] backgroundColor, ResourceLocation ImageLocation, int borderSize) {
		super(name, x, y, width, height, backgroundColor);
		this.ImageLocation = ImageLocation;
		this.borderSize = borderSize;
		type = ManualWidgetTypes.Image;
	}

	public ImageComponent(String name, int x, int y, int width, int height, String tooltip, float[] tooltipColor, ResourceLocation ImageLocation, int borderSize) {
		super(name, x, y, width, height, tooltip, tooltipColor);
		this.ImageLocation = ImageLocation;
		this.borderSize = borderSize;
		type = ManualWidgetTypes.Image;
	}
	
}
