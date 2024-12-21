package KOWI2003.LaserMod.gui.manual.data.widget;

import KOWI2003.LaserMod.gui.manual.ManualWidgetTypes;
import KOWI2003.LaserMod.gui.manual.data.WidgetBase;
import net.minecraft.world.item.ItemStack;

public class ItemComponent extends WidgetBase {

	public ItemStack stack;
	public float size = 1.0f;

	public ItemComponent(String name, int x, int y, String tooltip, float[] tooltipColor,
			float[] backgroundColor, ItemStack stack, float size) {
		super(name, x, y, (int)size, (int)size, tooltip, tooltipColor, backgroundColor);
		this.stack = stack;
		this.size = size;
		type = ManualWidgetTypes.Item;
	}

	public ItemComponent(String name, int x, int y, ItemStack stack, float size) {
		super(name, x, y, (int)size, (int)size);
		this.stack = stack;
		this.size = size;
		type = ManualWidgetTypes.Item;
	}

	public ItemComponent(String name, int x, int y, float[] backgroundColor, ItemStack stack, float size) {
		super(name, x, y, (int)size, (int)size, backgroundColor);
		this.stack = stack;
		this.size = size;
		type = ManualWidgetTypes.Item;
	}

	public ItemComponent(String name, int x, int y, String tooltip, float[] tooltipColor,
			ItemStack stack, float size) {
		super(name, x, y, (int)size, (int)size, tooltip, tooltipColor);
		this.stack = stack;
		this.size = size;
		type = ManualWidgetTypes.Item;
	}
	
}
