package KOWI2003.LaserMod;

import java.util.function.Supplier;

import KOWI2003.LaserMod.config.ConfigSerializer;
import KOWI2003.LaserMod.events.ModClientEvents;
import KOWI2003.LaserMod.handlers.EventHandler;
import KOWI2003.LaserMod.init.ModBlocks;
import KOWI2003.LaserMod.init.ModContainerTypes;
import KOWI2003.LaserMod.init.ModEntities;
import KOWI2003.LaserMod.init.ModItems;
import KOWI2003.LaserMod.init.ModSounds;
import KOWI2003.LaserMod.init.ModUpgrades;
import KOWI2003.LaserMod.proxy.ClientProxy;
import KOWI2003.LaserMod.proxy.CommonProxy;
import KOWI2003.LaserMod.utils.ModChecker;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.Row;
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

	public static final CreativeModeTab blocks = constructTab(() -> new ItemStack(ModBlocks.Laser.get()), "tabLaserMod")
			.displayItems((flag, out, does) -> {
				ModBlocks.tabBlocks.forEach(block -> out.accept(new ItemStack(block.get())));
				ModItems.tabStacks.forEach(item -> out.accept(item.get().getDefaultInstance()));
			}).build();
	
	public static final CreativeModeTab upgrades = constructTab(() -> new ItemStack(ModUpgrades.Speed.get()), "tabLaserMod.upgrades")
			.displayItems((flag, out, does) -> {
				ModUpgrades.tabStacks.forEach(item -> out.accept(new ItemStack(item.get())));
			}).build();
	
	public static final CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
	
	public MainMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::onCommonSetup);
        bus.addListener(this::onClientSetup);
        
        EventHandler.registerEvents();

		MinecraftForge.EVENT_BUS.register(new ModClientEvents());
		bus.register(new ModClientEvents());
//        new ModConfig();
    	ConfigSerializer.GetInstance();

        ModBlocks.register(bus);
        ModItems.register(bus);
        ModSounds.register(bus);
        ModEntities.register(bus);
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
    
    private static CreativeModeTab.Builder constructTab(Supplier<ItemStack> icon, String id) {
    	return CreativeModeTab.builder(Row.TOP, 10).icon(icon)
    			.title(MutableComponent.create(new TranslatableContents("itemGroup." + id)));
    }
	
}
