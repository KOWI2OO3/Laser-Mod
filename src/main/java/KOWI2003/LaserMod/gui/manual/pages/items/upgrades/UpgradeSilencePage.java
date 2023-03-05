package KOWI2003.LaserMod.gui.manual.pages.items.upgrades;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModUpgrades;
import net.minecraft.world.item.ItemStack;

public class UpgradeSilencePage extends GuiContext {

	public UpgradeSilencePage(String id) {
		super(id);
		setParent(ManualHandler.UpgradesHeader);
		setTitle(ModUpgrades.Silence.get().getName(ModUpgrades.Silence.get().getDefaultInstance()).getString());
	}

	@Override
	public void init() {
		super.init();
		
		addComponent(new ItemComponent("item", -45, -5, new ItemStack(ModUpgrades.Silence.get()), 10f));
		
		addComponent(new TextBoxComponent("info", 0, -30, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "This upgrade silences the laser", null));

		addComponent(new TextBoxComponent("info", 0, -10, 200, 0, new float[] {.4f, .4f, .4f}, "The laser will be completely silence with this upgrade, no startup or shutdown sounds, no winding sound of the device being active", null));


	}
	
}
