package KOWI2003.LaserMod.recipes.infuser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import KOWI2003.LaserMod.init.ModItems;
import KOWI2003.LaserMod.recipes.infuser.recipe.InfuserRecipeChargingTool;
import KOWI2003.LaserMod.tileentities.TileEntityInfuser;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class InfuserRecipeHandler {

	static Map<String, IInfuserRecipe> recipes = new HashMap<>();
	
	public static void register() {
		registerRecipe("laser_crystal", new ItemStack(ModItems.LaserCrystal.get()), new ItemStack(Items.REDSTONE, 4), new ItemStack(Items.DIAMOND));
		registerRecipe("tool_charger", new InfuserRecipeChargingTool());
		registerRecipe("silicon", new ItemStack(ModItems.Silicon.get()), Ingredient.of(new ItemStack(Items.IRON_NUGGET)), Ingredient.of(ItemTags.SAND));
	}
	
//	public static void registerOD() {
//		if(OreDictionaryHandler.doesTagExist(new ResourceLocation("forge:ingots/steel"))) {
//			ITag<Item> steel = OreDictionaryHandler.getTag(new ResourceLocation("forge:ingots/steel"));
//			if(steel != null) {
//				if(steel.getValues().size() > 0) {
//					for (Item item : steel.getValues()) {
//						if(item != null) {
//							registerRecipe("steel_ingot", new ItemStack(item), new ItemStack(Items.COAL, 4), new ItemStack(Items.IRON_INGOT));
//							break;
//						}
//					}
//				}
//			}
//		}
//		if(OreDictionaryHandler.doesTagExist(new ResourceLocation("forge:ingots/silver")) && 
//				OreDictionaryHandler.doesTagExist(new ResourceLocation("forge:ingots/copper")) && 
//				OreDictionaryHandler.doesTagExist(new ResourceLocation("forge:ingots/tin"))) {
//			ITag<Item> steel = OreDictionaryHandler.getTag(new ResourceLocation("forge:ingots/silver"));
//			if(steel != null) {
//				if(steel.getValues().size() > 0) {
//					for (Item item : steel.getValues()) {
//						if(item != null) {
//							registerRecipe("steel_ingot", new ItemStack(item), 
//									OreDictionaryHandler.getTag(new ResourceLocation("forge:ingots/tin")), 
//									OreDictionaryHandler.getTag(new ResourceLocation("forge:ingots/copper")));
//							break;
//						}
//					}
//				}
//			}
//		}
//	}
	public static void registerRecipe(String id, ItemStack output, Ingredient input1, Ingredient input2, float speed) {
		recipes.put(id, new InfuserRecipeBase(input1, input2, output, speed));
	}
	
	public static void registerRecipe(String id, ItemStack output, Ingredient input1, Ingredient input2) {
		recipes.put(id, new InfuserRecipeBase(input1, input2, output));
	}
	
	public static void registerRecipe(String id, ItemStack output, ItemStack input1, ItemStack input2, float speed) {
		recipes.put(id, new InfuserRecipeBase(Ingredient.of(input1), Ingredient.of(input2), output, speed));
	}
	
	public static void registerRecipe(String id, ItemStack output, ItemStack input1, ItemStack input2) {
		recipes.put(id, new InfuserRecipeBase(Ingredient.of(input1), Ingredient.of(input2), output));
	}
	
	public static void registerRecipe(String id, IInfuserRecipe recipe) {
		recipes.put(id, recipe);
	}
	
	public static IInfuserRecipe getRecipe(TileEntityInfuser te) {
		for (String id : recipes.keySet()) {
			IInfuserRecipe recipe = recipes.get(id);
			if(recipe.isRecipeValid(te))
				return recipe;
		}
		return null;
	}
	
	public static void handleRecipeEnd(IInfuserRecipe recipe, TileEntityInfuser te) {
		Object[] inputs = recipe.getInputs(te);
		ItemStack output = recipe.getOutput(te);
		if(te.handler.getStackInSlot(2).getItem() == output.getItem())
			te.handler.setStackInSlot(2, new ItemStack(te.handler.getStackInSlot(2).getItem(), 
					te.handler.getStackInSlot(2).getCount() + output.getCount(), te.handler.getStackInSlot(2).getTag()));
		else
			te.handler.setStackInSlot(2, output.copy());
		
		if(inputs.length >= 2) {
			if(inputs[0] != null) {
				if(inputs[0] instanceof ItemStack)
					te.handler.extractItem(0, ((ItemStack)inputs[0]).getCount(), false);
				else
					te.handler.extractItem(0, 1, false);
			}if(inputs[1] != null) {
				if(inputs[1] instanceof ItemStack)
					te.handler.extractItem(1, ((ItemStack)inputs[1]).getCount(), false);
				else
					te.handler.extractItem(1, 1, false);
			}
		}
	}
	
	public static List<IInfuserRecipe> getAllRecipes() {
		List<IInfuserRecipe> list = new ArrayList<>();
		for (String key : recipes.keySet())
			list.add(recipes.get(key));
		
		return list;
	}
	
	@SafeVarargs
	public static <T extends IInfuserRecipe> List<IInfuserRecipe> getRecipes(Class<? extends T> ... exluding) {
		List<Class<? extends T>> exRecipe = new ArrayList<>();
		for (Class<? extends T> recipeType : exluding)
			exRecipe.add(recipeType);
		
		List<IInfuserRecipe> list = new ArrayList<>();
		for (String key : recipes.keySet()) {
			IInfuserRecipe recipe = recipes.get(key);
			if(!exRecipe.contains(recipe.getClass()))
				list.add(recipes.get(key));
		}
		return list;
	}
}
