package KOWI2003.LaserMod.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public class PacketDeltaMovment {

	public Vec3 movement;
	
	public PacketDeltaMovment(FriendlyByteBuf buf) {
		movement = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
	}
	
	public PacketDeltaMovment(Vec3 movement) {
		this.movement = movement;
	}
	
	public void toBytes(FriendlyByteBuf buf) {
		buf.writeDouble(movement.x);
		buf.writeDouble(movement.y);
		buf.writeDouble(movement.z);
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
	    ctx.get().enqueueWork(() -> {
	        // Work that needs to be thread-safe (most work)
	    	
	        //ServerPlayer sender = ctx.get().getSender(); // the client that sent this packet
	    	
	        if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
	        	Minecraft.getInstance().player.setDeltaMovement(movement);
	        }else {
	        	ServerPlayer sender = ctx.get().getSender();
	        	sender.setDeltaMovement(movement);
	        	PacketHandler.sendToClient(new PacketDeltaMovment(movement), sender);
	        }
	    });
	    ctx.get().setPacketHandled(true);
	    //return true;
	}
	
}
