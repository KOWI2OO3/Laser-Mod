package KOWI2003.LaserMod.items.upgrades;

import KOWI2003.LaserMod.LaserProperties;
import KOWI2003.LaserMod.items.ItemUpgradeBase;

public class UpgradeCapacity extends ItemUpgradeBase {

	public UpgradeCapacity(String name, int tier) {
		super(name, tier,  new String[] {"Capacity"},new float[] {0.8f, 0.4f, 0.7f});
		AbilityNames = new String[] {"Capacity " + getTierLevelForAbilityName()};
		setCanBeUsedForLaserArmor(true);
		setCanBeUsedForLaserTools(LaserTools.ALL, LaserTools.OMNI);
	}
	
	@Override
	public float getMultiplier(LaserProperties.Properties property) {
		if(property == LaserProperties.Properties.DURABILITY)
			return getTier() + 1;
		return super.getMultiplier(property);
	}
	
}
