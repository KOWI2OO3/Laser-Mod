package KOWI2003.LaserMod.gui.manual.pages.blocks;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModBlocks;
import net.minecraft.world.item.ItemStack;

public class LaserPage extends GuiContext {

	public LaserPage(String id) {
		super(id);
		setParent(ManualHandler.BlocksHeader);
		setTitle(ModBlocks.Laser.get());
	}
	
	@Override
	public void init() {
		super.init();
		
		addComponent(new ItemComponent("item", -45, -15, new ItemStack(ModBlocks.Laser.get()), 10f));
		
		addComponent(new TextBoxComponent("info", 0, -30, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "The most simple laser, it can be placed in any direction. supply it with a redstone signal to turn it on.", null));

		addComponent(new TextBoxComponent("info", 0, 1, 200, 0, new float[] {.4f, .4f, .4f}, "The Laser can be modified using upgrades, when you open the laser's gui you will be able to place the upgrades inside.", null));

		addPageSelector(-100 + 35, 30, 0, ManualHandler.UpgradesHeader, "Upgrades");
		addComponent(new TextBoxComponent("info", -100, 30, 100, 0, null, "Also Check: ", null));
	}
	
}
