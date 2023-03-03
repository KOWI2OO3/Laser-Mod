package KOWI2003.LaserMod.utils.compat.jei;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.recipes.infuser.IInfuserRecipe;
import KOWI2003.LaserMod.recipes.precisionAssembler.IPrecisionAssemblerRecipe;
import mezz.jei.api.recipe.RecipeType;

public class RecipeCategories {

	public static final RecipeType<IInfuserRecipe> INFUSER =
			RecipeType.create(Reference.MODID, "infuser", IInfuserRecipe.class);

	public static final RecipeType<IPrecisionAssemblerRecipe> PRECISION_ASSEMBLER =
			RecipeType.create(Reference.MODID, "precision_assembler", IPrecisionAssemblerRecipe.class);
}
