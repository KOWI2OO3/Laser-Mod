package KOWI2003.LaserMod.gui.manual.pages.blocks;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModBlocks;
import KOWI2003.LaserMod.utils.ModChecker;
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
		
		addComponent(new TextBoxComponent("info", 0, -30, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "manual.blocks.projector.info.basic", null));

		addComponent(new TextBoxComponent("info", 0, 1, 200, 0, new float[] {.4f, .4f, .4f}, "manual.blocks.projector.info.extra", null));

		if(ModChecker.isComputercraftLoaded()) {
			addPageSelector(-100 + width("manual.misc.see") / 2  + 3, 30, 0, ManualHandler.CCIntegrationHeader);
			addComponent(new TextBoxComponent("info", -100, 30, 100, 0, null, "manual.misc.see", null));
		}
	}

}
