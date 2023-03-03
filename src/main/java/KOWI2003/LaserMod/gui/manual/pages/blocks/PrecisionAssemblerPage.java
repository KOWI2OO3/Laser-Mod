package KOWI2003.LaserMod.gui.manual.pages.blocks;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.init.ModBlocks;

public class PrecisionAssemblerPage extends GuiContext {

	public PrecisionAssemblerPage(String id) {
		super(id);
		setParent(ManualHandler.BlocksHeader);
		setTitle(ModBlocks.PrecisionAssembler.get());
	}
	
	@Override
	public void init() {
		super.init();
	}

}
