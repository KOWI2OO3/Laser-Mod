package KOWI2003.LaserMod.proxy;

import KOWI2003.LaserMod.gui.GuiInfuser;
import KOWI2003.LaserMod.gui.GuiLaser;
import KOWI2003.LaserMod.gui.GuiModStation;
import KOWI2003.LaserMod.gui.GuiPrecisionAssembler;
import KOWI2003.LaserMod.gui.ItemStackPropertyGui;
import KOWI2003.LaserMod.handlers.EventHandler;
import KOWI2003.LaserMod.init.ModBlocks;
import KOWI2003.LaserMod.init.ModContainerTypes;
import KOWI2003.LaserMod.init.ModKeybindings;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ClientRegistry;

public class ClientProxy extends CommonProxy {
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void onSetupClient() {
		//Tile Entity renderers are moved to the ModClientEvents!
		MenuScreens.register(ModContainerTypes.LASER_CONTAINER_TYPE.get(), GuiLaser::new);
		MenuScreens.register(ModContainerTypes.INFUSER_CONTAINER_TYPE.get(), GuiInfuser::new);
		MenuScreens.register(ModContainerTypes.MOD_STATION_CONTAINER_TYPE.get(), GuiModStation::new);
		MenuScreens.register(ModContainerTypes.PRECISION_ASSEMBLER_TYPE.get(), GuiPrecisionAssembler::new);
		
		MenuScreens.register(ModContainerTypes.ITEM_PROPERTY_TYPE.get(), ItemStackPropertyGui::new);
		
		ItemBlockRenderTypes.setRenderLayer(ModBlocks.Infuser.get(), RenderType.translucent());
		
		for(KeyMapping key : ModKeybindings.mappings)
			ClientRegistry.registerKeyBinding(key);
		
		EventHandler.registerClientEvents();
	}
}
