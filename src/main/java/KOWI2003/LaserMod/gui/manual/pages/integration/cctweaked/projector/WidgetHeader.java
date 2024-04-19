package KOWI2003.LaserMod.gui.manual.pages.integration.cctweaked.projector;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;

public class WidgetHeader extends GuiContext {

	public WidgetHeader(String id) {
		super(id);
		setParent(ManualHandler.CCIntegrationHeader);
		setTitle("manual.integration.cc.widget.title");
	}
	
	@Override
	public void init() {
		super.init();
        
		int x = -105;
		int y = -35;

        x = -50;
        y = -50;
		// y = -55;
		
		int offset = 10;
		int offsetDecr = 0;

        float[] color = new float[] {.0f, .9f, .0f};
        float[] hoverColor = new float[] {.3f, 1f, .3f};

		offset = addPageSelector(x, y, offset, ManualHandler.getXFunction, "getX", color, hoverColor, true) - offsetDecr;
		offset = addPageSelector(x, y, offset, ManualHandler.getYFunction, "getY", color, hoverColor, true) - offsetDecr;
		offset = addPageSelector(x, y, offset, ManualHandler.getZFunction, "getZ", color, hoverColor, true) - offsetDecr;
		offset = addPageSelector(x, y, offset, ManualHandler.getWidgetsFunction, "getWidth", color, hoverColor, true) - offsetDecr;
		offset = addPageSelector(x, y, offset, ManualHandler.getHeightFunction, "getHeight", color, hoverColor, true) - offsetDecr;
		offset = addPageSelector(x, y, offset, ManualHandler.getDepthFunction, "getDepth", color, hoverColor, true) - offsetDecr;
		offset = addPageSelector(x, y, offset, ManualHandler.getRotationFunction, "getRotation", color, hoverColor, true) - offsetDecr;

        offset = 10;
        x = 0;

		offset = addPageSelector(x, y, offset, ManualHandler.getScaleFunction, "getScale", color, hoverColor, true) - offsetDecr;
		offset = addPageSelector(x, y, offset, ManualHandler.getAlphaFunction, "getAlpha", color, hoverColor, true) - offsetDecr;
		offset = addPageSelector(x, y, offset, ManualHandler.getTypeFunction, "getType", color, hoverColor, true) - offsetDecr;
		offset = addPageSelector(x, y, offset, ManualHandler.getIdFunction, "getId", color, hoverColor, true) - offsetDecr;
		offset = addPageSelector(x, y, offset, ManualHandler.setXFunction, "setX", color, hoverColor, true) - offsetDecr;
		offset = addPageSelector(x, y, offset, ManualHandler.setYFunction, "setY", color, hoverColor, true) - offsetDecr;
		offset = addPageSelector(x, y, offset, ManualHandler.setZFunction, "setZ", color, hoverColor, true) - offsetDecr;
        
        offset = 10;
        x = 50;
        
		offset = addPageSelector(x, y, offset, ManualHandler.setWidthFunction, "setWidth", color, hoverColor, true) - offsetDecr;
		offset = addPageSelector(x, y, offset, ManualHandler.setHeightFunction, "setHeight", color, hoverColor, true) - offsetDecr;
		offset = addPageSelector(x, y, offset, ManualHandler.setWidthFunction, "setDepth", color, hoverColor, true) - offsetDecr;
		offset = addPageSelector(x, y, offset, ManualHandler.setRotationFunction, "setRotation", color, hoverColor, true) - offsetDecr;
		offset = addPageSelector(x, y, offset, ManualHandler.setScaleFunction, "setScale", color, hoverColor, true) - offsetDecr;
		offset = addPageSelector(x, y, offset, ManualHandler.setAlphaFunction, "setAlpha", color, hoverColor, true) - offsetDecr;
	}
}
