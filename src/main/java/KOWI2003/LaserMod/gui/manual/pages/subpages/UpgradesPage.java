package KOWI2003.LaserMod.gui.manual.pages.subpages;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.init.ModUpgrades;

public class UpgradesPage extends GuiContext {

	public UpgradesPage(String id) {
		super(id);
		setParent(ManualHandler.MAIN);
		setTitle("manual.upgrades.header");
	}
	
	@Override
	public void init() {
		super.init();
		initUpgrades(-110, -55);
	}

	public void initUpgrades(int x, int y) {
//		addComponent(new TextBoxComponent("Chapter3Upgrades", x, y, 88, 30, "Upgrades", null));
		x += 5;
		
		int offset = 10;
		offset = addPageSelector(x, y, offset, ManualHandler.UpgradeSpeed, ModUpgrades.Speed.get().getDefaultInstance());
		offset = addPageSelector(x, y, offset, ManualHandler.UpgradeMining, ModUpgrades.Mining.get().getDefaultInstance());
		offset = addPageSelector(x, y, offset, ManualHandler.UpgradeFire, ModUpgrades.Fire.get().getDefaultInstance());
		offset = addPageSelector(x, y, offset, ManualHandler.UpgradeMode, ModUpgrades.Mode.get().getDefaultInstance());
		offset = addPageSelector(x, y, offset, ManualHandler.UpgradeDamage, ModUpgrades.Damage3.get().getDefaultInstance());
		offset = addPageSelector(x, y, offset, ManualHandler.UpgradeNoDamage, ModUpgrades.NoDamage.get().getDefaultInstance());
		offset = addPageSelector(x, y, offset, ManualHandler.UpgradeSilence, ModUpgrades.Silence.get().getDefaultInstance());
		
		offset = 10;
		x += 70;
		offset = addPageSelector(x, y, offset, ManualHandler.UpgradePush, ModUpgrades.Push.get().getDefaultInstance());
		offset = addPageSelector(x, y, offset, ManualHandler.UpgradePull, ModUpgrades.Pull.get().getDefaultInstance());
		offset = addPageSelector(x, y, offset, ManualHandler.UpgradeDistance, ModUpgrades.Distance.get().getDefaultInstance());
		offset = addPageSelector(x, y, offset, ManualHandler.UpgradeAirtightSeal, ModUpgrades.AirtightSeal.get().getDefaultInstance());
		offset = addPageSelector(x, y, offset, ManualHandler.UpgradeCapacity, ModUpgrades.Capacity3.get().getDefaultInstance());
	}
	
}
