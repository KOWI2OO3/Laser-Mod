package KOWI2003.LaserMod.gui.manual.pages.items.upgrades;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModUpgrades;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;

public class UpgradeAirtightSealPage extends GuiContext {

	public UpgradeAirtightSealPage(String id) {
		super(id);
		setParent(ManualHandler.UpgradesHeader);
		setTitle(ModUpgrades.AirtightSeal.get().getName(ModUpgrades.AirtightSeal.get().getDefaultInstance()).getString());
	}
	
	@Override
	public void init() {
		super.init();
		
		addComponent(new ItemComponent("item", -45, -5, new ItemStack(ModUpgrades.AirtightSeal.get()), 10f));
		
		addComponent(new TextBoxComponent("info", 0, -30, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "This upgrade can only be applied to the laser helmet", null));

		addComponent(new TextBoxComponent("info", 0, -12, 200, 0, new float[] {.4f, .4f, .4f}, "When applied it will seal the helmet so you can breath underwater", null));

		if(ModList.get().isLoaded("aerospace"))
			addComponent(new TextBoxComponent("info", 0, 6, 204, 0, new float[] {.4f, .4f, .4f}, "It can also be an standin for an oxygen mask to go to space!", null));

		addComponent(new TextBoxComponent("info", 0, 28, 200, 0, new float[] {.4f, .4f, .4f}, "NOTE: The helmet needs to be active for this upgrade to take effect", new float[] {.7f, .2f, .2f}));

	}

}
