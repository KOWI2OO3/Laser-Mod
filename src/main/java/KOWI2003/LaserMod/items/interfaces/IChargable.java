package KOWI2003.LaserMod.items.interfaces;

import KOWI2003.LaserMod.utils.LaserItemUtils;
import net.minecraft.world.item.ItemStack;

public interface IChargable {

	public default int getCharge(ItemStack stack) {
		return LaserItemUtils.getCharge(stack);
	}
	
	public default ItemStack setCharge(ItemStack stack, int value) {
		return LaserItemUtils.setCharge(stack, value);
	}
	
	public int getMaxCharge(ItemStack stack);
	
}
