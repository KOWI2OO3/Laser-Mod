package KOWI2003.LaserMod.gui.manual.pages.integration.cctweaked;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;

public class LaserFunctions extends GuiContext {

	public LaserFunctions(String id) {
		super(id);
		setParent(ManualHandler.CCIntegrationHeader);
		setTitle("manual.integration.cc.laser.title");
	}
	
	@Override
	public void init() {
		super.init();
        
		int x = -105;
		int y = -35;

        x = 0;
        y = -50;
		// y = -55;
		
		int offset = 10;
		int offsetDecr = 0;

        float[] color = new float[] {.0f, .9f, .0f};
        float[] hoverColor = new float[] {.3f, 1f, .3f};

		offset = addPageSelector(x, y, offset, ManualHandler.setColorFunction, "setColor", color, hoverColor, true) - offsetDecr;
		offset = addPageSelector(x, y, offset, ManualHandler.setModeFunction, "setMode", color, hoverColor, true) - offsetDecr;
		offset = addPageSelector(x, y, offset, ManualHandler.canBeColoredFunction, "canBeColored", color, hoverColor, true) - offsetDecr;
	}
}