package KOWI2003.LaserMod.items.interfaces;

import KOWI2003.LaserMod.utils.LaserItemUtils;
import net.minecraft.world.item.ItemStack;

public interface IItemColorable {

	default float[] getRGB(ItemStack stack, int tintindex) {
		return getColor(stack);
	}
	
	default float[] getColor(ItemStack stack)
	{
		return LaserItemUtils.getColor(stack);
	}
	
	default ItemStack setColor(ItemStack stack, float red, float green, float blue) {
		return LaserItemUtils.setColor(stack, red, green, blue);
	}
	
}
