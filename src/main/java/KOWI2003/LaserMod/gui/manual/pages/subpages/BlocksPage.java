package KOWI2003.LaserMod.gui.manual.pages.subpages;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.init.ModBlocks;
import net.minecraft.world.item.ItemStack;

public class BlocksPage extends GuiContext {

	public BlocksPage(String id) {
		super(id);
		setParent(ManualHandler.MAIN);
		setTitle("manual.blocks.header");
	}
	
	@Override
	public void init() {
		super.init();
		
		int x = -105;
		int y = -55;
		
		int offset = 10;
		offset = addPageSelector(x, y, offset, ManualHandler.Laser, new ItemStack(ModBlocks.Laser.get()));
		offset = addPageSelector(x, y, offset, ManualHandler.LaserCatcher, new ItemStack(ModBlocks.LaserCatcher.get()));
		offset = addPageSelector(x, y, offset, ManualHandler.Infuser, new ItemStack(ModBlocks.Infuser.get()));
		offset = addPageSelector(x, y, offset, ManualHandler.ModStation, new ItemStack(ModBlocks.ModStation.get()));
		offset = addPageSelector(x, y, offset, ManualHandler.LaserProjector, new ItemStack(ModBlocks.LaserProjector.get()));
		offset = addPageSelector(x, y, offset, ManualHandler.LaserController, new ItemStack(ModBlocks.LaserController.get()));
		
		offset = 10;
		x += 70;
		offset = addPageSelector(x, y, offset, ManualHandler.Mirror, new ItemStack(ModBlocks.Mirror.get()));
		offset = addPageSelector(x, y, offset, ManualHandler.PrecisionAssembler, new ItemStack(ModBlocks.PrecisionAssembler.get()));
		offset = addPageSelector(x, y, offset, ManualHandler.AdvancedLaser, new ItemStack(ModBlocks.AdvancedLaser.get()));
//		offset = addPageSelector(x, y, offset, ManualHandler.LaserCrafter, new ItemStack(ModBlocks.LaserCrafter.get()));
	}
}
