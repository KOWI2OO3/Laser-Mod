package KOWI2003.LaserMod.recipes.infuser;

import net.minecraft.world.item.ItemStack;

public class InfuserRecipeBase implements IInfuserRecipe {

	private ItemStack output; 
	private Object input1, input2;
	private float speed = 1;
	
	public InfuserRecipeBase(Object input1, Object input2, ItemStack output) {
		this.input1 = input1;
		this.input2 = input2;
		this.output = output;
	}
	
	public InfuserRecipeBase(Object input1, Object input2, ItemStack output, float speed) {
		this(input1, input2, output);
		this.speed = speed;
	}
	
	public ItemStack getOutput() { return output; }
	public Object[] getInputs() { return new Object[] { input1, input2 }; }
	public float getRecipeSpeed() { return speed; }
}
