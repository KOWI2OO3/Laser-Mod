package KOWI2003.LaserMod.utils.compat.jei;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.Rect2i;

public class SlotMover<T extends AbstractContainerScreen<?>> implements IGuiContainerHandler<T> {

	private static List<Rect2i> list;
	
	public SlotMover() {
		list = new ArrayList<Rect2i>();
	}
	
	public void addRectangle(int x, int y, int width, int height) {
		list.add(new Rect2i(x, y, width, height));
	}
	
	public void removeRectangle(int x, int y, int width, int height) {
		list.remove(new Rect2i(x, y, width, height));
	}
	
	public void addRectangle(Rect2i rect) {
		list.add(rect);
	}
	
	public void removeRectangle(Rect2i rect) {
		list.remove(rect);
	}
	
	@Override
	public List<Rect2i> getGuiExtraAreas(@Nonnull T containerScreen) {
		List<Rect2i> tabBoxes = new ArrayList<>();
		
		for(Rect2i rect : list) {
			if(rect != null)
				tabBoxes.add(rect);
		}
		
		return tabBoxes;
	}
	
	@Override
	public Object getIngredientUnderMouse(@Nonnull T containerScreen, double mouseX, double mouseY) {
		return null;
	}
	
}
