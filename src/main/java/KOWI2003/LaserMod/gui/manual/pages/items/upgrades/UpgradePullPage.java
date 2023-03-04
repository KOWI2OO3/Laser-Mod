package KOWI2003.LaserMod.gui.manual.pages.items.upgrades;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModUpgrades;
import net.minecraft.world.item.ItemStack;

public class UpgradePullPage extends GuiContext {

	public UpgradePullPage(String id) {
		super(id);
		setParent(ManualHandler.UpgradesHeader);
		setTitle(ModUpgrades.Pull.get());
	}
	
	@Override
	public void init() {
		super.init();
		
		addComponent(new ItemComponent("item", -45, -5, new ItemStack(ModUpgrades.Pull.get()), 10f));
		
		addComponent(new TextBoxComponent("info", 0, -35, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "When applied to an laser, it will pulls all the entities in the beam closer to the origin of the laser", null));

		addComponent(new TextBoxComponent("info", 0, -15, 200, 0, new float[] {.4f, .4f, .4f}, "When applied to laser tools, it acts as attract, pulling entities that have been hit closer to make damaging faster", null));

		addComponent(new TextBoxComponent("info", 0, 15, 200, 0, new float[] {.4f, .4f, .4f}, "NOTE: Having both push and pull upgrades installed on the same device negates the effect of both", new float[] {.7f, .2f, .2f}));

	}
	
}
