package KOWI2003.LaserMod.recipes.infuser.recipe;

import KOWI2003.LaserMod.config.ModConfig;
import KOWI2003.LaserMod.items.interfaces.IChargable;
import KOWI2003.LaserMod.recipes.infuser.IInfuserRecipe;
import KOWI2003.LaserMod.tileentities.TileEntityInfuser;
import KOWI2003.LaserMod.utils.LaserItemUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

public class InfuserRecipeChargingTool implements IInfuserRecipe {

	public ItemStack redstone;
	public ItemStack tool;
	
	public InfuserRecipeChargingTool() {}
	
	public static float getRedstoneToChargeRatio() {
		return ModConfig.GetConfig().redstoneChargeValue;
	}
	
	@Override
	public ItemStack getOutput(TileEntityInfuser te) {
		ItemStack toolStack = new ItemStack(Blocks.VOID_AIR);
		if(!(te.handler.getStackInSlot(1).getItem() instanceof IChargable))
			return null;
		toolStack = te.handler.getStackInSlot(1);
		toolStack = LaserItemUtils.setCharge(toolStack, (int) Math.min(LaserItemUtils.getCharge(toolStack) + 
				(int)(getRequiredRedstone(te) * getRedstoneToChargeRatio()),((IChargable)toolStack.getItem()).getMaxCharge(toolStack)));
		return toolStack;
	}

	public int getRequiredRedstone(TileEntityInfuser te) {
		int stackSize = 1;
		if(te.handler.getStackInSlot(0).getItem() == Items.REDSTONE) {
			stackSize = te.handler.getStackInSlot(0).getCount();
		}
		if(!(te.handler.getStackInSlot(1).getItem() instanceof IChargable))
			return stackSize;
		ItemStack toolStack = te.handler.getStackInSlot(1);
		int charge = LaserItemUtils.getCharge(toolStack);
		int chargeDiff = ((IChargable)toolStack.getItem()).getMaxCharge(toolStack) - charge;
		float redstoneRequired = chargeDiff / getRedstoneToChargeRatio();
		return (int) Math.min(stackSize, Math.ceil(redstoneRequired));
	}
	
	@Override
	public Ingredient[] getInputs(TileEntityInfuser te) {
		int stackSize = getRequiredRedstone(te);
		ItemStack toolStack = new ItemStack(Blocks.VOID_AIR);
		if(te.handler.getStackInSlot(1).getItem() instanceof IChargable)
			toolStack = te.handler.getStackInSlot(1);
		return new Ingredient[] {Ingredient.of(new ItemStack(Items.REDSTONE, stackSize)), Ingredient.of(toolStack)};
	}
	
	@Override
	public boolean isRecipeValid(TileEntityInfuser te) {
		return te.handler.getStackInSlot(2).isEmpty() && te.handler.getStackInSlot(0).getItem() == Items.REDSTONE && 
				(te.handler.getStackInSlot(1).getItem() instanceof IChargable && LaserItemUtils.getDurabilityForDisplay(te.handler.getStackInSlot(1)) > 0.0f);
	}

	//Charge Speed (Change in Config?)
	@Override
	public float getRecipeSpeed() {
		return 2;
	}

	@Override
	public ItemStack getOutput() {
//		return new ItemStack(ModItems.LaserSword);
		return new ItemStack(Blocks.AIR);
	}

	@Override
	public Ingredient[] getInputs() {
		return new Ingredient[] {Ingredient.of(new ItemStack(Items.REDSTONE)), 
//				new ItemStack(ModItems.LaserSword)};
				Ingredient.of(new ItemStack(Blocks.AIR))};
	}

}
