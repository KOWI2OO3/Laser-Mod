package KOWI2003.LaserMod.handlers;

import KOWI2003.LaserMod.events.ConfigSyncEvent;
import KOWI2003.LaserMod.events.GeneralEvents;
import KOWI2003.LaserMod.events.LaserArmorEvents;
import KOWI2003.LaserMod.events.LaserBlockBreakEvent;
import KOWI2003.LaserMod.events.LaserMultiToolEvents;
import KOWI2003.LaserMod.events.LaserToolEvents;
import KOWI2003.LaserMod.events.WorldJoinEvent;
import net.minecraftforge.common.MinecraftForge;

public class EventHandler {

	public static void registerEvents() {
		MinecraftForge.EVENT_BUS.register(new WorldJoinEvent());
		MinecraftForge.EVENT_BUS.register(new LaserBlockBreakEvent());
		MinecraftForge.EVENT_BUS.register(new LaserArmorEvents());
		MinecraftForge.EVENT_BUS.register(new LaserMultiToolEvents());
		MinecraftForge.EVENT_BUS.register(new LaserToolEvents());
		MinecraftForge.EVENT_BUS.register(new ConfigSyncEvent());
		MinecraftForge.EVENT_BUS.register(new GeneralEvents());
	}
	
}
