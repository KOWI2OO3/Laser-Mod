package KOWI2003.LaserMod.recipes.infuser;

import javax.annotation.Nullable;

import KOWI2003.LaserMod.init.ModRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import oshi.util.tuples.Pair;

public class InfuserRecipeBase implements IInfuserRecipe {

	ResourceLocation id;

	private ItemStack output; 
	private Pair<Ingredient, Integer>[] inputs;
	private float speed = 1;
	
	@SuppressWarnings("unchecked")
	InfuserRecipeBase(Pair<Ingredient, Integer> input1, Pair<Ingredient, Integer> input2, ItemStack output, float speed, ResourceLocation id) {
		this.inputs = new Pair[] { input1, input2 };
		this.output = output;
		this.speed = speed;
		this.id = id;
	}
	
	public ItemStack getOutput() { return output; }

	public Pair<Ingredient, Integer>[] getInputs(@Nullable Container container) { return inputs; }

	public float getRecipeSpeed() { return speed; }

	@Override
	public ResourceLocation getId() { return id; }

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModRecipeTypes.INFUSER.getSerializer();
	}

	@Override
	public RecipeType<?> getType() {
		return ModRecipeTypes.INFUSER.getType();
	}
}
