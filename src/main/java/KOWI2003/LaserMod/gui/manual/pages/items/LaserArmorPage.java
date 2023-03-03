package KOWI2003.LaserMod.gui.manual.pages.items;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;

public class LaserArmorPage extends GuiContext {

	public LaserArmorPage(String id) {
		super(id);
		setTitle("Laser Armor");
		setParent(ManualHandler.ItemsHeader);
	}

	@Override
	public void init() {
		super.init();
	}
}
