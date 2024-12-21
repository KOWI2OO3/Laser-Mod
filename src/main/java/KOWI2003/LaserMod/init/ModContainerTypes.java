package KOWI2003.LaserMod.init;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.container.ContainerInfuser;
import KOWI2003.LaserMod.container.ContainerItemStackProperty;
import KOWI2003.LaserMod.container.ContainerLaser;
import KOWI2003.LaserMod.container.ContainerModStation;
import KOWI2003.LaserMod.container.ContainerPrecisionAssembler;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModContainerTypes {

	public static final DeferredRegister<MenuType<?>> CONTAINER_TYPES = DeferredRegister
			.create(ForgeRegistries.MENU_TYPES, Reference.MODID);
	
	public static final RegistryObject<MenuType<ContainerLaser>> LASER_CONTAINER_TYPE = CONTAINER_TYPES
			.register("laser", () -> IForgeMenuType.create(ContainerLaser::new));
	public static final RegistryObject<MenuType<ContainerInfuser>> INFUSER_CONTAINER_TYPE = CONTAINER_TYPES
			.register("infuser", () -> IForgeMenuType.create(ContainerInfuser::new));
	public static final RegistryObject<MenuType<ContainerModStation>> MOD_STATION_CONTAINER_TYPE = CONTAINER_TYPES
			.register("mod_station", () -> IForgeMenuType.create(ContainerModStation::new));
	public static final RegistryObject<MenuType<ContainerPrecisionAssembler>> PRECISION_ASSEMBLER_TYPE = CONTAINER_TYPES
			.register("precision_assembler", () -> IForgeMenuType.create(ContainerPrecisionAssembler::new));
	
	public static final RegistryObject<MenuType<ContainerItemStackProperty>> ITEM_PROPERTY_TYPE = CONTAINER_TYPES
			.register("item_property", () -> IForgeMenuType.create(ContainerItemStackProperty::new));
}
