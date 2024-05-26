package KOWI2003.LaserMod.events;

import KOWI2003.LaserMod.LaserProperties;
import KOWI2003.LaserMod.items.ItemLaserArmorBase;
import KOWI2003.LaserMod.items.ItemUpgradeBase;
import KOWI2003.LaserMod.utils.LaserItemUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class LaserArmorEvents {

	@SubscribeEvent
	public void onGettingAttacked(LivingDamageEvent event) 
	{
		LivingEntity wearer = event.getEntityLiving();	//Wears Armor
		for(ItemStack stack : wearer.getArmorSlots()) {
			if(stack.getItem() instanceof ItemLaserArmorBase)
			{
				if(!LaserItemUtils.isExtended(stack))
					return;
				LaserProperties properties = LaserItemUtils.getProperties(stack);
				DamageSource source = event.getSource();
				Entity entity = source.getDirectEntity();
				LivingEntity attacker = null;
				if(entity instanceof LivingEntity)
					attacker = (LivingEntity)entity;
				
				for(ItemUpgradeBase upgrade : properties.getUpgrades())
					if(attacker != null)
						upgrade.runOnEntityHitArmor(stack, attacker, wearer, event.getAmount());
			}
		}
	}
	
}
