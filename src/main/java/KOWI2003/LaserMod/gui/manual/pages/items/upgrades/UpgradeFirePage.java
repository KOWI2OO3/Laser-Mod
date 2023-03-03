package KOWI2003.LaserMod.gui.manual.pages.items.upgrades;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.init.ModUpgrades;

public class UpgradeFirePage extends GuiContext {

	public UpgradeFirePage(String id) {
		super(id);
		setParent(ManualHandler.UpgradesHeader);
		setTitle(ModUpgrades.Fire.get().getName(ModUpgrades.Fire.get().getDefaultInstance()).getString());
	}

}
