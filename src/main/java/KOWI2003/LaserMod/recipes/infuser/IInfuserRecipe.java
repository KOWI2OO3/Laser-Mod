package KOWI2003.LaserMod.recipes.infuser;

import KOWI2003.LaserMod.tileentities.TileEntityInfuser;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public interface IInfuserRecipe {

	public default ItemStack getOutput(TileEntityInfuser te) { return getOutput(); }
	public default Ingredient[] getInputs(TileEntityInfuser te) { return getInputs(); }
	public ItemStack getOutput();
	public Ingredient[] getInputs();
	public default boolean isRecipeValid(TileEntityInfuser te) {
		if(getInputs(te).length >= 2) {
			if(getInputs(te)[0] != null && getInputs(te)[1] != null && getOutput(te) != null) {
				boolean con1  = false;
				if(!getInputs(te)[0].isEmpty())
					con1 = getInputs(te)[0].test(te.handler.getStackInSlot(0)) && te.handler.getStackInSlot(0).getCount() >= 1;
				else
					con1 = te.handler.getStackInSlot(0).isEmpty();
				
				boolean con2 = false;
				if(!getInputs(te)[1].isEmpty())
					con2 = getInputs(te)[1].test(te.handler.getStackInSlot(1)) && te.handler.getStackInSlot(1).getCount() >= 1;
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
