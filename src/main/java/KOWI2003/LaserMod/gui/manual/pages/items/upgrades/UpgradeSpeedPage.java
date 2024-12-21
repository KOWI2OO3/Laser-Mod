package KOWI2003.LaserMod.gui.manual.pages.items.upgrades;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModUpgrades;
import net.minecraft.world.item.ItemStack;

public class UpgradeSpeedPage extends GuiContext {
	
	public UpgradeSpeedPage(String id) {
		super(id);
		setParent(ManualHandler.UpgradesHeader);
		setTitle(ModUpgrades.Speed.get().getName(ModUpgrades.Speed.get().getDefaultInstance()).getString());
	}
	
	@Override
	public void init() {
		super.init();
		
		addComponent(new ItemComponent("item", -45, -5, new ItemStack(ModUpgrades.Speed.get()), 10f));
		
		addComponent(new TextBoxComponent("info", 0, -35, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "manual.upgrades.speed.info.base", null));

		addComponent(new TextBoxComponent("info", 0, -15, 200, 0, new float[] {.4f, .4f, .4f}, "manual.upgrades.speed.info.context.machine", null));

		addComponent(new TextBoxComponent("info", 0, 15, 200, 0, new float[] {.4f, .4f, .4f}, "manual.upgrades.speed.info.context.tools", null));

	}
}
