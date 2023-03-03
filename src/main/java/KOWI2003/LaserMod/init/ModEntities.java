package KOWI2003.LaserMod.init;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.entities.EntityLaserBullet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES 
	= DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Reference.MODID);
	
	public static final RegistryObject<EntityType<EntityLaserBullet>> LASER_BULLET = ENTITY_TYPES.register("laser_bullet", 
			() ->  EntityType.Builder.<EntityLaserBullet>of(EntityLaserBullet::new, MobCategory.MISC)
			.sized(1, 1).clientTrackingRange(10)
			.build(new ResourceLocation(Reference.MODID, "laser_bullet").toString()));
	
	public static void register(IEventBus eventBus) {
		ENTITY_TYPES.register(eventBus);
	}
	
}
