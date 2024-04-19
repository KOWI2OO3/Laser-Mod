package KOWI2003.LaserMod.init;

import KOWI2003.LaserMod.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSounds {

	public static final DeferredRegister<SoundEvent> SOUNDS = 
			DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Reference.MODID);
	
	public static final RegistryObject<SoundEvent> LASER_ACTIVATE = register("block.laser.activate");
	public static final RegistryObject<SoundEvent> LASER_ACTIVE = register("block.laser.active");
	public static final RegistryObject<SoundEvent> LASER_DEACTIVATE = register("block.laser.deactivate");
	
	private static RegistryObject<SoundEvent> register(String name) {
		return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(Reference.MODID, name)));
	}
	
	public static void register(IEventBus bus)
	{
		SOUNDS.register(bus);
	}
	
}
