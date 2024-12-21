package KOWI2003.LaserMod.gui.manual.pages.integration;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModBlocks;
import net.minecraft.world.item.ItemStack;

public class CCIntegrationPage extends GuiContext {

	public CCIntegrationPage(String id) {
		super(id);
		setParent(ManualHandler.IntegrationHeader);
		setTitle("manual.integration.cc.header");
	}
	
	@Override
	public void init() {
		super.init();
        
		int x = -105;
		int y = -35;

        addComponent(new TextBoxComponent("info", x, y, 141, 0, new float[] {0.4f, 0.4f, 0.4f}, "manual.integration.cc.info", null));

        x = 0;
        y = -45;
		
		int offset = 10;

		offset = addPageSelector(x, y, offset, ManualHandler.BaseFunctionsHeader, "manual.integration.cc.base.header", new ItemStack(ModBlocks.LaserController.get()));
		offset = addPageSelector(x, y, offset, ManualHandler.LaserFunctionsHeader, "manual.integration.cc.laser.header", new ItemStack(ModBlocks.Laser.get()));
		offset = addPageSelector(x, y, offset, ManualHandler.AdvancedLaserFunctionsHeader, "manual.integration.cc.adv_laser.header", new ItemStack(ModBlocks.AdvancedLaser.get()));
		offset = addPageSelector(x, y, offset, ManualHandler.ProjectorFunctionsHeader, "manual.integration.cc.projector.header", new ItemStack(ModBlocks.LaserProjector.get()));
		offset = addPageSelector(x, y, offset, ManualHandler.WidgetsHeader, "manual.integration.cc.widget.header", new ItemStack(ModBlocks.LaserProjector.get()));
		
		offset = width("manual.misc.see") / 2 + 3;
		addPageSelector(-100 + offset, 30, 0, ManualHandler.LaserController);
		offset += width(ManualHandler.LaserController.getTitle()) / 2 + 3;
		addComponent(new TextBoxComponent("info", -100 + offset - 6, 30, 100, 0, null, ",", null));

		addPageSelector(-100 + offset, 30, 0, ManualHandler.Laser);
		offset += width(ManualHandler.Laser.getTitle()) / 2 + 3;
		addComponent(new TextBoxComponent("info", -100 + offset - 6, 30, 100, 0, null, ",", null));

		addPageSelector(-100 + offset, 30, 0, ManualHandler.AdvancedLaser);
		offset += width(ManualHandler.AdvancedLaser.getTitle()) / 2 + 3;
		addComponent(new TextBoxComponent("info", -100 + offset - 6, 30, 100, 0, null, ",", null));

		addPageSelector(-100 + offset, 30, 0, ManualHandler.LaserProjector);
		addComponent(new TextBoxComponent("info", -100, 30, 100, 0, null, "manual.misc.see", null));
	}
}