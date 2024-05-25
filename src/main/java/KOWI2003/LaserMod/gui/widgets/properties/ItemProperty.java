package KOWI2003.LaserMod.gui.widgets.properties;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.gui.GuiLaserProjector;
import KOWI2003.LaserMod.network.PacketHandler;
import KOWI2003.LaserMod.network.PacketOpenItemPropertyMenu;
import KOWI2003.LaserMod.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class ItemProperty extends DataProperty<ItemStack> {

	GuiLaserProjector gui;
	
	Button openMenu;
	
	public static ItemProperty openInstance;
	
	public ItemProperty(int x, int y, int width, int height, String displayName, ItemStack value, GuiLaserProjector gui) {
		super(x, y, width, height, displayName, value);
		this.gui = gui;
		openMenu = new Button(x + 35, y, width - 40, height, MutableComponent.create(new LiteralContents("Edit")), (button) -> {onOpenMenu();});
	}
	
	void onOpenMenu() {
		openInstance = this;
		PacketHandler.sendToServer(new PacketOpenItemPropertyMenu(this));
	}
	
	public void onCloseMenu() {
		Minecraft.getInstance().setScreen(gui);
		openInstance = null;
	}
	
	public ItemStackHandler getItemStackHandler() {
		ItemStackHandler handler = new ItemStackHandler(1);
		getValue().setCount(1);
		handler.setStackInSlot(0, getValue());
		return handler;
	}
	
	@Override
	public void render(@Nonnull PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
		openMenu.render(matrix, mouseX, mouseY, partialTicks);
		RenderUtils.Gui.drawStringWithinBox(matrix, getDisplayName() + ": ", x + 2, y, 35f, 10, 0xffffff);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		return openMenu.mouseClicked(mouseX, mouseY, button);
	}
	
	@Override
	public void mouseMoved(double mouseX, double mouseY) {
		super.mouseMoved(mouseX, mouseY);
		openMenu.mouseMoved(mouseX, mouseY);
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		return openMenu.mouseReleased(mouseX, mouseY, button);
	}
	
	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
	}

}
