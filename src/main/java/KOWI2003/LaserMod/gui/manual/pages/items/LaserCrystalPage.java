package KOWI2003.LaserMod.gui.manual.pages.items;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModItems;
import net.minecraft.world.item.ItemStack;

public class LaserCrystalPage extends GuiContext {

	public LaserCrystalPage(String id) {
		super(id);
		setTitle(ModItems.LaserCrystal.get());
		setParent(ManualHandler.ItemsHeader);
	}

	@Override
	public void init() {
		super.init();
		
		addComponent(new ItemComponent("item", -45, -5, new ItemStack(ModItems.LaserCrystal.get()), 10f));
		
		addComponent(new TextBoxComponent("info", 0, -30, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "The base material used to create anything laser related", null));

		addComponent(new TextBoxComponent("info", 0, -10, 200, 0, new float[] {.4f, .4f, .4f}, "This crystal has the ability to bend light into a straight beam of light", null));

		addComponent(new TextBoxComponent("info", 0, 10, 204, 0, new float[] {.4f, .4f, .4f}, "L.A.S.E.R standing for Light Amplification by Stimulated Emission of Radiation", null));

//		addPageSelector(-100 + 33, 30, 0, ManualHandler.UpgradesHeader, "Upgrades");
//		addComponent(new TextBoxComponent("info", -100, 30, 100, 0, null, "Also Check: ", null));
	}
	
}
