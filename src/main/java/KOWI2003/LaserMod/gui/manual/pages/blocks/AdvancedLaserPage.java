package KOWI2003.LaserMod.gui.manual.pages.blocks;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModBlocks;
import net.minecraft.world.item.ItemStack;

public class AdvancedLaserPage extends GuiContext {

	public AdvancedLaserPage(String id) {
		super(id);
		setParent(ManualHandler.BlocksHeader);
		setTitle(ModBlocks.AdvancedLaser.get());
	}
	
	@Override
	public void init() {
		super.init();
		
		addComponent(new ItemComponent("item", -45, -15, new ItemStack(ModBlocks.AdvancedLaser.get()), 10f));
		
		addComponent(new TextBoxComponent("info", 0, -35, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "An advanced version of the simple laser, it can be placed in any direction. supply it with a redstone signal to turn it on.", null));

		addComponent(new TextBoxComponent("info", 0, -9, 200, 0, new float[] {.4f, .4f, .4f}, "It can also be upgraded like the simple laser, but this device has de ability to rotate its laser emitter, to shoot it out at an angle!", null));

		addComponent(new TextBoxComponent("info", 0, 20, 200, 0, new float[] {.4f, .4f, .4f}, "NOTE: This feature is Work In Progress, so it might not fucntion correctly for you!", new float[] {.7f, .2f, .2f}));
		
		addPageSelector(-100 + 33, 30, 0, ManualHandler.UpgradesHeader, "Upgrades");
		addComponent(new TextBoxComponent("info", -100, 30, 100, 0, null, "Also Check: ", null));
	}

}
