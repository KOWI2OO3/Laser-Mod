package KOWI2003.LaserMod.gui.manual.pages.items.upgrades;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.init.ModUpgrades;

public class UpgradeColorPage extends GuiContext {

	public UpgradeColorPage(String id) {
		super(id);
		setParent(ManualHandler.UpgradesHeader);
		setTitle(ModUpgrades.Color.get().getName(ModUpgrades.Color.get().getDefaultInstance()).getString());
	}

}
