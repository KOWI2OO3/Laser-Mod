package KOWI2003.LaserMod.recipes.precisionAssembler;

import javax.annotation.Nonnull;

import KOWI2003.LaserMod.init.ModRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class PrecisionAssemblerRecipeShaped implements IPrecisionAssemblerRecipe {

	public ResourceLocation id;

	public Ingredient[] inputs;
	public Ingredient inputBase;
	public ItemStack output;
	public float speed;
	
	PrecisionAssemblerRecipeShaped(ResourceLocation id, ItemStack output, float speed, Ingredient inputBase, Ingredient... inputs) {
		this.id = id;
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
		for (Object input : getInputs()) 
			inputs += input.toString() + ", ";

		if(inputs.length() > 2) 
			inputs = inputs.substring(0, inputs.length() - 2);
		
		inputs += "] + " + getInputBase().toString() + " -> " + getOutput();
		return inputs;
	}

	@Override
	@SuppressWarnings("null")
	public ItemStack assemble(@Nonnull Container container) {
		if(!matches(container, null))
			return ItemStack.EMPTY;
		
		Ingredient[] inputs = getInputs();
		for(int i = 0; i < inputs.length; i++)
			container.removeItem(i, 1);

		container.removeItem(3, 1);
		
		ItemStack output = getOutput();
		ItemStack slot = container.getItem(4).copy();
		if(!slot.isEmpty())
			output.setCount(output.getCount() + slot.getCount());
		container.setItem(4, output);
		return output;
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModRecipeTypes.PRECISION_ASSEMBLER_SHAPED.getSerializer();
	}

	@Override
	public RecipeType<?> getType() {
		return ModRecipeTypes.PRECISION_ASSEMBLER_SHAPED.getType();
	}

}
