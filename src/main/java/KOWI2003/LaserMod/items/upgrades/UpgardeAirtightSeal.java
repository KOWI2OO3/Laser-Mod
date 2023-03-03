package KOWI2003.LaserMod.items.upgrades;

import KOWI2003.LaserMod.items.ItemUpgradeBase;
import KOWI2003.LaserMod.items.interfaces.IExtendable;
import KOWI2003.LaserMod.utils.LaserItemUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class UpgardeAirtightSeal extends ItemUpgradeBase {

	public UpgardeAirtightSeal(String name, String[] AbilityNames, float[] AbilityNameColor) {
		super(name, AbilityNames, AbilityNameColor);
		setUsefullForArmor(true, false, false, false);
	}
	
	@Override
	public void runOnArmorTick(ItemStack stack, Level level, Player player, EquipmentSlot slot) {
		if(slot == EquipmentSlot.HEAD) 
		{
			boolean isExtended = true;
			if(stack.getItem() instanceof IExtendable)
				isExtended = LaserItemUtils.isExtended(stack);
			if(isExtended)
				player.setAirSupply(player.getMaxAirSupply());
		}
	}
	
}
