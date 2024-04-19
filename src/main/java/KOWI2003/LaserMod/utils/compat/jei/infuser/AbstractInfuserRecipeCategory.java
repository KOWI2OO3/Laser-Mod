package KOWI2003.LaserMod.utils.compat.jei.infuser;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.recipes.infuser.IInfuserRecipe;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractInfuserRecipeCategory<T extends IInfuserRecipe> implements IRecipeCategory<T> {

	protected static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID,
			"textures/gui/container/infuser.png");
	
	protected static final int input1 = 0;
	protected static final int input2 = 1;
	protected static final int output = 2;
	
	protected final IDrawableAnimated animatedPorgress;
	
	public AbstractInfuserRecipeCategory(IGuiHelper helper) {
		IDrawableStatic staticArrow1 = helper.createDrawable(TEXTURE, 176, 0, 45, 9);
		animatedPorgress = helper.createAnimatedDrawable(staticArrow1, 100, IDrawableAnimated.StartDirection.LEFT, false);
	}
	
}
