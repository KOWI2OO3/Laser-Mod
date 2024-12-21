package KOWI2003.LaserMod.items.upgrades;

import KOWI2003.LaserMod.LaserProperties;
import KOWI2003.LaserMod.config.Config;
import KOWI2003.LaserMod.init.ModBlocks;
import KOWI2003.LaserMod.items.ItemUpgradeBase;
import net.minecraft.world.level.block.Block;

public class UpgradeSpeed extends ItemUpgradeBase {

	public UpgradeSpeed(String name) {
		super(name);
		setCanBeUsedForLaserTools(LaserTools.ALL);
		setCanBeUsedForLaserTools(LaserTools.OMNI);
		USEFULL_MACHINES.add(ModBlocks.Infuser.get());
		USEFULL_MACHINES.add(ModBlocks.PrecisionAssembler.get());
		AbilityNames = new String[] {"Efficiency"};
		abilityNameColor = new float[] {0.1f, .9f, .8f};
	}
	
	@Override
	public void getMachineUse(Block Machine) {
		
		super.getMachineUse(Machine);
	}
	
	@Override
	public float getMultiplier(LaserProperties.Properties property) {
		if(property == LaserProperties.Properties.SPEED)
			return Config.getInstance().upgradeSettings.speedUpgradeMultiplier;
		return super.getMultiplier(property);
	}
	
}
