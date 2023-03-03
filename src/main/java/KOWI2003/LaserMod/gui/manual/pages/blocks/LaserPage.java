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
		
		addComponent(new ItemComponent("item", -80, -55, new ItemStack(ModBlocks.Laser.get()), 5f));
		
		addComponent(new TextBoxComponent("info", -100, -30, 200, 0, new float[] {.5f, .5f, .5f}, "The most simple laser, it can be placed in any direction. supply it with a redstone signal to turn it on.", null));

		addComponent(new TextBoxComponent("info", -100, 1, 200, 0, new float[] {.5f, .5f, .5f}, "The Laser can be modified using upgrades, when you open the laser's gui you will be able to place the upgrades in it.", null));

		addComponent(new TextBoxComponent("info", -100, 30, 45, 0, null, "Also Check: ", null));
		addPageSelector(-100 + 35, 30, 0, ManualHandler.UpgradesHeader, "upgrades");
	}
	
}
