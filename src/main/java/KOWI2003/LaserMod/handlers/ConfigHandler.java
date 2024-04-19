package KOWI2003.LaserMod.handlers;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.config.ModConfig;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ConfigHandler {

	public static ModConfig config;
	
	
}
