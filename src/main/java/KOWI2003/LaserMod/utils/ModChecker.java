package KOWI2003.LaserMod.utils;

import net.minecraftforge.fml.ModList;

public class ModChecker {

	public static boolean isComputercraftLoaded = false;
	
	public static void check() {
		isComputercraftLoaded = ModList.get().isLoaded("computercraft");
	}
	
	public static boolean isComputercraftLoaded() {
		isComputercraftLoaded = ModList.get().isLoaded("computercraft");
		return isComputercraftLoaded;
	}
}
