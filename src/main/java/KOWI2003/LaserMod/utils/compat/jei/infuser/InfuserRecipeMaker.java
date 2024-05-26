package KOWI2003.LaserMod.utils.compat.jei.infuser;

import java.util.List;

import KOWI2003.LaserMod.init.ModItems;
import KOWI2003.LaserMod.items.interfaces.IChargable;
import KOWI2003.LaserMod.recipes.infuser.IInfuserRecipe;
import KOWI2003.LaserMod.recipes.infuser.InfuserRecipeHandler;
import KOWI2003.LaserMod.recipes.infuser.recipe.InfuserRecipeChargingTool;
import KOWI2003.LaserMod.utils.LaserItemUtils;
import mezz.jei.api.helpers.IJeiHelpers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class InfuserRecipeMaker {

	public static List<IInfuserRecipe> getRecipes(IJeiHelpers helper) {
		List<IInfuserRecipe> recipes = InfuserRecipeHandler.getRecipes(InfuserRecipeChargingTool.class);
		
		recipes.add(contructToolRecipe(ModItems.LaserSword.get()));
		recipes.add(contructToolRecipe(ModItems.LaserPickaxe.get()));
		recipes.add(contructToolRecipe(ModItems.LaserAxe.get()));
		recipes.add(contructToolRecipe(ModItems.LaserShovel.get()));
		recipes.add(contructToolRecipe(ModItems.LaserHoe.get()));
		recipes.add(contructToolRecipe(ModItems.LaserMultiTool.get()));
		recipes.add(contructToolRecipe(ModItems.LaserHelmet.get()));
		recipes.add(contructToolRecipe(ModItems.LaserChestplate.get()));
		recipes.add(contructToolRecipe(ModItems.LaserLeggings.get()));
		recipes.add(contructToolRecipe(ModItems.LaserBoots.get()));
		
		return recipes;
	}
	
	public static IInfuserRecipe contructToolRecipe(Item item) {
		return contructToolRecipe(item, 1);
	}
	
	public static IInfuserRecipe contructToolRecipe(Item item, int redstoneAmount) {
		ItemStack stack = item.getDefaultInstance();
		ItemStack rd = new ItemStack(Items.REDSTONE, redstoneAmount);
		ItemStack in = LaserItemUtils.setCharge(item.getDefaultInstance(), (int) (((IChargable)item).getMaxCharge(item.getDefaultInstance()) - (InfuserRecipeChargingTool.getRedstoneToChargeRatio() * redstoneAmount)));
		return new EmptyInfuserRecipe(stack, rd, in);
	}
	
public static class EmptyInfuserRecipe implements IInfuserRecipe {

	public ItemStack output;
	public ItemStack[] input;
	
	public EmptyInfuserRecipe(ItemStack output, ItemStack input1, ItemStack input2) {
		this.output = output;
		this.input = new ItemStack[] {input1, input2};
	}
	
	@Override
	public ItemStack getOutput() { return output; }
	@Override
	public ItemStack[] getInputs() { return input; }
	@Override
	public float getRecipeSpeed() {return 1; }
}
	
}
