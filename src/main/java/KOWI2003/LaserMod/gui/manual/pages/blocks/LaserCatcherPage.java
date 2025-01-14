package KOWI2003.LaserMod.gui.manual.pages.blocks;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModBlocks;
import net.minecraft.world.item.ItemStack;

public class LaserCatcherPage extends GuiContext {

	public LaserCatcherPage(String id) {
		super(id);
		setParent(ManualHandler.BlocksHeader);
		setTitle(ModBlocks.LaserCatcher.get());
	}
	
	@Override
	public void init() {
		super.init();
		
		addComponent(new ItemComponent("item", -45, -7, new ItemStack(ModBlocks.LaserCatcher.get()), 8f));
		
		addComponent(new TextBoxComponent("info", 0, -30, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "manual.blocks.laser_catcher.info.basic", null));

		addPageSelector(-100 + width("manual.misc.see") / 2 + 3, 30, 0, ManualHandler.Laser, ModBlocks.Laser.get().getName().getString());
		addComponent(new TextBoxComponent("info", -100, 30, 100, 0, null, "manual.misc.see", null));
	}

}
