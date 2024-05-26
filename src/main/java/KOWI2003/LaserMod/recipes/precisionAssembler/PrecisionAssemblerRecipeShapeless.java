package KOWI2003.LaserMod.recipes.precisionAssembler;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import KOWI2003.LaserMod.init.ModRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class PrecisionAssemblerRecipeShapeless implements IPrecisionAssemblerRecipe {

	ResourceLocation id;

	ItemStack output;
	Ingredient[] inputs;
	Ingredient inputBase;
	float speed;
	
	PrecisionAssemblerRecipeShapeless(ResourceLocation id, ItemStack output, float speed, Ingredient inputBase, Ingredient... inputs) {
		this.id = id;
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
	public boolean matches(@Nonnull Container container, @Nonnull Level level) {
		boolean condition = true;
		Ingredient[] inputs = getInputs();
		List<Integer> slots = new ArrayList<Integer>();
		for (int i = 0; i < inputs.length; i++) {
			Ingredient in = inputs[i];
				boolean con = false;
				for(int j = 0; j < container.getContainerSize() - 2; j++) {
					ItemStack slot = container.getItem(j).copy();
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
			if(!container.getItem(index).isEmpty())
				return false;
		}

		Ingredient in = getInputBase();
			if(container.getContainerSize() <= 0)
				return false;
			ItemStack slot = container.getItem(3).copy();
			if(inputBase.isEmpty())
				return false;
			condition = condition && (in.isEmpty() ? slot.isEmpty() : (in.test(slot) && 1 <= slot.getCount()));
			if(!condition)
				return false;
		
		ItemStack output = getOutput();
		if(container.getContainerSize() <= 0)
			return false;
		ItemStack lastSlot = container.getItem(4).copy();
		if(output.isEmpty())
			return false;
		condition = condition && lastSlot.isEmpty() ? true : (lastSlot.getItem() == output.getItem() && lastSlot.getCount() + output.getCount() <= output.getItem().getMaxStackSize(output));
		return condition;
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
		for(int i = 0; i < inputs.length; i++) {
			Ingredient in = inputs[i];
			for(int j = 0; j < container.getContainerSize(); j++) {
				if(in.test(container.getItem(j))) {
					container.removeItem(j, 1);
					break;
				}
			}
		}

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
		return ModRecipeTypes.PRECISION_ASSEMBLER_SHAPELESS.getSerializer();
	}

	@Override
	public RecipeType<?> getType() {
		return ModRecipeTypes.PRECISION_ASSEMBLER_SHAPELESS.getType();
	}
}
