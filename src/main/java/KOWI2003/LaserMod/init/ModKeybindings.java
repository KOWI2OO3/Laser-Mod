package KOWI2003.LaserMod.init;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.KeyMapping;

public class ModKeybindings {

	public static final List<KeyMapping> mappings = new ArrayList<>();
	
	public static final KeyMapping ArmorToggle = register(new KeyMapping("lasermod.armor_toggle", GLFW.GLFW_KEY_V, "key.categories.lasermod"));

	public static KeyMapping register(KeyMapping keyBinding)
	{
		mappings.add(keyBinding);
		return keyBinding;
	}
}
