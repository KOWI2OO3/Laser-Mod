package KOWI2003.LaserMod.utils;

import KOWI2003.LaserMod.LaserProperties;
import KOWI2003.LaserMod.items.interfaces.IChargable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class LaserItemUtils {

	public static ItemStack setProperties(ItemStack stack, LaserProperties properties) {
		CompoundTag nbt = stack.getOrCreateTag();
		properties.save(nbt);
		stack.setTag(nbt);
		return stack;
	}
	
	public static LaserProperties getProperties(ItemStack stack) {
		CompoundTag nbt = stack.getTag();
		LaserProperties props = new LaserProperties();
		if(nbt != null)
			props.load(nbt);
		return props;
	}
	
	public static int getCharge(ItemStack stack) {
		CompoundTag nbt = stack.getTag();
		if(nbt == null)
			return 0;
		if(nbt.contains("charge"))
			return getProperCharge(nbt.getInt("charge"), stack);
		return 0;
	}
	
	public static ItemStack setCharge(ItemStack stack, int value) {
		CompoundTag nbt = stack.getOrCreateTag();
		value = Math.min(((IChargable)stack.getItem()).getMaxCharge(stack), Math.max(0, value));
		nbt.putInt("charge", value);
		stack.setTag(nbt);
		return stack;
	}
	
	public static float[] getColor(ItemStack stack) {
		CompoundTag nbt = stack.getTag();
		if(nbt == null)
			return new float[] {1, 1, 1};
		float red = 1f;
		if(nbt.contains("Red"))
			red = nbt.getFloat("Red");
		float green = 1f;
		if(nbt.contains("Green"))
			green= nbt.getFloat("Green");
		float blue = 1f;
		if(nbt.contains("Blue"))
			blue = nbt.getFloat("Blue");
		return new float[] {red, green, blue};
	}
	
	public static ItemStack setColor(ItemStack stack, float red, float green, float blue) {
		CompoundTag nbt = stack.getOrCreateTag();
		nbt.putFloat("Red", red);
		nbt.putFloat("Green", green);
		nbt.putFloat("Blue", blue);
		stack.setTag(nbt);
		return stack;
	}
	
	public static float getDurabilityMul(ItemStack stack) {
		LaserProperties prop = getProperties(stack);
		if(prop.hasProperty(LaserProperties.Properties.DURABILITY))
			return prop.getProperty(LaserProperties.Properties.DURABILITY);
		return 1.0f;
	}
	
	/**
	 * gets the additional Durability that is added by a upgrade
	 * @param stack
	 * @return
	 */
	public static float getDurabilityAddition(ItemStack stack) {
		LaserProperties prop = getProperties(stack);
		if(prop.hasProperty(LaserProperties.Properties.DURABILITY)) {
			float value = prop.getProperty(LaserProperties.Properties.DURABILITY);
			if(value > prop.getPropertyBase(LaserProperties.Properties.DURABILITY))
				return value;
		}
		return 0.0f;
	}
	
	public static boolean isExtended(ItemStack stack) {
		CompoundTag nbt = stack.getTag();
		if(nbt == null)
			return false;
		if(nbt.contains("extended"))
			return nbt.getBoolean("extended");
		return false;
	}
	
	public static ItemStack setExtended(ItemStack stack, boolean value) {
		boolean canExtend = true;
		if(stack.getItem() instanceof IChargable)
			canExtend = getCharge(stack) > 0;
		CompoundTag nbt = stack.getOrCreateTag();
		nbt.putBoolean("extended", canExtend ? value : false);
		stack.setTag(nbt);
		return stack;
	}
	
	public static double getDurabilityForDisplay(ItemStack stack) {
		if(stack.getItem() instanceof IChargable)
			return 1d - (double)getCharge(stack)/(double)((IChargable)stack.getItem()).getMaxCharge(stack);
		return 0.0d;
	}
	
	public static ItemStack handleCharge(ItemStack stack) {
		return setCharge(stack, getCharge(stack));
	}
	
	private static int getProperCharge(int charge, ItemStack stack) {
		if(charge > ((IChargable)stack.getItem()).getMaxCharge(stack))
			setCharge(stack, charge);
		return charge;
	}
}
