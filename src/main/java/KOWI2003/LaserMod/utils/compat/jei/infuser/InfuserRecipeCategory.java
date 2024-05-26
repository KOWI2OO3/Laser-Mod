package KOWI2003.LaserMod.utils.compat.jei.infuser;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.init.ModBlocks;
import KOWI2003.LaserMod.recipes.infuser.IInfuserRecipe;
import KOWI2003.LaserMod.utils.compat.jei.RecipeCategories;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class InfuserRecipeCategory extends AbstractInfuserRecipeCategory<IInfuserRecipe> {

	private final IDrawable background;
	private final IDrawable icon;
	
	public InfuserRecipeCategory(IGuiHelper helper) {
		super(helper);
		background = helper.createDrawable(TEXTURE, 4, 11, 148, 62);
		icon = helper.createDrawableIngredient(new ItemStack(ModBlocks.Infuser.get()));
	}
	
	@Override
	public void draw(IInfuserRecipe recipe, PoseStack matrixStack, double mouseX, double mouseY) {
		animatedPorgress.draw(matrixStack, 60, 49);
		super.draw(recipe, matrixStack, mouseX, mouseY);
	}
	
	@Override
	public ResourceLocation getUid() {
		return RecipeCategories.INFUSER;
	}
	
	@Override
	public Class<? extends IInfuserRecipe> getRecipeClass() {
		return IInfuserRecipe.class;
	}
	
	@Override
	public Component getTitle() {
		return new TranslatableComponent("container.lasermod.infuser");
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}
	
	@Override
	public void setIngredients(IInfuserRecipe recipe, IIngredients ingredients) {
		List<Ingredient> stacks = new ArrayList<>();
		for (Object obj: recipe.getInputs()) {
			if(obj instanceof Ingredient)
				stacks.add((Ingredient)obj);
			else if(obj instanceof ItemStack)
				stacks.add(Ingredient.of((ItemStack)obj));
			else if(obj instanceof TagKey<?>)
				stacks.add(Ingredient.of((TagKey<Item>)obj));
			else 
				stacks.add(Ingredient.EMPTY);
		}
		ingredients.setInputIngredients(stacks);
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IInfuserRecipe recipe, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(input1, true, 8, 0);
		stacks.init(input2, true, 36, 44);
		stacks.init(output, false, 111, 44);
		stacks.set(ingredients);
	}

}
