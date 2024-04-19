package KOWI2003.LaserMod.gui.manual.pages.items.upgrades;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModUpgrades;
import net.minecraft.world.item.ItemStack;

public class UpgradeDamagePage extends GuiContext {

	public UpgradeDamagePage(String id) {
		super(id);
		setParent(ManualHandler.UpgradesHeader);
		setTitle(ModUpgrades.Damage3.get().getName(ModUpgrades.Damage3.get().getDefaultInstance()).getString());
	}	
	
	@Override
	public void init() {
		super.init();
		
		addComponent(new ItemComponent("item", -45, -5, new ItemStack(ModUpgrades.Damage3.get()), 10f));
		
		addComponent(new TextBoxComponent("info", 0, -40, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "manual.upgrades.damage.info.base", null));
		addComponent(new TextBoxComponent("info", 0, -20, 200, 0, new float[] {.4f, .4f, .4f}, "manual.upgrades.damage.info.context.tools", null));
		addComponent(new TextBoxComponent("info", 0, 0, 200, 0, new float[] {.4f, .4f, .4f}, "manual.upgrades.damage.info.context.armor", null));
		addComponent(new TextBoxComponent("info", -20, 28, 247, 0, new float[] {.4f, .4f, .4f}, "manual.upgrades.damage.info.note", new float[] {.7f, .2f, .2f}));

	}
}
