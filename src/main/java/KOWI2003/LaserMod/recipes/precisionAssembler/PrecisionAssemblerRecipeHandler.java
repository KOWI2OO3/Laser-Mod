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
		registerRecipeShapeless(1, new ItemStack(ModUpgrades.Speed.get()), new ItemStack(ModItems.CircuitBoard.get()), new ItemStack(Items.FEATHER), new ItemStack(Items.REDSTONE), new ItemStack(Items.GLOWSTONE_DUST));
		registerRecipeShapeless(1, new ItemStack(ModUpgrades.Mining.get()), new ItemStack(ModItems.CircuitBoard.get()), new ItemStack(Items.IRON_PICKAXE));
		registerRecipeShapeless(1, new ItemStack(ModUpgrades.Fire.get()), new ItemStack(ModItems.CircuitBoard.get()), new ItemStack(Items.FLINT_AND_STEEL));
		registerRecipeShapeless(1, new ItemStack(ModUpgrades.Color.get()), new ItemStack(ModItems.CircuitBoard.get()), new ItemStack(Items.RED_DYE), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.GREEN_DYE));
		registerRecipeShapeless(1, new ItemStack(ModUpgrades.Damage1.get()), new ItemStack(ModItems.CircuitBoard.get()), new ItemStack(Items.WOODEN_SWORD));
		registerRecipeShapeless(1, new ItemStack(ModUpgrades.Damage2.get()), new ItemStack(ModItems.CircuitBoard.get()), new ItemStack(Items.STONE_SWORD));
		registerRecipeShapeless(1, new ItemStack(ModUpgrades.Damage3.get()), new ItemStack(ModItems.CircuitBoard.get()), new ItemStack(Items.IRON_SWORD));
		registerRecipeShapeless(1, new ItemStack(ModUpgrades.Damage4.get()), new ItemStack(ModItems.CircuitBoard.get()), new ItemStack(Items.DIAMOND_SWORD));
		registerRecipeShapeless(1, new ItemStack(ModUpgrades.Damage5.get()), new ItemStack(ModItems.CircuitBoard.get()), new ItemStack(Items.NETHERITE_SWORD));
		registerRecipeShapeless(1, new ItemStack(ModUpgrades.NoDamage.get()), new ItemStack(ModItems.CircuitBoard.get()), ItemTags.WOOL);
		registerRecipeShapeless(1, new ItemStack(ModUpgrades.Silence.get()), Ingredient.of(new ItemStack(ModItems.CircuitBoard.get())), Ingredient.of(ItemTags.WOOL), Ingredient.of(ItemTags.WOOL), Ingredient.of(ItemTags.WOOL));
		registerRecipeShapeless(1, new ItemStack(ModUpgrades.Push.get()), new ItemStack(ModItems.CircuitBoard.get()), new ItemStack(Blocks.PISTON));
		registerRecipeShapeless(1, new ItemStack(ModUpgrades.Pull.get()), new ItemStack(ModItems.CircuitBoard.get()), new ItemStack(Blocks.STICKY_PISTON));
		registerRecipe(1, new ItemStack(ModUpgrades.Distance.get()), new ItemStack(ModItems.CircuitBoard.get()), new ItemStack(Blocks.GLASS), ItemStack.EMPTY, new ItemStack(Blocks.GLASS));
		registerRecipe(1, new ItemStack(ModUpgrades.Distance2.get()), new ItemStack(ModUpgrades.Distance.get()), new ItemStack(Blocks.GLASS), ItemStack.EMPTY, new ItemStack(Blocks.GLASS));
		registerRecipe(1, new ItemStack(ModUpgrades.Distance3.get()), new ItemStack(ModUpgrades.Distance2.get()), new ItemStack(Blocks.GLASS), ItemStack.EMPTY, new ItemStack(Blocks.GLASS));
		registerRecipeShapeless(1, new ItemStack(ModUpgrades.Mode.get()), new ItemStack(ModItems.CircuitBoard.get()), new ItemStack(Blocks.GLASS), new ItemStack(Items.PAPER));
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
		
		registerRecipe(1, ModItems.LaserMultiTool.get().getDefaultInstance() , ModItems.LaserSword.get().getDefaultInstance(), ModItems.LaserPickaxe.get().getDefaultInstance(), ModItems.LaserAxe.get().getDefaultInstance(), ModItems.LaserShovel.get().getDefaultInstance());
		
		registerRecipe(1, ModItems.LaserHelmet.get().getDefaultInstance() ,new ItemStack(Items.IRON_HELMET), new ItemStack(ModItems.LaserCrystal.get()), new ItemStack(ModItems.LaserCrystal.get()), new ItemStack(ModItems.LaserCrystal.get()));
		registerRecipe(1, ModItems.LaserChestplate.get().getDefaultInstance() ,new ItemStack(Items.IRON_CHESTPLATE), new ItemStack(ModItems.LaserCrystal.get()), new ItemStack(ModItems.LaserCrystal.get()), new ItemStack(ModItems.LaserCrystal.get()));
		registerRecipe(1, ModItems.LaserLeggings.get().getDefaultInstance() ,new ItemStack(Items.IRON_LEGGINGS), new ItemStack(ModItems.LaserCrystal.get()), new ItemStack(ModItems.LaserCrystal.get()), new ItemStack(ModItems.LaserCrystal.get()));
		registerRecipe(1, ModItems.LaserBoots.get().getDefaultInstance() , new ItemStack(Items.IRON_BOOTS), new ItemStack(ModItems.LaserCrystal.get()), new ItemStack(ModItems.LaserCrystal.get()), new ItemStack(ModItems.LaserCrystal.get()));
		
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
	
	public static void registerRecipe(float speed, ItemStack output, Object inputBase, Object... inputs) {
		recipes.add(new PrecisionAssemblerRecipeBase(output, speed, inputBase, inputs));
	}
	
	public static void registerRecipeShapeless(float speed, ItemStack output, Object inputBase, Object... inputs) {
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
		Object[] inputs = recipe.getInputs();
		if (recipe instanceof PrecisionAssemblerRecipeShapeless) {
			for(int i = 0; i < inputs.length; i++) {
				Object obj = inputs[i];
				if(obj instanceof ItemStack) {
					ItemStack input = ((ItemStack)obj).copy();
					for (int j = 0; j < handler.getSlots() - 2; j++) {
						ItemStack slot = handler.getStackInSlot(j).copy();
						if(slot.getItem() == input.getItem()) {
							int newCount = slot.getCount() - input.getCount();
							if(newCount <= 0)
								slot = ItemStack.EMPTY;
							else
								slot.setCount(slot.getCount() - input.getCount());
							handler.setStackInSlot(j, slot);
						}
					}
				}else if(obj instanceof ITag<?>) {
					for (int j = 0; j < handler.getSlots() - 2; j++) {
						ItemStack slot = handler.getStackInSlot(j).copy();
						if(((ITag<Item>)obj).contains(slot.getItem())) {
							int newCount = slot.getCount() - 1;
							if(newCount <= 0)
								slot = ItemStack.EMPTY;
							else
								slot.setCount(slot.getCount() - 1);
							handler.setStackInSlot(j, slot);
						}
					}
				}else if(obj instanceof TagKey<?>) {
					for (int j = 0; j < handler.getSlots() - 2; j++) {
						ItemStack slot = handler.getStackInSlot(j).copy();
						if(slot.is((TagKey<Item>)obj)) {
							int newCount = slot.getCount() - 1;
							if(newCount <= 0)
								slot = ItemStack.EMPTY;
							else
								slot.setCount(slot.getCount() - 1);
							handler.setStackInSlot(j, slot);
						}
					}
				}
			}
		}else {
			for (int i = 0; i < Math.min(inputs.length, handler.getSlots() - 2); i++) {
				Object obj = inputs[i];
				if(obj instanceof ItemStack) {
					ItemStack input = ((ItemStack)obj).copy();
					ItemStack slot = handler.getStackInSlot(i).copy();
					int newCount = slot.getCount() - input.getCount();
					if(newCount <= 0)
						slot = ItemStack.EMPTY;
					else
						slot.setCount(slot.getCount() - input.getCount());
					handler.setStackInSlot(i, slot);
				}else if(obj instanceof ITag<?>) {
					ItemStack slot = handler.getStackInSlot(i).copy();
					if(((ITag<Item>)obj).contains(slot.getItem())) {
						int newCount = slot.getCount() - 1;
						if(newCount <= 0)
							slot = ItemStack.EMPTY;
						else
							slot.setCount(slot.getCount() - 1);
						handler.setStackInSlot(i, slot);
					}
				}else if(obj instanceof TagKey<?>) {
					ItemStack slot = handler.getStackInSlot(i).copy();
					if(slot.is((TagKey<Item>)obj)) {
						int newCount = slot.getCount() - 1;
						if(newCount <= 0)
							slot = ItemStack.EMPTY;
						else
							slot.setCount(slot.getCount() - 1);
						handler.setStackInSlot(i, slot);
					}
				}
			}
		}
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
