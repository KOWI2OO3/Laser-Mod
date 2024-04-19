package KOWI2003.LaserMod.items.upgrades;

import KOWI2003.LaserMod.DamageSourceLaser.DamageSourceLaserArmor;
import KOWI2003.LaserMod.LaserProperties;
import KOWI2003.LaserMod.items.ItemUpgradeBase;
import KOWI2003.LaserMod.utils.LaserItemUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class UpgradeDamage extends ItemUpgradeBase {

	public UpgradeDamage(String name) {
		super(name);
		setCanBeUsedForLaser(true);
		setCanBeUsedForLaserTools(LaserTools.ALL, LaserTools.OMNI);
		setCanBeUsedForLaserArmor(true);
		AbilityNames = new String[] {"Sharpness"};
		abilityNameColor = new float[] {0.9f, 0.9f, 0.9f};
	}
	
	public UpgradeDamage(String name, int tier) {
		super(name, tier);
		setCanBeUsedForLaser(true);
		setCanBeUsedForLaserTools(LaserTools.ALL, LaserTools.OMNI);
		setCanBeUsedForLaserArmor(true);
		AbilityNames = new String[] {"Sharpness " + getTierLevelForAbilityName()};
		abilityNameColor = new float[] {0.9f, 0.9f, 0.9f};
	}
	
	@Override
	public boolean isUsefullForLaser() {
		return true;
	}
	
	@Override
	public String[] getArmorAbilityNames() {
		return new String[] {"Thorns " + getTierLevelForAbilityName()};
	}
	
	@Override
	public float getMultiplier(LaserProperties.Properties property) {
		if(property == LaserProperties.Properties.DAMAGE)
			return 1.3f * getTier();
		return super.getMultiplier(property);
	}
	
	@Override
	public void runOnEntityHitArmor(ItemStack item, LivingEntity attacker, LivingEntity player, float damageAmount) {
		DamageSource source = new DamageSourceLaserArmor("laser", LaserItemUtils.getProperties(item).hasUpgarde("fire"), player);
		int tier = getTierOr(1);
		attacker.hurt(source, 2 * tier/5);
		super.runOnEntityHitArmor(item, attacker, player, damageAmount);
	}
	
}
