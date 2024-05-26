package KOWI2003.LaserMod.events;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.entities.render.LaserBulletRenderer;
import KOWI2003.LaserMod.init.ModEntities;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Reference.MODID)
public class RegistryEvents {

	@SubscribeEvent
	public static void rendererRegister(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(ModEntities.LASER_BULLET.get(), LaserBulletRenderer::new);
	}
}