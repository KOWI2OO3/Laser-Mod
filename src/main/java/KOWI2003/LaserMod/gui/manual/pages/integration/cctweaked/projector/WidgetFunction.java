package KOWI2003.LaserMod.gui.manual.pages.integration.cctweaked.projector;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.tileentities.projector.ProjectorWidgetTypes;

public class WidgetFunction  extends GuiContext {

	String functionName;

	public WidgetFunction(String functionName, GuiContext parent) {
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

		List<ProjectorWidgetTypes> types = new ArrayList<>(List.of(ProjectorWidgetTypes.values()));
		types.remove(ProjectorWidgetTypes.None);

		Supplier<String> max = () -> (types.size() - 1) + "";
		Supplier<String> typeMappings = () -> {
			String result = "";
			int index = 0;
			for (ProjectorWidgetTypes type : types) {
				result += "'\u00A7a" + type.name() + "\u00A7r'";
				if(index < types.size() - 1)
					result += ", ";
					
				index++;
			}
			return result;
		};

		addComponent(new TextBoxComponent("function", x, y, 300, 10000, "manual." + getParent().getId() + "." + functionName + ".name", color, true));
        addComponent(new TextBoxComponent("docs", x, y + 20, 400, 10000, "manual."  + getParent().getId() + "." + functionName + ".desc", new float[0], true).withArgument(List.of(max, typeMappings)));
	
        addPageSelector(-100 + width("manual.misc.see") / 2 + 3, 30, 0, ManualHandler.WidgetsHeader, "manual.integration.cc.widget.header");
		addComponent(new TextBoxComponent("info", -100, 30, 100, 0, null, "manual.misc.see", null));
    }
}
