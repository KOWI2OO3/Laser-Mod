package KOWI2003.LaserMod.recipes.precisionAssembler;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class PrecisionAssemblerRecipeBase implements IPrecisionAssemblerRecipe {

	public Ingredient[] inputs;
	public Ingredient inputBase;
	public ItemStack output;
	public float speed;
	
	public PrecisionAssemblerRecipeBase(ItemStack output, float speed, Ingredient inputBase, Ingredient... inputs) {
		this.inputs = inputs;
		this.output = output;
		this.speed = speed;
		this.inputBase = inputBase;
	}
	
	@Override
	public Ingredient getInputBase() {
		return inputBase;
	}

	@Override
	public Ingredient[] getInputs() {
		return inputs;
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
