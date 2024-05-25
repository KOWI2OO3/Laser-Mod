package KOWI2003.LaserMod;

import javax.annotation.Nonnull;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.network.chat.contents.TranslatableContents;
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
	public Component getLocalizedDeathMessage(@Nonnull LivingEntity entityLivingBaseIn) {
		return MutableComponent.create(new TranslatableContents("death.block.laser", entityLivingBaseIn.getDisplayName()));
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
		public Component getLocalizedDeathMessage(@Nonnull LivingEntity entityLivingBaseIn) {
			return MutableComponent.create(new TranslatableContents("death.armor.laser", entityLivingBaseIn.getDisplayName(), getEntityName()));
		}
		
		Component getEntityName() { 
			return defender != null ? defender.getDisplayName() : MutableComponent.create(new LiteralContents("someone"));
		}
		
	}
}
