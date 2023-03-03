package KOWI2003.LaserMod.recipes.precisionAssembler;
	
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.items.ItemStackHandler;

public interface IPrecisionAssemblerRecipe {

	public Ingredient[] getInputs();
	public Ingredient getInputBase();
	public ItemStack getOutput();
	public default boolean isRecipeValid(ItemStackHandler handler) {
		if(handler != null) {
			boolean condition = true;
			Ingredient[] inputs = getInputs();
			for (int i = 0; i < Math.min(handler.getSlots() - 2, inputs.length); i++) {
				Ingredient in = inputs[i];
				ItemStack slotStack = handler.getStackInSlot(i).copy();
				condition = condition && (in.isEmpty() ? slotStack.isEmpty() : (in.test(slotStack) && slotStack.getCount() >= 1));
				if(!condition)
					return false;
			}
			
			Ingredient in = getInputBase();
			ItemStack slot = handler.getStackInSlot(3).copy();
			condition = condition && in.isEmpty() ? slot.isEmpty() : (in.test(slot) && slot.getCount() >= 1);
			if(!condition)
				return false;
			
			ItemStack output = getOutput().copy();
			ItemStack lastSlot = handler.getStackInSlot(4).copy();
			if(output.isEmpty())
				return false;
			condition = condition && lastSlot.isEmpty() ? true : (lastSlot.getItem() == output.getItem() && lastSlot.getCount() + output.getCount() <= output.getItem().getMaxStackSize(output));
			return condition;
		}
		return false;
	}
	
	public float getRecipeSpeed();
	
}
