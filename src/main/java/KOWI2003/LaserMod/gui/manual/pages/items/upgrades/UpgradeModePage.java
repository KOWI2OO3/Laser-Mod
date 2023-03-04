package KOWI2003.LaserMod.gui.manual.pages.items.upgrades;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModUpgrades;
import net.minecraft.world.item.ItemStack;

public class UpgradeModePage extends GuiContext {

	public UpgradeModePage(String id) {
		super(id);
		setParent(ManualHandler.UpgradesHeader);
		setTitle(ModUpgrades.Mode.get().getName(ModUpgrades.Mode.get().getDefaultInstance()).getString());
	}

	@Override
	public void init() {
		super.init();
		
		addComponent(new ItemComponent("item", -45, -5, new ItemStack(ModUpgrades.Mode.get()), 10f));
		
		addComponent(new TextBoxComponent("info", 0, -30, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "An upgrade which can only be applied to an simple and advanced laser, which chages the look of the laser", null));

		addComponent(new TextBoxComponent("info", 0, -10, 200, 0, new float[] {.4f, .4f, .4f}, "This upgrade also allows an laser to be invisble, only to be seen by wearers of IR glasses", null));
		
		addComponent(new TextBoxComponent("info", 0, 15, 200, 0, new float[] {.4f, .4f, .4f}, "NOTE: this is only visual and does not have an effect on the functionality of the laser", new float[] {.7f, .2f, .2f}));

		addPageSelector(-100 + 33, 35, 0, ManualHandler.IRGlasses, "IR Glasses");
		addComponent(new TextBoxComponent("info", -100, 35, 100, 0, null, "Also Check: ", null));
	}
	
}
