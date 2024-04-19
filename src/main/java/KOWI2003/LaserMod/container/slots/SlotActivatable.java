package KOWI2003.LaserMod.container.slots;

import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotActivatable extends SlotItemHandler {

	boolean isActive = true;

	public SlotActivatable(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	@Override
	public boolean isActive() {
		return isActive;
	}

}
