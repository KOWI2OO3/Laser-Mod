package KOWI2003.LaserMod.recipes.infuser.recipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.config.ModConfig;
import KOWI2003.LaserMod.init.ModItems;
import KOWI2003.LaserMod.init.ModRecipeTypes;
import KOWI2003.LaserMod.items.interfaces.IChargable;
import KOWI2003.LaserMod.recipes.infuser.IInfuserRecipe;
import KOWI2003.LaserMod.utils.LaserItemUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Blocks;
import oshi.util.tuples.Pair;

public class InfuserRecipeChargingTool implements IInfuserRecipe {

	public ItemStack redstone;
	public ItemStack tool;
	
	public InfuserRecipeChargingTool() {}
	
	public static float getRedstoneToChargeRatio() {
		return ModConfig.GetConfig().redstoneChargeValue;
	}
	
	@Override
	public ItemStack getOutput(@Nullable Container container) {
		if(container == null) {
			var mock = getInputs(null)[1].getA().getItems()[0].copy();
			mock = LaserItemUtils.setCharge(mock, 10);
			return mock;
		}
		
		ItemStack toolStack = new ItemStack(Blocks.VOID_AIR);
		if(!(container.getItem(1).getItem() instanceof IChargable))
			return null;
		toolStack = container.getItem(1);
		toolStack = LaserItemUtils.setCharge(toolStack, (int) Math.min(LaserItemUtils.getCharge(toolStack) + 
				(int)(getRequiredRedstone(container) * getRedstoneToChargeRatio()),((IChargable)toolStack.getItem()).getMaxCharge(toolStack)));
		return toolStack;
	}

	public int getRequiredRedstone(@Nonnull Container container) {
		int stackSize = 1;
		if(container.getItem(0).getItem() == Items.REDSTONE) 
			stackSize = container.getItem(0).getCount();
		
		if(!(container.getItem(1).getItem() instanceof IChargable))
			return stackSize;
		
		ItemStack toolStack = container.getItem(1);
		int charge = LaserItemUtils.getCharge(toolStack);
		int chargeDiff = ((IChargable)toolStack.getItem()).getMaxCharge(toolStack) - charge;
		float redstoneRequired = chargeDiff / getRedstoneToChargeRatio();
		return (int) Math.min(stackSize, Math.ceil(redstoneRequired));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Pair<Ingredient, Integer>[] getInputs(@Nullable Container container) {
		if(container == null) {
			return new Pair[] {new Pair<>(Ingredient.of(new ItemStack(Items.REDSTONE)), 10), new Pair<>(Ingredient.of(
				new ItemStack(ModItems.LaserPickaxe.get()),
				new ItemStack(ModItems.LaserAxe.get()),
				new ItemStack(ModItems.LaserSword.get()),
				new ItemStack(ModItems.LaserShovel.get()),
				new ItemStack(ModItems.LaserHoe.get()),

				new ItemStack(ModItems.LaserBoots.get()),
				new ItemStack(ModItems.LaserLeggings.get()),
				new ItemStack(ModItems.LaserHelmet.get()),
				new ItemStack(ModItems.LaserChestplate.get())
			), 1)};
		}

		int stackSize = getRequiredRedstone(container);
		ItemStack toolStack = new ItemStack(Blocks.VOID_AIR);
		if(container.getItem(1).getItem() instanceof IChargable)
			toolStack = container.getItem(1);
		return new Pair[] {new Pair<>(Ingredient.of(new ItemStack(Items.REDSTONE)), stackSize), new Pair<>(Ingredient.of(toolStack), 1)};
	}
	
	@Override
	public boolean isRecipeValid(@Nonnull Container container) {
		return container.getItem(2).isEmpty() && container.getItem(0).getItem() == Items.REDSTONE && 
				(container.getItem(1).getItem() instanceof IChargable && LaserItemUtils.getDurabilityForDisplay(container.getItem(1)) > 0.0f);
	}

	@Override
	public float getRecipeSpeed() {
		return ModConfig.GetConfig().machineSettings.infuserToolChargingSpeed;
	}

	@Override
	public ItemStack getOutput() {
		return new ItemStack(Blocks.AIR);
	}

	@Override
	public ResourceLocation getId() {
		return new ResourceLocation(Reference.MODID, "infuser/tool_charger");
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return new InfuserChargingRecipeSerializer();
	}

	@Override
	public RecipeType<?> getType() {
		return ModRecipeTypes.simpleType(getId());
	}
}
