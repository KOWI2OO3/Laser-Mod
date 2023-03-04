package KOWI2003.LaserMod.gui.manual.pages.blocks;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModBlocks;
import net.minecraft.world.item.ItemStack;

public class MirrorPage extends GuiContext {

	public MirrorPage(String id) {
		super(id);
		setParent(ManualHandler.BlocksHeader);
		setTitle(ModBlocks.Mirror.get());
	}
	
	@Override
	public void init() {
		super.init();

		addComponent(new ItemComponent("item", -45, -10, new ItemStack(ModBlocks.Mirror.get()), 10f));
		
		addComponent(new TextBoxComponent("info", 0, -30, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "This is only an simple mirror, what did you expect here?", null));

		addComponent(new TextBoxComponent("info", 0, 1, 200, 0, new float[] {.4f, .4f, .4f}, "When an laser hits the mirror, it will redirect the laser into an different direction.", null));

		addPageSelector(-100 + 33, 30, 0, ManualHandler.Laser, "Laser");
		addComponent(new TextBoxComponent("info", -100, 30, 100, 0, null, "Also Check: ", null));
		
	}

}
