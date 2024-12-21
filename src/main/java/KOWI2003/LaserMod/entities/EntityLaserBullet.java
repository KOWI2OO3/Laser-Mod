package KOWI2003.LaserMod.entities;

import java.util.List;

import javax.annotation.Nonnull;

import KOWI2003.LaserMod.items.ItemUpgradeBase;
import KOWI2003.LaserMod.items.LaserItem;
import KOWI2003.LaserMod.utils.LaserItemUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EntityLaserBullet extends AbstractArrow {

	private static final EntityDataAccessor<Float> RED = SynchedEntityData.defineId(EntityLaserBullet.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> GREEN = SynchedEntityData.defineId(EntityLaserBullet.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> BLUE = SynchedEntityData.defineId(EntityLaserBullet.class, EntityDataSerializers.FLOAT);

	private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(EntityLaserBullet.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> KNOCKBACK = SynchedEntityData.defineId(EntityLaserBullet.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> FIRE_DAMAGE = SynchedEntityData.defineId(EntityLaserBullet.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<ItemStack> TOOL = SynchedEntityData.defineId(EntityLaserBullet.class, EntityDataSerializers.ITEM_STACK);
	
	public EntityLaserBullet(EntityType<EntityLaserBullet> type, LivingEntity archer, Level level) {
		super(type, archer, level);
	}
	
	public EntityLaserBullet(EntityType<EntityLaserBullet> type, LivingEntity archer, Level level, float damage, float knockback, float fireDamage) {
		this(type, archer, level);
//		setUpgrades(properties.getUpgrades());
		setDamageProperties(damage, knockback, fireDamage);
	}
	
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(RED, 1.0f);
		entityData.define(GREEN, 1.0f);
		entityData.define(BLUE, 1.0f);
		entityData.define(DAMAGE, 6.0f);
		entityData.define(KNOCKBACK, .5f);
		entityData.define(FIRE_DAMAGE, 0f);
		entityData.define(TOOL, ItemStack.EMPTY);
	}

	public void setFiringTool(ItemStack tool) { 
		entityData.set(TOOL, tool);
	}
	
	public void setDamageProperties(float damage, float knockback, float fireDamage) {
		entityData.set(DAMAGE, damage);
		entityData.set(KNOCKBACK, knockback);
		entityData.set(FIRE_DAMAGE, fireDamage);
	}
	
	public void setColor(float[] color) {
		entityData.set(RED, color.length > 0 ? color[0] : 1.0f);
		entityData.set(GREEN, color.length > 1 ? color[1] : 1.0f);
		entityData.set(BLUE, color.length > 2 ? color[2] : 1.0f);
	}
	
	public List<ItemUpgradeBase> getUpgrades() {
		ItemStack stack = entityData.get(TOOL);
		if(!stack.isEmpty())
			if(stack.getItem() instanceof LaserItem)
				return LaserItemUtils.getProperties(stack).getUpgrades();
		return List.of();
	}
	
	@Override
	public void tick() {
		super.tick();
		if(tickCount / 20f > 10) // when it has existed for 10 seconds get rid of it (maybe later use an nice animation like make it smaller until it doesn't exist anymore)
			kill();
	}

	public EntityLaserBullet(EntityType<EntityLaserBullet> type, Level level) {
		super(type, level);
	}

	@Override
	protected ItemStack getPickupItem() {
		return ItemStack.EMPTY;
	}
	
	@Override
	protected void onHit(@Nonnull HitResult hit) {
		HitResult.Type hitresult$type = hit.getType();
		if (hitresult$type == HitResult.Type.ENTITY) {
			this.onHitEntity((EntityHitResult)hit);
		}
		kill();
	}
	
	@Override
	protected void onHitEntity(@Nonnull EntityHitResult hit) {
		float knockback = entityData.get(KNOCKBACK);
		
		Entity entity = hit.getEntity();
		Entity entity1 = this.getOwner();
		DamageSource damagesource;
		if (entity1 == null) {
			damagesource = DamageSource.arrow(this, this);
		} else {
    	  damagesource = DamageSource.arrow(this, entity1);
    	  if (entity1 instanceof LivingEntity) {
    		  ((LivingEntity)entity1).setLastHurtMob(entity);
    	  }
		}
		
		if(entity instanceof LivingEntity && entity1 instanceof LivingEntity) {
			List<ItemUpgradeBase> upgrades = getUpgrades();
			for (ItemUpgradeBase upgrade : upgrades) {
				upgrade.runLaserToolHitEnemy(entityData.get(TOOL), (LivingEntity)entity, (LivingEntity)entity1);
			}
		}
		
		if(entity.hurt(damagesource, entityData.get(DAMAGE))) {
			if (entity instanceof LivingEntity) {
				LivingEntity livingentity = (LivingEntity)entity;
				if (knockback > 0) {
					Vec3 vec3 = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double)knockback * 0.6D);
					if (vec3.lengthSqr() > 0.0D) {
						livingentity.push(vec3.x, 0.1D, vec3.z);
					}
				}
				if(entityData.get(FIRE_DAMAGE) > 0)
					livingentity.setSecondsOnFire(entityData.get(FIRE_DAMAGE).intValue());
			}
		}

	}
	
	public float[] getColor() {
		return new float[] {entityData.get(RED), entityData.get(GREEN), entityData.get(BLUE)};
	}

}
