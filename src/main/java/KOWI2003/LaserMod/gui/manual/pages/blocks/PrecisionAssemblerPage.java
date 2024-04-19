package KOWI2003.LaserMod.gui.manual.pages.blocks;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.data.widget.ItemComponent;
import KOWI2003.LaserMod.gui.manual.data.widget.TextBoxComponent;
import KOWI2003.LaserMod.init.ModBlocks;
import net.minecraft.world.item.ItemStack;

public class PrecisionAssemblerPage extends GuiContext {

	public PrecisionAssemblerPage(String id) {
		super(id);
		setParent(ManualHandler.BlocksHeader);
		setTitle(ModBlocks.PrecisionAssembler.get());
	}
	
	@Override
	public void init() {
		super.init();
		
		addComponent(new ItemComponent("item", -45, -15, new ItemStack(ModBlocks.PrecisionAssembler.get()), 10f));
		
		addComponent(new TextBoxComponent("info", 0, -30, 200, 0, new float[] {0.4f, 0.4f, 0.4f}, "manual.blocks.assembler.info.basic", null));

		addComponent(new TextBoxComponent("info", 0, 1, 200, 0, new float[] {.4f, .4f, .4f}, "manual.blocks.assembler.info.extra", null));

		int offset = width("manual.misc.see") / 2 + 3;
		addPageSelector(-100 + offset, 30, 0, ManualHandler.UpgradesHeader);
		offset += width(ManualHandler.UpgradesHeader.getTitle()) / 2 + 3;
		addComponent(new TextBoxComponent("info", -100 + offset - 6, 30, 100, 0, null, ", ", null));
		addPageSelector(-100 + offset, 30, 0, ManualHandler.LaserTools);
		addComponent(new TextBoxComponent("info", -100, 30, 100, 0, null, "manual.misc.see", null));
	}

}
