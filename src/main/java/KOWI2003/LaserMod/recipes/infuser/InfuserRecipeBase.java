package KOWI2003.LaserMod.recipes.infuser;

import java.util.Random;

import javax.annotation.Nullable;

import KOWI2003.LaserMod.Reference;
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
	
	@Deprecated
	public InfuserRecipeBase(Ingredient input1, Ingredient input2, ItemStack output, float speed) {
		this(new Pair<>(input1, 1), new Pair<>(input2, 1), output, speed, new ResourceLocation(Reference.MODID, "infuser/custom" + new Random().nextInt()));
	}

	@Deprecated
	public InfuserRecipeBase(Ingredient input1, Ingredient input2, ItemStack output) {
		this(input1, input2, output, 1);
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
