package KOWI2003.LaserMod.gui.manual.pages.subpages;

import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import dan200.computercraft.shared.Registry;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModInfo;

public class IntegrationPage extends GuiContext {

	public IntegrationPage(String id) {
		super(id);
		setParent(ManualHandler.MAIN);
		setTitle("manual.integration.header");
	}
	
	@Override
	public void init() {
		super.init();
		
		int x = -105;
		int y = -55;
		
		for(IModInfo info : ModList.get().getMods()) {
			if(info.getModId().equals("computercraft")) {
				int offset = 10;
				offset = addPageSelector(x, y, offset, ManualHandler.CCIntegrationHeader, info.getDisplayName(), new ItemStack(Registry.ModBlocks.COMPUTER_NORMAL.get()));
			}
		}
	}
}
