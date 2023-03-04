package KOWI2003.LaserMod.recipes.precisionAssembler;

import java.util.ArrayList;
import java.util.List;

import KOWI2003.LaserMod.init.ModBlocks;
import KOWI2003.LaserMod.init.ModItems;
import KOWI2003.LaserMod.init.ModUpgrades;
import KOWI2003.LaserMod.tileentities.TileEntityPrecisionAssembler;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.tags.ITag;

public class PrecisionAssemblerRecipeHandler {

	static List<IPrecisionAssemblerRecipe> recipes = new ArrayList<IPrecisionAssemblerRecipe>();
	
	public static void registerRecipes() {
		registerRecipe(1, new ItemStack(ModItems.CircuitBoard.get()), new ItemStack(ModItems.SiliconBase.get()), new ItemStack(Items.IRON_INGOT), ItemStack.EMPTY, new ItemStack(Items.REDSTONE));
		registerRecipeShapeless(1, new ItemStack(ModUpgrades.Speed.get()), Ingredient.of(new ItemStack(ModItems.CircuitBoard.get())), Ingredient.of(new ItemStack(Items.FEATHER)), Ingredient.of(new ItemStack(Items.REDSTONE)), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)));
		registerRecipeShapeless(1, new ItemStack(ModUpgrades.Mining.get()), Ingredient.of(new ItemStack(ModItems.CircuitBoard.get())), Ingredient.of(new ItemStack(Items.IRON_PICKAXE)));
		registerRecipeShapeless(1, new ItemStack(ModUpgrades.Fire.get()), Ingredient.of(new ItemStack(ModItems.CircuitBoard.get())), Ingredient.of(new ItemStack(Items.FLINT_AND_STEEL)));
		registerRecipeShapeless(1, new ItemStack(ModUpgrades.Color.get()), Ingredient.of(new ItemStack(ModItems.CircuitBoard.get())), Ingredient.of(new ItemStack(Items.RED_DYE)), Ingredient.of(new ItemStack(Items.LAPIS_LAZULI)), Ingredient.of(new ItemStack(Items.GREEN_DYE)));
		registerRecipeShapeless(1, new ItemStack(ModUpgrades.Damage1.get()), Ingredient.of(new ItemStack(ModItems.CircuitBoard.get())), Ingredient.of(new ItemStack(Items.WOODEN_SWORD)));
		registerRecipeShapeless(1, new ItemStack(ModUpgrades.Damage2.get()), Ingredient.of(new ItemStack(ModItems.CircuitBoard.get())), Ingredient.of(new ItemStack(Items.STONE_SWORD)));
		registerRecipeShapeless(1, new ItemStack(ModUpgrades.Damage3.get()), Ingredient.of(new ItemStack(ModItems.CircuitBoard.get())), Ingredient.of(new ItemStack(Items.IRON_SWORD)));
		registerRecipeShapeless(1, new ItemStack(ModUpgrades.Damage4.get()), Ingredient.of(new ItemStack(ModItems.CircuitBoard.get())), Ingredient.of(new ItemStack(Items.DIAMOND_SWORD)));
		registerRecipeShapeless(1, new ItemStack(ModUpgrades.Damage5.get()), Ingredient.of(new ItemStack(ModItems.CircuitBoard.get())), Ingredient.of(new ItemStack(Items.NETHERITE_SWORD)));
		registerRecipeShapeless(1, new ItemStack(ModUpgrades.NoDamage.get()), Ingredient.of(new ItemStack(ModItems.CircuitBoard.get())), Ingredient.of(ItemTags.WOOL));
		registerRecipeShapeless(1, new ItemStack(ModUpgrades.Silence.get()), Ingredient.of(new ItemStack(ModItems.CircuitBoard.get())), Ingredient.of(ItemTags.WOOL), Ingredient.of(ItemTags.WOOL), Ingredient.of(ItemTags.WOOL));
		registerRecipeShapeless(1, new ItemStack(ModUpgrades.Push.get()), Ingredient.of(new ItemStack(ModItems.CircuitBoard.get())), Ingredient.of(new ItemStack(Blocks.PISTON)));
		registerRecipeShapeless(1, new ItemStack(ModUpgrades.Pull.get()), Ingredient.of(new ItemStack(ModItems.CircuitBoard.get())), Ingredient.of(new ItemStack(Blocks.STICKY_PISTON)));
		registerRecipe(1, new ItemStack(ModUpgrades.Distance.get()), new ItemStack(ModItems.CircuitBoard.get()), new ItemStack(Blocks.GLASS), ItemStack.EMPTY, new ItemStack(Blocks.GLASS));
		registerRecipe(1, new ItemStack(ModUpgrades.Distance2.get()), new ItemStack(ModUpgrades.Distance.get()), new ItemStack(Blocks.GLASS), ItemStack.EMPTY, new ItemStack(Blocks.GLASS));
		registerRecipe(1, new ItemStack(ModUpgrades.Distance3.get()), new ItemStack(ModUpgrades.Distance2.get()), new ItemStack(Blocks.GLASS), ItemStack.EMPTY, new ItemStack(Blocks.GLASS));
		registerRecipeShapeless(1, new ItemStack(ModUpgrades.Mode.get()), Ingredient.of(new ItemStack(ModItems.CircuitBoard.get())), Ingredient.of(new ItemStack(Blocks.GLASS)), Ingredient.of(new ItemStack(Items.PAPER)));
		registerRecipe(1, new ItemStack(ModUpgrades.Capacity.get()), new ItemStack(ModItems.CircuitBoard.get()), new ItemStack(Blocks.GLASS), new ItemStack(ModItems.LaserCrystal.get()), new ItemStack(Items.IRON_INGOT));
		registerRecipe(1, new ItemStack(ModUpgrades.Capacity2.get()), new ItemStack(ModUpgrades.Capacity.get()), new ItemStack(Blocks.GLASS), new ItemStack(ModItems.LaserCrystal.get()), new ItemStack(Items.IRON_INGOT));
		registerRecipe(1, new ItemStack(ModUpgrades.Capacity3.get()), new ItemStack(ModUpgrades.Capacity2.get()), new ItemStack(Blocks.GLASS), new ItemStack(ModItems.LaserCrystal.get()), new ItemStack(Items.IRON_INGOT));
		registerRecipe(1, new ItemStack(ModUpgrades.AirtightSeal.get()), new ItemStack(ModItems.CircuitBoard.get()), new ItemStack(Blocks.GLASS), new ItemStack(Blocks.GLASS), new ItemStack(Blocks.GLASS));
		registerRecipe(1, new ItemStack(ModItems.IR_Glasses.get()), new ItemStack(Items.IRON_INGOT), new ItemStack(Blocks.BLACK_STAINED_GLASS), new ItemStack(Items.IRON_INGOT), new ItemStack(Blocks.BLACK_STAINED_GLASS));
		
		registerRecipe(1, ModItems.LaserSword.get().getDefaultInstance() ,new ItemStack(ModItems.LaserToolShell.get()), new ItemStack(Items.IRON_SWORD), new ItemStack(ModItems.LaserCrystal.get()), new ItemStack(ModItems.LaserCrystal.get()));
		registerRecipe(1, ModItems.LaserPickaxe.get().getDefaultInstance() ,new ItemStack(ModItems.LaserToolShell.get()), new ItemStack(Items.IRON_PICKAXE), new ItemStack(ModItems.LaserCrystal.get()), new ItemStack(ModItems.LaserCrystal.get()));
		registerRecipe(1, ModItems.LaserAxe.get().getDefaultInstance() ,new ItemStack(ModItems.LaserToolShell.get()), new ItemStack(Items.IRON_AXE), new ItemStack(ModItems.LaserCrystal.get()), new ItemStack(ModItems.LaserCrystal.get()));
		registerRecipe(1, ModItems.LaserShovel.get().getDefaultInstance() ,new ItemStack(ModItems.LaserToolShell.get()), new ItemStack(Items.IRON_SHOVEL), new ItemStack(ModItems.LaserCrystal.get()), new ItemStack(ModItems.LaserCrystal.get()));
		registerRecipe(1, ModItems.LaserHoe.get().getDefaultInstance() ,new ItemStack(ModItems.LaserToolShell.get()), new ItemStack(Items.IRON_HOE), new ItemStack(ModItems.LaserCrystal.get()), new ItemStack(ModItems.LaserCrystal.get()));
		
		registerRecipe(1, new ItemStack(ModBlocks.LaserProjector.get()), new ItemStack(ModBlocks.Laser.get()), new ItemStack(Items.IRON_INGOT), new ItemStack(ModItems.CircuitBoard.get()), new ItemStack(ModItems.LaserCrystal.get()));
		registerRecipe(1, new ItemStack(ModBlocks.ModStation.get()), new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE), new ItemStack(Items.IRON_INGOT), new ItemStack(ModItems.CircuitBoard.get()), new ItemStack(Items.IRON_INGOT));
		registerRecipe(1, ModItems.Linker.get().getDefaultInstance(), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.IRON_INGOT), new ItemStack(ModItems.CircuitBoard.get()), new ItemStack(Items.REDSTONE));
		registerRecipe(1, new ItemStack(ModBlocks.LaserController.get()), new ItemStack(Blocks.IRON_BLOCK), new ItemStack(Items.LEVER), new ItemStack(ModItems.CircuitBoard.get()), new ItemStack(Items.REDSTONE));
		registerRecipe(1, new ItemStack(ModBlocks.Mirror.get()), new ItemStack(Blocks.GLASS), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.IRON_INGOT));
		registerRecipe(1, new ItemStack(ModItems.SiliconBase.get()), ItemStack.EMPTY, new ItemStack(ModItems.Silicon.get()));
	}
	
	public static void registerRecipe(IPrecisionAssemblerRecipe recipe) {
		recipes.add(recipe);
	}
	
	public static void registerRecipe(float speed, ItemStack output, ItemStack inputBase, ItemStack... inputs) {
		Ingredient[] in = new Ingredient[inputs.length];
		for(int i = 0; i < in.length; i++)
			in[i] = Ingredient.of(inputs[i]);
		registerRecipe(speed, output, Ingredient.of(inputBase), in);
	}
	
	public static void registerRecipe(float speed, ItemStack output, Ingredient inputBase, Ingredient... inputs) {
		recipes.add(new PrecisionAssemblerRecipeBase(output, speed, inputBase, inputs));
	}
	
	public static void registerRecipeShapeless(float speed, ItemStack output, Ingredient inputBase, Ingredient... inputs) {
		recipes.add(new PrecisionAssemblerRecipeShapeless(output, speed, inputBase, inputs));
	}
	
	public static IPrecisionAssemblerRecipe getRecipe(TileEntityPrecisionAssembler te) { 
		for (IPrecisionAssemblerRecipe recipe : recipes) {
			if(recipe.isRecipeValid(te.getHandler()))
				return recipe;
		}
		return null;
	}
	
	public static void handleRecipeEnd(IPrecisionAssemblerRecipe recipe, TileEntityPrecisionAssembler te) {
		if(!recipe.isRecipeValid(te.getHandler()))
			return;
		ItemStackHandler handler = te.getHandler();
		Ingredient[] inputs = recipe.getInputs();
		if (recipe instanceof PrecisionAssemblerRecipeShapeless) {
			for(int i = 0; i < inputs.length; i++) {
				Ingredient in = inputs[i];
				for(int j = 0; j < handler.getSlots(); j++) {
					if(in.test(handler.getStackInSlot(j))) {
						handler.extractItem(j, 1, false);
						break;
					}
				}
			}
		}else
			for(int i = 0; i < inputs.length; i++)
				handler.extractItem(i, 1, false);

		handler.extractItem(3, 1, false);
		
		Object obj = recipe.getInputBase();
		ItemStack slot = handler.getStackInSlot(3).copy();
		if(obj instanceof ItemStack) {
			ItemStack inputBase = (ItemStack) obj;
			slot = handler.getStackInSlot(3).copy();
			int newCount = slot.getCount() - inputBase.getCount();
			if(newCount <= 0)
				slot = ItemStack.EMPTY;
			else
				slot.setCount(slot.getCount() - inputBase.getCount());
			handler.setStackInSlot(3, slot);
		}else if(obj instanceof ITag<?>) {
			if(((ITag<Item>)obj).contains(slot.getItem())) {
				int newCount = slot.getCount() - 1;
				if(newCount <= 0)
					slot = ItemStack.EMPTY;
				else
					slot.setCount(slot.getCount() - 1);
				handler.setStackInSlot(3, slot);
			}
		}else if(obj instanceof TagKey<?>) {
			if(slot.is((TagKey<Item>)obj)) {
				int newCount = slot.getCount() - 1;
				if(newCount <= 0)
					slot = ItemStack.EMPTY;
				else
					slot.setCount(slot.getCount() - 1);
				handler.setStackInSlot(3, slot);
			}
		}
		
		ItemStack output = recipe.getOutput();
		slot = handler.getStackInSlot(4).copy();
		if(!slot.isEmpty())
			output.setCount(output.getCount() + slot.getCount());
		handler.setStackInSlot(4, output);
		te.handler = handler;
	}
	
	public static List<IPrecisionAssemblerRecipe> getAllRecipes() {
		return recipes;
	}
}
