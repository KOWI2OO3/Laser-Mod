package KOWI2003.LaserMod.gui.manual.pages.items;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModItems;
import net.minecraft.world.item.ItemStack;

public class IRGlassesPage extends GuiContext {

	public IRGlassesPage(String id) {
		super(id);
		setTitle(ModItems.IR_Glasses.get());
		setParent(ManualHandler.ItemsHeader);
	}

	@Override
	public void init() {
		super.init();
		
		addComponent(new ItemComponent("item", -45, -5, new ItemStack(ModItems.IR_Glasses.get()), 10f));
		
		addComponent(new TextBoxComponent("info", 0, -20, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "manual.items.ir_glasses.info.base", null));

//		addComponent(new TextBoxComponent("info", 0, 1, 200, 0, new float[] {.4f, .4f, .4f}, "It can also be upgraded like the simple laser, but this device has de ability to rotate its laser emitter, to shoot it out at an angle!", null));

//		addPageSelector(-100 + 33, 30, 0, ManualHandler.UpgradesHeader, "Upgrades");
//		addComponent(new TextBoxComponent("info", -100, 30, 100, 0, null, "Also Check: ", null));
	}
}
