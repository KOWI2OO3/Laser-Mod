package KOWI2003.LaserMod.gui.manual.pages.items.upgrades;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.init.ModUpgrades;

public class UpgradeAirtightSealPage extends GuiContext {

	public UpgradeAirtightSealPage(String id) {
		super(id);
		setParent(ManualHandler.UpgradesHeader);
		setTitle(ModUpgrades.AirtightSeal.get().getName(ModUpgrades.AirtightSeal.get().getDefaultInstance()).getString());
	}

}
