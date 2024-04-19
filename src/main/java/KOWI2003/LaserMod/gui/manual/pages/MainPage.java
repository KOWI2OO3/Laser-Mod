package KOWI2003.LaserMod.gui.manual.pages;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;

public class MainPage extends GuiContext {

	public MainPage(String id) {
		super(id);
		setTitle("Laser Mod Manual");
		
	}
	
	@Override
	public void init() {
		super.init();
		
		int offset = 0;
		int x = -110;
		x = -1;
		int y = -35;
		offset = addPageSelector(x, y, offset, ManualHandler.BlocksHeader, ManualHandler.BlocksHeader.getTitle(), true);
		offset = addPageSelector(x, y, offset, ManualHandler.ItemsHeader, ManualHandler.ItemsHeader.getTitle(), true);
		offset = addPageSelector(x, y, offset, ManualHandler.UpgradesHeader, ManualHandler.UpgradesHeader.getTitle(), true);
		offset = addPageSelector(x, y, offset, ManualHandler.IntegrationHeader, ManualHandler.IntegrationHeader.getTitle(), true);
	}
}
