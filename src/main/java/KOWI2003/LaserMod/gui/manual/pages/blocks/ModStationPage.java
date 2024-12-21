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
		
		addComponent(new TextBoxComponent("info", 0, -30, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "manual.blocks.mod_station.info.basic", null));

//		addComponent(new TextBoxComponent("info", 0, 1, 200, 0, new float[] {.4f, .4f, .4f}, "One of the main uses is to craft upgrades for the various items and blocks!", null));

		int offset = width("manual.misc.see") / 2 + 3;
		addPageSelector(-100 + offset, 30, 0, ManualHandler.UpgradesHeader);
		offset += width(ManualHandler.UpgradesHeader.getTitle()) / 2 + 3;
		addComponent(new TextBoxComponent("info", -100 + offset - 6, 30, 100, 0, null, ", ", null));

		addPageSelector(-100 + offset, 30, 0, ManualHandler.LaserTools);
		offset += width(ManualHandler.LaserTools.getTitle()) / 2 + 3;
		addComponent(new TextBoxComponent("info", -100 + offset - 5, 30, 100, 0, null, ", ", null));

		addPageSelector(-100 + offset, 30, 0, ManualHandler.LaserArmor);
		
		addComponent(new TextBoxComponent("info", -100, 30, 100, 0, null, "manual.misc.see ", null));
	}

}
