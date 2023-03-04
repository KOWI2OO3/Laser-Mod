package KOWI2003.LaserMod.gui.manual.pages.items;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModItems;
import net.minecraft.world.item.ItemStack;

public class LinkerPage extends GuiContext {

	public LinkerPage(String id) {
		super(id);
		setTitle(ModItems.Linker.get());
		setParent(ManualHandler.ItemsHeader);
	}

	@Override
	public void init() {
		super.init();
		
		addComponent(new ItemComponent("item", -45, -8, new ItemStack(ModItems.Linker.get()), 10f));
		
		addComponent(new TextBoxComponent("info", 0, -30, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "An simple handheld device used for linking an laser to an laser controller.", null));

		addComponent(new TextBoxComponent("info", 0, 1, 200, 0, new float[] {.4f, .4f, .4f}, "while sneaking, rightclick on an laser to save it in the linker, then rightclick on an laser controller, to link the laser and the controller together", null));

		addPageSelector(-100 + 33, 30, 0, ManualHandler.LaserController, "Laser Controller");
		addComponent(new TextBoxComponent("info", -100, 30, 100, 0, null, "Also Check: ", null));
	}
}
