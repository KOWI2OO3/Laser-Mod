package KOWI2003.LaserMod.utils.compat.jei.infuser;

import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.init.ModBlocks;
import KOWI2003.LaserMod.recipes.infuser.IInfuserRecipe;
import KOWI2003.LaserMod.utils.compat.jei.RecipeCategories;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class InfuserRecipeCategory extends AbstractInfuserRecipeCategory<IInfuserRecipe> {

	private final IDrawable background;
	private final IDrawable icon;
	
	public InfuserRecipeCategory(IGuiHelper helper) {
		super(helper);
		background = helper.createDrawable(TEXTURE, 4, 11, 148, 62);
		icon = helper.createDrawableItemStack(new ItemStack(ModBlocks.Infuser.get()));
	}
	
	@Override
	public void draw(IInfuserRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX,
			double mouseY) {
		animatedPorgress.draw(stack, 60, 49);
		super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
	}
	
	@Override
	public Component getTitle() {
		return MutableComponent.create(new TranslatableContents("container.lasermod.infuser"));
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
	public RecipeType<IInfuserRecipe> getRecipeType() {
		return RecipeCategories.INFUSER;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, IInfuserRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 9, 1).addIngredients(recipe.getInputs().length > 0 ? recipe.getInputs()[0] : Ingredient.EMPTY);
		builder.addSlot(RecipeIngredientRole.INPUT, 37, 45).addIngredients(recipe.getInputs().length > 1 ? recipe.getInputs()[1] : Ingredient.EMPTY);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 112, 45).addItemStack(recipe.getOutput());
		
	}

}
