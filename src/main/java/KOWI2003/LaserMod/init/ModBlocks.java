package KOWI2003.LaserMod.init;

import java.util.LinkedList;
import java.util.function.Supplier;

import KOWI2003.LaserMod.MainMod;
import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.blocks.AdvancedLaserBlock;
import KOWI2003.LaserMod.blocks.BlockDeviceHub;
import KOWI2003.LaserMod.blocks.BlockInfuser;
import KOWI2003.LaserMod.blocks.BlockLaser;
import KOWI2003.LaserMod.blocks.BlockLaserCatcher;
import KOWI2003.LaserMod.blocks.BlockLaserProjector;
import KOWI2003.LaserMod.blocks.BlockMirror;
import KOWI2003.LaserMod.blocks.BlockModificationStation;
import KOWI2003.LaserMod.blocks.BlockPrecisionAssembler;
import KOWI2003.LaserMod.blocks.BlockRemote;
import KOWI2003.LaserMod.handlers.ColorHandler;
import KOWI2003.LaserMod.utils.ModChecker;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {
	
	public static final LinkedList<RegistryObject<Block>> tabBlocks = new LinkedList<>();
	
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MODID);
	
	public static RegistryObject<Block> Laser = register("laser", () -> new BlockLaser(Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(1.5F, 1.5F)));
	public static RegistryObject<Block> LaserCatcher = register("laser_catcher", () -> new BlockLaserCatcher(Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.5F, 5.0F)));
	public static RegistryObject<Block> Infuser = register("infuser", () -> new BlockInfuser(Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(1.5F, 3.5F)));
	public static RegistryObject<Block> ModStation = register("mod_station", () -> new BlockModificationStation(Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(1.5F, 3.5F)));
	public static RegistryObject<Block> LaserProjector = register("laser_projector", () -> new BlockLaserProjector(Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(1.5F, 4F)));
	public static RegistryObject<Block> LaserController = register("laser_controller", () -> new BlockRemote(Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(1.5F, 4F)));
	public static RegistryObject<Block> Mirror = register("mirror", () -> new BlockMirror(Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(1.5F, 4.0F)));
	public static RegistryObject<Block> PrecisionAssembler = register("precision_assembler", () -> new BlockPrecisionAssembler(Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(1.5F, 3.5F)));
	public static RegistryObject<Block> AdvancedLaser = register("advanced_laser", () -> new AdvancedLaserBlock(Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(1.5F, 1.5F)));
	public static RegistryObject<Block> DeviceHub;
	
	static {
		ModChecker.check();
		if(ModChecker.isComputercraftLoaded)
			DeviceHub = registerHidden("device_hub", () -> new BlockDeviceHub(Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(1.5F, 4.0F)));
	}
	
	private static RegistryObject<Block> register(String name, final Supplier<? extends Block> block) {
		RegistryObject<Block> toReturn = BLOCKS.register(name, block);
		registerBlockItem(name, toReturn, MainMod.blocks);
		tabBlocks.add(toReturn);
		return toReturn;
	}
	
	private static RegistryObject<Block> registerHidden(String name, final Supplier<? extends Block> block) {
		RegistryObject<Block> toReturn = BLOCKS.register(name, block);
		registerBlockItemHidden(name, toReturn);
		return toReturn;
	}
	
	private static RegistryObject<Item> registerBlockItemHidden(String name, RegistryObject<Block> block) {
		RegistryObject<Item> i = ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
		ModItems.hidenItems.add(i);
		return i;
	}
	
	private static RegistryObject<Item> registerBlockItem(String name, RegistryObject<Block> block, CreativeModeTab tab) {
		return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
	}
	
	public static void register(IEventBus bus) {
		BLOCKS.register(bus);
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void registerBlockColors(final ColorHandlerEvent.Block event){
		event.getBlockColors().register(new ColorHandler.Block(), ModBlocks.LaserCatcher.get(), ModBlocks.Laser.get(), ModBlocks.AdvancedLaser.get());
	}
}
