package KOWI2003.LaserMod.proxy;

import KOWI2003.LaserMod.Reference;
import KOWI2003.LaserMod.network.PacketHandler;

public class CommonProxy {

	public void init() {
		
	}
	
	public void onSetupClient() {
		
	}
	
	public void onSetupCommon() {
		PacketHandler.registerMessages(Reference.MODID);
	}
	
}
