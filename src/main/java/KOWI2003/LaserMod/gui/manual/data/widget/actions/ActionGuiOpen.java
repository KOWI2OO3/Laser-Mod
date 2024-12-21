package KOWI2003.LaserMod.gui.manual.data.widget.actions;

import KOWI2003.LaserMod.gui.manual.data.widget.ButtonComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.IButtonAction;

public class ActionGuiOpen implements IButtonAction {

	public String GuiId;
	
	public ActionGuiOpen(String guiId) {
		GuiId = guiId;
	}

	@Override
	public void run(ButtonComponent button) {}	
}
