package KOWI2003.LaserMod.recipes.infuser;

import KOWI2003.LaserMod.tileentities.TileEntityInfuser;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import oshi.util.tuples.Pair;

public interface IInfuserRecipe {

	public default ItemStack getOutput(TileEntityInfuser te) { return getOutput(); }
	public default Pair<Ingredient, Integer>[] getInputs(TileEntityInfuser te) { 
		Ingredient[] in = getInputs();
		Pair<Ingredient, Integer>[] list = new Pair[in.length];
		for (int i = 0; i < list.length; i++)
			list[i] = new Pair<Ingredient, Integer>(in[i], 1);
		return list;
	}
	public ItemStack getOutput();
	public Ingredient[] getInputs();
	public default boolean isRecipeValid(TileEntityInfuser te) {
		if(getInputs(te).length >= 2) {
			if(getInputs(te)[0] != null && getInputs(te)[1] != null && getOutput(te) != null) {
				boolean con1  = false;
				if(!getInputs(te)[0].getA().isEmpty())
					con1 = getInputs(te)[0].getA().test(te.handler.getStackInSlot(0)) && te.handler.getStackInSlot(0).getCount() >= getInputs(te)[0].getB();
				else
					con1 = te.handler.getStackInSlot(0).isEmpty();
				
				boolean con2 = false;
				if(!getInputs(te)[1].getA().isEmpty())
					con2 = getInputs(te)[1].getA().test(te.handler.getStackInSlot(1)) && te.handler.getStackInSlot(1).getCount() >= getInputs(te)[0].getB();
				else
					con2 = te.handler.getStackInSlot(1).isEmpty();
				
				boolean out = (te.handler.getStackInSlot(2).getItem() == getOutput(te).getItem() && 
						te.handler.getStackInSlot(2).getCount() + getOutput(te).getCount() <= te.handler.getStackInSlot(2).getItem().getMaxStackSize(te.handler.getStackInSlot(2))) || te.handler.getStackInSlot(2).isEmpty();
				return con1 && con2 && out;
			}
		}
		return false;	
	}
	public float getRecipeSpeed();
}
