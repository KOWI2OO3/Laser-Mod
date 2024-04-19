package KOWI2003.LaserMod.gui.manual.pages.integration.cctweaked.base;

import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;

public class FunctionPage extends GuiContext {

    protected String functionName;

	public FunctionPage(String functionName, GuiContext parent) {
		super(functionName.toLowerCase() + "_function");
		setParent(parent);
		setTitle(parent.getTitle());
		this.functionName = functionName;
	}
	
	@Override
	public void init() {
		super.init();

        int x = 0;
        int y = -30;

        float[] color = new float[] {1f, 1f, .5f};

        addComponent(new TextBoxComponent("function", x, y, 300, 10000, "manual." + getParent().getId() + "." + functionName + ".name", color, true));
        addComponent(new TextBoxComponent("docs", x, y + 20, 300, 10000, "manual."  + getParent().getId() + "." + functionName + ".desc", new float[0], true));
	}
}
