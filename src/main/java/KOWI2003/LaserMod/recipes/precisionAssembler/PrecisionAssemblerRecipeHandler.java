package KOWI2003.LaserMod.recipes.precisionAssembler;

import java.util.ArrayList;
import java.util.List;

import KOWI2003.LaserMod.init.ModRecipeTypes;
import KOWI2003.LaserMod.tileentities.TileEntityPrecisionAssembler;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class PrecisionAssemblerRecipeHandler {
	
	@SuppressWarnings("null")
	public static IPrecisionAssemblerRecipe getRecipe(TileEntityPrecisionAssembler tile) { 
		var level = tile.getLevel();
		if(level == null) return null;

		for (var recipe : getAllRecipes(level)) {
			if(recipe.matches(new RecipeWrapper(tile.getHandler()), tile.getLevel()))
				return (IPrecisionAssemblerRecipe) recipe;
		}
		return null;
	}
	
	public static void handleRecipeEnd(IPrecisionAssemblerRecipe recipe, TileEntityPrecisionAssembler te) {
		Container container = new RecipeWrapper(te.getHandler());
		recipe.assemble(container);
	}
	
	public static List<IPrecisionAssemblerRecipe> getAllRecipes(Level level) {
		var recipes = new ArrayList<Recipe<Container>>(level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.PRECISION_ASSEMBLER_SHAPED.getType()));
		recipes.addAll(level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.PRECISION_ASSEMBLER_SHAPELESS.getType()));
		return recipes.stream().map(IPrecisionAssemblerRecipe.class::cast).toList();
	}
}
