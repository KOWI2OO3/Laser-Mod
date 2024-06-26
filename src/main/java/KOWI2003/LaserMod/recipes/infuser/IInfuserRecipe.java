package KOWI2003.LaserMod.recipes.infuser;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import oshi.util.tuples.Pair;

public interface IInfuserRecipe extends Recipe<Container> {

	public default ItemStack getOutput(@Nullable Container container) { return getOutput(); }
	
	public ItemStack getOutput();

	public Pair<Ingredient, Integer>[] getInputs(@Nullable Container container);
	
	public default boolean isRecipeValid(@Nonnull Container container) {
		if(getInputs(container).length >= 2) {
			if(getInputs(container)[0] != null && getInputs(container)[1] != null && getOutput(container) != null) {
				boolean conditionSlot1  = false;
				if(!getInputs(container)[0].getA().isEmpty())
					conditionSlot1 = getInputs(container)[0].getA().test(container.getItem(0)) && container.getItem(0).getCount() >= getInputs(container)[0].getB();
				else
					conditionSlot1 = container.getItem(0).isEmpty();
				
				boolean conditionSlot2 = false;
				if(!getInputs(container)[1].getA().isEmpty())
					conditionSlot2 = getInputs(container)[1].getA().test(container.getItem(1)) && container.getItem(1).getCount() >= getInputs(container)[1].getB();
				else
					conditionSlot2 = container.getItem(1).isEmpty();
				
				boolean out = (container.getItem(2).getItem() == getOutput(container).getItem() && 
						container.getItem(2).getCount() + getOutput(container).getCount() <= container.getItem(2).getItem().getMaxStackSize(container.getItem(2))) || container.getItem(2).isEmpty();
				return conditionSlot1 && conditionSlot2 && out;
			}
		}
		return false;	
	}

	@Override
	public default ItemStack assemble(@Nonnull Container container) {
		Pair<Ingredient, Integer>[] inputs = getInputs(container);
		ItemStack output = getOutput(container);
		
		if(container.getItem(2).getItem() == output.getItem())
			container.setItem(2, new ItemStack(container.getItem(2).getItem(), 
				container.getItem(2).getCount() + output.getCount(), container.getItem(2).getTag()));
		else
			container.setItem(2, output.copy());
		
		if(inputs.length >= 2) {
			if(inputs[0] != null && !inputs[0].getA().isEmpty())
				container.removeItem(0, inputs[0].getB());
			if(inputs[1] != null && !inputs[1].getA().isEmpty())
				container.removeItem(1, inputs[1].getB());
		}

		return container.getItem(2).copy();
	}
	
	public float getRecipeSpeed();

	@Override
	public default boolean matches(@Nonnull Container container, @Nonnull Level level) { return isRecipeValid(container); }

	@Override
	public default boolean canCraftInDimensions(int width, int height) { return true; }

	@Override
	public default ItemStack getResultItem() { return getOutput(); }
}
