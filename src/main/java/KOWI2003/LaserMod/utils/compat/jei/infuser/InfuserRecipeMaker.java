package KOWI2003.LaserMod.utils.compat.jei.infuser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import KOWI2003.LaserMod.init.ModItems;
import KOWI2003.LaserMod.items.interfaces.IChargable;
import KOWI2003.LaserMod.recipes.infuser.IInfuserRecipe;
import KOWI2003.LaserMod.recipes.infuser.InfuserRecipeHelper;
import KOWI2003.LaserMod.recipes.infuser.recipe.InfuserRecipeChargingTool;
import KOWI2003.LaserMod.utils.LaserItemUtils;
import mezz.jei.api.helpers.IJeiHelpers;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import oshi.util.tuples.Pair;

public class InfuserRecipeMaker {

	@SuppressWarnings("resource")
	public static List<IInfuserRecipe> getRecipes(IJeiHelpers helper) {
		List<IInfuserRecipe> recipes = new ArrayList<>(InfuserRecipeHelper.getRecipesWithoutTools(Minecraft.getInstance().level));

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
		return contructToolRecipe(item, 10);
	}
	
	public static IInfuserRecipe contructToolRecipe(Item item, int redstoneAmount) {
		ItemStack result = item.getDefaultInstance();
		ItemStack redstoneIn = new ItemStack(Items.REDSTONE, redstoneAmount);
		ItemStack toolIn = LaserItemUtils.setCharge(item.getDefaultInstance(), (int) (((IChargable)item).getMaxCharge(item.getDefaultInstance()) - (InfuserRecipeChargingTool.getRedstoneToChargeRatio() * redstoneAmount)));
		return new EmptyInfuserRecipe(result, redstoneIn, toolIn);
	}
	
public static class EmptyInfuserRecipe implements IInfuserRecipe {

	public ItemStack output;
	public Ingredient[] input;
	
	public EmptyInfuserRecipe(ItemStack output, ItemStack input1, ItemStack input2) {
		this.output = output;
		this.input = new Ingredient[] {Ingredient.of(input1), Ingredient.of(input2)};
	}
	
	@Override
	public ItemStack getOutput() { return output; }

	@Override
	public float getRecipeSpeed() {return 1; }

	@Override
	public ResourceLocation getId() {
		return new ResourceLocation("unknown");
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		throw new UnsupportedOperationException("Unimplemented method 'getSerializer'");
	}

	@Override
	public RecipeType<?> getType() {
		throw new UnsupportedOperationException("Unimplemented method 'getType'");
	}

	@Override
	@SuppressWarnings("unchecked")
	public Pair<Ingredient, Integer>[] getInputs(@Nullable Container container) {
		return Arrays.stream(input).map(ingredient -> new Pair<>(ingredient, 1)).toArray(Pair[]::new);
	}
}
	
}
