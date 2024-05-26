package KOWI2003.LaserMod.recipes.infuser;

import KOWI2003.LaserMod.tileentities.TileEntityInfuser;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.tags.ITag;

public interface IInfuserRecipe {

	public default ItemStack getOutput(TileEntityInfuser te) { return getOutput(); }
	public default Object[] getInputs(TileEntityInfuser te) { return getInputs(); }
	public ItemStack getOutput();
	public Object[] getInputs();
	public default boolean isRecipeValid(TileEntityInfuser te) {
		if(getInputs(te).length >= 2) {
			if(getInputs(te)[0] != null && getInputs(te)[1] != null && getOutput(te) != null) {
				boolean con1  = false;
				if(getInputs(te)[0] instanceof ItemStack)
					con1 = te.handler.getStackInSlot(0).getItem() == ((ItemStack)getInputs(te)[0]).getItem() && te.handler.getStackInSlot(0).getCount() >= ((ItemStack)getInputs(te)[0]).getCount();
				else if(getInputs(te)[0] instanceof TagKey<?>) {
					TagKey<Item> tag = (TagKey<Item>)getInputs(te)[0];
					con1 = te.handler.getStackInSlot(0).is(tag) && te.handler.getStackInSlot(0).getCount() > 0;
				}else if(getInputs(te)[0] instanceof ITag<?>) {
					ITag<Item> tag = (ITag<Item>)getInputs(te)[0];
					con1 = tag.contains(te.handler.getStackInSlot(0).getItem()) && te.handler.getStackInSlot(0).getCount() > 0;
				}else
					con1 = te.handler.getStackInSlot(0).isEmpty();
				boolean con2 = false;
				if(getInputs(te)[1] instanceof ItemStack)
					con2 = te.handler.getStackInSlot(1).getItem() == ((ItemStack)getInputs(te)[1]).getItem() && te.handler.getStackInSlot(1).getCount() >= ((ItemStack)getInputs(te)[1]).getCount();
				else if(getInputs(te)[1] instanceof TagKey<?>) {
					TagKey<Item> tag = (TagKey<Item>)getInputs(te)[1];
					con2 = te.handler.getStackInSlot(1).is(tag) && te.handler.getStackInSlot(1).getCount() > 0;
				}else
					con2 = te.handler.getStackInSlot(1).isEmpty();
				boolean out = (te.handler.getStackInSlot(2).getItem() == getOutput(te).getItem() && 
						te.handler.getStackInSlot(2).getCount() + getOutput(te).getCount() < te.handler.getStackInSlot(2).getItem().getItemStackLimit(te.handler.getStackInSlot(2))) || te.handler.getStackInSlot(2).isEmpty();
				return con1 && con2 && out;
			}
		}
		return false;	
	}
	public float getRecipeSpeed();
}
