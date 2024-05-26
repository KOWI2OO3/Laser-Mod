package KOWI2003.LaserMod.recipes.precisionAssembler;
	
import java.util.ArrayList;
import java.util.List;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.tags.ITag;

public interface IPrecisionAssemblerRecipe {

	public Object[] getInputs();
	public Object getInputBase();
	public ItemStack getOutput();
	public default boolean isRecipeValid(ItemStackHandler handler) {
		if(handler != null) {
			boolean condition = true;
			Object[] inputs = getInputs();
			for (int i = 0; i < Math.min(handler.getSlots() - 2, inputs.length); i++) {
				Object obj = inputs[i];
				ItemStack slotStack = handler.getStackInSlot(i).copy();
				if(obj instanceof ItemStack) {
					ItemStack stack = ((ItemStack)obj).copy();
					condition = condition && stack.isEmpty() ? slotStack.isEmpty() : (stack.getItem() == slotStack.getItem() && stack.getCount() <= slotStack.getCount());
					if(!condition)
						return false;
				}else if(obj instanceof ITag<?>) {
					ITag<Item> itemTag = (ITag<Item>)obj;
					condition = condition && itemTag.contains(slotStack.getItem()) && slotStack.getCount() >= 1;
					if(!condition)
						return false;
				}else if(obj instanceof TagKey<?>) {
					TagKey<Item> itemTag = (TagKey<Item>)obj;
					condition = condition && slotStack.is(itemTag) && slotStack.getCount() >= 1;
					if(!condition)
						return false;
				}else {
					condition = condition && slotStack.isEmpty();
					if(!condition)
						return false;
				}
			}
			if(!condition)
				return false;
			
			Object obj = getInputBase();
			ItemStack slot = handler.getStackInSlot(3).copy();
			if(obj instanceof ItemStack) {
				ItemStack inputBase = ((ItemStack)obj).copy();
				if(handler.getSlots() <= 0)
					return false;
				if(inputBase.isEmpty())
					return slot.isEmpty();
				condition = condition && inputBase.isEmpty() ? slot.isEmpty() : (inputBase.getItem() == slot.getItem() && inputBase.getCount() <= slot.getCount());
				if(!condition)
					return false;
			}else if(obj instanceof ITag<?>) {
				ITag<Item> itemTag = (ITag<Item>)obj;
				condition = condition && itemTag.contains(slot.getItem()) && slot.getCount() >= 1;
				if(!condition)
					return false;
			}else if(obj instanceof TagKey<?>) {
				TagKey<Item> itemTag = (TagKey<Item>)obj;
				condition = condition && slot.is(itemTag) && slot.getCount() >= 1;
				if(!condition)
					return false;
			}else {
				condition = condition && slot.isEmpty();
				if(!condition)
					return false;
			}
			
			ItemStack output = getOutput().copy();
			ItemStack lastSlot = handler.getStackInSlot(4).copy();
			if(output.isEmpty())
				return false;
			condition = condition && lastSlot.isEmpty() ? true : (lastSlot.getItem() == output.getItem() && lastSlot.getCount() + output.getCount() <= output.getItem().getItemStackLimit(output));
			return condition;
		}
		return false;
	}

	public default Ingredient[] getInputsIngredient() {
		Object[] objs = getInputs();
		List<Ingredient> list = new ArrayList<Ingredient>();
		for (Object obj : objs) {
			if(obj instanceof ItemStack) {
				list.add(Ingredient.of((ItemStack)obj));
			}else if(obj instanceof ITag<?>) {
//				list.add(Ingredient.of((ITag<Item>)obj));
			}else 
				list.add(Ingredient.EMPTY);
		}
		return list.toArray(new Ingredient[] {});
	}
	
	public default Ingredient getInputBaseIngredient() {
		Object obj = getInputBase();
		if(obj instanceof ItemStack) {
			return Ingredient.of((ItemStack)obj);
		}else if(obj instanceof TagKey<?>) {
			return Ingredient.of((TagKey<Item>)obj);
		}else 
			return Ingredient.EMPTY;
	}
	
	public float getRecipeSpeed();
	
}
