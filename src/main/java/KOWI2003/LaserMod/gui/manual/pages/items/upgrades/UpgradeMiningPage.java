package KOWI2003.LaserMod.gui.manual.pages.items.upgrades;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModUpgrades;
import net.minecraft.world.item.ItemStack;

public class UpgradeMiningPage extends GuiContext {

	public UpgradeMiningPage(String id) {
		super(id);
		setParent(ManualHandler.UpgradesHeader);
		setTitle(ModUpgrades.Mining.get().getName(ModUpgrades.Mining.get().getDefaultInstance()).getString());
	}

	@Override
	public void init() {
		super.init();
		
		addComponent(new ItemComponent("item", -45, -5, new ItemStack(ModUpgrades.Mining.get()), 10f));
		
		addComponent(new TextBoxComponent("info", 0, -30, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "An upgrade which, when applied to an laser, will allow it to break blocks in the world", null));

		addComponent(new TextBoxComponent("info", 0, -5, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "When applied to an laser pickaxe, shovel or axe, it will act as Hammering, which will break an area of 3x3 blocks when mining. To mine a single block with this upgrade just sneak while breaking.", null));

	}	
	
}
