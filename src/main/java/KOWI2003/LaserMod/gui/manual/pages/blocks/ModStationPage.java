package KOWI2003.LaserMod.gui.manual.pages.blocks;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModBlocks;
import net.minecraft.world.item.ItemStack;

public class ModStationPage extends GuiContext {

	public ModStationPage(String id) {
		super(id);
		setParent(ManualHandler.BlocksHeader);
		setTitle(ModBlocks.ModStation.get());
	}
	
	@Override
	public void init() {
		super.init();

		addComponent(new ItemComponent("item", -45, -15, new ItemStack(ModBlocks.ModStation.get()), 10f));
		
		addComponent(new TextBoxComponent("info", 0, -30, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "This device is used to apply upgrades to laser armor and laser tools!", null));

//		addComponent(new TextBoxComponent("info", 0, 1, 200, 0, new float[] {.4f, .4f, .4f}, "One of the main uses is to craft upgrades for the various items and blocks!", null));

		addPageSelector(-100 + 35, 30, 0, ManualHandler.UpgradesHeader, "Upgrades");

		addPageSelector(-100 + 63, 30, 0, ManualHandler.LaserTools, "Laser Tools");
		addPageSelector(-100 + 97, 30, 0, ManualHandler.LaserArmor, "Laser Armor");
		addComponent(new TextBoxComponent("info", -100, 30, 100, 0, null, "Also Check: ", null));
		addComponent(new TextBoxComponent("info", -100 + 57, 30, 100, 0, null, ", ", null));
		addComponent(new TextBoxComponent("info", -100 + 91, 30, 100, 0, null, ", ", null));
	}

}
