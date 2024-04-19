package KOWI2003.LaserMod.recipes.precisionAssembler;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.items.ItemStackHandler;

public class PrecisionAssemblerRecipeShapeless implements IPrecisionAssemblerRecipe {

	ItemStack output;
	Ingredient[] inputs;
	Ingredient inputBase;
	float speed;
	
	public PrecisionAssemblerRecipeShapeless(ItemStack output, float speed, Ingredient inputBase, Ingredient... inputs) {
		this.inputs = inputs;
		this.output = output;
		this.speed = speed;
		this.inputBase = inputBase;
	}
	
	@Override
	public Ingredient[] getInputs() {
		return inputs;
	}

	@Override
	public Ingredient getInputBase() {
		return inputBase;
	}

	@Override
	public ItemStack getOutput() {
		return output.copy();
	}

	@Override
	public float getRecipeSpeed() {
		return speed;
	}
	
	@Override
	public boolean isRecipeValid(ItemStackHandler handler) {
		if(handler != null) {
			boolean condition = true;
			Ingredient[] inputs = getInputs();
			List<Integer> slots = new ArrayList<Integer>();
			for (int i = 0; i < inputs.length; i++) {
				Ingredient in = inputs[i];
					boolean con = false;
					for(int j = 0; j < handler.getSlots() - 2; j++) {
						ItemStack slot = handler.getStackInSlot(j).copy();
						if(condition && (in.isEmpty() ? slot.isEmpty() : (in.test(slot) && 1 <= slot.getCount()))) {
							con = true;
							break;
						}else
							slots.add(j);
					}
					if(!con)
						return false;
			}
			for (int index : slots) {
				if(!handler.getStackInSlot(index).isEmpty())
					return false;
			}
			Ingredient in = getInputBase();
				if(handler.getSlots() <= 0)
					return false;
				ItemStack slot = handler.getStackInSlot(3).copy();
				if(inputBase.isEmpty())
					return false;
				condition = condition && (in.isEmpty() ? slot.isEmpty() : (in.test(slot) && 1 <= slot.getCount()));
				if(!condition)
					return false;
			
			ItemStack output = getOutput();
			if(handler.getSlots() <= 0)
				return false;
			ItemStack lastSlot = handler.getStackInSlot(4).copy();
			if(output.isEmpty())
				return false;
			condition = condition && lastSlot.isEmpty() ? true : (lastSlot.getItem() == output.getItem() && lastSlot.getCount() + output.getCount() <= output.getItem().getMaxStackSize(output));
			return condition;
		}
		return false;
	}
	
	@Override
	public String toString() {
		String inputs = "[";
		for (Object input : getInputs()) {
			inputs += input.toString() + ", ";
		}
		if(inputs.length() > 2) {
			inputs = inputs.substring(0, inputs.length() - 2);
		}
		inputs += "] + " + getInputBase().toString() + " -> " + getOutput();
		return inputs;
	}
}
