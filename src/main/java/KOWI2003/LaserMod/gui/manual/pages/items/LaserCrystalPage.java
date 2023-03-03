package KOWI2003.LaserMod.gui.manual.pages.items;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.init.ModItems;

public class LaserCrystalPage extends GuiContext {

	public LaserCrystalPage(String id) {
		super(id);
		setTitle(ModItems.LaserCrystal.get());
		setParent(ManualHandler.ItemsHeader);
	}

	@Override
	public void init() {
		super.init();
	}
	
}
