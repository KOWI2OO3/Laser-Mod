package KOWI2003.LaserMod.gui.manual.pages.items.upgrades;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.init.ModUpgrades;

public class UpgradeNoDamagePage extends GuiContext {

	public UpgradeNoDamagePage(String id) {
		super(id);
		setParent(ManualHandler.UpgradesHeader);
		setTitle(ModUpgrades.NoDamage.get().getName(ModUpgrades.NoDamage.get().getDefaultInstance()).getString());
	}
	
}
