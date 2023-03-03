package KOWI2003.LaserMod.items.interfaces;

import KOWI2003.LaserMod.LaserProperties;
import KOWI2003.LaserMod.items.ItemUpgradeBase;
import KOWI2003.LaserMod.utils.LaserItemUtils;
import net.minecraft.world.item.ItemStack;

public interface ILaserUpgradable extends IItemColorable {

	public default ItemStack setProperties(ItemStack stack, LaserProperties properties) {
		return LaserItemUtils.setProperties(stack, properties);
	}
	
	public default LaserProperties getProperties(ItemStack stack) {
		return LaserItemUtils.getProperties(stack);
	}
	
	public default boolean canBeUsed(ItemUpgradeBase upgrade) {
		return false;
	}
	
	public default String[] getAbilityNames(ItemUpgradeBase upgrade) 
	{
		return upgrade.getAbilityNames();
	}
}
