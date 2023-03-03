package KOWI2003.LaserMod.config;

import com.google.gson.annotations.Expose;

public class Config {

	static Config instance = new Config();
	
		//The Charge a 1 piece of redstone dust gives to a laser tool
		public float redstoneChargeValue;
		
		public int defaultLaserDamage;
		
		@Expose(serialize = false, deserialize = false)
		public int defaultLaserDistance;
		
		public UpdateCheckerConfig updateChecker;
	
	private Config() {
		redstoneChargeValue = 15f;
		defaultLaserDamage = 3;
		defaultLaserDistance = 10;
		updateChecker = new UpdateCheckerConfig();
	}

	public static Config GetInstance() {
		if(instance == null)
			instance = new Config();
		return instance;
	}
	
	public class UpdateCheckerConfig {
		public boolean useUpdateChecker;
		public String updateCheckerType;
		
		private UpdateCheckerConfig() {
			useUpdateChecker = true;
			updateCheckerType = "recommended";
		}
	}
}
