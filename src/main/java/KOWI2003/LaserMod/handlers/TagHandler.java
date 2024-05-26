package KOWI2003.LaserMod.handlers;

import KOWI2003.LaserMod.Reference;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TagHandler {

	@SubscribeEvent
	public static void registerTags(RegistryEvent.Register<SoundEvent> event) {
		
	}
	
}
