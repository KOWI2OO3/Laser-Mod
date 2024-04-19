package KOWI2003.LaserMod.items;

import KOWI2003.LaserMod.items.interfaces.IItemColorable;
import net.minecraft.world.item.ItemStack;

public class ItemLaserTool extends ItemDefault implements IItemColorable {

	@Override
	public float[] getRGB(ItemStack stack, int tintindex) {
		return new float[] {1.0f, 0.0f, 0.0f};
	}

}
