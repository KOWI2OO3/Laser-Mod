package KOWI2003.LaserMod;

import javax.annotation.Nonnull;

import KOWI2003.LaserMod.config.ConfigHandler;
import KOWI2003.LaserMod.events.ModClientEvents;
import KOWI2003.LaserMod.handlers.EventHandler;
import KOWI2003.LaserMod.init.ModBlocks;
import KOWI2003.LaserMod.init.ModContainerTypes;
import KOWI2003.LaserMod.init.ModEntities;
import KOWI2003.LaserMod.init.ModItems;
import KOWI2003.LaserMod.init.ModRecipeTypes;
import KOWI2003.LaserMod.init.ModSounds;
import KOWI2003.LaserMod.init.ModUpgrades;
import KOWI2003.LaserMod.proxy.ClientProxy;
import KOWI2003.LaserMod.proxy.CommonProxy;
import KOWI2003.LaserMod.utils.ModChecker;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("lasermod")
public class MainMod {

	public static final CreativeModeTab blocks = new CreativeModeTab("tabLaserMod")
    {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(ModBlocks.Laser.get());
		}
		
		@Override
		public void fillItemList(@Nonnull NonNullList<ItemStack> list) {
			list.add(new ItemStack(ModItems.Manual.get()));
			ModBlocks.tabBlocks.forEach(block -> list.add(new ItemStack(block.get())));
			ModItems.tabStacks.forEach(item -> list.add(item.get().getDefaultInstance()));
		}
    };
    
    public static final CreativeModeTab upgrades = new CreativeModeTab("tabLaserMod.upgrades")
    {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(ModUpgrades.Speed.get());
		}
		
		@Override
		public void fillItemList(@Nonnull NonNullList<ItemStack> list) {
			ModUpgrades.tabStacks.forEach(item -> list.add(new ItemStack(item.get())));
		}
    };
	
	public static final CommonProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
	
	public MainMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::onCommonSetup);
        bus.addListener(this::onClientSetup);
        
        EventHandler.registerEvents();

		MinecraftForge.EVENT_BUS.register(new ModClientEvents());
		bus.register(new ModClientEvents());

    	ConfigHandler.register();

        ModBlocks.register(bus);
        ModItems.register(bus);
        ModSounds.register(bus);
        ModEntities.register(bus);
		ModRecipeTypes.register(bus);
        ModContainerTypes.CONTAINER_TYPES.register(bus);
	}
    
    private void onCommonSetup(final FMLCommonSetupEvent event)
    {	
    	ModChecker.check();
    	proxy.onSetupCommon();
    }
    
    private void onClientSetup(final FMLClientSetupEvent event)
    {
    	proxy.onSetupClient();
    	Utils.isClient = true;
//    	ModItems.registerPropertyOverrides(event);
    }
	
}
