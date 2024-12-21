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
		
		addComponent(new TextBoxComponent("info", 0, -30, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "manual.blocks.infuser.info.basic", null));

		addComponent(new TextBoxComponent("info", 0, 1, 200, 0, new float[] {.4f, .4f, .4f}, "manual.blocks.infuser.info.extra", null));
		
		addPageSelector(-100 + width("manual.misc.see") / 2 + 3, 30, 0, ManualHandler.LaserCrystal);
		addPageSelector(-100 + width("manual.misc.see") / 2 + 6 + width(ManualHandler.LaserCrystal.getTitle())/2, 30, 0, ManualHandler.LaserTools);
		addComponent(new TextBoxComponent("info", -100, 30, 100, 0, null, "manual.misc.see", null));
		addComponent(new TextBoxComponent("info", -100 + width("manual.misc.see") / 2 + 1 + width(ManualHandler.LaserCrystal.getTitle())/2, 30, 100, 0, null, ", ", null));
		
	}

}
