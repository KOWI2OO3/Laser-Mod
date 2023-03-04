package KOWI2003.LaserMod.gui.manual.pages.blocks;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModBlocks;
import net.minecraft.world.item.ItemStack;

public class LaserProjectorPage extends GuiContext {

	public LaserProjectorPage(String id) {
		super(id);
		setParent(ManualHandler.BlocksHeader);
		setTitle(ModBlocks.LaserProjector.get());
	}
	
	@Override
	public void init() {
		super.init();
		
		addComponent(new ItemComponent("item", -45, -10, new ItemStack(ModBlocks.LaserProjector.get()), 8f));
		
		addComponent(new TextBoxComponent("info", 0, -30, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "An advanced type of laser capable of projecting complex shapes, supply it with an redstone signal to turn it on", null));

		addComponent(new TextBoxComponent("info", 0, 1, 200, 0, new float[] {.4f, .4f, .4f}, "Its fully modifiable through its gui, choose one of the presets and start modifying it, click the elements on the left to add them to the scene you'r working on and select them to modify there properties", null));

//		addPageSelector(-100 + 35, 30, 0, ManualHandler.UpgradesHeader, "Upgrades");
//		addComponent(new TextBoxComponent("info", -100, 30, 100, 0, null, "Also Check: ", null));
	}

}
