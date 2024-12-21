package KOWI2003.LaserMod.gui.manual.pages.items.upgrades;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModUpgrades;
import net.minecraft.world.item.ItemStack;

public class UpgradeNoDamagePage extends GuiContext {

	public UpgradeNoDamagePage(String id) {
		super(id);
		setParent(ManualHandler.UpgradesHeader);
		setTitle(ModUpgrades.NoDamage.get().getName(ModUpgrades.NoDamage.get().getDefaultInstance()).getString());
	}

	@Override
	public void init() {
		super.init();
		
		addComponent(new ItemComponent("item", -45, -5, new ItemStack(ModUpgrades.NoDamage.get()), 10f));
		
		addComponent(new TextBoxComponent("info", 0, -15, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "manual.upgrades.no_damage.info.base", null));

		addComponent(new TextBoxComponent("info", 0, 10, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "manual.upgrades.no_damage.info.note", new float[] {.7f, .2f, .2f}));

	}
	
}
