package KOWI2003.LaserMod.config;

public class Config {

	static Config instance = new Config();
		
	@ConfigName("General Settings")
	public GeneralSettings generalSettings;
	
	@ConfigName("Laser Settings")
	public LaserSettings laserSettings;
	
	@ConfigName("Machine Settings")
	public MachineSettings machineSettings;
	
	@ConfigName("Upgrade Settings")
	public UpgradeSettings upgradeSettings;
	
	@ConfigName("Update Checker")
	public UpdateCheckerConfig updateChecker;
	
	Config() {
		generalSettings = new GeneralSettings();
		laserSettings = new LaserSettings();
		updateChecker = new UpdateCheckerConfig();
		machineSettings = new MachineSettings();
		upgradeSettings = new UpgradeSettings();
	}

	public static Config getInstance() {
		return ConfigHandler.instance;
	}

	public class GeneralSettings {
		@ConfigRange(min = 0.1f, max = Integer.MAX_VALUE)
		@ConfigDesc("The Charge a single piece of redstone dust gives to a laser tool [default: 15]")
		public float redstoneChargeValue = 15f;
		
		@ConfigRange(min = 0f, max = Integer.MAX_VALUE)
		@ConfigDesc("The damage a laser does by default wihout any upgrades [default: 3]")
		public int defaultLaserDamage = 3;
		
		@ConfigRange(min = 0.1f, max = Integer.MAX_VALUE)
		@ConfigDesc("The length a laser by default without any upgrades [default: 10]")
		public int defaultLaserDistance = 10;
	}
	
	public class UpdateCheckerConfig {
		@ConfigDesc("Whether the mod should check for updates [default: true]")
		public boolean useUpdateChecker;
		
		@ConfigDesc("The type of updates to check for (recommended or newest) [default: recommended]")
		public String updateCheckerType;
		
		private UpdateCheckerConfig() {
			useUpdateChecker = true;
			updateCheckerType = "recommended";
		}
	}

	public class LaserSettings {
		@ConfigDesc("Whether the focused laser mod has a hit marker on the block it hits [default: true]")
		public boolean hitmarkerFocusedLaser = true;

		@ConfigDesc("Whether the power laser mod has a hit marker on the block it hits [default: true]")
		public boolean hitmarkerPowerLaser = true;
	}

	public static class MachineSettings {
		@ConfigRange(min = 0.1f, max = Integer.MAX_VALUE)
		@ConfigDesc("The speed of the infuser [default: 1.0]")
		public float infuserSpeed = 1.0f;

		@ConfigRange(min = 0.1f, max = Integer.MAX_VALUE)
		@ConfigDesc("The speed of the precision assembler [default: 1.0]")
		public float precisionAssemblerSpeed = 1.0f;

		@ConfigRange(min = 0.1f, max = Integer.MAX_VALUE)
		@ConfigDesc("The speed of the infuser when charging tools [default: 1.0]")
		public float infuserToolChargingSpeed = 1.0f;
	}

	public static class UpgradeSettings {
		@ConfigRange(min = 0.1f, max = Integer.MAX_VALUE)
		@ConfigDesc("The speed upgrade multiplier [default: 2.0]")
		public float speedUpgradeMultiplier = 2.0f;

		@ConfigRange(min = 0.1f, max = Integer.MAX_VALUE)
		@ConfigDesc("The distance upgrade multiplier per tier, note: 1 is always added so distance = 1 + teir * multiplier [default: 1.0]")
		public float distanceUpgradeMultiplier = 1.0f;

		@ConfigRange(min = 0.1f, max = Integer.MAX_VALUE)
		@ConfigDesc("The damage upgrade multiplier per tier, each successive tier being the multiplier higher [default: 0.4]")
		public float damageUpgradeMultiplier = 0.4f;

		@ConfigRange(min = 0.1f, max = Integer.MAX_VALUE)
		@ConfigDesc("The capacity upgrade multiplier per tier [default: 1.0]")
		public float capacityUpgradeMultiplier = 1.0f;

		@ConfigRange(min = 0.1f, max = Integer.MAX_VALUE)
		@ConfigDesc("The push/pull upgrade speed multiplier by the laser block [default: 0.05]")
		public float pushPullUpgradeMultiplier = 0.05f;

		@ConfigRange(min = 0.1f, max = Integer.MAX_VALUE)
		@ConfigDesc("The push/pull upgrade knockback multiplier on the laser tools [default: 0.8]")
		public float pushPullUpgradeKnockback = 0.8f;

		@ConfigRange(min = 0.1f, max = Integer.MAX_VALUE)
		@ConfigDesc("The burn time of the fire upgrade in seconds [default: 5]")
		public int fireUpgradeBurnTime = 5;
	}
}
