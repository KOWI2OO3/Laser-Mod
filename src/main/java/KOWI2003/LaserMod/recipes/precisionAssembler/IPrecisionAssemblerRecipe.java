package KOWI2003.LaserMod.recipes.precisionAssembler;
	
import javax.annotation.Nonnull;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;

public interface IPrecisionAssemblerRecipe extends Recipe<Container> {

	public Ingredient[] getInputs();
	public Ingredient getInputBase();
	public ItemStack getOutput();

	@Override
	default boolean matches(@Nonnull Container container, @Nonnull Level level) {
		boolean condition = true;
		Ingredient[] inputs = getInputs();
		for (int i = 0; i < Math.min(container.getContainerSize() - 2, inputs.length); i++) {
			Ingredient in = inputs[i];
			ItemStack slotStack = container.getItem(i).copy();
			condition = condition && (in.isEmpty() ? slotStack.isEmpty() : (in.test(slotStack) && slotStack.getCount() >= 1));
			if(!condition)
				return false;
		}
		
		Ingredient in = getInputBase();
		ItemStack slot = container.getItem(3).copy();
		condition = condition && in.isEmpty() ? slot.isEmpty() : (in.test(slot) && slot.getCount() >= 1);
		if(!condition)
			return false;
		
		ItemStack output = getOutput().copy();
		ItemStack lastSlot = container.getItem(4).copy();
		if(output.isEmpty())
			return false;
		condition = condition && lastSlot.isEmpty() ? true : (lastSlot.getItem() == output.getItem() && lastSlot.getCount() + output.getCount() <= output.getItem().getMaxStackSize(output));
		return condition;
	}
	
	public float getRecipeSpeed();

	@Override
	public default boolean canCraftInDimensions(int width, int height) { return true; }

	@Override
	public default ItemStack getResultItem() { return getOutput(); }
}
