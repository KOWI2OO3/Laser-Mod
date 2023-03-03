package KOWI2003.LaserMod.items.interfaces;

import KOWI2003.LaserMod.utils.LaserItemUtils;
import net.minecraft.world.item.ItemStack;	

public interface IExtendable {

	public default boolean isExtended(ItemStack stack)
	{
		return LaserItemUtils.isExtended(stack);
	}
	
	public default ItemStack setExtended(ItemStack stack, boolean value)
	{
		return LaserItemUtils.setExtended(stack, value);
	}
	
}
