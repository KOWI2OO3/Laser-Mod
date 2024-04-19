package KOWI2003.LaserMod.events;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.init.ModTileTypes;
import KOWI2003.LaserMod.tileentities.render.InfuserRenderer;
import KOWI2003.LaserMod.tileentities.render.LaserControllerRender;
import KOWI2003.LaserMod.tileentities.render.LaserProjectorRenderer;
import KOWI2003.LaserMod.tileentities.render.LaserRender;
import KOWI2003.LaserMod.tileentities.render.MirrorRender;
import KOWI2003.LaserMod.tileentities.render.ModStationRenderer;
import KOWI2003.LaserMod.tileentities.render.PrecisionAssemblerRenderer;
import KOWI2003.LaserMod.tileentities.render.RenderAdvancedLaser;
import KOWI2003.LaserMod.utils.ModChecker;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public class ModClientEvents {

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void registerRenderer(RegisterRenderers event) {
		event.registerBlockEntityRenderer(ModTileTypes.LASER, LaserRender::new);
		event.registerBlockEntityRenderer(ModTileTypes.INFUSER, InfuserRenderer::new);
		event.registerBlockEntityRenderer(ModTileTypes.MOD_STATION, ModStationRenderer::new);
		event.registerBlockEntityRenderer(ModTileTypes.LASER_PROJECTOR, LaserProjectorRenderer::new);
		event.registerBlockEntityRenderer(ModTileTypes.LASER_CONTROLLER, LaserControllerRender::new);
		event.registerBlockEntityRenderer(ModTileTypes.MIRROR, MirrorRender::new);
		event.registerBlockEntityRenderer(ModTileTypes.ADVANCED_LASER, RenderAdvancedLaser::new);
		event.registerBlockEntityRenderer(ModTileTypes.PRECISION_ASSEMBLER, PrecisionAssemblerRenderer::new);
		
		ModChecker.check();
		if(ModChecker.isComputercraftLoaded)
			event.registerBlockEntityRenderer(ModTileTypes.LASER_CONTROLLER_CC, LaserControllerRender::new);
	}
	
}
