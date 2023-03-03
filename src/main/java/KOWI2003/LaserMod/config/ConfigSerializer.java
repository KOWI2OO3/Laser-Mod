package KOWI2003.LaserMod.config;

import java.io.File;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import KOWI2003.LaserMod.utils.JsonUtils;

public class ConfigSerializer {

	public static final String configFileName = "KOWIsLaserMod.json";
	private static File configFile;
	
	static ConfigSerializer instance;
	
	ConfigSerializer() {
//		if(Utils.isClient)
//			configFile = new File(Minecraft.getInstance().gameDirectory + "/config/" + configFileName);
//		else
		configFile = new File("./config/" + configFileName);
		
		if(configFile.exists())
			readFromFile();
		else
			writeToFile();
		instance = this;
	}
	
	public void updateConfigToFile() {
		writeToFile();
	}
	
	public void updateConfigFromFile() {
		if(configFile.exists())
			readFromFile();
	}
	
	public static ConfigSerializer GetInstance() {
		if(instance == null)
			instance = new ConfigSerializer();
		return instance;
	}
	
	private JsonElement getJson()
	{
		Gson gson = new Gson();
		JsonElement json = gson.toJsonTree(Config.instance);
		return json;
	}
	
	private void readJson(JsonElement json) {
		Gson gson = new Gson();
		Config temp = gson.fromJson(json, Config.class);
		if(temp != null)
			Config.instance = temp;
	}
	
	private void readFromFile() {
		readJson(JsonUtils.getJsonObj(configFile));
	}
	
	private void writeToFile() {
		JsonUtils.writeToFile(getJson(), configFile);
	}
	
	public void setActiveConfig(Config config) {
		Config.instance = config;
	}
}
