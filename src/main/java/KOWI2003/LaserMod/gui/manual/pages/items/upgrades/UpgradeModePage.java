package KOWI2003.LaserMod.gui.manual.pages.items.upgrades;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.init.ModUpgrades;

public class UpgradeModePage extends GuiContext {

	public UpgradeModePage(String id) {
		super(id);
		setParent(ManualHandler.UpgradesHeader);
		setTitle(ModUpgrades.Mode.get().getName(ModUpgrades.Mode.get().getDefaultInstance()).getString());
	}

	
	
}
