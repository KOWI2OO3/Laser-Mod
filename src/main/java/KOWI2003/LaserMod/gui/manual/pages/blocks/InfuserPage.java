package KOWI2003.LaserMod.gui.manual.pages.blocks;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModBlocks;
import net.minecraft.world.item.ItemStack;

public class InfuserPage extends GuiContext {

	public InfuserPage(String id) {
		super(id);
		setParent(ManualHandler.BlocksHeader);
		setTitle(ModBlocks.Infuser.get());
	}
	
	@Override
	public void init() {
		super.init();
		
		addComponent(new ItemComponent("item", -45, -15, new ItemStack(ModBlocks.Infuser.get()), 10f));
		
		addComponent(new TextBoxComponent("info", 0, -30, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "This device is used to craft some of the base materials used for lasers", null));

		addComponent(new TextBoxComponent("info", 0, 1, 200, 0, new float[] {.4f, .4f, .4f}, "It can also be used to charge laser tools and armor!", null));

		addPageSelector(-100 + 35, 30, 0, ManualHandler.LaserCrystal, "Laser Crystal");

		addPageSelector(-100 + 75, 30, 0, ManualHandler.LaserTools, "Laser Tools");
		addComponent(new TextBoxComponent("info", -100, 30, 100, 0, null, "Also Check: ", null));
		addComponent(new TextBoxComponent("info", -100 + 68, 30, 100, 0, null, ", ", null));
		
	}

}
