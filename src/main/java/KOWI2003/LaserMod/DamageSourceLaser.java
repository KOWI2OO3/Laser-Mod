package KOWI2003.LaserMod;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class DamageSourceLaser extends DamageSource {

	boolean isFire = false;
	
	public DamageSourceLaser(String damageTypeIn, boolean isFire) {
		super(damageTypeIn);
		this.isFire = isFire;
	}
	
	@Override
	public boolean isFire() {
		return isFire;
	}
	
	@Override
	public Component getLocalizedDeathMessage(LivingEntity entityLivingBaseIn) {
		return new TranslatableComponent("death.block.laser", entityLivingBaseIn.getDisplayName());
	}
	
	public static class DamageSourceLaserArmor extends DamageSource {

		boolean isFire = false;
		LivingEntity defender;
		
		public DamageSourceLaserArmor(String damageTypeIn, boolean isFire) {
			super(damageTypeIn);
			this.isFire = isFire;
		}
		
		public DamageSourceLaserArmor(String damageTypeIn, boolean isFire, LivingEntity defender) {
			super(damageTypeIn);
			this.isFire = isFire;
			this.defender = defender;
		}
		
		@Override
		public boolean isFire() {
			return isFire;
		}
		
		
		@Override
		public Component getLocalizedDeathMessage(LivingEntity entityLivingBaseIn) {
			return new TranslatableComponent("death.armor.laser", entityLivingBaseIn.getDisplayName(), getEntityName());
		}
		
		Component getEntityName() { 
			return defender != null ? defender.getDisplayName() : new TextComponent("someone");
		}
		
	}
}
