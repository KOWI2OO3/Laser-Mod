package KOWI2003.LaserMod.gui.manual.pages.items;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModItems;

public class LaserToolsPage extends GuiContext {

	public LaserToolsPage(String id) {
		super(id);
		setTitle("manual.items.category.tools");
		setParent(ManualHandler.ItemsHeader);
	}

	@Override
	public void init() {
		super.init();
		
		addComponent(new ItemComponent("item", -50, -5, ModItems.LaserPickaxe.get().getDefaultInstance(), 10));
		
		addComponent(new TextBoxComponent("info", -20, -35, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "manual.items.tools.info.base", null));
		addComponent(new TextBoxComponent("info", -20, -8, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "manual.items.tools.info.extra", null));
		addComponent(new TextBoxComponent("info", -20, 10, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "manual.items.tools.info.usage", null));

		addPageSelector(-100 + width("manual.misc.see") / 2 + 3, 35, 0, ManualHandler.UpgradesHeader, "Upgrades");
		addComponent(new TextBoxComponent("info", -100, 35, 100, 0, null, "manual.misc.see", null));
	}
}
