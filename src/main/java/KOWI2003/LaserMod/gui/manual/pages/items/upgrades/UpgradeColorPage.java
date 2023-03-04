package KOWI2003.LaserMod.gui.manual.pages.items.upgrades;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModUpgrades;
import net.minecraft.world.item.ItemStack;

public class UpgradeColorPage extends GuiContext {

	public UpgradeColorPage(String id) {
		super(id);
		setParent(ManualHandler.UpgradesHeader);
		setTitle(ModUpgrades.Color.get().getName(ModUpgrades.Color.get().getDefaultInstance()).getString());
	}

	@Override
	public void init() {
		super.init();
		
		addComponent(new ItemComponent("item", -45, -5, new ItemStack(ModUpgrades.Color.get()), 10f));
		
		addComponent(new TextBoxComponent("info", 0, -30, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "This upgrade allows you to color whatever it is applied to", null));

		addComponent(new TextBoxComponent("info", 0, -10, 200, 0, new float[] {.4f, .4f, .4f}, "can be applied to lasers, to color the laser comming out of it", null));
		addComponent(new TextBoxComponent("info", 0, 5, 200, 0, new float[] {.4f, .4f, .4f}, "can be applied to laser tool, to modify the color of the tool", null));
		addComponent(new TextBoxComponent("info", 0, 20, 200, 0, new float[] {.4f, .4f, .4f}, "can be applied to laser armor, to modify the color of the armor piece", null));


	}
	
}
