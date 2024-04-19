package KOWI2003.LaserMod.recipes.infuser;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class InfuserRecipeBase implements IInfuserRecipe {

	private ItemStack output; 
	private Ingredient input1, input2;
	private float speed = 1;
	
	public InfuserRecipeBase(Ingredient input1, Ingredient input2, ItemStack output) {
		this.input1 = input1;
		this.input2 = input2;
		this.output = output;
	}
	
	public InfuserRecipeBase(Ingredient input1, Ingredient input2, ItemStack output, float speed) {
		this(input1, input2, output);
		this.speed = speed;
	}
	
	public ItemStack getOutput() { return output; }
	public Ingredient[] getInputs() { return new Ingredient[] { input1, input2 }; }
	public float getRecipeSpeed() { return speed; }
}
