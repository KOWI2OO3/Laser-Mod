package KOWI2003.LaserMod.gui.manual.pages.blocks;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModBlocks;
import net.minecraft.world.item.ItemStack;

public class AdvancedLaserPage extends GuiContext {

	public AdvancedLaserPage(String id) {
		super(id);
		setParent(ManualHandler.BlocksHeader);
		setTitle(ModBlocks.AdvancedLaser.get());
	}
	
	@Override
	public void init() {
		super.init();
		
		addComponent(new ItemComponent("item", -45, -15, new ItemStack(ModBlocks.AdvancedLaser.get()), 10f));
		
		addComponent(new TextBoxComponent("info", 0, -35, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "manual.blocks.adv_laser.info.basic", null));

		addComponent(new TextBoxComponent("info", 0, -9, 200, 0, new float[] {.4f, .4f, .4f}, "manual.blocks.adv_laser.info.extra", null));

		addComponent(new TextBoxComponent("info", 0, 20, 200, 0, new float[] {.4f, .4f, .4f}, "manual.blocks.adv_laser.info.note", new float[] {.7f, .2f, .2f}));
		
		addPageSelector(-100 + width("manual.misc.see") / 2 + 3, 30, 0, ManualHandler.UpgradesHeader);
		addComponent(new TextBoxComponent("info", -100, 30, 100, 0, null, "manual.misc.see", null));
	}

}
