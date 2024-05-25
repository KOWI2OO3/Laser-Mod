package KOWI2003.LaserMod.container.slots;

import javax.annotation.Nonnull;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class MemorySlot extends SlotItemHandler {

	private ItemStack memory = ItemStack.EMPTY;
	
	protected MemorySlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}
	
	public MemorySlot(int xPosition, int yPosition) {
		super(null, 0, xPosition, yPosition);
	}
	
	public MemorySlot(int xPosition, int yPosition, ItemStack savedStack) {
		super(null, 0, xPosition, yPosition);
		memory = savedStack;
	}
	
	public void setStack(ItemStack stack) { memory = stack; }
	public ItemStack getStack() { return memory; }
	
	@Override
	public ItemStack getItem() {
		return memory;
	}
	
	public boolean isEmpty() { return memory == ItemStack.EMPTY; }
	
	@Override
	public void set(ItemStack stack) {
		setStack(stack.copy());
		memory.setCount(1);
	}
	
	@Override
	public ItemStack remove(int amount) {
		setStack(ItemStack.EMPTY.copy());
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean mayPlace(ItemStack stack) {
		if(isEmpty())
			set(stack);
		else
			setStack(ItemStack.EMPTY.copy());
		return false;
	}
	
	@Override
	public boolean mayPickup(Player playerIn) {
		if(!isEmpty())
			setStack(ItemStack.EMPTY.copy());
		return false;
	}
	
	@Override
	public boolean isSameInventory(@Nonnull Slot slot) {
		return false;
	}
	
	@Override
	public IItemHandler getItemHandler() {
		return null;
	}
	
	@Override
	public int getMaxStackSize() {
		return 1;
	}
	
	@Override
	public int getSlotIndex() {
		return 0;
	}
	
	@Override
	public int getMaxStackSize(ItemStack stack) {
		return 1;
	}

}
