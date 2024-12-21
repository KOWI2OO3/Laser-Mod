package KOWI2003.LaserMod.recipes.infuser;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import KOWI2003.LaserMod.init.ModRecipeTypes;
import KOWI2003.LaserMod.recipes.infuser.recipe.InfuserRecipeChargingTool;
import KOWI2003.LaserMod.tileentities.TileEntityInfuser;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class InfuserRecipeHelper {

	@SuppressWarnings({ "null" })
	public static IInfuserRecipe getRecipe(@Nonnull TileEntityInfuser tile) {
		return (IInfuserRecipe) tile.getLevel().getRecipeManager()
			.getAllRecipesFor(ModRecipeTypes.INFUSER.getType())
			.stream()
			.filter(recipe -> recipe.matches(new RecipeWrapper(tile.handler), tile.getLevel()))
			.findFirst()
			.orElseGet(() -> new InfuserRecipeChargingTool().isRecipeValid(new RecipeWrapper(tile.handler)) ? (IInfuserRecipe) new InfuserRecipeChargingTool() : null);
	}
	
	public static List<IInfuserRecipe> getAllRecipes(Level level) {
		List<IInfuserRecipe> list = new ArrayList<>(getRecipesWithoutTools(level));
		list.add(new InfuserRecipeChargingTool());
		return list;
	}

	public static <T extends IInfuserRecipe> List<IInfuserRecipe> getRecipesWithoutTools(Level level) {
		return level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.INFUSER.getType());
	}
}
