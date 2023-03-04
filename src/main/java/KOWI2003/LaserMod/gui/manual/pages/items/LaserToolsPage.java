package KOWI2003.LaserMod.gui.manual.pages.items;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModItems;

public class LaserToolsPage extends GuiContext {

	public LaserToolsPage(String id) {
		super(id);
		setTitle("Laser Tools");
		setParent(ManualHandler.ItemsHeader);
	}

	@Override
	public void init() {
		super.init();
		
		addComponent(new ItemComponent("item", -50, -5, ModItems.LaserPickaxe.get().getDefaultInstance(), 10));
		
		addComponent(new TextBoxComponent("info", -20, -35, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "Special Tools created of an base which holds the laser crystal,"
				+ " and when activated acts like the corresponding tool", null));
		addComponent(new TextBoxComponent("info", -20, -8, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "Laser Tools can be upgraded using an modification station and upgrades", null));
		addComponent(new TextBoxComponent("info", -20, 10, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "To activate the tools, rightclick while holding them in your hand", null));

		addPageSelector(-100 + 33, 35, 0, ManualHandler.UpgradesHeader, "Upgrades");
		addComponent(new TextBoxComponent("info", -100, 35, 100, 0, null, "Also Check: ", null));
	}
}
