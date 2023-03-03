package KOWI2003.LaserMod.utils.compat.jei.precisionAssembler;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.recipes.precisionAssembler.IPrecisionAssemblerRecipe;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractPrecisionRecipeCategory<T extends IPrecisionAssemblerRecipe> implements IRecipeCategory<T> {

	protected static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID,
			"textures/gui/container/precision_assembler.png");

	protected static final int inputBase = 0;
	protected static final int input1 = 1;
	protected static final int input2 = 2;
	protected static final int input3 = 3;
	protected static final int output = 4;
	
	protected final IDrawableAnimated animatedPorgress;
	
	public AbstractPrecisionRecipeCategory(IGuiHelper helper) {
		IDrawableStatic staticArrow1 = helper.createDrawable(TEXTURE, 176, 0, 45, 9);
		animatedPorgress = helper.createAnimatedDrawable(staticArrow1, 100, IDrawableAnimated.StartDirection.LEFT, false);
	}
}
