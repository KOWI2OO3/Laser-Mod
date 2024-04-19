package KOWI2003.LaserMod.utils;

import net.minecraft.client.Minecraft;

public class MultiVersionUtils {
	
	public static boolean is16() {
		return getMCVersionGroup().equals("1.16");
	}
	
	public static boolean is15() {
		return getMCVersionGroup().equals("1.15");
	}
	
	public static String getMCVersion() {
		return Minecraft.getInstance().getLaunchedVersion();
//		return MinecraftVersion.tryDetectVersion().getId();
	}
	
	public static String getMCVersionGroup() {
		String id = getMCVersion();
		String[] ids = id.replace('.', ';').split(";");
		if(ids.length >= 2)
			return ids[0] + "."+ ids[1];
		return id;
	}
}
