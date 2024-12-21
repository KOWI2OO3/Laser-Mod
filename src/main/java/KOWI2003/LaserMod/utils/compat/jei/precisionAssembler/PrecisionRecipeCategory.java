package KOWI2003.LaserMod.utils.compat.jei.precisionAssembler;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.init.ModBlocks;
import KOWI2003.LaserMod.recipes.precisionAssembler.IPrecisionAssemblerRecipe;
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

public class PrecisionRecipeCategory extends AbstractPrecisionRecipeCategory<IPrecisionAssemblerRecipe>{

	private final IDrawable background;
	private final IDrawable icon;
	
	public PrecisionRecipeCategory(IGuiHelper helper) {
		super(helper);
		background = helper.createDrawable(TEXTURE, 4, 11-2, 148, 62-2);
		icon = helper.createDrawableItemStack(new ItemStack(ModBlocks.PrecisionAssembler.get()));
	}
	
	@Override
	public void draw(@Nonnull IPrecisionAssemblerRecipe recipe, @Nonnull IRecipeSlotsView recipeSlotsView, @Nonnull PoseStack stack, double mouseX,
			double mouseY) {
		animatedPorgress.draw(stack, 72, 24 + 2);
		super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
	}
	
	@Override
	public Component getTitle() {
		return MutableComponent.create(new TranslatableContents("container.lasermod.precision_assembler"));
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
	public RecipeType<IPrecisionAssemblerRecipe> getRecipeType() {
		return RecipeCategories.PRECISION_ASSEMBLER;
	}

	@Override
	public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull IPrecisionAssemblerRecipe recipe, @Nonnull IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 46, 32-12 + 2).addIngredients(recipe.getInputBase());
		builder.addSlot(RecipeIngredientRole.INPUT, 10, -1 + 2).addIngredients(recipe.getInputs().length > 0 ? recipe.getInputs()[0] : Ingredient.EMPTY);
		builder.addSlot(RecipeIngredientRole.INPUT, 10, 32-12 + 2).addIngredients(recipe.getInputs().length > 1 ? recipe.getInputs()[1] : Ingredient.EMPTY);
		builder.addSlot(RecipeIngredientRole.INPUT, 10, 53-12 + 2).addIngredients(recipe.getInputs().length > 2 ? recipe.getInputs()[2] : Ingredient.EMPTY);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 128, 32-12 + 2).addItemStack(recipe.getOutput());
	}
	
}
