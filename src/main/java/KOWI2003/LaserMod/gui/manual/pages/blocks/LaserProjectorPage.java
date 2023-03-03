package KOWI2003.LaserMod.gui.manual.pages.blocks;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.init.ModBlocks;

public class LaserProjectorPage extends GuiContext {

	public LaserProjectorPage(String id) {
		super(id);
		setParent(ManualHandler.BlocksHeader);
		setTitle(ModBlocks.LaserProjector.get());
	}
	
	@Override
	public void init() {
		super.init();
	}

}
