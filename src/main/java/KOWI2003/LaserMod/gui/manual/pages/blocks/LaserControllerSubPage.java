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
		
		addComponent(new TextBoxComponent("info", 0, -35, 200, 0, new float[] {.4f, .4f, .4f}, "If the laser had an mode upgrade then lever in the top left can be used to cycle through the different modes, holding shift makes you cycle through them in the opposite direction.", null));

		addComponent(new TextBoxComponent("info", 0, 0, 200, 0, new float[] {.4f, .4f, .4f}, "If the laser has an color upgrade then the three small levers at the bottom can be used to change the color remotely", null));

		addComponent(new ImageComponent("explain", -100, -45, 190, 150, new ResourceLocation(Reference.MODID, "textures/gui/manual/controller_explain.png")));
		
		addPageSelector(-100 + 33, 35, 0, ManualHandler.Linker, "Linker");
		addComponent(new TextBoxComponent("info", -100, 35, 100, 0, null, "Also Check: ", null));
	}
}