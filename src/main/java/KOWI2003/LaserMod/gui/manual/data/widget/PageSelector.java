package KOWI2003.LaserMod.gui.manual.data.widget;

import KOWI2003.LaserMod.gui.manual.ManualWidgetTypes;
import KOWI2003.LaserMod.gui.manual.data.WidgetBase;
import net.minecraft.world.item.ItemStack;

public class PageSelector extends WidgetBase {

	public String GuiId;
	public String Text;
	public float[] TextColor;
	public float[] TextHoverColor;
	public ItemStack item;
	public boolean centered;
	
	public PageSelector(String name, int x, int y, int width, int height, String tooltip, float[] tooltipColor,
			float[] backgroundColor, String guiId, String text, ItemStack item, boolean centered) {
		super(name, x, y, width, height, tooltip, tooltipColor, backgroundColor);
		GuiId = guiId;
		Text = text;
		this.item = item;
		this.TextColor = null;
		type = ManualWidgetTypes.PageSelector;
		this.centered = centered;
	}
	
	public PageSelector(String name, int x, int y, int width, int height, String guiId, String text,
			ItemStack item, boolean centered) {
		super(name, x, y, width, height);
		GuiId = guiId;
		Text = text;
		this.item = item;
		this.TextColor = null;
		type = ManualWidgetTypes.PageSelector;
		this.centered = centered;
	}

	public PageSelector(String name, int x, int y, int width, int height, float[] backgroundColor, String guiId,
			String text, ItemStack item, boolean centered) {
		super(name, x, y, width, height, backgroundColor);
		GuiId = guiId;
		Text = text;
		this.item = item;
		this.TextColor = null;
		type = ManualWidgetTypes.PageSelector;
		this.centered = centered;
	}

	public PageSelector(String name, int x, int y, int width, int height, String tooltip, float[] tooltipColor,
			String guiId, String text, ItemStack item, boolean centered) {
		super(name, x, y, width, height, tooltip, tooltipColor);
		GuiId = guiId;
		Text = text;
		this.item = item;
		this.TextColor = null;
		type = ManualWidgetTypes.PageSelector;
		this.centered = centered;
	}
	
	public PageSelector(String name, int x, int y, int width, int height, String tooltip, float[] tooltipColor,
			float[] backgroundColor, String guiId, String text, boolean centered) {
		super(name, x, y, width, height, tooltip, tooltipColor, backgroundColor);
		GuiId = guiId;
		Text = text;
		this.item = null;
		this.TextColor = null;
		type = ManualWidgetTypes.PageSelector;
		this.centered = centered;
	}
	
	public PageSelector(String name, int x, int y, int width, int height, String guiId, String text, boolean centered) {
		super(name, x, y, width, height);
		GuiId = guiId;
		Text = text;
		this.item = null;
		this.TextColor = null;
		type = ManualWidgetTypes.PageSelector;
		this.centered = centered;
	}

	public PageSelector(String name, int x, int y, int width, int height, float[] backgroundColor, String guiId,
			String text, boolean centered) {
		super(name, x, y, width, height, backgroundColor);
		GuiId = guiId;
		Text = text;
		this.item = null;
		this.TextColor = null;
		type = ManualWidgetTypes.PageSelector;
		this.centered = centered;
	}

	public PageSelector(String name, int x, int y, int width, int height, String tooltip, float[] tooltipColor,
			String guiId, String text, boolean centered) {
		super(name, x, y, width, height, tooltip, tooltipColor);
		GuiId = guiId;
		Text = text;
		this.item = null;
		this.TextColor = null;
		type = ManualWidgetTypes.PageSelector;
		this.centered = centered;
	}
	
	public PageSelector(String name, int x, int y, int width, int height, String tooltip, float[] tooltipColor,
			float[] backgroundColor, String guiId, String text, ItemStack item, float[] TextColor, boolean centered) {
		super(name, x, y, width, height, tooltip, tooltipColor, backgroundColor);
		GuiId = guiId;
		Text = text;
		this.item = item;
		this.TextColor = TextColor;
		type = ManualWidgetTypes.PageSelector;
		this.centered = centered;
	}
	
	public PageSelector(String name, int x, int y, int width, int height, String guiId, String text,
			ItemStack item, float[] TextColor, boolean centered) {
		super(name, x, y, width, height);
		GuiId = guiId;
		Text = text;
		this.item = item;
		this.TextColor = TextColor;
		type = ManualWidgetTypes.PageSelector;
		this.centered = centered;
	}

	public PageSelector(String name, int x, int y, int width, int height, float[] backgroundColor, String guiId,
			String text, ItemStack item, float[] TextColor, boolean centered) {
		super(name, x, y, width, height, backgroundColor);
		GuiId = guiId;
		Text = text;
		this.item = item;
		this.TextColor = TextColor;
		type = ManualWidgetTypes.PageSelector;
		this.centered = centered;
	}

	public PageSelector(String name, int x, int y, int width, int height, String tooltip, float[] tooltipColor,
			String guiId, String text, ItemStack item, float[] TextColor, boolean centered) {
		super(name, x, y, width, height, tooltip, tooltipColor);
		GuiId = guiId;
		Text = text;
		this.item = item;
		this.TextColor = TextColor;
		type = ManualWidgetTypes.PageSelector;
		this.centered = centered;
	}
	
