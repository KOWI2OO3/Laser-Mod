package KOWI2003.LaserMod.gui.manual.pages.items.upgrades;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModUpgrades;
import net.minecraft.world.item.ItemStack;

public class UpgradeFirePage extends GuiContext {

	public UpgradeFirePage(String id) {
		super(id);
		setParent(ManualHandler.UpgradesHeader);
		setTitle(ModUpgrades.Fire.get().getName(ModUpgrades.Fire.get().getDefaultInstance()).getString());
	}

	@Override
	public void init() {
		super.init();
		
		addComponent(new ItemComponent("item", -45, -5, new ItemStack(ModUpgrades.Fire.get()), 10f));
		
//		addComponent(new TextBoxComponent("info", 0, -30, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "When applied to an laser will set fire to entities and flamable blocks that are in the way of the laser", null));

		addComponent(new TextBoxComponent("info", 0, -30, 200, 0, new float[] {.4f, .4f, .4f}, "manual.upgrades.fire.info.base", null));

		addComponent(new TextBoxComponent("info", 0, 5, 204, 0, new float[] {.4f, .4f, .4f}, "manual.upgrades.fire.info.context.laser", null));

	}
	
}
