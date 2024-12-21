package KOWI2003.LaserMod.gui.manual.pages.subpages;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.init.ModItems;

public class ItemsPage extends GuiContext {

	public ItemsPage(String id) {
		super(id);
		setParent(ManualHandler.MAIN);
		setTitle("manual.items.header");
	}
	
	@Override
	public void init() {
		super.init();
		
		int x = -105;
		int y = -55;

//		x = -5;
//		y = -55;
		
		int offset = 10;
		offset = addPageSelector(x, y, offset, ManualHandler.LaserCrystal, ModItems.LaserCrystal.get().getDefaultInstance());
		offset = addPageSelector(x, y, offset, ManualHandler.LaserTools, ModItems.LaserToolShell.get().getDefaultInstance());
		offset = addPageSelector(x, y, offset, ManualHandler.LaserArmor, ModItems.LaserChestplate.get().getDefaultInstance());
		offset = addPageSelector(x, y, offset, ManualHandler.IRGlasses, ModItems.IR_Glasses.get().getDefaultInstance());
		offset = addPageSelector(x, y, offset, ManualHandler.Linker, ModItems.Linker.get().getDefaultInstance());
	}
}
