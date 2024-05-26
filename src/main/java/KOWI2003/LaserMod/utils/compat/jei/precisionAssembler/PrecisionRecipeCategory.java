package KOWI2003.LaserMod.utils.compat.jei.precisionAssembler;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import KOWI2003.LaserMod.init.ModBlocks;
import KOWI2003.LaserMod.recipes.precisionAssembler.IPrecisionAssemblerRecipe;
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

public class PrecisionRecipeCategory extends AbstractPrecisionRecipeCategory<IPrecisionAssemblerRecipe>{

	private final IDrawable background;
	private final IDrawable icon;
	
	public PrecisionRecipeCategory(IGuiHelper helper) {
		super(helper);
		background = helper.createDrawable(TEXTURE, 4, 11-2, 148, 62-2);
		icon = helper.createDrawableIngredient(new ItemStack(ModBlocks.PrecisionAssembler.get()));
	}
	
	@Override
	public void draw(IPrecisionAssemblerRecipe recipe, PoseStack matrixStack, double mouseX, double mouseY) {
		animatedPorgress.draw(matrixStack, 72, 24 + 2);
		super.draw(recipe, matrixStack, mouseX, mouseY);
	}
	
	@Override
	public ResourceLocation getUid() {
		return RecipeCategories.PRECISION_ASSEMBLER;
	}
	
	@Override
	public Class<? extends IPrecisionAssemblerRecipe> getRecipeClass() {
		return IPrecisionAssemblerRecipe.class;
	}
	
	@Override
	public Component getTitle() {
		return new TranslatableComponent("container.lasermod.precision_assembler");
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
	public void setIngredients(IPrecisionAssemblerRecipe recipe, IIngredients ingredients) {
		List<Ingredient> stacks = new ArrayList<>();
		stacks.add(recipe.getInputBaseIngredient());
		for(Object obj: recipe.getInputsIngredient()) {
			if(obj instanceof Ingredient)
				stacks.add((Ingredient)obj);
			else if(obj instanceof ItemStack)
				stacks.add(Ingredient.of((ItemStack)obj));
			else if(obj instanceof TagKey<?>)
				stacks.add(Ingredient.of((TagKey<Item>)obj));
			else 
				stacks.add(Ingredient.EMPTY);
		}
		if(recipe.getInputs().length < 3) {
			for (int i = 0; i < recipe.getInputs().length - 3; i++) {
				stacks.add(null);
			}
		}
		
		ingredients.setInputIngredients(stacks);
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IPrecisionAssemblerRecipe recipe, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(inputBase, true, 45, 31-12 + 2);
		stacks.init(input1, true, 9, -2 + 2);
		stacks.init(input2, true, 9, 31-12 + 2);
		stacks.init(input3, true, 9, 52-12 + 2);
		stacks.init(output, false, 127, 31-12 + 2);
		stacks.set(ingredients);
	}
	
}