	public PageSelector(String name, int x, int y, int width, int height, String tooltip, float[] tooltipColor,
			float[] backgroundColor, String guiId, String text, float[] TextColor, boolean centered) {
		super(name, x, y, width, height, tooltip, tooltipColor, backgroundColor);
		GuiId = guiId;
		Text = text;
		this.item = null;
		this.TextColor = TextColor;
		type = ManualWidgetTypes.PageSelector;
		this.centered = centered;
	}
	
	public PageSelector(String name, int x, int y, int width, int height, String guiId, String text, float[] TextColor, boolean centered) {
		super(name, x, y, width, height);
		GuiId = guiId;
		Text = text;
		this.item = null;
		this.TextColor = TextColor;
		type = ManualWidgetTypes.PageSelector;
		this.centered = centered;
	}

	public PageSelector(String name, int x, int y, int width, int height, float[] backgroundColor, String guiId,
			String text, float[] TextColor, boolean centered) {
		super(name, x, y, width, height, backgroundColor);
		GuiId = guiId;
		Text = text;
		this.item = null;
		this.TextColor = TextColor;
		type = ManualWidgetTypes.PageSelector;
		this.centered = centered;
	}

	public PageSelector(String name, int x, int y, int width, int height, String tooltip, float[] tooltipColor,
			String guiId, String text, float[] TextColor, boolean centered) {
		super(name, x, y, width, height, tooltip, tooltipColor);
		GuiId = guiId;
		Text = text;
		this.item = null;
		this.TextColor = TextColor;
		type = ManualWidgetTypes.PageSelector;
		this.centered = centered;
	}
	
	public PageSelector(String name, int x, int y, int width, int height, String tooltip, float[] tooltipColor,
			float[] backgroundColor, String guiId, String text, ItemStack item, float[] TextColor, float[] TextHoverColor, boolean centered) {
		super(name, x, y, width, height, tooltip, tooltipColor, backgroundColor);
		GuiId = guiId;
		Text = text;
		this.item = item;
		this.TextColor = TextColor;
		type = ManualWidgetTypes.PageSelector;
		this.centered = centered;
	}
	
	public PageSelector(String name, int x, int y, int width, int height, String guiId, String text,
			ItemStack item, float[] TextColor, float[] TextHoverColor, boolean centered) {
		super(name, x, y, width, height);
		GuiId = guiId;
		Text = text;
		this.item = item;
		this.TextColor = TextColor;
		this.TextHoverColor = TextHoverColor;
		type = ManualWidgetTypes.PageSelector;
		this.centered = centered;
	}

	public PageSelector(String name, int x, int y, int width, int height, float[] backgroundColor, String guiId,
			String text, ItemStack item, float[] TextColor, float[] TextHoverColor, boolean centered) {
		super(name, x, y, width, height, backgroundColor);
		GuiId = guiId;
		Text = text;
		this.item = item;
		this.TextColor = TextColor;
		this.TextHoverColor = TextHoverColor;
		type = ManualWidgetTypes.PageSelector;
		this.centered = centered;
	}

	public PageSelector(String name, int x, int y, int width, int height, String tooltip, float[] tooltipColor,
			String guiId, String text, ItemStack item, float[] TextColor, float[] TextHoverColor, boolean centered) {
		super(name, x, y, width, height, tooltip, tooltipColor);
		GuiId = guiId;
		Text = text;
		this.item = item;
		this.TextColor = TextColor;
		this.TextHoverColor = TextHoverColor;
		type = ManualWidgetTypes.PageSelector;
		this.centered = centered;
	}
	
	public PageSelector(String name, int x, int y, int width, int height, String tooltip, float[] tooltipColor,
			float[] backgroundColor, String guiId, String text, float[] TextColor, float[] TextHoverColor, boolean centered) {
		super(name, x, y, width, height, tooltip, tooltipColor, backgroundColor);
		GuiId = guiId;
		Text = text;
		this.item = null;
		this.TextColor = TextColor;
		this.TextHoverColor = TextHoverColor;
		type = ManualWidgetTypes.PageSelector;
		this.centered = centered;
	}
	
	public PageSelector(String name, int x, int y, int width, int height, String guiId, String text, float[] TextColor, float[] TextHoverColor, boolean centered) {
		super(name, x, y, width, height);
		GuiId = guiId;
		Text = text;
		this.item = null;
		this.TextColor = TextColor;
		this.TextHoverColor = TextHoverColor;
		type = ManualWidgetTypes.PageSelector;
		this.centered = centered;
	}

	public PageSelector(String name, int x, int y, int width, int height, float[] backgroundColor, String guiId,
			String text, float[] TextColor, float[] TextHoverColor, boolean centered) {
		super(name, x, y, width, height, backgroundColor);
		GuiId = guiId;
		Text = text;
		this.item = null;
		this.TextColor = TextColor;
		this.TextHoverColor = TextHoverColor;
		type = ManualWidgetTypes.PageSelector;
		this.centered = centered;
	}

	public PageSelector(String name, int x, int y, int width, int height, String tooltip, float[] tooltipColor,
			String guiId, String text, float[] TextColor, float[] TextHoverColor, boolean centered) {
		super(name, x, y, width, height, tooltip, tooltipColor);
		GuiId = guiId;
		Text = text;
		this.item = null;
		this.TextColor = TextColor;
		this.TextHoverColor = TextHoverColor;
		type = ManualWidgetTypes.PageSelector;
		this.centered = centered;
	}
}
