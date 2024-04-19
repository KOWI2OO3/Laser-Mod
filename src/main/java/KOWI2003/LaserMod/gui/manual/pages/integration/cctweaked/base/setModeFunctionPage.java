package KOWI2003.LaserMod.gui.manual.pages.integration.cctweaked.base;

import java.util.List;
import java.util.function.Supplier;

import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.tileentities.TileEntityLaser.MODE;

public class setModeFunctionPage extends GuiContext {

	String functionName;

	public setModeFunctionPage(String functionName, GuiContext parent) {
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

		Supplier<String> max = () -> (MODE.values().length - 1) + "";
		Supplier<String> typeMappings = () -> {
			String result = "";
			for (MODE mode : MODE.values()) {
				result += mode.ordinal() + " = " + "\u00A7a" + mode.getFormalName() + "\u00A7r";
				if(mode.ordinal() < MODE.values().length - 1)
					result += ", ";
			}
			return result;
		};

		addComponent(new TextBoxComponent("function", x, y, 300, 10000, "manual." + getParent().getId() + "." + functionName + ".name", color, true));
        addComponent(new TextBoxComponent("docs", x, y + 20, 400, 10000, "manual."  + getParent().getId() + "." + functionName + ".desc", new float[0], true).withArgument(List.of(max, typeMappings)));
	}
}