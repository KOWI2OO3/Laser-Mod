package KOWI2003.LaserMod.items;

import KOWI2003.LaserMod.MainMod;
import net.minecraft.world.item.Item;

public class ItemDefault extends Item {

	public ItemDefault(Properties properties) {
		super(properties);
	}
	
	public ItemDefault() {
		super(new Item.Properties().tab(MainMod.blocks));
	}

}
