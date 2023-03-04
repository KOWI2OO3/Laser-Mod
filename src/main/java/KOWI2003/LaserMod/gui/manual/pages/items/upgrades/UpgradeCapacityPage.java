package KOWI2003.LaserMod.gui.manual.pages.items.upgrades;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModUpgrades;
import net.minecraft.world.item.ItemStack;

public class UpgradeCapacityPage extends GuiContext {

	public UpgradeCapacityPage(String id) {
		super(id);
		setParent(ManualHandler.UpgradesHeader);
		setTitle(ModUpgrades.Capacity3.get().getName(ModUpgrades.Capacity3.get().getDefaultInstance()).getString());
	}
	
	@Override
	public void init() {
		super.init();
		
		addComponent(new ItemComponent("item", -45, -5, new ItemStack(ModUpgrades.Capacity3.get()), 10f));
		
		addComponent(new TextBoxComponent("info", 0, -20, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "When applied to lase tools or laser armor, it increases the maximum charge that device can hold", null));

		addComponent(new TextBoxComponent("info", -20, 28, 247, 0, new float[] {.4f, .4f, .4f}, "This upgrade has multiple tiers, every tier higher increases the effect of the upgrade", new float[] {.7f, .2f, .2f}));

	}

}
