package KOWI2003.LaserMod.items;

import KOWI2003.LaserMod.gui.GuiManual;
import KOWI2003.LaserMod.gui.manual.ManualHandler;
import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.pages.MainPage;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemManual extends ItemDefault {

	public ItemManual(Properties properties) {
		super(properties);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		if(level.isClientSide) {
			if(!ManualHandler.isInitialized())
				ManualHandler.initContext();
//			Minecraft.getInstance().setScreen(new GuiManual(ManualHandler.MAIN));
			GuiContext data = new MainPage("main");
			data.init();
			ManualHandler.initContext();
			Minecraft.getInstance().setScreen(new GuiManual(data));
		}
		return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
	}
	
}
