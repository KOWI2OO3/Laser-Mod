package KOWI2003.LaserMod.utils.compat.jei.infuser;

import javax.annotation.Nonnull;

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
	public void draw(@Nonnull IInfuserRecipe recipe, @Nonnull IRecipeSlotsView recipeSlotsView, @Nonnull PoseStack stack, double mouseX,
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
	public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull IInfuserRecipe recipe, @Nonnull IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 9, 1).addIngredients(recipe.getInputs(null).length > 0 ? recipe.getInputs(null)[0].getA() : Ingredient.EMPTY);
		builder.addSlot(RecipeIngredientRole.INPUT, 37, 45).addIngredients(recipe.getInputs(null).length > 1 ? recipe.getInputs(null)[1].getA() : Ingredient.EMPTY);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 112, 45).addItemStack(recipe.getOutput());	
	}
}
