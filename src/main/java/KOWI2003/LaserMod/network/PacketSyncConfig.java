package KOWI2003.LaserMod.network;

import java.util.function.Supplier;

import com.google.gson.Gson;

import KOWI2003.LaserMod.config.Config;
import KOWI2003.LaserMod.config.ConfigSerializer;
import KOWI2003.LaserMod.config.ModConfig;
import KOWI2003.LaserMod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public class PacketSyncConfig {

	public Config temporaryInstance;
	
	public PacketSyncConfig(FriendlyByteBuf buf) {
		String json = buf.readUtf();
		Gson gson = new Gson();
		temporaryInstance = gson.fromJson(json, Config.class);
	}
	
	public PacketSyncConfig() {
		temporaryInstance = ModConfig.GetConfig();
		Utils.getLogger().info("Syncing LaserMod Config Data From Server..");
	}
	
	public void toBytes(FriendlyByteBuf buf) {
		Gson gson = new Gson();
		String s = gson.toJson(temporaryInstance);
		buf.writeUtf(s);
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
	    ctx.get().enqueueWork(() -> {
	        // Work that needs to be thread-safe (most work)
	    	
	        //ServerPlayer sender = ctx.get().getSender(); // the client that sent this packet
	    	
	        if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
        		ConfigSerializer.GetInstance().setActiveConfig(temporaryInstance);
        		Utils.getLogger().info("Done Syncing LaserMod Config Data From Server!");
				System.out.println(Minecraft.getInstance().player.getName().getString() + ": " + new Gson().toJson(Config.GetInstance()));
	        }else {
	        	ServerPlayer sender = ctx.get().getSender();
	        	PacketHandler.sendToClient(new PacketSyncConfig(), sender);
	        }

	    });
	    ctx.get().setPacketHandled(true);
	    //return true;
	}
	
}
