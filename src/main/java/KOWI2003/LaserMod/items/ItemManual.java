package KOWI2003.LaserMod.items;

import javax.annotation.Nonnull;

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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemManual extends ItemDefault {

	public ItemManual(Properties properties) {
		super(properties);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(@Nonnull Level level, @Nonnull Player player, @Nonnull InteractionHand hand) {
		if(level.isClientSide) {
			handleClient();
		}
		return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
	}
	
	@OnlyIn(Dist.CLIENT)
	void handleClient() {
		if(!ManualHandler.isInitialized())
			ManualHandler.initContext();
			
		GuiContext data = GuiManual.openMenu == null ? new MainPage("main") : GuiManual.openMenu; // from Gui.OpenMenu
		data.init();
		ManualHandler.initContext();
		Minecraft.getInstance().setScreen(new GuiManual(data));
	}
	
}
