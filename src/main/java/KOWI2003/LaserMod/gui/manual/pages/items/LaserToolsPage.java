package KOWI2003.LaserMod.gui.manual.pages.items;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;

public class LaserToolsPage extends GuiContext {

	public LaserToolsPage(String id) {
		super(id);
		setTitle("Laser Tools");
		setParent(ManualHandler.ItemsHeader);
	}

	@Override
	public void init() {
		super.init();
	}
}
