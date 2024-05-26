package KOWI2003.LaserMod.config;

public class ClientConfig {
 
    public boolean useSounds = true;
    public boolean useMultiToolRecoil = true;

    public static ClientConfig getInstance() {
        return ConfigHandler.client;
    }

}
