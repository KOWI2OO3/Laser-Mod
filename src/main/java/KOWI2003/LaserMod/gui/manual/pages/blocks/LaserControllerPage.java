package KOWI2003.LaserMod.gui.manual.pages.blocks;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModBlocks;
import net.minecraft.world.item.ItemStack;

public class LaserControllerPage extends GuiContext {

	public LaserControllerPage(String id) {
		super(id);
		setParent(ManualHandler.BlocksHeader);
		setTitle(ModBlocks.LaserController.get());
	}
	
	@Override
	public void init() {
		super.init();
		
		addComponent(new ItemComponent("item", -45, -8, new ItemStack(ModBlocks.LaserController.get()), 8f));
		
		addComponent(new TextBoxComponent("info", 0, -35, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "This device is used to control laser from an distance without needing an redstone signal!", null));

		addComponent(new TextBoxComponent("info", 0, -10, 200, 0, new float[] {.4f, .4f, .4f}, "Link an laser to the controller using an [REFERENCE], when linked you can flip the big lever to turn it on and off. ", null));
		
		addPageSelector(-100 + 33, 35, 0, ManualHandler.Linker, "Linker");
		addComponent(new TextBoxComponent("info", -100, 35, 100, 0, null, "Also Check: ", null));
	}

}
