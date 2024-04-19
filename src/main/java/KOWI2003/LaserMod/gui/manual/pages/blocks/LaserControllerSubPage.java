package KOWI2003.LaserMod.gui.manual.pages.blocks;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.ImageComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModBlocks;
import net.minecraft.resources.ResourceLocation;

public class LaserControllerSubPage extends GuiContext {

	public LaserControllerSubPage(String id) {
		super(id);
		setParent(ManualHandler.BlocksHeader);
		setTitle(ModBlocks.LaserController.get());
	}
	
	@Override
	public void init() {
		super.init();
		
		//TODO requires second page to display and explain the levers!
		
		addComponent(new TextBoxComponent("info", 0, -35, 200, 0, new float[] {.4f, .4f, .4f}, "manual.blocks.controller.sub.info.basic", null));

		addComponent(new TextBoxComponent("info", 0, 0, 200, 0, new float[] {.4f, .4f, .4f}, "manual.blocks.controller.sub.info.extra", null));

		addComponent(new ImageComponent("explain", -100, -45, 190, 150, new ResourceLocation(Reference.MODID, "textures/gui/manual/controller_explain.png")));
		
		addPageSelector(-100 + width("manual.misc.see") / 2 + 3, 35, 0, ManualHandler.Linker);
		addComponent(new TextBoxComponent("info", -100, 35, 100, 0, null, "manual.misc.see", null));
	}
}