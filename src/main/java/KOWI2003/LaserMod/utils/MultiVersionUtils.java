package KOWI2003.LaserMod.utils;

import net.minecraft.client.Minecraft;

public class MultiVersionUtils {
	
	public static String getMCVersion() {
		return Minecraft.getInstance().getLaunchedVersion();
	}
	
	public static String getMCVersionGroup() {
		String id = getMCVersion();
		String[] ids = id.replace('.', ';').split(";");
		if(ids.length >= 2)
			return ids[0] + "."+ ids[1];
		return id;
	}
}
